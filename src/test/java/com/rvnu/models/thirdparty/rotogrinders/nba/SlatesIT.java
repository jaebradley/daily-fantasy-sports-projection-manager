package com.rvnu.models.thirdparty.rotogrinders.nba;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.json.numbers.PositiveIntegerKeyDeserializer;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public class SlatesIT extends TestCase {

    public void testDeserialization() {
        final InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/com/rvnu/data/thirdparty/json/rotogrinders/records/nba/2022/10/24/slates.json.gz"));
        try (final GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {
            final Slates slates = new ObjectMapper().registerModule(new SimpleModule().addKeyDeserializer(PositiveInteger.class, new PositiveIntegerKeyDeserializer())).readerFor(Slates.class).readValue(gzipInputStream);
            assertNotNull(slates);
        } catch (IOException e) {
            throw new RuntimeException("unexpected", e);
        }
    }
}