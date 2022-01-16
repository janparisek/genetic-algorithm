package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Generation;

import javax.imageio.ImageIO;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Logger {
    private final static String folder = "logs";
    private final static String filename = "population.csv";
    private final FileOutputStream stream;

    public Logger() {
        if (new File(folder).exists() == false) {
            new File(folder).mkdirs();
        }
        File file = new File(folder + File.separator + filename);
        System.out.println("Saving log to " + file.getAbsolutePath());

        FileOutputStream stream1;
        try {
            stream1 = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            stream1 = null;
            e.printStackTrace();
        }
        stream = stream1;

        try {
            stream.write("Generation,Avg. fitness,Best fitness,Peak fitness,Hp contacts,Overlaps\n".getBytes(StandardCharsets.UTF_8));
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

    public void log(Generation generation) {
        StringBuilder line = new StringBuilder()
            .append(generation.getNumber())
            .append(",")
            .append(generation.getAverageFitness())
            .append(",")
            .append(generation.getBestCandidate().getFitness())
            .append(",")
            .append("NaN")
            .append(",")
            .append(generation.getBestCandidate().getProperties().getHhBonds())
            .append(",")
            .append(generation.getBestCandidate().getProperties().getOverlaps())
            .append("\n");
        log(line.toString());
    }

}
