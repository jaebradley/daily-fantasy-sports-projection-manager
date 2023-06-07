package com.rvnu.data.thirdparty.csv.stokastic.records.mlb;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class DeserializerIT {
    @Test
    public void testDraftKings() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("2023-06-06/MLB DK Projections.csv"));
        final AtomicLong rowCount = new AtomicLong(0);
        final Map<com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getInstance()
                    .deserialize(
                            inputStream,
                            (record -> {
                                rowCount.incrementAndGet();
                                TestCase.assertNotNull(record);
                            })
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        Assert.assertEquals(300, rowCount.longValue());
    }
}
