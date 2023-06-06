package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import org.jetbrains.annotations.NotNull;

public interface LineupValidator<PlayerIdentifier, Position> {
    boolean isValid(@NotNull Lineup<PlayerIdentifier, Position> lineup);
}
