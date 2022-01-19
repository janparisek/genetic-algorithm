package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Population;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

import static de.h_da.fbi.ga.mo12.parisek.genetics.Population.Algorithm.*;

public class Main {
    public static void main(final String[] args) {

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
            population.generateNext(LAB3);

            // Update peak candidate
            if(peak.getFitness() < population.getBestCandidate().getFitness()) {
                peak = new Protein(population.getBestCandidate());
            }

            // Do logging
            l.log(population, peak.getFitness());
        }


        peak.reconstructPhenotype(); // TODO: Shouldn't need to be called
        Renderer r = new Renderer();
        r.renderProtein(peak);

        System.out.println("Done. Peak fitness was " + peak.getFitness());
    }
}
