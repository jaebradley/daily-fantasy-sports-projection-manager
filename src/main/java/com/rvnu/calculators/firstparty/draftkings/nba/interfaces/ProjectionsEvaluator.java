package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import com.rvnu.applications.nba.draftkings.ProjectionsMerger;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import org.jetbrains.annotations.NotNull;

public interface ProjectionsEvaluator {
    class UnableToEvaluateProjections extends Exception {

        public UnableToEvaluateProjections(String message) {
            super(message);
        }
    }

    ProjectionsMerger.Projections evaluateProjections(@NotNull ContestPlayer player) throws UnableToEvaluateProjections;
}
