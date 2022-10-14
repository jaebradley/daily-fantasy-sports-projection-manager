package com.rvnu.data.thirdparty.csv.draftkings.records.nba;

import com.rvnu.models.thirdparty.iso.PositiveInteger;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class DeserializerIT {

    @Test
    public void test() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("2022-10-12/DKSalaries.csv"));
        final Map<com.rvnu.data.thirdparty.csv.draftkings.record.nba.Deserializer.Error, PositiveInteger> result;
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

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}