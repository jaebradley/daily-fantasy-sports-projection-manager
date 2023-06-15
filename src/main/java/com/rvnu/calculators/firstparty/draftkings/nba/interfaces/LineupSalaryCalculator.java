package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import org.jetbrains.annotations.NotNull;

public interface LineupSalaryCalculator<PlayerIdentifier, Position extends Enum<Position>> {
    @NotNull
    NonNegativeDollars calculateSalary(@NotNull Lineup<PlayerIdentifier, Position> lineup);
}
