package com.rvnu.models.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.util.Set;

public record ContestPlayer(
        NonEmptyString name,
        PlayerId id,
        Set<Position> eligiblePositions,
        NonNegativeDollars salary,
        Team team,
        GameInformation gameInformation
) {
}
