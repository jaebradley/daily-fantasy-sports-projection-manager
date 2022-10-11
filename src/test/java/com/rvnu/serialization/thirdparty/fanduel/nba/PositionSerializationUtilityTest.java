package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import junit.framework.TestCase;

import java.util.Optional;

public class PositionSerializationUtilityTest extends TestCase {

    public void test() {
        assertEquals(Optional.of(Position.CENTER), PositionSerializationUtility.getInstance().deserialize("C"));
        assertTrue(PositionSerializationUtility.getInstance().deserialize("j").isEmpty());
    }
}