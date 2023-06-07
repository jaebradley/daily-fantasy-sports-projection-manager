package com.rvnu.serialization.thirdparty.stokastic.mlb;

import com.rvnu.models.thirdparty.stokastic.mlb.Position;
import com.rvnu.serialization.firstparty.collections.NonEmptyLinkedHashSetSerializationUtility;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class PositionsSerializationUtility extends NonEmptyLinkedHashSetSerializationUtility<Position> {
    @NotNull
    private static final PositionsSerializationUtility INSTANCE = new PositionsSerializationUtility(
            PositionSerializationUtility.getInstance(),
            PositionSerializationUtility.getInstance()
    );

    private PositionsSerializationUtility(@NotNull final Deserializer<Position> valueDeserializer, @NotNull final Serializer<Position> valueSerializer) {
        super(valueDeserializer, valueSerializer);
    }

    @Override
    protected final Iterator<String> calculatePartsFromValue(@NotNull String value) {
        return Arrays.stream(value.split("/")).iterator();
    }

    @NotNull
    public static PositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
