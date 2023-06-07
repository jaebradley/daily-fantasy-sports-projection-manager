package com.rvnu.serialization.thirdparty.stokastic.mlb;

import com.rvnu.models.thirdparty.stokastic.mlb.Position;
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
                            Map.entry(Position.CATCHER, "C"),
                            Map.entry(Position.PITCHER, "P"),
                            Map.entry(Position.FIRST_BASE, "1B"),
                            Map.entry(Position.SECOND_BASE, "2B"),
                            Map.entry(Position.SHORTSTOP, "SS"),
                            Map.entry(Position.THIRD_BASE, "3B"),
                            Map.entry(Position.OUTFIELD, "OF")
                    ).collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))
            ),
            Position.class
    );

    private PositionSerializationUtility(
            @NotNull final EnumMap<Position, String> serializationsByValue,
            @NotNull final Class<Position> keyClass) {
        super(serializationsByValue, keyClass);
    }

    @NotNull
    public static PositionSerializationUtility getInstance() {
        return INSTANCE;
    }
}
