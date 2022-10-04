package com.rvnu.models.thirdparty.awesomeo.nba;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Position;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Set;

public record Projection(
        @NotNull NonEmptyString name,
        @NotNull BigDecimal fantasyPoints,
        @NotNull Set<Position> eligiblePositions,
        @NotNull Team team,
        @NotNull Team opposingTeam,
        @NotNull NonNegativeDecimal minutes,
        @NotNull NonNegativeDollars salary,
        @NotNull BigDecimal fantasyPointsPerOneThousandDollars,
        @NotNull BigDecimal value
) {
}
