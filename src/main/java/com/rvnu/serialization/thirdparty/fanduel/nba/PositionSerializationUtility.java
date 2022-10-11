package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.serialization.firstparty.enumerations.AbstractEnumeratedValuesSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PositionSerializationUtility extends AbstractEnumeratedValuesSerializationUtility<Position> {
    @NotNull
    private static final PositionSerializationUtility INSTANCE = new PositionSerializationUtility(
            new EnumMap<Position, String>(
                    Stream.of(
                            Map.entry(Position.CENTER, "C"),
                            Map.entry(Position.POINT_GUARD, "PG"),
                            Map.entry(Position.SHOOTING_GUARD, "SG"),
                            Map.entry(Position.SMALL_FORWARD, "SF"),
                            Map.entry(Position.POWER_FORWARD, "PF")
                    ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            Position.class
    );

    private PositionSerializationUtility(
            @NotNull final EnumMap<Position, String> serializationsByValue,
            @NotNull final Class<Position> keyClass
    ) {
        super(serializationsByValue, keyClass);
    }

    @NotNull
    public static PositionSerializationUtility getInstance() {
        return INSTANCE;
    }
}
