package com.rvnu.data.thirdparty.csv.dailyroto.records.nba;

import com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class DeserializerIT extends TestCase {
    public void testDraftKings() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("draftkings/2022-10-18/DailyRoto_NBAProjections_2022-10-18_DraftKings.csv"));
        final Map<BaseDeserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getDraftKingsDeserializer()
                    .deserialize(
                            inputStream,
                            (TestCase::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    public void testFanDuel() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("fanduel/2022-10-18/DailyRoto_NBAProjections_2022-10-18_FanDuel.csv"));
        final Map<BaseDeserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getFanDuelDeserializer()
                    .deserialize(
                            inputStream,
                            (TestCase::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}