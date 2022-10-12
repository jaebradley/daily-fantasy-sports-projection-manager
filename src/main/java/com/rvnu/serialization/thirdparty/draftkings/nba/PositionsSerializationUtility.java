package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Optional;

public class PositionsSerializationUtility implements Deserializer<LinkedHashSet<Position>> {
    @NotNull
    private static final PositionsSerializationUtility INSTANCE = new PositionsSerializationUtility(
            AbbreviatedPositionSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<Position> positionDeserializer;

    private PositionsSerializationUtility(@NotNull final Deserializer<Position> positionDeserializer) {
        this.positionDeserializer = positionDeserializer;
    }

    @Override
    public Optional<LinkedHashSet<Position>> deserialize(@NotNull final String value) {
        final String[] parts = value.split(",");
        if (0 == parts.length) {
            return Optional.empty();
        }

        final LinkedHashSet<Position> positions = new LinkedHashSet<>();

        for (final String part : parts) {
            final Optional<Position> deserializedPosition = positionDeserializer.deserialize(part);
            if (deserializedPosition.isEmpty()) {
                return Optional.empty();
            }

            if (!positions.add(deserializedPosition.get())) {
                return Optional.empty();
            }
        }

        return Optional.of(positions);
    }

    @NotNull
    public static PositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
