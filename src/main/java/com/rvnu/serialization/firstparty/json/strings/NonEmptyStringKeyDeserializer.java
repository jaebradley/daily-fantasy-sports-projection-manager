package com.rvnu.serialization.firstparty.json.strings;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class NonEmptyStringKeyDeserializer extends KeyDeserializer {
    @Override
    public NonEmptyString deserializeKey(@NotNull final String key, @NotNull final DeserializationContext deserializationContext) throws IOException {
        final Optional<NonEmptyString> deserializedValue = NonEmptyStringSerializationUtility.getInstance().deserialize(key);
        if (deserializedValue.isEmpty()) {
            throw JsonMappingException.from(deserializationContext, "Unable to parse key as non-empty string");
        }
        return deserializedValue.get();
    }
}
