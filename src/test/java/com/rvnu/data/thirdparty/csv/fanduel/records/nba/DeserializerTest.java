package com.rvnu.data.thirdparty.csv.fanduel.records.nba;

import com.rvnu.models.thirdparty.fanduel.nba.*;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;

public class DeserializerTest extends TestCase {
    public void test() {
        try {
            Deserializer
                    .getInstance()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            Id,Position,First Name,Nickname,Last Name,FPPG,Played,Salary,Game,Team,Opponent,Injury Indicator,Injury Details,Tier,,,Roster Position
                                            81576-9488,SF/PF,LeBron,LeBron James,James,53.00877192982456,57,10800,LAL@GS,LAL,GS,GTD,Rest,,,,SF/PF
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (contestPlayer -> {
                                try {
                                    assertEquals(
                                            new ContestPlayer(
                                                    new ContestPlayerId(new PositiveInteger(81576), new PositiveInteger(9488)),
                                                    Set.of(Position.SMALL_FORWARD, Position.POWER_FORWARD),
                                                    new NonEmptyString("LeBron"),
                                                    new NonEmptyString("James"),
                                                    new NonNegativeDollars(new NonNegativeDecimal(new BigDecimal("10800"))),
                                                    Team.Los_Angeles_Lakers,
                                                    Team.Golden_State_Warriors,
                                                    InjuryIndicator.Game_Time_Decision,
                                                    Optional.of(new NonEmptyString("Rest"))
                                            ),
                                            contestPlayer
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NonNegativeDecimal.ValueCannotBeNegative | NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}