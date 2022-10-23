package com.rvnu.serialization.firstparty.json.numbers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import com.rvnu.serialization.firstparty.numbers.PositiveIntegerSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class PositiveIntegerDeserializer extends JsonDeserializer<PositiveInteger> {
    @Override
    public PositiveInteger deserialize(@NotNull final JsonParser jsonParser, @NotNull final DeserializationContext deserializationContext) throws IOException, JacksonException {
        final String value = jsonParser.getValueAsString();
        final Optional<PositiveInteger> deserializedValue = PositiveIntegerSerializationUtility.getDefaultInstance().deserialize(value);
        if (deserializedValue.isEmpty()) {
            throw new InvalidFormatException(jsonParser, "Unable to parse value", value, PositiveInteger.class);
        }
        return deserializedValue.get();
    }
}
