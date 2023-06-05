package com.rvnu.applications.nba.draftkings;

import com.google.common.collect.MinMaxPriorityQueue;
import com.rvnu.calculators.firstparty.draftkings.nba.implementation.LineupSalaryCalculator;
import com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer;
import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
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
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProjectionLineupGenerator {
    enum RequiredLineupPosition {
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

    public Set<Lineup<NonEmptyString, RequiredLineupPosition>> generate(@NotNull final File awesomeoFile, @NotNull final File draftkingsFile) {
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
                .forEach((name, projection) -> projection
                        .eligiblePositions()
                        .forEach(
                                position -> {
                                    switch (position) {
                                        case POINT_GUARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.POINT_GUARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.POINT_GUARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.GUARD, projections);
                                            }
                                        }

                                        case SHOOTING_GUARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.SHOOTING_GUARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.SHOOTING_GUARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.GUARD, projections);
                                            }
                                        }

                                        case SMALL_FORWARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.SMALL_FORWARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.SMALL_FORWARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.FORWARD, projections);
                                            }
                                        }

                                        case POWER_FORWARD -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.POWER_FORWARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.POWER_FORWARD, projections);
                                            }
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.FORWARD, projections);
                                            }
                                        }

                                        case CENTER -> {
                                            {
                                                final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.CENTER, new HashSet<>());
                                                projections.add(projection);
                                                projectionsByPosition.put(RequiredLineupPosition.CENTER, projections);
                                            }
                                        }
                                    }

                                    final Set<Projection> projections = projectionsByPosition.getOrDefault(RequiredLineupPosition.UTILITY, new HashSet<>());
                                    projections.add(projection);
                                    projectionsByPosition.put(RequiredLineupPosition.UTILITY, projections);
                                }
                        ));
        final MinMaxPriorityQueue<Map.Entry<Lineup<NonEmptyString, RequiredLineupPosition>, BigDecimal>> lineups = MinMaxPriorityQueue
                .<Map.Entry<Lineup<NonEmptyString, RequiredLineupPosition>, BigDecimal>>orderedBy(Map.Entry.comparingByValue())
                .maximumSize(10)
                .create();
        final Set<Set<NonEmptyString>> seenLineups = new HashSet<>();
        final AtomicLong counter = new AtomicLong(0);
        for (final Projection pointGuardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.POINT_GUARD, Collections.emptySet())) {
            Lineup<NonEmptyString, RequiredLineupPosition> lineup = Lineup.withPlayer(pointGuardProjection.name(), RequiredLineupPosition.POINT_GUARD, pointGuardProjection.name(), pointGuardProjection.salary());
            if (salaryValidator.lineupHasValidSalary(lineup)) {
                for (final Projection shootingGuardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.SHOOTING_GUARD, Collections.emptySet())) {
                    try {
                        lineup = lineup.addPlayer(shootingGuardProjection.name(), RequiredLineupPosition.SHOOTING_GUARD, shootingGuardProjection.name(), shootingGuardProjection.salary());
                    } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                        throw new RuntimeException("unexpected", e);
                    } catch (Lineup.PlayerAlreadyExists e) {
                        continue;
                    }
                    if (salaryValidator.lineupHasValidSalary(lineup)) {
                        for (final Projection smallForwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.SMALL_FORWARD, Collections.emptySet())) {
                            try {
                                lineup = lineup.addPlayer(smallForwardProjection.name(), RequiredLineupPosition.SMALL_FORWARD, smallForwardProjection.name(), smallForwardProjection.salary());
                            } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                throw new RuntimeException("unexpected", e);
                            } catch (Lineup.PlayerAlreadyExists e) {
                                continue;
                            }
                            if (salaryValidator.lineupHasValidSalary(lineup)) {
                                for (final Projection powerForwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.POWER_FORWARD, Collections.emptySet())) {
                                    try {
                                        lineup = lineup.addPlayer(powerForwardProjection.name(), RequiredLineupPosition.POWER_FORWARD, powerForwardProjection.name(), powerForwardProjection.salary());
                                    } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                        throw new RuntimeException("unexpected", e);
                                    } catch (Lineup.PlayerAlreadyExists e) {
                                        continue;
                                    }
                                    if (salaryValidator.lineupHasValidSalary(lineup)) {
                                        for (final Projection centerProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.CENTER, Collections.emptySet())) {
                                            try {
                                                lineup = lineup.addPlayer(centerProjection.name(), RequiredLineupPosition.CENTER, centerProjection.name(), centerProjection.salary());
                                            } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                                throw new RuntimeException("unexpected", e);
                                            } catch (Lineup.PlayerAlreadyExists e) {
                                                continue;
                                            }
                                            if (salaryValidator.lineupHasValidSalary(lineup)) {
                                                for (final Projection guardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, Collections.emptySet())) {
                                                    try {
                                                        lineup = lineup.addPlayer(guardProjection.name(), RequiredLineupPosition.GUARD, guardProjection.name(), guardProjection.salary());
                                                    } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                                        throw new RuntimeException("unexpected", e);
                                                    } catch (Lineup.PlayerAlreadyExists e) {
                                                        continue;
                                                    }
                                                    if (salaryValidator.lineupHasValidSalary(lineup)) {
                                                        for (final Projection forwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, Collections.emptySet())) {
                                                            try {
                                                                lineup = lineup.addPlayer(forwardProjection.name(), RequiredLineupPosition.FORWARD, forwardProjection.name(), forwardProjection.salary());
                                                            } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                                                throw new RuntimeException("unexpected", e);
                                                            } catch (Lineup.PlayerAlreadyExists e) {
                                                                continue;
                                                            }
                                                            if (salaryValidator.lineupHasValidSalary(lineup)) {
                                                                for (final Projection utilityProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.UTILITY, Collections.emptySet())) {
                                                                    try {
                                                                        lineup = lineup.addPlayer(utilityProjection.name(), RequiredLineupPosition.UTILITY, utilityProjection.name(), utilityProjection.salary());
                                                                        if (salaryValidator.lineupHasValidSalary(lineup) && seenLineups.add(lineup.getDetailsByIdentifier().keySet())) {
                                                                            final BigDecimal points = lineup
                                                                                    .getDetailsByIdentifier()
                                                                                    .keySet()
                                                                                    .stream()
                                                                                    .map(playerId -> Optional.ofNullable(awesomeoProjectionsByName.get(playerId)).orElseThrow())
                                                                                    .map(Projection::fantasyPoints)
                                                                                    .reduce(BigDecimal.ZERO, BigDecimal::subtract);
                                                                            lineups.add(Map.entry(lineup, points));
                                                                            final long count = counter.incrementAndGet();
                                                                            if (0 == (count % 1_000_000L)) {
                                                                                System.out.println("Lineups collected: " + count);
                                                                            }
                                                                        }
                                                                        lineup = lineup.removePlayer(utilityProjection.name());
                                                                    } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                                                                        throw new RuntimeException("unexpected", e);
                                                                    } catch (Lineup.PlayerAlreadyExists ignored) {
                                                                    }
                                                                }
                                                            }
                                                            lineup = lineup.removePlayer(forwardProjection.name());
                                                        }
                                                    }
                                                    lineup = lineup.removePlayer(guardProjection.name());
                                                }
                                            }
                                            lineup = lineup.removePlayer(centerProjection.name());
                                        }
                                    }
                                    lineup = lineup.removePlayer(powerForwardProjection.name());
                                }
                            }
                            lineup = lineup.removePlayer(smallForwardProjection.name());
                        }
                    }
                    lineup = lineup.removePlayer(shootingGuardProjection.name());
                }
            }
            lineup = lineup.removePlayer(pointGuardProjection.name());
        }

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

        final Set<Lineup<NonEmptyString, RequiredLineupPosition>> generatedLineups = new ProjectionLineupGenerator(
                com.rvnu.data.thirdparty.csv.awesomeo.records.nba.Deserializer.getInstance(),
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
