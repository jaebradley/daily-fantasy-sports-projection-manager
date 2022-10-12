package com.rvnu.data.thirdparty.csv.sabersim.records.nba;

import com.rvnu.data.thirdparty.csv.sabersim.record.nba.DraftKingsPlayerProjectionDeserializer;
import com.rvnu.models.thirdparty.draftkings.nba.PlayerId;
import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.models.thirdparty.sabersim.nba.DraftKingsPlayerProjection;
import org.jetbrains.annotations.NotNull;

public class Deserializer {
    @NotNull
    private static final BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> DRAFT_KINGS_DESERIALIZER = new BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection>(DraftKingsPlayerProjectionDeserializer.getInstance()) {
    };

    public static BaseDeserializer<PlayerId, Position, DraftKingsPlayerProjection> getDraftKingsDeserializer() {
        return DRAFT_KINGS_DESERIALIZER;
    }
}
