package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Population;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

public class Main {
    public static void main(final String[] args) {
        long before = System.nanoTime();

        Sequence sequence = null;
        try {
            sequence = new Sequence(Settings.SEQUENCE);
        } catch (Exception e) {
            System.err.println("Provided genetic sequence was invalid.");
            System.exit(1);
        }
        Population population = new Population(sequence);

        Logger l = new Logger();
        Protein peak = new Protein(population.getBestCandidate());
        l.log(population, peak.getFitness());

        for(int i = 0; i < Settings.GENERATIONS; ++i) {
            population.generateNext();

            // Update peak candidate
            if(peak.getFitness() < population.getBestCandidate().getFitness()) {
                peak = new Protein(population.getBestCandidate());
            }

            // Do logging
            l.log(population, peak.getFitness());
        }
        population.close();

        // Show best candidate
        Renderer r = new Renderer();
        r.renderProtein(peak);

        System.out.println("Done. Peak fitness was " + peak.getFitness());


        long after = System.nanoTime();
        long durationMs = (after - before)/1_000_000;
        System.out.println(durationMs);
    }
}
