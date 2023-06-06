package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import org.jetbrains.annotations.NotNull;

public interface LineupSalaryValidator<PlayerIdentifier, Position> {
    boolean lineupHasValidSalary(@NotNull final Lineup<PlayerIdentifier, Position> lineup);
}
