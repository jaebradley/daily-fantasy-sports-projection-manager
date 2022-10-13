package com.rvnu.serialization.firstparty.collections;

import com.rvnu.models.firstparty.NonEmptyLinkedHashSet;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Optional;

public abstract class NonEmptyLinkedHashSetSerializationUtility<T> implements Deserializer<NonEmptyLinkedHashSet<T>> {
    protected NonEmptyLinkedHashSetSerializationUtility(@NotNull final Deserializer<T> valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    @NotNull
    private final Deserializer<T> valueDeserializer;

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

    protected abstract Iterator<String> calculatePartsFromValue(@NotNull final String value);
}
