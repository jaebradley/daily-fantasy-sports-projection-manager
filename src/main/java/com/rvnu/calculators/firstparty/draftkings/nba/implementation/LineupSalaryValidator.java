package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import org.jetbrains.annotations.NotNull;

public class LineupSalaryValidator<PlayerIdentifier, Position extends Enum<Position>> implements com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator<PlayerIdentifier, Position> {
    @NotNull
    private final LineupSalaryCalculator<PlayerIdentifier, Position> lineupSalaryCalculator;

    @NotNull
    private final NonNegativeDollars maximumInclusiveSalary;

    public LineupSalaryValidator(
            @NotNull final LineupSalaryCalculator<PlayerIdentifier, Position> lineupSalaryCalculator,
            @NotNull final NonNegativeDollars maximumInclusiveSalary) {
        this.lineupSalaryCalculator = lineupSalaryCalculator;
        this.maximumInclusiveSalary = maximumInclusiveSalary;
    }

    @Override
    public boolean lineupHasValidSalary(@NotNull final Lineup<PlayerIdentifier, Position> lineup) {
        return lineupSalaryCalculator.calculateSalary(lineup).getValue().isLessThanOrEqualTo(maximumInclusiveSalary.getValue());
    }
}
