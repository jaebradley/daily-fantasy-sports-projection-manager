package com.rvnu.applications.nba.draftkings;

import com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupsProducer;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer;
import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.contests.classic.Lineup;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private static final class PotentialLineup {
        public static PotentialLineup withPlayer(
                @NotNull final NonEmptyString name,
                @NotNull final BigDecimal fantasyPoints,
                @NotNull final NonNegativeDollars salary) {
            return new PotentialLineup(
                    Set.of(name),
                    fantasyPoints,
                    salary
            );
        }

        @NotNull
        private final Set<NonEmptyString> playerNames;
        @NotNull
        private final BigDecimal totalPoints;
        @NotNull
        private final NonNegativeDollars totalSalary;

        private PotentialLineup(
                @NotNull final Set<NonEmptyString> playerNames,
                @NotNull final BigDecimal totalPoints,
                @NotNull final NonNegativeDollars totalSalary
        ) {
            this.playerNames = playerNames;
            this.totalPoints = totalPoints;
            this.totalSalary = totalSalary;
        }

        public Set<NonEmptyString> getPlayerNames() {
            return playerNames;
        }

        public BigDecimal getTotalPoints() {
            return totalPoints;
        }

        public NonNegativeDollars getTotalSalary() {
            return totalSalary;
        }

        public PotentialLineup addPlayer(
                @NotNull final NonEmptyString name,
                @NotNull final BigDecimal fantasyPoints,
                @NotNull final NonNegativeDollars salary
        ) {
            try {
                return new PotentialLineup(
                        Stream.concat(
                                Stream.of(name),
                                getPlayerNames().stream()
                        ).collect(Collectors.toSet()),
                        getTotalPoints().add(fantasyPoints),
                        new NonNegativeDollars(new NaturalNumber(getTotalSalary().getValue().add(salary.getValue()).getNumber().longValue())));
            } catch (NaturalNumber.ValueMustNotBeNegative e) {
                throw new RuntimeException("unexpected", e);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PotentialLineup that = (PotentialLineup) o;
            return playerNames.equals(that.playerNames) && totalPoints.equals(that.totalPoints) && totalSalary.equals(that.totalSalary);
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerNames, totalPoints, totalSalary);
        }

        @Override
        public String toString() {
            return "PotentialLineup{" +
                    "playerNames=" + playerNames +
                    ", totalPoints=" + totalPoints +
                    ", totalSalary=" + totalSalary +
                    '}';
        }
    }

    private static final class PotentialLineupSerializer implements Serializer<PotentialLineup> {
        @NotNull
        private static final PotentialLineupSerializer INSTANCE = new PotentialLineupSerializer();

        @Override
        public String serialize(@NotNull final PotentialLineup value) {
            return String.join("|",
                    value.playerNames.stream().map(NonEmptyString::getValue).collect(Collectors.joining(",")),
                    value.totalPoints.toPlainString(),
                    value.totalSalary.getValue().toString()
            );
        }

        @NotNull
        public static PotentialLineupSerializer getInstance() {
            return INSTANCE;
        }
    }

    @NotNull
    private final AbstractDeserializer<Projection, Deserializer.Column, Deserializer.Error> awesomeoDeserializer;

    @NotNull
    private final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> draftKingsDeserializer;

    @NotNull
    private final LineupsProducer lineupsProducer;

    public ProjectionLineupGenerator(
            @NotNull final AbstractDeserializer<Projection, Deserializer.Column, Deserializer.Error> awesomeoDeserializer,
            @NotNull final AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error> draftKingsDeserializer,
            @NotNull final LineupsProducer lineupsProducer) {
        this.awesomeoDeserializer = awesomeoDeserializer;
        this.draftKingsDeserializer = draftKingsDeserializer;
        this.lineupsProducer = lineupsProducer;
    }

    public Set<PotentialLineup> generate(@NotNull final File awesomeoFile, @NotNull final File draftkingsFile) {
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

        final Set<Lineup> potentialLineups = new HashSet<>();
        try {
            lineupsProducer.produce(NonEmptyLinkedHashSet.from(playersById.values().stream().toList()), potentialLineups::add);
        } catch (final NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
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
        final NonNegativeDollars maximumInclusiveSalary;
        try {
            maximumInclusiveSalary = new NonNegativeDollars(new NaturalNumber(Money.of(BigDecimal.valueOf(50_000), "USD").getNumber().longValue()));
        } catch (NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
        final Set<PotentialLineup> lineups = new HashSet<>();
        for (final Projection pointGuardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.POINT_GUARD, Collections.emptySet())) {
            PotentialLineup lineup = PotentialLineup.withPlayer(pointGuardProjection.name(), pointGuardProjection.fantasyPoints(), pointGuardProjection.salary());
            for (final Projection shootingGuardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.SHOOTING_GUARD, Collections.emptySet())) {
                if (!lineup.playerNames.contains(shootingGuardProjection.name())) {
                    lineup = lineup.addPlayer(shootingGuardProjection.name(), shootingGuardProjection.fantasyPoints(), shootingGuardProjection.salary());
                    for (final Projection smallForwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.SMALL_FORWARD, Collections.emptySet())) {
                        if (!lineup.playerNames.contains(smallForwardProjection.name())) {
                            lineup = lineup.addPlayer(smallForwardProjection.name(), smallForwardProjection.fantasyPoints(), smallForwardProjection.salary());
                            for (final Projection powerForwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.POWER_FORWARD, Collections.emptySet())) {
                                if (!lineup.playerNames.contains(powerForwardProjection.name())) {
                                    lineup = lineup.addPlayer(powerForwardProjection.name(), powerForwardProjection.fantasyPoints(), powerForwardProjection.salary());
                                    for (final Projection centerProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.CENTER, Collections.emptySet())) {
                                        if (!lineup.playerNames.contains(centerProjection.name())) {
                                            lineup = lineup.addPlayer(centerProjection.name(), centerProjection.fantasyPoints(), centerProjection.salary());
                                            for (final Projection guardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.GUARD, Collections.emptySet())) {
                                                if (!lineup.playerNames.contains(guardProjection.name())) {
                                                    lineup = lineup.addPlayer(guardProjection.name(), guardProjection.fantasyPoints(), guardProjection.salary());
                                                    for (final Projection forwardProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.FORWARD, Collections.emptySet())) {
                                                        if (!lineup.playerNames.contains(forwardProjection.name())) {
                                                            lineup = lineup.addPlayer(forwardProjection.name(), forwardProjection.fantasyPoints(), forwardProjection.salary());
                                                            for (final Projection utilityProjection : projectionsByPosition.getOrDefault(RequiredLineupPosition.UTILITY, Collections.emptySet())) {
                                                                if (!lineup.playerNames.contains(utilityProjection.name())) {
                                                                    lineup = lineup.addPlayer(utilityProjection.name(), utilityProjection.fantasyPoints(), utilityProjection.salary());
                                                                    if (lineup.totalSalary.getValue().isLessThanOrEqualTo(maximumInclusiveSalary.getValue())) {
                                                                        lineups.add(lineup);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        final PriorityQueue<PotentialLineup> orderedLineups = new PriorityQueue<>(lineups.size(), Comparator.comparing(potentialLineup -> -potentialLineup.totalPoints.longValue()));
        orderedLineups.addAll(lineups);
        final Set<PotentialLineup> result = new HashSet<>();
        for (int i = 0; i < 10; i += 1) {
            result.add(orderedLineups.remove());
        }
        return result;
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

        final Set<PotentialLineup> generatedLineups = new ProjectionLineupGenerator(
                com.rvnu.data.thirdparty.csv.awesomeo.records.nba.Deserializer.getInstance(),
                com.rvnu.data.thirdparty.csv.draftkings.records.nba.Deserializer.getInstance(),
                new com.rvnu.calculators.firstparty.draftkings.nba.implementation.LineupsProducer())
                .generate(awesomeoFile, draftKingsFile);

        generatedLineups.forEach(lineup -> System.out.println(PotentialLineupSerializer.getInstance().serialize(lineup)));
    }
}
