package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.GameInformation;
import com.rvnu.models.thirdparty.nba.Team;
import junit.framework.TestCase;

import java.time.*;
import java.util.Optional;

public class GameInformationSerializationUtilityTest extends TestCase {

    public void test() {
        assertEquals(
                Optional.of(
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
                GameInformationSerializationUtility.getInstance().deserialize("PHI@NOP 10/20/2021 08:00PM ET")
        );
    }
}