package com.rvnu.serialization.firstparty.json.numbers;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class PositiveIntegerKeyDeserializer extends KeyDeserializer {
    @Override
    public PositiveInteger deserializeKey(@NotNull final String key, @NotNull final DeserializationContext deserializationContext) throws IOException {
        final Optional<PositiveInteger> deserializedValue = PositiveIntegerSerializationUtility.getDefaultInstance().deserialize(key);
        if (deserializedValue.isEmpty()) {
            throw JsonMappingException.from(deserializationContext, "Unable to parse key as positive integer");
        }
        return deserializedValue.get();
    }
}
