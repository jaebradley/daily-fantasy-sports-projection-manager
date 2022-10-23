package com.rvnu.calculators.firstparty.draftkings.nba.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ProjectionIdentifier<Identifier, Projection> {
    Optional<Projection> identifyProjection(@NotNull Identifier contestPlayerIdentifier);
}
