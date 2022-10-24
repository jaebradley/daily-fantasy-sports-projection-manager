package com.rvnu.serialization.firstparty.time;

import com.rvnu.serialization.firstparty.interfaces.Serializer;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class YearMonthDaySerializationUtility implements Serializer<ZonedDateTime> {
    @NotNull
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @NotNull
    private static final YearMonthDaySerializationUtility INSTANCE = new YearMonthDaySerializationUtility();

    private YearMonthDaySerializationUtility() {
    }

    @Override
    public String serialize(@NotNull final ZonedDateTime value) {
        return FORMATTER.format(value);
    }

    @NotNull
    public static YearMonthDaySerializationUtility getInstance() {
        return INSTANCE;
    }
}
