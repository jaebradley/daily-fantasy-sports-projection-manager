package com.rvnu.models.thirdparty.draftkings.nba;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

public record ContestPlayer(
        NonEmptyString name,
        PlayerId id,
        NonEmptyLinkedHashSet<Position> eligiblePositions,
        NonNegativeDollars salary,
        Team team,
        GameInformation gameInformation
) {
}
