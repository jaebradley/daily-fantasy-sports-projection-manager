package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class PositiveIntegerSerializationUtilityTest extends TestCase {

    public void test() {
        Stream.of(
                "0",
                "-1",
                "",
                "foo"
        ).forEach(v -> assertTrue(PositiveIntegerSerializationUtility.getDefaultInstance().deserialize(v).isEmpty()));

        try {
            Stream.of(
                    Map.entry("1", new PositiveInteger(1))
            ).forEach(e -> {
                assertEquals(e.getKey(), PositiveIntegerSerializationUtility.getDefaultInstance().serialize(e.getValue()));
                assertEquals(Optional.of(e.getValue()), PositiveIntegerSerializationUtility.getDefaultInstance().deserialize(e.getKey()));
            });
        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}