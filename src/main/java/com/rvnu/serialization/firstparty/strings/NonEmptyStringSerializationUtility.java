package com.rvnu.serialization.firstparty.strings;

import com.rvnu.models.thirdparty.strings.NonEmptyString;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class NonEmptyStringSerializationUtility implements Deserializer<NonEmptyString> {
    private static final NonEmptyStringSerializationUtility INSTANCE = new NonEmptyStringSerializationUtility();

    private NonEmptyStringSerializationUtility() {
    }

    @Override
    public Optional<NonEmptyString> deserialize(@NotNull final String value) {
        try {
            return Optional.of(new NonEmptyString(value));
        } catch (NonEmptyString.ValueMustNotBeEmpty valueMustNotBeEmpty) {
            return Optional.empty();
        }
    }

    public static NonEmptyStringSerializationUtility getInstance() {
        return INSTANCE;
    }
}
