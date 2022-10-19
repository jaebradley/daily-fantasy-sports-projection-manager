package com.rvnu.serialization.firstparty.collections;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class NonEmptyLinkedHashSetSerializationUtility<T> implements Deserializer<NonEmptyLinkedHashSet<T>>, Serializer<NonEmptyLinkedHashSet<T>> {
    protected NonEmptyLinkedHashSetSerializationUtility(@NotNull final Deserializer<T> valueDeserializer, @NotNull final Serializer<T> valueSerializer) {
        this.valueDeserializer = valueDeserializer;
        this.valueSerializer = valueSerializer;
    }

    @NotNull
    private final Deserializer<T> valueDeserializer;

    @NotNull
    private final Serializer<T> valueSerializer;

    @Override
    public Optional<NonEmptyLinkedHashSet<T>> deserialize(@NotNull final String value) {
        final NonEmptyLinkedHashSet.Builder<T> builder = new NonEmptyLinkedHashSet.Builder<>();
        for (final Iterator<String> it = calculatePartsFromValue(value); it.hasNext(); ) {
            final Optional<T> deserializedValue = valueDeserializer.deserialize(it.next());
            if (deserializedValue.isEmpty() || !builder.add(deserializedValue.get())) {
                return Optional.empty();
            }
        }
        try {
            return Optional.of(builder.build());
        } catch (NonEmptyLinkedHashSet.CollectionCannotBeEmpty e) {
            return Optional.empty();
        }
    }

    @Override
    public String serialize(@NotNull final NonEmptyLinkedHashSet<T> value) {
        return value.stream().map(valueSerializer::serialize).collect(Collectors.joining("/"));
    }

    protected abstract Iterator<String> calculatePartsFromValue(@NotNull final String value);
}
