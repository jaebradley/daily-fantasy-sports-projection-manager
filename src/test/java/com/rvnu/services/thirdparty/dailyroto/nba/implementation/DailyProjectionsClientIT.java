package com.rvnu.services.thirdparty.dailyroto.nba.implementation;

import com.rvnu.serialization.firstparty.time.YearMonthDaySerializationUtility;
import com.rvnu.services.thirdparty.dailyroto.nba.interfaces.DailyProjectionsAccessor;
import junit.framework.TestCase;

import java.net.http.HttpClient;
import java.time.ZonedDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class DailyProjectionsClientIT extends TestCase {

    public void test() {
        final AtomicInteger counter = new AtomicInteger(0);
        try {
            new DailyProjectionsClient(
                    HttpClient.newBuilder().build(),
                    YearMonthDaySerializationUtility.getInstance()
            ).getProjections(ZonedDateTime.parse("2022-10-23T00:00:00.000Z"), dailyProjections -> {
                counter.incrementAndGet();
                assertNotNull(dailyProjections);
            });
        } catch (DailyProjectionsAccessor.UnableToGetProjections e) {
            throw new RuntimeException("unexpected", e);
        }

        assertEquals(1, counter.get());
    }
}