package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.ContestPlayerId;
import com.rvnu.models.thirdparty.fanduel.nba.FixtureListId;
import com.rvnu.models.thirdparty.fanduel.nba.PlayerId;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ContestPlayerIdSerializationUtility implements Deserializer<ContestPlayerId> {
    @NotNull
    private static final ContestPlayerIdSerializationUtility INSTANCE = new ContestPlayerIdSerializationUtility(
            FixtureListIdSerializationUtility.getInstance(),
            PlayerIdSerializationUtility.getInstance()
    );

    @NotNull
    private final Deserializer<FixtureListId> fixtureListIdDeserializer;

    @NotNull
    private final Deserializer<PlayerId> playerIdDeserializer;

    private ContestPlayerIdSerializationUtility(
            @NotNull final Deserializer<FixtureListId> fixtureListIdDeserializer,
            @NotNull final Deserializer<PlayerId> playerIdDeserializer) {
        this.fixtureListIdDeserializer = fixtureListIdDeserializer;
        this.playerIdDeserializer = playerIdDeserializer;
    }

    @Override
    public Optional<ContestPlayerId> deserialize(@NotNull final String value) {
        final String[] parts = value.split("-");
        if (2 != parts.length) {
            return Optional.empty();
        }

        return fixtureListIdDeserializer.deserialize(parts[0])
                .flatMap(fixtureListId -> playerIdDeserializer.deserialize(parts[1])
                        .map(playerId -> new ContestPlayerId(fixtureListId, playerId)));
    }

    @NotNull
    public static ContestPlayerIdSerializationUtility getInstance() {
        return INSTANCE;
    }
}
