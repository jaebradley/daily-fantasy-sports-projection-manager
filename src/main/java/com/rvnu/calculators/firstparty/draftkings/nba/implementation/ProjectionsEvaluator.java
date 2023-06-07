package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.applications.nba.draftkings.ProjectionsMerger;
import com.rvnu.calculators.firstparty.draftkings.nba.interfaces.ProjectionIdentifier;
import com.rvnu.models.thirdparty.stokastic.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectionsEvaluator implements com.rvnu.calculators.firstparty.draftkings.nba.interfaces.ProjectionsEvaluator {

    @NotNull
    private final ProjectionIdentifier<NonEmptyString, Projection> stokasticProjectionIdentifier;
    @NotNull
    private final ProjectionIdentifier<NonEmptyString, com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrindersProjectionIdentifier;
    @NotNull
    private final ProjectionIdentifier<PlayerId, DraftKingsPlayerProjection> sabersimProjectionIdentifier;
    @NotNull
    private final ProjectionIdentifier<PlayerId, com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>> dailyRotoProjectionIdentifier;

    public ProjectionsEvaluator(
            @NotNull final ProjectionIdentifier<NonEmptyString, Projection> stokasticProjectionIdentifier,
            @NotNull final ProjectionIdentifier<NonEmptyString, com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrindersProjectionIdentifier,
            @NotNull final ProjectionIdentifier<PlayerId, DraftKingsPlayerProjection> sabersimProjectionIdentifier,
            @NotNull final ProjectionIdentifier<PlayerId, com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>> dailyRotoProjectionIdentifier
    ) {
        this.stokasticProjectionIdentifier = stokasticProjectionIdentifier;
        this.rotogrindersProjectionIdentifier = rotogrindersProjectionIdentifier;
        this.sabersimProjectionIdentifier = sabersimProjectionIdentifier;
        this.dailyRotoProjectionIdentifier = dailyRotoProjectionIdentifier;
    }

    @Override
    public ProjectionsMerger.Projections evaluateProjections(@NotNull final ContestPlayer player) throws UnableToEvaluateProjections {
        final Optional<Projection> stokasticProjection = stokasticProjectionIdentifier.identifyProjection(player.name());
        final Optional<com.rvnu.models.thirdparty.rotogrinders.nba.Projection> rotogrindersProjection = rotogrindersProjectionIdentifier.identifyProjection(player.name());
        final Optional<DraftKingsPlayerProjection> sabersimProjection = sabersimProjectionIdentifier.identifyProjection(player.id());
        final Optional<com.rvnu.models.thirdparty.dailyroto.nba.Projection<Position, PlayerId>> dailyRotoProjection = dailyRotoProjectionIdentifier.identifyProjection(player.id());

        if (
                1 != Stream.of(
                                stokasticProjection.map(Projection::fantasyPoints),
                                rotogrindersProjection.map(com.rvnu.models.thirdparty.rotogrinders.nba.Projection::fantasyPoints),
                                sabersimProjection.map(BaseSitePlayerProjection::getProjectedPoints),
                                dailyRotoProjection.map(com.rvnu.models.thirdparty.dailyroto.nba.Projection::points))
                        .map(Optional::isPresent)
                        .collect(Collectors.toMap(Function.identity(), v -> 1, Math::addExact))
                        .size()
        ) {
            // TODO: @jbradley this is messy and needs to be tuned
            if (3 <= Stream.of(
                            stokasticProjection.map(Projection::fantasyPoints),
                            rotogrindersProjection.map(com.rvnu.models.thirdparty.rotogrinders.nba.Projection::fantasyPoints),
                            sabersimProjection.map(BaseSitePlayerProjection::getProjectedPoints),
                            dailyRotoProjection.map(com.rvnu.models.thirdparty.dailyroto.nba.Projection::points))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .filter(v -> !v.equals(BigDecimal.ZERO))
                    .collect(Collectors.toSet())
                    .size()) {
                throw new UnableToEvaluateProjections("Unable to evaluate projections for player: " + player.name().getValue());
            }
        }

        return new ProjectionsMerger.Projections(
                player,
                stokasticProjection,
                rotogrindersProjection,
                sabersimProjection,
                dailyRotoProjection
        );
    }
}
