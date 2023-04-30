package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.draftkings.nba.contests.classic.Lineup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class LineupsProducer implements com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupsProducer {
    @Override
    public void produce(final NonEmptyLinkedHashSet<ContestPlayer> players, final Consumer<Lineup> lineupConsumer) {
        final Map<Position, Set<ContestPlayer>> playersByPosition = Lineup
                .REQUIRED_POSITIONS
                .stream()
                .flatMap(position -> players.stream().filter(player -> player.eligiblePositions().contains(position)).map(player -> Map.entry(position, player)))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        HashMap::new,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ));
        // for each position
        // pick one player if not in current player lineup
        // add player to current player lineup
        // if player cannot be added, skip player
        // after evaluating position remove player if it was added

        final AtomicInteger count = new AtomicInteger(0);
        for (final ContestPlayer p1 : playersByPosition.getOrDefault(Position.POINT_GUARD, Collections.emptySet())) {
            final Lineup.Builder builder;
            try {
                builder = new Lineup.Builder(p1);
                for (final ContestPlayer p2 : playersByPosition.getOrDefault(Position.SHOOTING_GUARD, Collections.emptySet())) {
                    if (builder.addPlayer(p2)) {
                        for (final ContestPlayer p3 : playersByPosition.getOrDefault(Position.SMALL_FORWARD, Collections.emptySet())) {
                            if (builder.addPlayer(p3)) {
                                for (final ContestPlayer p4 : playersByPosition.getOrDefault(Position.POWER_FORWARD, Collections.emptySet())) {
                                    if (builder.addPlayer(p4)) {
                                        for (final ContestPlayer p5 : playersByPosition.getOrDefault(Position.CENTER, Collections.emptySet())) {
                                            if (builder.addPlayer(p5)) {
                                                for (final ContestPlayer p6 : playersByPosition.getOrDefault(Position.GUARD, Collections.emptySet())) {
                                                    if (builder.addPlayer(p6)) {
                                                        for (final ContestPlayer p7 : playersByPosition.getOrDefault(Position.FORWARD, Collections.emptySet())) {
                                                            if (builder.addPlayer(p7)) {
                                                                for (final ContestPlayer p8 : playersByPosition.getOrDefault(Position.UTILITY, Collections.emptySet())) {
                                                                    if (builder.addPlayer(p8)) {
                                                                        try {
                                                                            lineupConsumer.accept(builder.build());
                                                                            if (0 == (count.getAndIncrement() % 1_000_000)) {
                                                                                System.out.printf("Counted %s times%n", count.get());
                                                                            }
                                                                        } catch (IllegalArgumentException | Lineup.InvalidNumberOfPlayers | Lineup.PositionMissingPlayer | Lineup.ExceedsSalaryCap e) {
                                                                            // ignore;
                                                                        }
                                                                    builder.removePlayer(p8);
                                                                    }
                                                                }
                                                            builder.removePlayer(p7);
                                                            }
                                                        }
                                                    builder.removePlayer(p6);
                                                    }
                                                }
                                            builder.removePlayer(p5);
                                            }
                                        }
                                    builder.removePlayer(p4);
                                    }
                                }
                            builder.removePlayer(p3);
                            }
                        }
                    builder.removePlayer(p2);
                    }
                }
                builder.removePlayer(p1);
            } catch (Lineup.ExceedsSalaryCap e) {
                break;
            }
        }
    }
}
