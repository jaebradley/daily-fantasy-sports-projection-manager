package com.rvnu.serialization.thirdparty.draftkings.nba;

import com.rvnu.models.thirdparty.draftkings.nba.Position;
import com.rvnu.serialization.firstparty.collections.NonEmptyLinkedHashSetSerializationUtility;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class AbbreviatedPositionsSerializationUtility extends NonEmptyLinkedHashSetSerializationUtility<Position> {
    @NotNull
    private static final AbbreviatedPositionsSerializationUtility INSTANCE = new AbbreviatedPositionsSerializationUtility(
            AbbreviatedPositionSerializationUtility.getInstance(),
            AbbreviatedPositionSerializationUtility.getInstance()
    );

    private AbbreviatedPositionsSerializationUtility(@NotNull final Deserializer<Position> valueDeserializer, @NotNull final Serializer<Position> valueSerializer) {
        super(valueDeserializer, valueSerializer);
    }

    @Override
    protected final Iterator<String> calculatePartsFromValue(@NotNull String value) {
        return Arrays.stream(value.split("/")).iterator();
    }

    public static AbbreviatedPositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
