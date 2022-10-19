package com.rvnu.data.firstparty.csv.records.deserialization.implementation;

import com.rvnu.data.firstparty.csv.records.printing.interfaces.Printer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

public abstract class AbstractPrinter<Record> implements Printer<Record> {
    @NotNull
    private final CSVFormat csvFormat;

    @NotNull
    private final com.rvnu.data.firstparty.csv.record.printing.interfaces.Printer<Record> recordPrinter;

    protected AbstractPrinter(
            @NotNull final CSVFormat csvFormat,
            @NotNull final com.rvnu.data.firstparty.csv.record.printing.interfaces.Printer<Record> recordPrinter
    ) {
        this.csvFormat = csvFormat;
        this.recordPrinter = recordPrinter;
    }

    @Override
    public void print(@NotNull final Writer writer, @NotNull final Iterator<Record> records) throws IOException {
        try (final CSVPrinter printer = new CSVPrinter(writer, csvFormat)) {
            while (records.hasNext()) {
                recordPrinter.print(records.next(), v -> {
                    try {
                        printer.print(v);
                    } catch (IOException e) {
                        // TODO: @jbradley this is not graceful
                        // Revisit to find a graceful way to recover
                        throw new RuntimeException("unable to write file", e);
                    }
                });
                printer.println();
            }
        }
    }
}
