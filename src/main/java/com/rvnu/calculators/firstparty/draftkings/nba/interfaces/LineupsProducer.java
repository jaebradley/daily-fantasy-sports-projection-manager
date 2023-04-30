package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.contests.classic.Lineup;

import java.util.function.Consumer;

public interface LineupsProducer {
    void produce(NonEmptyLinkedHashSet<ContestPlayer> players, Consumer<Lineup> lineupConsumer);
}
