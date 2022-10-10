package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class AbbreviatedPositionsSerializationUtility implements Deserializer<Set<Position>> {
    @NotNull
    private static final AbbreviatedPositionsSerializationUtility INSTANCE = new AbbreviatedPositionsSerializationUtility(
            AbbreviatedPositionSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<Position> positionDeserializer;

    private AbbreviatedPositionsSerializationUtility(@NotNull final Deserializer<Position> positionDeserializer) {
        this.positionDeserializer = positionDeserializer;
    }

    @Override
    public Optional<Set<Position>> deserialize(@NotNull final String value) {
        final Set<Position> positions = new HashSet<>();
        final String[] parts = value.split("/");
        for (final String part : parts) {
            final Optional<Position> parsedPosition = positionDeserializer.deserialize(part);
            if (parsedPosition.isEmpty()) {
                return Optional.empty();
            }

            positions.add(parsedPosition.get());
        }

        return Optional.of(positions);
    }

    public static AbbreviatedPositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
