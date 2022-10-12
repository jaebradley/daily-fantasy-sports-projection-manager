package com.rvnu.models.thirdparty.sabersim.nba;

import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.LinkedHashSet;

public class DraftKingsPlayerProjection extends BaseSitePlayerProjection<PlayerId, Position> {
    public DraftKingsPlayerProjection(
            @NotNull final PlayerId playerId,
            @NotNull final NonEmptyString name,
            @NotNull final LinkedHashSet<Position> eligiblePositions,
            @NotNull final Team team,
            @NotNull final Team opponent,
            @NotNull final NonNegativeDollars salary,
            @NotNull final BigDecimal projectedPoints,
            @NotNull final NonNegativeDecimal projectedOwnership
    ) {
        super(playerId, name, eligiblePositions, team, opponent, salary, projectedPoints, projectedOwnership);
    }
}
