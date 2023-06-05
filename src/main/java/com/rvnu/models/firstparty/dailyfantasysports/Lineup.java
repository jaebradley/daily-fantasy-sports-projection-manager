package com.rvnu.models.firstparty.dailyfantasysports;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lineup<PlayerIdentifier, Position> {

    public static class PlayerAlreadyExistsForPosition extends Exception {
    }

    public static class PlayerAlreadyExists extends Exception {
    }

    public static record PlayerDetails<Position>(
            @NotNull NonEmptyString name,
            @NotNull NonNegativeDollars salary,
            @NotNull Position position
    ) {
    }

    public static <PlayerIdentifier, Position> Lineup<PlayerIdentifier, Position> withPlayer(
            @NotNull final PlayerIdentifier playerIdentifier,
            @NotNull final Position playerPosition,
            @NotNull final NonEmptyString name,
            @NotNull final NonNegativeDollars salary) {
        return new Lineup<>(Map.of(playerIdentifier, new PlayerDetails<>(name, salary, playerPosition)), Map.of(playerPosition, playerIdentifier));
    }

    @NotNull
    private final Map<PlayerIdentifier, PlayerDetails<Position>> detailsByIdentifier;

    @NotNull
    private final Map<Position, PlayerIdentifier> identifiersByPosition;

    private Lineup(
            @NotNull final Map<PlayerIdentifier, PlayerDetails<Position>> detailsByIdentifier,
            @NotNull final Map<Position, PlayerIdentifier> identifiersByPosition
    ) {
        this.detailsByIdentifier = detailsByIdentifier;
        this.identifiersByPosition = identifiersByPosition;
    }

    @NotNull
    public Map<PlayerIdentifier, PlayerDetails<Position>> getDetailsByIdentifier() {
        return detailsByIdentifier;
    }

    public Lineup<PlayerIdentifier, Position> addPlayer(
            @NotNull final PlayerIdentifier playerIdentifier,
            @NotNull final Position playerPosition,
            @NotNull final NonEmptyString name,
            @NotNull final NonNegativeDollars salary
    ) throws PlayerAlreadyExistsForPosition, PlayerAlreadyExists {
        if (detailsByIdentifier.containsKey(playerIdentifier)) {
            throw new PlayerAlreadyExists();
        }

        if (identifiersByPosition.containsKey(playerPosition)) {
            throw new PlayerAlreadyExistsForPosition();
        }

        return new Lineup<>(
                Stream.concat(
                        Stream.of(Map.entry(playerIdentifier, new PlayerDetails<>(name, salary, playerPosition))),
                        detailsByIdentifier.entrySet().stream()
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                Stream.concat(
                        Stream.of(Map.entry(playerPosition, playerIdentifier)),
                        identifiersByPosition.entrySet().stream()
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public Lineup<PlayerIdentifier, Position> removePlayer(@NotNull final PlayerIdentifier playerIdentifier) {
        return new Lineup<>(
                detailsByIdentifier
                        .entrySet()
                        .stream()
                        .filter(v -> !v.getKey().equals(playerIdentifier))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                identifiersByPosition
                        .entrySet()
                        .stream()
                        .filter(v -> !v.getValue().equals(playerIdentifier))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lineup<?, ?> lineup = (Lineup<?, ?>) o;
        return detailsByIdentifier.keySet().equals(lineup.detailsByIdentifier.keySet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(detailsByIdentifier.keySet());
    }
}
