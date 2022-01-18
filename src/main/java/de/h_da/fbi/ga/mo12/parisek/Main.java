package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Population;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

import static de.h_da.fbi.ga.mo12.parisek.genetics.Population.Algorithm.*;

public class Main {
    public static void main(final String[] args) {

        Sequence sequence = null;
        try {
            sequence = new Sequence(Examples.SEQ50);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Population population = new Population(sequence);

        Logger l = new Logger();
        Protein peak = new Protein(population.getBestCandidate());
        l.log(population, peak.getFitness());

        final int ITERATIONS = 10000;
        for(int i = 0; i < ITERATIONS; ++i) {
            population.generateNext(LAB3);

            // Update peak candidate
            if(peak.getFitness() < population.getBestCandidate().getFitness()) {
                peak = new Protein(population.getBestCandidate());
            }

            // Do logging
            l.log(population, peak.getFitness());
        }


        peak.update();
        Renderer r = new Renderer();
        r.renderProtein(peak);

        System.out.println("Done. Peak fitness was " + peak.getFitness());
    }
}
