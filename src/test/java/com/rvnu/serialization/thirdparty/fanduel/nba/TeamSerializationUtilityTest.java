package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Team;
import junit.framework.TestCase;

import java.util.Optional;

public class TeamSerializationUtilityTest extends TestCase {

    public void test() {
        assertEquals(Optional.of(Team.Boston_Celtics), TeamSerializationUtility.getInstance().deserialize("BOS"));
        assertTrue(TeamSerializationUtility.getInstance().deserialize("foo").isEmpty());
    }
}