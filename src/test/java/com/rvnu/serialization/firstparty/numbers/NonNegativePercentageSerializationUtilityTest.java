package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.numbers.NonNegativeDecimal;
import com.rvnu.models.thirdparty.numbers.NonNegativePercentage;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class NonNegativePercentageSerializationUtilityTest extends TestCase {

    public void test() {
        try {
            Stream.of(
                            Map.entry("100", new NonNegativePercentage(new BigDecimal(100))),
                            Map.entry("99.999", new NonNegativePercentage(new BigDecimal("99.999")))
                    )
                    .forEach(e -> assertEquals(Optional.of(e.getValue()), NonNegativePercentageSerializationUtility.getInstance().deserialize(e.getKey())));
        } catch (NonNegativeDecimal.ValueCannotBeNegative | NonNegativePercentage.ValueCannotBeGreaterThan100 e) {
            throw new RuntimeException("unexpected", e);
        }

        Stream.of(
                "101",
                "100.00000001"
        ).forEach(v -> assertTrue(NonNegativePercentageSerializationUtility.getInstance().deserialize(v).isEmpty()));
    }
}