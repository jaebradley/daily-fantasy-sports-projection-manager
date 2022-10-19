package com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.printing;

import com.rvnu.applications.nba.draftkings.ProjectionsMerger;
import com.rvnu.data.firstparty.csv.record.printing.interfaces.Printer;
import com.rvnu.data.firstparty.csv.records.deserialization.implementation.AbstractPrinter;
import org.apache.commons.csv.CSVFormat;
import org.jetbrains.annotations.NotNull;

public class DraftKingsProjectionsPrinter extends AbstractPrinter<ProjectionsMerger.Projections> {
    @NotNull
    private static final DraftKingsProjectionsPrinter INSTANCE = new DraftKingsProjectionsPrinter(
            CSVFormat.DEFAULT
                    .builder()
                    .setHeader(com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.Printer.Column.class)
                    .build(),
            com.rvnu.data.firstparty.csv.draftkings.record.nba.projections.Printer.getInstance()
    );

    private DraftKingsProjectionsPrinter(@NotNull CSVFormat csvFormat, @NotNull Printer<ProjectionsMerger.Projections> projectionsPrinter) {
        super(csvFormat, projectionsPrinter);
    }

    @NotNull
    public static DraftKingsProjectionsPrinter getInstance() {
        return INSTANCE;
    }
}
