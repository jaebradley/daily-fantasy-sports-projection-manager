package com.rvnu.models.thirdparty.stokastic.mlb;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public record Projection(
        @NotNull NonEmptyString name,
        @NotNull BigDecimal fantasyPoints,
        @NotNull Team team,
        @NotNull NonEmptyLinkedHashSet<Position> eligiblePositions,
        @NotNull NonNegativeDollars salary,
        @NotNull BigDecimal fantasyPointsPerOneThousandDollars,
        @NotNull BigDecimal value
) {
}
