package com.rvnu.data.thirdparty.csv.stokastic.records.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class DeserializerIT {

    @Test
    public void testDraftKings() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("2022-10-18/NBA DK Projections.csv"));
        final Map<com.rvnu.data.thirdparty.csv.stokastic.record.nba.Deserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getInstance()
                    .deserialize(
                            inputStream,
                            (TestCase::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFanDuel() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("2022-10-18/NBA FD Projections.csv"));
        final Map<com.rvnu.data.thirdparty.csv.stokastic.record.nba.Deserializer.Error, PositiveInteger> result;
        try {
            result = Deserializer
                    .getInstance()
                    .deserialize(
                            inputStream,
                            (TestCase::assertNotNull)
                    );
        } catch (com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer.UnableToDeserializeRecords e) {
            throw new RuntimeException("unexpected", e);
        }

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}