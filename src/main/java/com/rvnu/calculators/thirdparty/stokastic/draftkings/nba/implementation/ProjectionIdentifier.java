package com.rvnu.calculators.thirdparty.stokastic.draftkings.nba.implementation;

import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class ProjectionIdentifier implements com.rvnu.calculators.firstparty.draftkings.nba.interfaces.ProjectionIdentifier<NonEmptyString, Projection> {
    @NotNull
    private final Map<NonEmptyString, Projection> projectionsByName;

    @NotNull
    private final Function<NonEmptyString, NonEmptyString> projectionNameToContestNameTranslator;

    public ProjectionIdentifier(
            @NotNull final Map<NonEmptyString, Projection> projectionsByName,
            @NotNull final Function<NonEmptyString, NonEmptyString> projectionNameToContestNameTranslator
    ) {
        this.projectionsByName = projectionsByName;
        this.projectionNameToContestNameTranslator = projectionNameToContestNameTranslator;
    }

    @Override
    public Optional<Projection> identifyProjection(@NotNull final NonEmptyString contestPlayerIdentifier) {
        return Optional.ofNullable(projectionsByName.get(projectionNameToContestNameTranslator.apply(contestPlayerIdentifier)));
    }
}
