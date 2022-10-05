package com.rvnu.data.firstparty.csv.record.columns;

import com.rvnu.data.firstparty.csv.record.interfaces.Deserializer;
import com.rvnu.data.firstparty.csv.record.interfaces.Record;
import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

public class BaseValueDeserializer<Value, Column extends Enum<Column>, Error extends Enum<Error>> implements Deserializer<Value, Column, Error> {
    private final com.rvnu.serialization.firstparty.interfaces.Deserializer<Value> valueDeserializer;
    private final Column column;
    private final Error columnDoesNotExistError;
    private final Error invalidValueError;

    public BaseValueDeserializer(
            final com.rvnu.serialization.firstparty.interfaces.Deserializer<Value> valueDeserializer,
            final Column column,
            final Error columnDoesNotExistError,
            final Error invalidValueError
    ) {
        this.valueDeserializer = valueDeserializer;
        this.column = column;
        this.columnDoesNotExistError = columnDoesNotExistError;
        this.invalidValueError = invalidValueError;
    }

    @Override
    public @NotNull Either<Error, Value> deserialize(final Record<Column> record) {
        return record.getValue(column)
                .map(v -> valueDeserializer
                        .deserialize(v)
                        .map(Either::<Error, Value>right)
                        .orElseGet(() -> Either.left(invalidValueError)))
                .orElseGet(() -> Either.left(columnDoesNotExistError));
    }
}
