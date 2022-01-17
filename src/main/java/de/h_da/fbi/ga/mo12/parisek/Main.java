package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Generation;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

import static de.h_da.fbi.ga.mo12.parisek.genetics.Generation.Algorithm.*;

public class Main {
    public static void main(final String[] args) {

        Sequence sequence = null;
        try {
            sequence = new Sequence(Examples.SEQ20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Generation generation = new Generation(sequence);

        /*
        Renderer r = new Renderer();
        r.renderProtein(generation.getBestCandidate());
         */

        Logger l = new Logger();
        Protein peak = generation.getBestCandidate();
        l.log(generation, peak.getFitness());

        final int ITERATIONS = 150;
        for(int i = 0; i < ITERATIONS; ++i) {
            generation.generateNext(LAB3);

            // Update peak candidate
            if(peak.getFitness() < generation.getBestCandidate().getFitness()) {
                peak = generation.getBestCandidate();
            }

            // Do logging
            l.log(generation, peak.getFitness());
        }

        System.out.println("Done.");
    }
}
