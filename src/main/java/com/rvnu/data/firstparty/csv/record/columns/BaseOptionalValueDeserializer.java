package com.rvnu.data.firstparty.csv.record.columns;

import com.rvnu.data.firstparty.csv.record.interfaces.Record;
import com.rvnu.serialization.firstparty.interfaces.Deserializer;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class BaseOptionalValueDeserializer<Value, Column extends Enum<Column>, Error extends Enum<Error>> implements com.rvnu.data.firstparty.csv.record.interfaces.Deserializer<Optional<Value>, Column, Error> {
    @NotNull
    private final com.rvnu.serialization.firstparty.interfaces.Deserializer<Value> valueDeserializer;

    @NotNull
    private final Column column;

    @NotNull
    private final Error columnDoesNotExistError;

    @NotNull
    private final Error invalidValueError;

    public BaseOptionalValueDeserializer(
            @NotNull final Deserializer<Value> valueDeserializer,
            @NotNull final Column column,
            @NotNull final Error columnDoesNotExistError,
            @NotNull final Error invalidValueError
    ) {
        this.valueDeserializer = valueDeserializer;
        this.column = column;
        this.columnDoesNotExistError = columnDoesNotExistError;
        this.invalidValueError = invalidValueError;
    }

    @Override
    public @NotNull Either<Error, Optional<Value>> deserialize(Record<Column> record) {
        return record
                .getValue(column)
                .<Either<Error, Optional<Value>>>map(v -> {
                    if (v.isEmpty()) {
                        return Either.right(Optional.empty());
                    }
                    return valueDeserializer
                            .deserialize(v)
                            .map(deserializedValue -> Either.<Error, Optional<Value>>right(Optional.of(deserializedValue)))
                            .orElseGet(() -> Either.left(invalidValueError));
                }).orElseGet(() -> Either.left(columnDoesNotExistError));
    }
}
