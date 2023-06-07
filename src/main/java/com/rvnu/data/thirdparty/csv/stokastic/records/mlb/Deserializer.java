package com.rvnu.data.thirdparty.csv.stokastic.records.mlb;

import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.models.thirdparty.stokastic.mlb.Projection;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Deserializer extends AbstractDeserializer<Projection, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> {
    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            StandardCharsets.UTF_8,
            CSVFormat.DEFAULT
                    .builder()
                    .setHeader(com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column.class)
                    .setSkipHeaderRecord(true)
                    .build(),
            com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.getInstance()
    );

    private Deserializer(
            @NotNull final Charset characterSet,
            @NotNull final CSVFormat format,
            @NotNull final com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer<Projection, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Column, com.rvnu.data.thirdparty.csv.stokastic.record.mlb.Deserializer.Error> resultDeserializer) {
        super(characterSet, format, resultDeserializer);
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}
