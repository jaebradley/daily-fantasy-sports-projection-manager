package com.rvnu.serialization.thirdparty.fanduel.nba;

import com.rvnu.models.thirdparty.fanduel.nba.Position;
import com.rvnu.serialization.firstparty.collections.NonEmptyLinkedHashSetSerializationUtility;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class PositionsSerializationUtility extends NonEmptyLinkedHashSetSerializationUtility<Position> {
    @NotNull
    private static final PositionsSerializationUtility INSTANCE = new PositionsSerializationUtility(
            PositionSerializationUtility.getInstance()
    );

    private PositionsSerializationUtility(@NotNull final Deserializer<Position> positionDeserializer) {
        super(positionDeserializer);
    }

    @Override
    protected final Iterator<String> calculatePartsFromValue(@NotNull final String value) {
        return Arrays.stream(value.split("/")).iterator();
    }

    @NotNull
    public static PositionsSerializationUtility getInstance() {
        return INSTANCE;
    }
}
