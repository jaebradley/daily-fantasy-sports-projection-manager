package com.rvnu.applications.nba.draftkings;

import com.google.common.collect.MinMaxPriorityQueue;
import com.rvnu.calculators.firstparty.draftkings.nba.implementation.LineupGenerator;
import com.rvnu.calculators.firstparty.draftkings.nba.implementation.LineupSalaryCalculator;
import com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.stokastic.record.nba.Deserializer;
import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.stokastic.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProjectionLineupGenerator {
    public enum RequiredLineupPosition {
        POINT_GUARD,
        SHOOTING_GUARD,
        SMALL_FORWARD,
        POWER_FORWARD,
        CENTER,
        GUARD,
        FORWARD,
        UTILITY,
    }

    @NotNull
    private static final NonNegativeDollars maximumInclusiveSalary;

    static {
        try {
            maximumInclusiveSalary = new NonNegativeDollars(new NaturalNumber(Money.of(BigDecimal.valueOf(50_000), "USD").getNumber().longValue()));
        } catch (NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    @NotNull
    private final AbstractDeserializer<Projection, Deserializer.Column, Deserializer.Error> awesomeoDeserializer;

    @NotNull
    private final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> draftKingsDeserializer;

    @NotNull
    private final LineupSalaryValidator<NonEmptyString, RequiredLineupPosition> salaryValidator;

    public ProjectionLineupGenerator(
            @NotNull final AbstractDeserializer<Projection, Deserializer.Column, Deserializer.Error> awesomeoDeserializer,
            @NotNull final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> draftKingsDeserializer,
            @NotNull final LineupSalaryValidator<NonEmptyString, RequiredLineupPosition> salaryValidator) {
        this.awesomeoDeserializer = awesomeoDeserializer;
        this.draftKingsDeserializer = draftKingsDeserializer;
        this.salaryValidator = salaryValidator;
    }

    public Set<Set<NonEmptyString>> generate(@NotNull final File awesomeoFile, @NotNull final File draftkingsFile) {
        final Map<com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error, PositiveInteger> results;
        final Map<PlayerId, ContestPlayer> playersById = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(draftkingsFile)) {
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

        final Map<NonEmptyString, Projection> awesomeoProjectionsByName = new HashMap<>();
        try (final FileInputStream fileInputStream = new FileInputStream(awesomeoFile)) {
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

        final Map<RequiredLineupPosition, Set<Projection>> projectionsByPosition = new HashMap<>();
        awesomeoProjectionsByName
                .entrySet()
                .stream()
                .filter(e -> e.getValue().fantasyPointsPerOneThousandDollars().compareTo(BigDecimal.valueOf(5)) > 0)
                .forEach(e -> e.getValue()
                        .eligiblePositions()
                        .forEach(
                                position -> {
                                    switch (position) {
                                        case POINT_GUARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.POINT_GUARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.POINT_GUARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.GUARD, projections);
                                            }
                                        }

                                        case SHOOTING_GUARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.SHOOTING_GUARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.SHOOTING_GUARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.GUARD, projections);
                                            }
                                        }

                                        case SMALL_FORWARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.SMALL_FORWARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.SMALL_FORWARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.FORWARD, projections);
                                            }
                                        }

                                        case POWER_FORWARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.POWER_FORWARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.POWER_FORWARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.FORWARD, projections);
                                            }
                                        }

                                        case CENTER -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.CENTER, new HashSet<>());
                                                projections.add(e.getValue());
                                                projectionsByPosition.put(RequiredLineupPosition.CENTER, projections);
                                            }
                                        }
                                    }

                                    final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.UTILITY, new HashSet<>());
                                    projections.add(e.getValue());
                                    projectionsByPosition.put(RequiredLineupPosition.UTILITY, projections);
                                }
                        ));
        final MinMaxPriorityQueue<Map.Entry<Set<NonEmptyString>, BigDecimal>> lineups = MinMaxPriorityQueue
                .<Map.Entry<Set<NonEmptyString>, BigDecimal>>orderedBy(Map.Entry.comparingByValue())
                .maximumSize(10)
                .create();
        final AtomicLong counter = new AtomicLong(0);
        final LineupGenerator lineupGenerator = new LineupGenerator(projectionsByPosition, salaryValidator);
        final Set<Set<NonEmptyString>> seenLineups = new HashSet<>();
        lineupGenerator.generateLineup(
                lineup -> {
                    if (seenLineups.add(lineup.getDetailsByIdentifier().keySet())) {
                        lineups.add(
                                Map.entry(
                                        lineup.getDetailsByIdentifier().keySet(),
                                        lineup
                                                .getDetailsByIdentifier()
                                                .values()
                                                .stream()
                                                .map(Lineup.PlayerDetails::name)
                                                .map(awesomeoProjectionsByName::get)
                                                .map(Projection::fantasyPoints)
                                                .reduce(BigDecimal.ZERO, BigDecimal::subtract))
                        );
                    }

                    final long count = counter.incrementAndGet();
                    if (0 == (count % 1_000_000L)) {
                        System.out.println("Count is " + count);
                    }
                }
        );

        return lineups.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static void main(@NotNull final String[] args) {
        final File awesomeoFile = new File(args[0]);
        if (!awesomeoFile.exists() || !awesomeoFile.isFile() || !awesomeoFile.canRead()) {
            throw new RuntimeException("Cannot access awesomeo file");
        }

        final File draftKingsFile = new File(args[1]);
        if (!draftKingsFile.exists() || !draftKingsFile.isFile() || !draftKingsFile.canRead()) {
            throw new RuntimeException("Cannot access DraftKings file");
        }

        final Set<Set<NonEmptyString>> generatedLineups = new ProjectionLineupGenerator(
                com.rvnu.data.thirdparty.csv.stokastic.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.draftkings.records.nba.Deserializer.getInstance(),
                new com.rvnu.calculators.firstparty.draftkings.nba.implementation.LineupSalaryValidator<>(
                        new LineupSalaryCalculator<>(),
                        maximumInclusiveSalary
                ))
                .generate(awesomeoFile, draftKingsFile);

        assert null != generatedLineups;
        assert 0 < generatedLineups.size();
    }
}
