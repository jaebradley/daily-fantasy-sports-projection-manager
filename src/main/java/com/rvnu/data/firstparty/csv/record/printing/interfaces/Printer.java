package com.rvnu.data.firstparty.csv.record.printing.interfaces;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Printer<T> {
    void print(@NotNull T record, @NotNull Consumer<String> valueConsumer);
}
