package com.rvnu.data.firstparty.csv.records.printing.interfaces;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;

public interface Printer<Record> {
    void print(@NotNull Writer writer, @NotNull Iterator<Record> records) throws IOException;
}
