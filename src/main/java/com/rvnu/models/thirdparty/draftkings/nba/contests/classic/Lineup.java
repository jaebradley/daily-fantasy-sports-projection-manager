package com.rvnu.models.thirdparty.draftkings.nba.contests.classic;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import org.javamoney.moneta.Money;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Lineup {

    public static class InvalidNumberOfPlayers extends Exception {
    }

    public static class PositionMissingPlayer extends Exception {
    }

    public static class ExceedsSalaryCap extends Exception {
    }

    public static final Set<Position> REQUIRED_POSITIONS = Arrays.stream(Position.values()).collect(Collectors.toSet());
    public static final NonNegativeDollars INCLUSIVE_SALARY_CAP;

    static {
        try {
            INCLUSIVE_SALARY_CAP = new NonNegativeDollars(new NaturalNumber(50_000));
        } catch (final NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }

    public static class Builder {
        @NotNull
        private final NonEmptyLinkedHashSet<ContestPlayer> players;

        public Builder(@NotNull final ContestPlayer player) throws ExceedsSalaryCap {
            try {
                this.players = NonEmptyLinkedHashSet.from(Set.of(player));
            } catch (final NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
                throw new RuntimeException("unexpected", e);
            }

            if (players.stream().map(ContestPlayer::salary).map(NonNegativeDollars::getValue).reduce(NonNegativeDollars.ZERO.getValue(), Money::add).isGreaterThan(INCLUSIVE_SALARY_CAP.getValue())) {
                throw new ExceedsSalaryCap();
            }
        }

        public boolean addPlayer(@NotNull final ContestPlayer player) {
            if (players.stream().map(ContestPlayer::salary).map(NonNegativeDollars::getValue).reduce(player.salary().getValue(), Money::add).isGreaterThan(INCLUSIVE_SALARY_CAP.getValue())) {
                return false;
            }

            return this.players.add(player);
        }

        public boolean removePlayer(@NotNull final ContestPlayer player) {
            return this.players.remove(player);
        }

        public Lineup build() throws InvalidNumberOfPlayers, PositionMissingPlayer, ExceedsSalaryCap {
            return new Lineup(this.players);
        }
    }

    @NotNull
    private final Set<ContestPlayer> players;

    public Lineup(@NotNull final NonEmptyLinkedHashSet<ContestPlayer> players) throws InvalidNumberOfPlayers, PositionMissingPlayer, ExceedsSalaryCap {
        if (REQUIRED_POSITIONS.size() != players.size()) {
            throw new InvalidNumberOfPlayers();
        }

        {
            final Map<Position, Set<ContestPlayer>> playersByPosition = players
                    .stream()
                    .flatMap(player -> player.eligiblePositions().stream().map(position -> Map.entry(position, player)))
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            HashMap::new,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

            if (
                    (!REQUIRED_POSITIONS.equals(playersByPosition.keySet())) ||
                            (playersByPosition
                                    .entrySet()
                                    .stream()
                                    .anyMatch(e -> playersByPosition
                                            .entrySet()
                                            .stream()
                                            .filter(v -> !e.getKey().equals(v.getKey()))
                                            .map(Map.Entry::getValue)
                                            .allMatch(p -> p.containsAll(e.getValue()))))) {
                throw new PositionMissingPlayer();
            }
        }

        if (
                players
                        .stream()
                        .map(ContestPlayer::salary)
                        .map(NonNegativeDollars::getValue)
                        .reduce(NonNegativeDollars.ZERO.getValue(), Money::add)
                        .isGreaterThan(INCLUSIVE_SALARY_CAP.getValue())) {
            throw new ExceedsSalaryCap();
        }


        this.players = players;
    }

    @NotNull
    public Set<ContestPlayer> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lineup lineup = (Lineup) o;
        return players.equals(lineup.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }
}
