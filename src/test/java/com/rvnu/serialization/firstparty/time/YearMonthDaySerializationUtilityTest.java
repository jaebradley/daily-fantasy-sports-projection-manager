package com.rvnu.serialization.firstparty.time;

import junit.framework.TestCase;

import java.time.ZonedDateTime;

public class YearMonthDaySerializationUtilityTest extends TestCase {

    public void test() {
        assertEquals(
                "2022-01-01", YearMonthDaySerializationUtility.getInstance().serialize(ZonedDateTime.parse("2022-01-01T00:00:00.000Z"))
        );
    }
}