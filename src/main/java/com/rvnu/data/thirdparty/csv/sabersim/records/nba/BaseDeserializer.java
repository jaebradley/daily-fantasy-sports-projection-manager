package com.rvnu.data.thirdparty.csv.sabersim.records.nba;

import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractDeserializer;
import com.rvnu.models.thirdparty.sabersim.nba.BaseSitePlayerProjection;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;

public abstract class BaseDeserializer<SitePlayerId, SitePosition, PlayerProjection extends BaseSitePlayerProjection<SitePlayerId, SitePosition>> extends AbstractDeserializer<PlayerProjection, com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer.Error> {
    BaseDeserializer(
            @NotNull final Deserializer<PlayerProjection, com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer.Column, com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer.Error> resultDeserializer
    ) {
        super(
                StandardCharsets.UTF_8,
                CSVFormat.DEFAULT
                        .builder()
                        .setHeader(com.rvnu.data.thirdparty.csv.sabersim.record.nba.BaseDeserializer.Column.class)
                        .setSkipHeaderRecord(true)
                        .build(),
                resultDeserializer
        );
    }
}
