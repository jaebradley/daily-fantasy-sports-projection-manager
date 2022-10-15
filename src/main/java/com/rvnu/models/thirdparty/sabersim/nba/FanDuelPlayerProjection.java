package com.rvnu.models.thirdparty.sabersim.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.fanduel.nba.ContestPlayerId;
import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class FanDuelPlayerProjection extends BaseSitePlayerProjection<ContestPlayerId, Position> {
    public FanDuelPlayerProjection(
            @NotNull final ContestPlayerId playerId,
            @NotNull final NonEmptyString name,
            @NotNull final NonEmptyLinkedHashSet<Position> eligiblePositions,
            @NotNull final Team team,
            @NotNull final Team opponent,
            @NotNull final NonNegativeDollars salary,
            @NotNull final BigDecimal projectedPoints,
            @NotNull final NonNegativeDecimal projectedOwnership
    ) {
        super(playerId, name, eligiblePositions, team, opponent, salary, projectedPoints, projectedOwnership);
    }
}
