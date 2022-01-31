package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Population;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Logger {
    private final static String folder = "logs";
    private final static String filename = "population.csv";
    private final FileOutputStream stream;

    public Logger() {
        if (!new File(folder).exists()) {
            new File(folder).mkdirs();
        }
        File file = new File(folder + File.separator + filename);
        System.out.println("Saving log to " + file.getAbsolutePath());

        FileOutputStream stream1;
        try {
            stream1 = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            stream1 = null;
            System.err.println("Could not open file " + file.getAbsolutePath());
            System.exit(2);
        }
        stream = stream1;

        try {
            stream.write("Generation,Avg. fitness,Best fitness,Peak fitness,Hp contacts,Overlaps,Mutation rate\n".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String prompt) {
        try {
            stream.write(prompt.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(Population population, Double peakFitness) {
        String line = population.getGenerationNumber() +
            "," +
            population.getAverageFitness() +
            "," +
            population.getBestCandidate().getFitness() +
            "," +
            peakFitness +
            "," +
            population.getBestCandidate().getHhBonds() +
            "," +
            population.getBestCandidate().getOverlaps() +
            "," +
            population.getMutationCount() +
            "\n";
        log(line);
    }

    public void logDebug(Population population) {
        StringBuilder line = new StringBuilder();

        for(Protein one : population.getPopulation()) {
            line.append(one.getFitness());
            line.append(",");
        }
        line.append("\n");

        log(line.toString());
    }

}
