package com.rvnu.models.thirdparty.sabersim.nba;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;

public class BaseSitePlayerProjection<SitePlayerId, SitePosition> {
    @NotNull
    private final SitePlayerId playerId;

    @NotNull
    private final NonEmptyString name;

    @NotNull
    private final LinkedHashSet<SitePosition> eligiblePositions;

    @NotNull
    private final Team team;

    @NotNull
    private final Team opponent;

    @NotNull
    private final NonNegativeDollars salary;

    @NotNull
    private final BigDecimal projectedPoints;

    @NotNull
    private final NonNegativeDecimal projectedOwnership;

    public BaseSitePlayerProjection(
            @NotNull final SitePlayerId playerId,
            @NotNull final NonEmptyString name,
            @NotNull final LinkedHashSet<SitePosition> eligiblePositions,
            @NotNull final Team team,
            @NotNull final Team opponent,
            @NotNull final NonNegativeDollars salary,
            @NotNull final BigDecimal projectedPoints,
            @NotNull final NonNegativeDecimal projectedOwnership
    ) {
        this.playerId = playerId;
        this.name = name;
        this.eligiblePositions = eligiblePositions;
        this.team = team;
        this.opponent = opponent;
        this.salary = salary;
        this.projectedPoints = projectedPoints;
        this.projectedOwnership = projectedOwnership;
    }

    @NotNull
    public SitePlayerId getPlayerId() {
        return playerId;
    }

    @NotNull
    public NonEmptyString getName() {
        return name;
    }

    @NotNull
    public LinkedHashSet<SitePosition> getEligiblePositions() {
        return eligiblePositions;
    }

    @NotNull
    public Team getTeam() {
        return team;
    }

    @NotNull
    public Team getOpponent() {
        return opponent;
    }

    @NotNull
    public NonNegativeDollars getSalary() {
        return salary;
    }

    @NotNull
    public BigDecimal getProjectedPoints() {
        return projectedPoints;
    }

    @NotNull
    public NonNegativeDecimal getProjectedOwnership() {
        return projectedOwnership;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseSitePlayerProjection<?, ?> that = (BaseSitePlayerProjection<?, ?>) o;
        return playerId.equals(that.playerId) && name.equals(that.name) && eligiblePositions.equals(that.eligiblePositions) && team == that.team && opponent == that.opponent && salary.equals(that.salary) && projectedPoints.equals(that.projectedPoints) && projectedOwnership.equals(that.projectedOwnership);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, name, eligiblePositions, team, opponent, salary, projectedPoints, projectedOwnership);
    }
}
