package de.h_da.fbi.ga.mo12.parisek;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logger {

    public void generateLog() {
        String firstLine = "Generation\tAverage fitness\tBest fitness\tPeak fitness\tHydrophobe contacts\tOverlaps";
    }

    public PrintWriter getPrintWriter() {
        String folder = "/tmp/alex/ga";
        /*
        Date now = new Date();
        Long unixTime = now.getTime();
        String filename = unixTime.toString() + ".csv";
         */
        String filename = "population.csv";
        if (new File(folder).exists() == false) new File(folder).mkdirs();
        File f = new File(folder + File.separator + filename);

        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(f);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return printWriter;
    }
}
