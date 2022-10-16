package com.rvnu.models.thirdparty.dailyroto.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.numbers.NonNegativePercentage;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.math.BigDecimal;

public record Projection<SitePosition>(
        Team team,
        Team opponent,
        NonEmptyString name,
        NonNegativeDecimal minutesPlayed,
        NonNegativePercentage usageRate,
        NonNegativePercentage reboundRate,
        NonNegativePercentage assistRate,
        NonEmptyLinkedHashSet<SitePosition> positions,
        NonNegativePercentage publicOwnershipPercentage,
        NonNegativeDollars salary,
        BigDecimal points,
        BigDecimal value
) {
}
