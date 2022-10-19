package com.rvnu.serialization.firstparty.numbers;

import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

public abstract class NaturalNumberSerializationUtility<T extends NaturalNumber> implements Deserializer<T>, Serializer<T> {
    @NotNull
    private static final NaturalNumberSerializationUtility<NaturalNumber> DEFAULT_INSTANCE = new NaturalNumberSerializationUtility<>(NumberFormat.getIntegerInstance(Locale.US)) {
        @Override
        protected @NotNull NaturalNumber construct(@NotNull final NaturalNumber value) {
            return value;
        }
    };

    @NotNull
    private final NumberFormat format;

    protected NaturalNumberSerializationUtility(@NotNull final NumberFormat format) {
        this.format = format;
    }

    @Override
    public Optional<T> deserialize(@NotNull final String value) {
        final NaturalNumber integer;
        try {
            integer = new NaturalNumber(format.parse(value).longValue());
        } catch (NaturalNumber.ValueMustNotBeNegative | ParseException e) {
            return Optional.empty();
        }

        return Optional.of(construct(integer));
    }

    @Override
    public String serialize(@NotNull final T value) {
        return format.format(value.getValue());
    }

    @NotNull
    protected abstract T construct(final NaturalNumber value);

    @NotNull
    public static NaturalNumberSerializationUtility<NaturalNumber> getDefaultInstance() {
        return DEFAULT_INSTANCE;
    }
}
