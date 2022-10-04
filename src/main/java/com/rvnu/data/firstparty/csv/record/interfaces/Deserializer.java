package com.rvnu.data.firstparty.csv.record.interfaces;

import io.vavr.control.Either;

public interface Deserializer<Record, Column extends Enum<Column>, Error extends Enum<?>> {
    Either<Error, Record> deserialize(com.rvnu.data.firstparty.csv.record.interfaces.Record<Column> record);
}