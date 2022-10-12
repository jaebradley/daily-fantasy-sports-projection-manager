package com.rvnu.data.thirdparty.csv.fanduel.records.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class DeserializerIT extends TestCase {

    public void test() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("FanDuel-NBA-2022 ET-10 ET-18 ET-81576-players-list.csv"));
        final Map<com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getInstance()
                    .deserialize(
                            inputStream,
                            (TestCase::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}