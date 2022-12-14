package com.rvnu.data.firstparty.csv.record.deserialization.interfaces;

import io.vavr.control.Either;
import org.jetbrains.annotations.NotNull;

public interface Deserializer<Record, Column extends Enum<Column>, Error extends Enum<?>> {
    @NotNull
    Either<Error, Record> deserialize(@NotNull com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Record<Column> record);
}