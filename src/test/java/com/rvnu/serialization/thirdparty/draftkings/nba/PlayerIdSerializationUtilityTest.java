package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;

import java.util.Optional;

public class PlayerIdSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            assertEquals(Optional.of(new PlayerId(1)), PlayerIdSerializationUtility.getInstance().deserialize("1"));
        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }

        assertTrue(PlayerIdSerializationUtility.getInstance().deserialize("foo").isEmpty());
    }
}