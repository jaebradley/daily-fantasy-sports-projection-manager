package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.money.NonNegativeDollars;
import junit.framework.TestCase;

import java.util.Optional;

public class NonNegativeDollarsSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            assertEquals(Optional.of(new NonNegativeDollars(new NaturalNumber(11000))), NonNegativeDollarsSerializationUtility.getInstance().deserialize("11,000"));
        } catch (NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}