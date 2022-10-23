package com.rvnu.serialization.firstparty.json.strings;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.strings.NonEmptyStringSerializationUtility;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Optional;

public class NonEmptyStringDeserializer extends JsonDeserializer<NonEmptyString> {
    @Override
    public NonEmptyString deserialize(@NotNull final JsonParser jsonParser, @NotNull final DeserializationContext deserializationContext) throws IOException, JacksonException {
        final String value = jsonParser.getValueAsString();
        final Optional<NonEmptyString> deserializedValue = NonEmptyStringSerializationUtility.getInstance().deserialize(value);
        if (deserializedValue.isEmpty()) {
            throw new InvalidFormatException(jsonParser, "Unable to parse value", value, NonEmptyString.class);
        }
        return deserializedValue.get();
    }
}
