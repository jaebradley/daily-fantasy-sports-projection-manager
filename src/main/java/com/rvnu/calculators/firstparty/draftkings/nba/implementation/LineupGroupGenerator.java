package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LineupGroupGenerator<ContestPlayerId, Position extends Enum<Position>> {
    public static class LineupGroup<ContestPlayerId, Position extends Enum<Position>> {
        @NotNull
        private final Map<ContestPlayerId, Pair<Position, Pair<BigDecimal, NonNegativeDollars>>> playerDetailsById;

        public LineupGroup(@NotNull Map<ContestPlayerId, Pair<Position, Pair<BigDecimal, NonNegativeDollars>>> playerDetailsById) {
            this.playerDetailsById = playerDetailsById;
        }

        public Map<ContestPlayerId, Pair<Position, Pair<BigDecimal, NonNegativeDollars>>> getPlayerDetailsById() {
            return playerDetailsById;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LineupGroup<?, ?> that = (LineupGroup<?, ?>) o;
            return playerDetailsById.keySet().equals(that.playerDetailsById.keySet());
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerDetailsById.keySet());
        }
    }

    @NotNull
    private final com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator<ContestPlayerId, Position> salaryValidator;

    @NotNull
    private final Map<Position, Map<ContestPlayerId, Pair<BigDecimal, NonNegativeDollars>>> playerDetailsByPosition;

    @NotNull
    private final Serializer<ContestPlayerId> playerIdSerializer;

    public LineupGroupGenerator(
            @NotNull final com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator<ContestPlayerId, Position> salaryValidator,
            @NotNull final Map<Position, Map<ContestPlayerId, Pair<BigDecimal, NonNegativeDollars>>> playerDetailsByPosition,
            @NotNull final Serializer<ContestPlayerId> playerIdSerializer) {
        this.salaryValidator = salaryValidator;
        this.playerDetailsByPosition = playerDetailsByPosition;
        this.playerIdSerializer = playerIdSerializer;
    }

    public Set<LineupGroup<ContestPlayerId, Position>> generateLineupGroups(@NotNull final List<Position> positions) {
        Set<LineupGroup<ContestPlayerId, Position>> groups = playerDetailsByPosition
                .get(positions.get(0))
                .entrySet()
                .stream()
                .map(e -> new LineupGroup<>(Map.of(e.getKey(), Pair.of(positions.get(0), e.getValue()))))
                .collect(Collectors.toSet());
        for (int positionIndex = 1; positionIndex < positions.size(); positionIndex += 1) {
            final Position position = positions.get(positionIndex);
            final Map<ContestPlayerId, Pair<BigDecimal, NonNegativeDollars>> playersDetails = playerDetailsByPosition.get(position);
            final Set<LineupGroup<ContestPlayerId, Position>> nextGroups = new HashSet<>();
            for (final LineupGroup<ContestPlayerId, Position> lineupGroup : groups) {
                for (final Map.Entry<ContestPlayerId, Pair<BigDecimal, NonNegativeDollars>> playerDetails : playersDetails.entrySet()) {
                    if (!lineupGroup.getPlayerDetailsById().containsKey(playerDetails.getKey())) {
                        nextGroups.add(
                                new LineupGroup<>(
                                        Stream.concat(
                                                        Stream.of(Map.entry(playerDetails.getKey(), Pair.of(position, playerDetails.getValue()))),
                                                        lineupGroup
                                                                .getPlayerDetailsById()
                                                                .entrySet()
                                                                .stream()
                                                )
                                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                                )
                        );
                    }
                }
            }
            groups = nextGroups;
        }

        return groups;
    }

    // TODO: @jbradley convert to nonempty list nonempty set
}
