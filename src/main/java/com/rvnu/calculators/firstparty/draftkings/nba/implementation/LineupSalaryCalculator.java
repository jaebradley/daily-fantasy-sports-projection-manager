package com.rvnu.calculators.firstparty.draftkings.nba.implementation;

import com.rvnu.models.firstparty.dailyfantasysports.Lineup;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import org.jetbrains.annotations.NotNull;

public class LineupSalaryCalculator<PlayerIdentifier, Position> implements com.rvnu.calculators.firstparty.draftkings.nba.interfaces.LineupSalaryCalculator<PlayerIdentifier, Position> {
    @NotNull
    @Override
    public NonNegativeDollars calculateSalary(@NotNull Lineup<PlayerIdentifier, Position> lineup) {
        try {
            return new NonNegativeDollars(
                    new NaturalNumber(
                            lineup
                                    .getDetailsByIdentifier()
                                    .values()
                                    .stream()
                                    .map(Lineup.PlayerDetails::salary)
                                    .map(v -> v.getValue().getNumber().longValue())
                                    .reduce(0L, Long::sum)));
        } catch (NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}
