package com.rvnu.data.thirdparty.csv.dailyroto.records.nba;

import com.rvnu.data.firstparty.csv.records.implementation.AbstractDeserializer;
import com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer;
import com.rvnu.models.thirdparty.dailyroto.nba.Projection;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.thirdparty.dailyroto.nba.PositionsSerializationUtility;
import com.rvnu.serialization.thirdparty.draftkings.nba.PlayerIdSerializationUtility;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Deserializer<SitePosition, SitePlayerId> extends AbstractDeserializer<Projection<SitePosition, SitePlayerId>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> {
    // TODO: @jbradley this is a hacky approach where the model needs to be rethought/**/
    @NotNull
    private static final Deserializer<Position, PlayerId> DRAFT_KINGS_DESERIALIZER = new Deserializer<>(
            new com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer<>(
                    PositionsSerializationUtility.getInstance(),
                    value -> Optional.of(PlayerIdSerializationUtility.getInstance().deserialize(value))
            ) {
            }
    );

    @NotNull
    private static final Deserializer<com.rvnu.models.thirdparty.fanduel.nba.Position, com.rvnu.models.thirdparty.fanduel.nba.PlayerId> FAN_DUEL_DESERIALIZER = new Deserializer<>(
            new BaseDeserializer<>(
                    com.rvnu.serialization.thirdparty.fanduel.nba.PositionsSerializationUtility.getInstance(),
                    value -> {
                        if (value.equals("0")) {
                            return Optional.of(Optional.empty());
                        }
                        return Optional.empty();
                    }
            ) {
            }
    );


    protected Deserializer(
            @NotNull final com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Projection<SitePosition, SitePlayerId>, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Error> resultDeserializer
    ) {
        super(StandardCharsets.UTF_8, CSVFormat.DEFAULT
                        .builder()
                        .setHeader(com.rvnu.data.thirdparty.csv.dailyroto.record.nba.BaseDeserializer.Column.class)
                        .setSkipHeaderRecord(true)
                        .build(),
                resultDeserializer);
    }

    @NotNull
    public static Deserializer<Position, PlayerId> getDraftKingsDeserializer() {
        return DRAFT_KINGS_DESERIALIZER;
    }

    @NotNull
    public static Deserializer<com.rvnu.models.thirdparty.fanduel.nba.Position, com.rvnu.models.thirdparty.fanduel.nba.PlayerId> getFanDuelDeserializer() {
        return FAN_DUEL_DESERIALIZER;
    }
}
