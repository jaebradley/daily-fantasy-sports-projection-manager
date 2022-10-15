package com.rvnu.data.thirdparty.csv.fanduel.records.nba;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.models.thirdparty.fanduel.nba.*;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigInteger;
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
                                                    new ContestPlayerId(new FixtureListId(81576), new PlayerId(9488)),
                                                    NonEmptyLinkedHashSet.from(Set.of(Position.SMALL_FORWARD, Position.POWER_FORWARD)),
                                                    new NonEmptyString("LeBron"),
                                                    new NonEmptyString("James"),
                                                    new NonNegativeDollars(new NaturalNumber(new BigInteger("10800").longValue())),
                                                    Team.LOS_ANGELES_LAKERS,
                                                    Team.GOLDEN_STATE_WARRIORS,
                                                    Optional.of(InjuryIndicator.Game_Time_Decision),
                                                    Optional.of(new NonEmptyString("Rest"))
                                            ),
                                            contestPlayer
                                    );
                                } catch (NonEmptyString.ValueMustNotBeEmpty | NaturalNumber.ValueMustNotBeNegative | PositiveInteger.ValueMustBePositive | NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
                                    throw new RuntimeException("unexpected", e);
                                }
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}