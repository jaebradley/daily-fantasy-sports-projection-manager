package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.models.firstparty.collections.NonEmptyCollection;
import com.rvnu.models.firstparty.collections.NonEmptyHashMap;
import com.rvnu.models.firstparty.collections.NonEmptyList;
import com.rvnu.models.firstparty.collections.NonEmptySet;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class LineupGroupGenerator<PlayerIdentifier, Position extends Enum<Position>> {

    @NotNull
    private final Map<Position, NonEmptySet<PlayerIdentifier>> playerIdsByPosition;

    public LineupGroupGenerator(@NotNull final Map<Position, NonEmptySet<PlayerIdentifier>> playersByPosition) {
        this.playerIdsByPosition = playersByPosition;
    }

    // TODO: @jbradley convert to nonempty list nonempty set
    public Set<NonEmptyHashMap<PlayerIdentifier, Position>> generatePositionGroups(@NotNull final NonEmptyList<Position> positions) {
        Set<NonEmptyHashMap<PlayerIdentifier, Position>> currentPositionGroupPlayers = new HashSet<>();

        for (final Position currentPosition : positions.getValues()) {
            final Set<Set<PlayerIdentifier>> currentPositionGroupPlayerIds = new HashSet<>();

            if (currentPositionGroupPlayers.isEmpty()) {
                currentPositionGroupPlayers = Optional
                        .ofNullable(playerIdsByPosition.get(currentPosition))
                        .map(NonEmptySet::getValues)
                        .orElse(Collections.emptySet())
                        .stream()
                        .map(playerId -> {
                            try {
                                return new NonEmptyHashMap<>(new HashMap<>(Map.of(playerId, currentPosition)));
                            } catch (NonEmptyCollection.ValuesCannotBeEmpty e) {
                                throw new RuntimeException("unexpected", e);
                            }
                        })
                        .collect(Collectors.toSet());
                currentPositionGroupPlayers.stream().map(NonEmptyHashMap::getValues).map(Map::keySet).forEach(currentPositionGroupPlayerIds::add);
            } else {
                final Set<NonEmptyHashMap<PlayerIdentifier, Position>> nextPositionGroupPlayers = new HashSet<>();
                final Set<PlayerIdentifier> currentPositionPlayerIdentifiers = Optional
                        .ofNullable(playerIdsByPosition.get(currentPosition))
                        .map(NonEmptySet::getValues)
                        .orElse(Collections.emptySet());
                for (final PlayerIdentifier playerId : currentPositionPlayerIdentifiers) {
                    for (final NonEmptyHashMap<PlayerIdentifier, Position> currentPositionGroup : currentPositionGroupPlayers) {
                        final Map<PlayerIdentifier, Position> nextPositionGroup = new HashMap<>(currentPositionGroup.getValues());
                        if (null == nextPositionGroup.put(playerId, currentPosition) && currentPositionGroupPlayerIds.add(nextPositionGroup.keySet())) {
                            try {
                                nextPositionGroupPlayers.add(new NonEmptyHashMap<>(nextPositionGroup));
                            } catch (NonEmptyCollection.ValuesCannotBeEmpty e) {
                                throw new RuntimeException("unexpected", e);
                            }
                        }
                    }
                }

                currentPositionGroupPlayers = nextPositionGroupPlayers;
            }
        }

        return currentPositionGroupPlayers;
    }
}
