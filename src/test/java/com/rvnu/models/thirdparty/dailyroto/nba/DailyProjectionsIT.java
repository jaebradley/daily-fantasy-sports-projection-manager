package com.rvnu.models.thirdparty.dailyroto.nba;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class DailyProjectionsIT extends TestCase {

    public void testDeserialization() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/com/rvnu/data/thirdparty/json/dailyroto/records/nba/2022/10/23/projections.json.gz"));
        try (final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            final DailyProjections dailyProjections = new ObjectMapper().readerFor(DailyProjections.class).readValue(gzipInputStream);
            assertNotNull(dailyProjections);
        } catch (IOException e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}