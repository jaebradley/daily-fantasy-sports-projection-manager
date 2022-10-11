package com.rvnu.models.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.util.Optional;
import java.util.Set;

public record ContestPlayer(
        ContestPlayerId id,
        Set<Position> positions,
        NonEmptyString firstName,
        NonEmptyString lastName,
        NonNegativeDollars salary,
        Team team,
        Team opponent,
        InjuryIndicator injuryIndicator,
        Optional<NonEmptyString> injuryDetails
) {
}
