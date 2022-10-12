package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.InjuryIndicator;
import junit.framework.TestCase;

import java.util.Optional;

public class InjuryIndicatorSerializationUtilityTest extends TestCase {

    public void test() {
        assertEquals(Optional.of(InjuryIndicator.Game_Time_Decision), InjuryIndicatorSerializationUtility.getInstance().deserialize("GTD"));
        assertTrue(InjuryIndicatorSerializationUtility.getInstance().deserialize("foo").isEmpty());
    }
}