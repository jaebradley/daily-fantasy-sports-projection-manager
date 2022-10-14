package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.rvnu.models.thirdparty.awesomeo.nba.Position;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;

import java.math.BigDecimal;

public record Projection(
        PositiveInteger playerId,
        Team team,
        Team opposition,
        Position position,
        NonEmptyString name,
        BigDecimal fantasyPoints,
        NonNegativeDollars salary,
        PositiveInteger rotogrindersId,
        PositiveInteger partnerId
) {
}
