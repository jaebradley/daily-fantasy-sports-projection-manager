package com.rvnu.models.firstparty.dailyfantasysports;

import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

public class ContestPlayerProjection<ContestPlayerId, Position> {
    @NotNull
    private final ContestPlayerId playerId;

    @NotNull
    private final Position position;

    @NotNull
    private final BigDecimal projectedPoints;

    @NotNull
    private final NonNegativeDollars salary;

    public ContestPlayerProjection(
            @NotNull final ContestPlayerId playerId,
            @NotNull final Position position,
            @NotNull final BigDecimal projectedPoints,
            @NotNull final NonNegativeDollars salary) {
        this.playerId = playerId;
        this.position = position;
        this.projectedPoints = projectedPoints;
        this.salary = salary;
    }

    @NotNull
    public ContestPlayerId getPlayerId() {
        return playerId;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }

    @NotNull
    public BigDecimal getProjectedPoints() {
        return projectedPoints;
    }

    @NotNull
    public NonNegativeDollars getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContestPlayerProjection<?, ?> that = (ContestPlayerProjection<?, ?>) o;
        return playerId.equals(that.playerId) && position.equals(that.position) && projectedPoints.equals(that.projectedPoints) && salary.equals(that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, position, projectedPoints, salary);
    }
}
