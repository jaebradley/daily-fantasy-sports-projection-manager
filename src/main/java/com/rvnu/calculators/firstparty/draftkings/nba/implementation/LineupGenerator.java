package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.applications.nba.draftkings.ProjectionLineupGenerator;
import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.awesomeo.nba.Projection;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static com.rvnu.applications.nba.draftkings.ProjectionLineupGenerator.RequiredLineupPosition.*;

public class LineupGenerator {
    @NotNull
    private final Map<ProjectionLineupGenerator.RequiredLineupPosition, Set<Projection>> projectionsByPosition;

    @NotNull
    private final com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition> salaryValidator;

    public LineupGenerator(
            @NotNull final Map<ProjectionLineupGenerator.RequiredLineupPosition, Set<Projection>> projectionsByPosition,
            @NotNull final com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryValidator<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition> salaryValidator
    ) {
        this.projectionsByPosition = projectionsByPosition;
        this.salaryValidator = salaryValidator;
    }

    private void processLineup(
            @NotNull final ProjectionLineupGenerator.RequiredLineupPosition position,
            @NotNull final Lineup<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition> previousLineup,
            @NotNull final Consumer<Lineup<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition>> nextLineupHandler) {
        final Set<Projection> projections = projectionsByPosition.getOrDefault(position, Collections.emptySet());
        for (final Projection projection : projections) {
            final Lineup<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition> nextLineup;
            try {
                nextLineup = previousLineup.addPlayer(
                        projection.name(),
                        position,
                        projection.name(),
                        projection.salary()
                );
            } catch (Lineup.PlayerAlreadyExistsForPosition e) {
                throw new RuntimeException("unexpected", e);
            } catch (Lineup.PlayerAlreadyExists ignored) {
                continue;
            }
            if (salaryValidator.lineupHasValidSalary(nextLineup)) {
                nextLineupHandler.accept(nextLineup);
            }
        }
    }

    public void generateLineup(final Consumer<Lineup<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition>> lineupConsumer) {
        for (final Projection pointGuardProjection : projectionsByPosition.getOrDefault(POINT_GUARD, Collections.emptySet())) {
            final Lineup<NonEmptyString, ProjectionLineupGenerator.RequiredLineupPosition> lineup = Lineup.withPlayer(pointGuardProjection.name(), POINT_GUARD, pointGuardProjection.name(), pointGuardProjection.salary());

            if (salaryValidator.lineupHasValidSalary(lineup)) {
                processLineup(
                        SHOOTING_GUARD,
                        lineup,
                        lineupWithShootingGuard -> processLineup(
                                SMALL_FORWARD,
                                lineupWithShootingGuard,
                                lineupWithSmallForward -> processLineup(
                                        POWER_FORWARD,
                                        lineupWithSmallForward,
                                        lineupWithPowerForward -> processLineup(
                                                CENTER,
                                                lineupWithPowerForward,
                                                lineupWithCenter -> processLineup(
                                                        GUARD,
                                                        lineupWithCenter,
                                                        lineupWithGuard -> processLineup(
                                                                FORWARD,
                                                                lineupWithGuard,
                                                                lineupWithForward -> processLineup(
                                                                        UTILITY,
                                                                        lineupWithForward,
                                                                        lineupConsumer
                                                                )
                                                        )
                                                )
                                        )
                                )
                        ));
            }
        }
    }
}
