package com.rvnu.data.thirdparty.csv.dailyroto.records.nba;

import com.rvnu.data.firstparty.csv.records.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer;
import com.rvnu.models.thirdparty.dailyroto.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.thirdparty.dailyroto.nba.PositionsSerializationUtility;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public class Deserializer<SitePosition> extends AbstractDeserializer<Projection<SitePosition>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> {
    @NotNull
    private static final Deserializer<Position> DRAFT_KINGS_DESERIALIZER = new Deserializer<Position>(
            new com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer<Position>(
                    PositionsSerializationUtility.getInstance()
            ) {
            }
    );

    @NotNull
    private static final Deserializer<com.rvnu.models.thirdparty.fanduel.nba.Position> FAN_DUEL_DESERIALIZER = new Deserializer<>(
            new BaseDeserializer<com.rvnu.models.thirdparty.fanduel.nba.Position>(
                    com.rvnu.serialization.thirdparty.fanduel.nba.PositionsSerializationUtility.getInstance()
            ) {
            }
    );


    protected Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Projection<SitePosition>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> resultDeserializer
    ) {
        super(StandardCharsets.UTF_8, CSVFormat.DEFAULT
                        .builder()
                        .setHeader(com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column.class)
                        .setSkipHeaderRecord(true)
                        .build(),
                resultDeserializer);
    }

    @NotNull
    public static Deserializer<Position> getDraftKingsDeserializer() {
        return DRAFT_KINGS_DESERIALIZER;
    }

    @NotNull
    public static Deserializer<com.rvnu.models.thirdparty.fanduel.nba.Position> getFanDuelDeserializer() {
        return FAN_DUEL_DESERIALIZER;
    }
}
