package com.rvnu.data.thirdparty.csv.fanduel.records.nba;

import com.rvnu.data.firstparty.csv.records.implementation.AbstractDeserializer;
import com.rvnu.models.thirdparty.fanduel.nba.ContestPlayer;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Deserializer extends AbstractDeserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.Error> {
    @NotNull
    private static final Deserializer INSTANCE = new Deserializer(
            StandardCharsets.UTF_8,
            CSVFormat.DEFAULT
                    .builder()
                    .setHeader(com.rvnu.data.thirdparty.csv.awesomeo.record.nba.Deserializer.Column.class)
                    .setSkipHeaderRecord(true)
                    .build(),
            com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.getInstance()
    );

    private Deserializer(
            @NotNull final Charset characterSet,
            @NotNull final CSVFormat format,
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<ContestPlayer, com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.Column, com.rvnu.data.thirdparty.csv.fanduel.record.nba.Deserializer.Error> resultDeserializer
    ) {
        super(characterSet, format, resultDeserializer);
    }

    @NotNull
    public static Deserializer getInstance() {
        return INSTANCE;
    }
}
