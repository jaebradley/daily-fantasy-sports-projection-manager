package com.rvnu.applications.nba.draftkings;

import com.rvnu.data.firstparty.csv.records.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer;
import com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProjectionsMerger {
    private static record Projections(
            ContestPlayer contestPlayer,
            Projection awesomeo,
            com.rvnu.models.thirdparty.rotogrinders.nba.Projection rotogrinders,
            DraftKingsPlayerProjection sabersim
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

    private ProjectionsMerger(
            @NotNull final AbstractDeserializer<ContestPlayer, Deserializer.Column, Deserializer.Error> draftKingsDeserializer,
            @NotNull final AbstractDeserializer<Projection, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Error> awesomeoDeserializer,
            @NotNull final AbstractDeserializer<com.rvnu.models.thirdparty.rotogrinders.nba.Projection, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Error> rotogrindersDeserializer,
            @NotNull final AbstractDeserializer<DraftKingsPlayerProjection, BaseDeserializer.Column, BaseDeserializer.Error> sabersimDeserializer
    ) {
        this.draftKingsDeserializer = draftKingsDeserializer;
        this.awesomeoDeserializer = awesomeoDeserializer;
        this.rotogrindersDeserializer = rotogrindersDeserializer;
        this.sabersimDeserializer = sabersimDeserializer;
    }

    public File merge(
            @NotNull final File draftKingsContestPlayersCsv,
            @NotNull final File awesomeoDraftKingsProjectionsCsv,
            @NotNull final File rotogrindersDraftKingsProjectionsCsv,
            @NotNull final File sabersimDraftKingsProjectionsCsv
    ) {
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
        } catch (IOException | com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        if (!results.isEmpty()) {
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
        } catch (IOException | com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
        final Map<PositiveInteger, com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrindersProjectsionById = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(rotogrindersDraftKingsProjectionsCsv)) {
            rotogrindersDeserializer.deserialize(
                    fileInputStream,
                    projection -> {
                        if (null != rotogrindersProjectsionById.put(projection.partnerId(), projection)) {
                            throw new RuntimeException("duplicate projection");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        final Map<PlayerId, DraftKingsPlayerProjection> sabersimProjectionsById = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(sabersimDraftKingsProjectionsCsv)) {
            sabersimDeserializer.deserialize(
                    fileInputStream,
                    draftKingsPlayerProjection -> {
                        if (null != sabersimProjectionsById.put(draftKingsPlayerProjection.getPlayerId(), draftKingsPlayerProjection)) {
                            throw new RuntimeException("duplicate projection");
                        }
                    }
            );
        } catch (IOException | com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        final Map<PlayerId, Projections> projectionsByPlayerId = new HashMap<>();
        for (final Map.Entry<PlayerId, ContestPlayer> e : playersById.entrySet()) {
            try {
                if (null != projectionsByPlayerId.put(
                        e.getKey(),
                        new Projections(
                                e.getValue(),
                                Optional.ofNullable(awesomeoProjectionsByName.get(e.getValue().name())).orElseThrow(),
                                Optional.ofNullable(rotogrindersProjectsionById.get(new PlayerId(e.getKey().getValue()))).orElseThrow(),
                                Optional.ofNullable(sabersimProjectionsById.get(e.getKey())).orElseThrow()
                        )
                )) {
                    throw new RuntimeException("duplicate players");
                }
            } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative ex) {
                throw new RuntimeException("unexpected", ex);
            }
        }

        return new File("foo");
    }

    public static void main(@NotNull final String[] args) {
        if (4 != args.length) {
            throw new RuntimeException("Expected four file paths");
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
        final ProjectionsMerger projectionsMerger = new ProjectionsMerger(
                com.rvnu.data.thirdparty.csv.draftkings.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.awesomeo.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.rotogrinders.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.sabersim.records.nba.Deserializer.getDraftKingsDeserializer()
        );

        projectionsMerger.merge(draftKingsFile, awesomeoFile, rotogrindersFile, sabersimFile);
    }
}
