package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.rvnu.models.firstparty.collections.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.stokastic.nba.Position;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.math.BigDecimal;

public record Projection(
        PositiveInteger playerId,
        Team team,
        Team opposition,
        NonEmptyLinkedHashSet<Position> positions,
        NonEmptyString name,
        BigDecimal fantasyPoints,
        NonNegativeDollars salary,
        long rotogrindersId,
        // TODO: @jbradley this should be a generic ID
        PositiveInteger partnerId
) {
}
