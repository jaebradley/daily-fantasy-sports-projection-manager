package com.rvnu.data.thirdparty.csv.sabersim.records.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class DeserializerIT {
    @Test
    public void testDraftKings() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("draftkings/NBA_2022-01-02_DK_600PM-6-games-Main.csv"));
        final Map<com.rvnu.data.thirdparty.csv.sabersim.record.nba.DraftKingsPlayerProjectionDeserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getDraftKingsDeserializer()
                    .deserialize(
                            inputStream,
                            (Assertions::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testFanDuel() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("fanduel/NBA_2022-01-02_FD_330PM-7-games-All-Day.csv"));
        final Map<com.rvnu.data.thirdparty.csv.sabersim.record.nba.DraftKingsPlayerProjectionDeserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getFanDuelDeserializer()
                    .deserialize(
                            inputStream,
                            (Assertions::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }
}