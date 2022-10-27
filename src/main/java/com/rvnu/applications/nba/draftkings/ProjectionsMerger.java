package com.rvnu.applications.nba.draftkings;

import com.rvnu.calculators.firstparty.draftkings.nba.interfaces.ProjectionsEvaluator;
import com.rvnu.calculators.thirdparty.stokastic.draftkings.nba.implementation.ContestPlayerNameToProjectionNameTranslator;
import com.rvnu.calculators.thirdparty.stokastic.draftkings.nba.implementation.ProjectionIdentifier;
import com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.printing.DraftKingsProjectionsPrinter;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer;
import com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ProjectionsMerger {
    public static record Projections(
            ContestPlayer contestPlayer,
            Optional<Projection> awesomeo,
            Optional<com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrinders,
            Optional<DraftKingsPlayerProjection> sabersim,
            Optional<com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>> dailyRoto
    ) {
    }

    @NotNull
    private final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> draftKingsDeserializer;

    @NotNull
    private final AbstractDeserializer<Projection, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Error> awesomeoDeserializer;

    @NotNull
    private final AbstractDeserializer<com.rvnu.models.thirdparty.rotogrinders.nba.Projection, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Error> rotogrindersDeserializer;

    @NotNull
    private final AbstractDeserializer<DraftKingsPlayerProjection, BaseDeserializer.Column, BaseDeserializer.Error> sabersimDeserializer;

    @NotNull
    private final AbstractDeserializer<com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> dailyRotoDeserializer;

    private ProjectionsMerger(
            @NotNull final AbstractDeserializer<ContestPlayer, Deserializer.Column, Deserializer.Error> draftKingsDeserializer,
            @NotNull final AbstractDeserializer<Projection, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Error> awesomeoDeserializer,
            @NotNull final AbstractDeserializer<com.rvnu.models.thirdparty.rotogrinders.nba.Projection, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Error> rotogrindersDeserializer,
            @NotNull final AbstractDeserializer<DraftKingsPlayerProjection, BaseDeserializer.Column, BaseDeserializer.Error> sabersimDeserializer,
            @NotNull final AbstractDeserializer<com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> dailyRotoDeserializer) {
        this.draftKingsDeserializer = draftKingsDeserializer;
        this.awesomeoDeserializer = awesomeoDeserializer;
        this.rotogrindersDeserializer = rotogrindersDeserializer;
        this.sabersimDeserializer = sabersimDeserializer;
        this.dailyRotoDeserializer = dailyRotoDeserializer;
    }

    public File merge(
            @NotNull final File draftKingsContestPlayersCsv,
            @NotNull final File awesomeoDraftKingsProjectionsCsv,
            @NotNull final File rotogrindersDraftKingsProjectionsCsv,
            @NotNull final File sabersimDraftKingsProjectionsCsv,
            @NotNull final File dailyRotoDraftKingsProjectionsCsv) {
        final Map<Deserializer.Error, PositiveInteger> results;
        final Map<PlayerId, ContestPlayer> playersById = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(draftKingsContestPlayersCsv)) {
            results = draftKingsDeserializer.deserialize(
                    fileInputStream,
                    contestPlayer -> {
                        if (null != playersById.put(contestPlayer.id(), contestPlayer)) {
                            throw new RuntimeException("duplicate player");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        if (playersById.isEmpty() || !results.isEmpty()) {
            throw new RuntimeException("errors when parsing DraftKings");
        }
        final Map<NonEmptyString, Projection> awesomeoProjectionsByName = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(awesomeoDraftKingsProjectionsCsv)) {
            awesomeoDeserializer.deserialize(
                    fileInputStream,
                    projection -> {
                        // Multiple names on different teams
                        if (null != awesomeoProjectionsByName.put(projection.name(), projection)) {
                            throw new RuntimeException("duplicate projection");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
        if (awesomeoProjectionsByName.isEmpty()) {
            throw new RuntimeException("errors when parsing Awesomeo");
        }

        final Map<com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Error, PositiveInteger> rotogrindersResult;
        final Map<NonEmptyString, com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrindersProjectsionById = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(rotogrindersDraftKingsProjectionsCsv)) {
            rotogrindersResult = rotogrindersDeserializer.deserialize(
                    fileInputStream,
                    projection -> {
                        if (null != rotogrindersProjectsionById.put(projection.name(), projection)) {
                            throw new RuntimeException("duplicate projection");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
        if (rotogrindersProjectsionById.isEmpty() || !rotogrindersResult.isEmpty()) {
            throw new RuntimeException("errors when parsing RotoGrinders");
        }

        final Map<PlayerId, DraftKingsPlayerProjection> sabersimProjectionsById = new HashMap<>();
        final Map<BaseDeserializer.Error, PositiveInteger> sabersimResults;
        try (final FileInputStream fileInputStream = new FileInputStream(sabersimDraftKingsProjectionsCsv)) {
            sabersimResults = sabersimDeserializer.deserialize(
                    fileInputStream,
                    draftKingsPlayerProjection -> {
                        if (null != sabersimProjectionsById.put(draftKingsPlayerProjection.getPlayerId(), draftKingsPlayerProjection)) {
                            throw new RuntimeException("duplicate projection");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
        if (sabersimProjectionsById.isEmpty() || !sabersimResults.isEmpty()) {
            throw new RuntimeException("errors when parsing Sabersim");
        }

        final Map<PlayerId, com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>> dailyRotoProjectionsByPlayerId = new HashMap<>();
        final Map<com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error, PositiveInteger> dailyRotoResults;
        try (final FileInputStream fileInputStream = new FileInputStream(dailyRotoDraftKingsProjectionsCsv)) {
            dailyRotoResults = dailyRotoDeserializer.deserialize(
                    fileInputStream,
                    draftKingsPlayerProjection -> draftKingsPlayerProjection.playerId().ifPresent(
                            playerId -> {
                                if (null != dailyRotoProjectionsByPlayerId.put(playerId, draftKingsPlayerProjection)) {
                                    throw new RuntimeException("duplicate projection");
                                }
                            }
                    )
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
        if (dailyRotoProjectionsByPlayerId.isEmpty() || !dailyRotoResults.isEmpty()) {
            throw new RuntimeException("errors when parsing DailyRoto");
        }

        final ProjectionsEvaluator evaluator = new com.rvnu.calculators.firstparty.draftkings.nba.implementation.ProjectionsEvaluator(
                new ProjectionIdentifier(awesomeoProjectionsByName, ContestPlayerNameToProjectionNameTranslator.getInstance()::apply),
                contestPlayerIdentifier -> Optional.ofNullable(rotogrindersProjectsionById.get(contestPlayerIdentifier)),
                contestPlayerIdentifier -> Optional.ofNullable(sabersimProjectionsById.get(contestPlayerIdentifier)),
                contestPlayerIdentifier -> Optional.ofNullable(dailyRotoProjectionsByPlayerId.get(contestPlayerIdentifier))
        );

        final Map<PlayerId, Projections> projectionsByPlayerId = new HashMap<>();
        for (final Map.Entry<PlayerId, ContestPlayer> e : playersById.entrySet()) {
            try {
                if (null != projectionsByPlayerId.put(
                        e.getKey(),
                        evaluator.evaluateProjections(e.getValue())
                )) {
                    throw new RuntimeException("duplicate players");
                }
            } catch (NoSuchElementException | ProjectionsEvaluator.UnableToEvaluateProjections ex) {
                throw new RuntimeException("unexpected", ex);
            }
        }

        final File output = new File("Combined.csv");
        try (final FileWriter fileWriter = new FileWriter(output)) {
            DraftKingsProjectionsPrinter.getInstance().print(fileWriter, projectionsByPlayerId.values().iterator());
        } catch (IOException e) {
            throw new RuntimeException("unexpected", e);
        }
        return output;
    }

    public static void main(@NotNull final String[] args) {
        if (5 != args.length) {
            throw new RuntimeException("Expected five file paths");
        }

        final File draftKingsFile = new File(args[0]);
        if (!draftKingsFile.exists() || !draftKingsFile.isFile() || !draftKingsFile.canRead()) {
            throw new RuntimeException("Cannot access DraftKings file");
        }
        final File awesomeoFile = new File(args[1]);
        if (!awesomeoFile.exists() || !awesomeoFile.isFile() || !awesomeoFile.canRead()) {
            throw new RuntimeException("Cannot access awesomeo file");
        }
        final File rotogrindersFile = new File(args[2]);
        if (!rotogrindersFile.exists() || !rotogrindersFile.isFile() || !rotogrindersFile.canRead()) {
            throw new RuntimeException("Cannot access rotogrinders file");
        }
        final File sabersimFile = new File(args[3]);
        if (!sabersimFile.exists() || !sabersimFile.isFile() || !sabersimFile.canRead()) {
            throw new RuntimeException("Cannot access sabersim file");
        }
        final File dailyRotoFile = new File(args[4]);
        if (!dailyRotoFile.exists() || !sabersimFile.isFile() || !sabersimFile.canRead()) {
            throw new RuntimeException("Cannot access dailyroto file");
        }

        final ProjectionsMerger projectionsMerger = new ProjectionsMerger(
                com.rvnu.data.thirdparty.csv.draftkings.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.awesomeo.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.rotogrinders.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.sabersim.records.nba.Deserializer.getDraftKingsDeserializer(),
                com.rvnu.data.thirdparty.csv.dailyroto.records.nba.Deserializer.getDraftKingsDeserializer());

        projectionsMerger.merge(draftKingsFile, awesomeoFile, rotogrindersFile, sabersimFile, dailyRotoFile);
    }
}
