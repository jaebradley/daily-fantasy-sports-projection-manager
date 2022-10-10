package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;

public class AbbreviatedPositionSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<Position> {
    @NotNull
    private static final AbbreviatedPositionSerializationUtility INSTANCE = new AbbreviatedPositionSerializationUtility(
            new EnumMap<Position, String>(
                    Map.of(
                            Position.POINT_GUARD, "PG",
                            Position.SHOOTING_GUARD, "SG",
                            Position.SMALL_FORWARD, "SF",
                            Position.POWER_FORWARD, "PF",
                            Position.CENTER, "C",
                            Position.UTILITY, "UTIL"
                    )
            ),
            Position.class
    );

    private AbbreviatedPositionSerializationUtility(
            @NotNull final EnumMap<Position, String> serializationsByValue,
            @NotNull final Class<Position> keyClass
    ) {
        super(serializationsByValue, keyClass);
    }

    public static AbbreviatedPositionSerializationUtility getInstance() {
        return INSTANCE;
    }
}
