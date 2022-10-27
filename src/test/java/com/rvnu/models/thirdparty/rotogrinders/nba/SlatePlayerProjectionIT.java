package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class SlatePlayerProjectionIT extends TestCase {
    public void testDeserialization() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/com/rvnu/data/thirdparty/json/rotogrinders/records/nba/2022/10/24/draftkings-slate-player-projections.json.gz"));
        try (final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            final List<SlatePlayerProjection> playerProjections = new ObjectMapper().readerForListOf(SlatePlayerProjection.class).readValue(gzipInputStream);
            assertNotNull(playerProjections);
        } catch (IOException e) {
            throw new RuntimeException("unexpected", e);
        }
    }

}