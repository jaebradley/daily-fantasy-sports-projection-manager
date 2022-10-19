package com.rvnu.data.thirdparty.csv.rotogrinders.records.nba;

import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.models.thirdparty.rotogrinders.nba.Projection;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class Deserializer extends AbstractDeserializer<Projection, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Error> {
    @NotNull
    private static final Deserializer INSTANCE = new Deserializer();

    private Deserializer() {
        super(
                StandardCharsets.UTF_8,
                CSVFormat.DEFAULT
                        .builder()
                        .setHeader(com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.Column.class)
                        .setSkipHeaderRecord(true)
                        .build(),
                com.rvnu.data.thirdparty.csv.rotogrinders.record.nba.Deserializer.getInstance());
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}
