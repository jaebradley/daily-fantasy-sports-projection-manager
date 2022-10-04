package com.rvnu.serialization.firstparty.enumerations;

import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractEnumeratedValuesSerializationUtility<T extends Enum<T>> implements Deserializer<T> {
    @NotNull
    private final EnumMap<T, String> serializationsByValue;

    @NotNull
    private final Map<String, T> valuesBySerialization;

    public AbstractEnumeratedValuesSerializationUtility(@NotNull final EnumMap<T, String> serializationsByValue, @NotNull final Class<T> keyClass) {
        this.serializationsByValue = serializationsByValue;
        this.valuesBySerialization = serializationsByValue
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                Map.Entry::getValue,
                                Map.Entry::getKey
                        )
                );

        if (keyClass.getEnumConstants().length != serializationsByValue.size()) {
            throw new RuntimeException("All enumerated values should be serialized");
        }
    }

    @Override
    public Optional<T> deserialize(@NotNull final String value) {
        return Optional.ofNullable(valuesBySerialization.get(value));
    }
}
