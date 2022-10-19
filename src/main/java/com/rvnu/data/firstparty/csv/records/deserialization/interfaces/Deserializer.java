package com.rvnu.data.firstparty.csv.records.deserialization.interfaces;

import com.rvnu.models.thirdparty.iso.PositiveInteger;

import java.io.InputStream;
import java.util.Map;
import java.util.function.Consumer;

public interface Deserializer<Record, Error extends Enum<Error>> {
    class UnableToDeserializeRecords extends Exception {
    }

    Map<Error, PositiveInteger> deserialize(InputStream inputStream, Consumer<Record> recordConsumer) throws UnableToDeserializeRecords;
}
