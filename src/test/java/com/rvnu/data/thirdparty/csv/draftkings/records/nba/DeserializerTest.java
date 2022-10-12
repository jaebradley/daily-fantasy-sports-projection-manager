package com.rvnu.data.thirdparty.csv.draftkings.records.nba;

import com.rvnu.models.thirdparty.draftkings.nba.ContestPlayer;
import com.rvnu.models.thirdparty.draftkings.nba.GameInformation;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import com.rvnu.models.thirdparty.nba.Team;
import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Set;

public class DeserializerTest extends TestCase {

    public void test() {
        try {
            Deserializer
                    .getInstance()
                    .deserialize(
                            new ByteArrayInputStream(
                                    ("""
                                            Position,Name + ID,Name,ID,Roster Position,Salary,Game Info,TeamAbbrev,AvgPointsPerGame
                                            C,Joel Embiid (19760143),Joel Embiid,19760143,C/UTIL,10600,PHI@NOP 10/20/2021 08:00PM ET,PHI,50.55
                                            """).getBytes(StandardCharsets.UTF_8)
                            ),
                            (contestPlayer -> {
                                try {
                                    assertEquals(
                                            new ContestPlayer(
                                                    new NonEmptyString("Joel Embiid"),
                                                    new PlayerId(19760143L),
                                                    Set.of(Position.CENTER, Position.UTILITY),
                                                    new NonNegativeDollars(new NonNegativeDecimal(BigDecimal.valueOf(10600))),
                                                    Team.PHILADELPHIA_76ERS,
                                                    new GameInformation(
                                                            Team.PHILADELPHIA_76ERS,
                                                            Team.NEW_ORLEANS_PELICANS,
                                                            ZonedDateTime.of(
                                                                    LocalDate.of(2021, Month.OCTOBER, 20),
                                                                    LocalTime.of(20, 0),
                                                                    ZoneId.of("America/New_York")
                                                            )
                                                    )
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