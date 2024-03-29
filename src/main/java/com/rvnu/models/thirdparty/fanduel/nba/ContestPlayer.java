package com.rvnu.models.thirdparty.fanduel.nba;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.util.Optional;

public record ContestPlayer(
        ContestPlayerId id,
        NonEmptyLinkedHashSet<Position> positions,
        NonEmptyString firstName,
        NonEmptyString lastName,
        NonNegativeDollars salary,
        Team team,
        Team opponent,
        Optional<InjuryIndicator> injuryIndicator,
        Optional<NonEmptyString> injuryDetails
) {
}
