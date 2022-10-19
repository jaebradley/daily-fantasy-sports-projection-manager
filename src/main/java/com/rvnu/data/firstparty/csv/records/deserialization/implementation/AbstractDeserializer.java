package com.rvnu.data.firstparty.csv.records.deserialization.implementation;

import com.rvnu.data.firstparty.csv.record.deserialization.interfaces.Deserializer;
import com.rvnu.models.thirdparty.iso.NaturalNumber;
import com.rvnu.models.thirdparty.iso.PositiveInteger;
import io.vavr.control.Either;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class AbstractDeserializer<Result, Column extends Enum<Column>, Error extends Enum<Error>> implements com.rvnu.data.firstparty.csv.records.deserialization.interfaces.Deserializer<Result, Error> {
    @NotNull
    private final Charset characterSet;

    @NotNull
    private final CSVFormat format;

    @NotNull
    private final Deserializer<Result, Column, Error> resultDeserializer;

    protected AbstractDeserializer(
            @NotNull final Charset characterSet,
            @NotNull final CSVFormat format,
            @NotNull final Deserializer<Result, Column, Error> resultDeserializer
    ) {
        this.characterSet = characterSet;
        this.format = format;
        this.resultDeserializer = resultDeserializer;
    }

    @Override
    public Map<Error, PositiveInteger> deserialize(@NotNull final InputStream inputStream, @NotNull final Consumer<Result> resultConsumer) throws UnableToDeserializeRecords {
        final Map<Error, PositiveInteger> countsByError = new HashMap<>();

        try (final CSVParser parser = CSVParser.parse(
                inputStream,
                characterSet,
                format
        )) {
            for (final CSVRecord record : parser) {
                final Either<Error, Result> deserializationResult = resultDeserializer.deserialize(
                        column -> Optional.ofNullable(record.get(column.ordinal()))
                );

                if (deserializationResult.isLeft()) {
                    countsByError.merge(deserializationResult.getLeft(), PositiveInteger.ONE, (positiveInteger, positiveInteger2) -> {
                        try {
                            return new PositiveInteger(positiveInteger.getValue() + positiveInteger2.getValue());
                        } catch (PositiveInteger.ValueMustBePositive | NaturalNumber.ValueMustNotBeNegative e) {
                            throw new IllegalStateException();
                        }
                    });
                } else {
                    resultConsumer.accept(deserializationResult.get());
                }
            }
        } catch (IOException e) {
            throw new UnableToDeserializeRecords();
        }

        return countsByError;
    }
}
