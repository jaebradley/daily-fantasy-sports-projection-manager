package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import org.jetbrains.annotations.NotNull;

public interface LineupPositionsValidator<PlayerIdentifier, Position> {
    boolean lineupHasValidPositions(@NotNull final Lineup<PlayerIdentifier, Position> lineup);
}
