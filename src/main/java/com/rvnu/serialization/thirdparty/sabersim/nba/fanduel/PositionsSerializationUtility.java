package com.rvnu.serialization.thirdparty.sabersim.nba.fanduel;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.serialization.firstparty.collections.NonEmptyLinkedHashSetSerializationUtility;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import com.rvnu.serialization.thirdparty.fanduel.nba.PositionSerializationUtility;
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
    protected Iterator<String> calculatePartsFromValue(@NotNull final String value) {
        return Arrays.stream(value.split(",")).iterator();
    }

    @NotNull
    public static PositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
