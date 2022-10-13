package com.rvnu.data.thirdparty.csv.sabersim.records.nba;

import com.rvnu.data.thirdparty.csv.sabersim.record.nba.DraftKingsPlayerProjectionDeserializer;
import com.rvnu.data.thirdparty.csv.sabersim.record.nba.FanDuelPlayerProjectionDeserializer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import com.rvnu.models.thirdparty.sabersim.nba.FanDuelPlayerProjection;
import org.jetbrains.annotations.NotNull;

public class Deserializer {
    @NotNull
    private static final BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> DRAFT_KINGS_DESERIALIZER = new BaseDeserializer<>(DraftKingsPlayerProjectionDeserializer.getInstance()) {
    };

    @NotNull
    private static final BaseDeserializer<com.rvnu.models.thirdparty.fanduel.nba.PlayerId, com.rvnu.models.thirdparty.fanduel.nba.Position, FanDuelPlayerProjection> FAN_DUEL_DESERIALIZER = new BaseDeserializer<>(FanDuelPlayerProjectionDeserializer.getInstance()) {
    };

    @NotNull
    public static BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> getDraftKingsDeserializer() {
        return DRAFT_KINGS_DESERIALIZER;
    }

    @NotNull
    public static BaseDeserializer<com.rvnu.models.thirdparty.fanduel.nba.PlayerId, com.rvnu.models.thirdparty.fanduel.nba.Position, FanDuelPlayerProjection> getFanDuelDeserializer() {
        return FAN_DUEL_DESERIALIZER;
    }
}
