package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PositionsSerializationUtility implements Deserializer<Set<Position>> {
    @NotNull
    private static final PositionsSerializationUtility INSTANCE = new PositionsSerializationUtility(
            PositionSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<Position> positionDeserializer;

    private PositionsSerializationUtility(@NotNull final Deserializer<Position> positionDeserializer) {
        this.positionDeserializer = positionDeserializer;
    }

    @Override
    public Optional<Set<Position>> deserialize(@NotNull final String value) {
        final String[] parts = value.split("/");
        if (0 == parts.length) {
            return Optional.empty();
        }

        final Set<Position> positions = new HashSet<>();

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
