package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Generation;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

public class Main {
    public static void main(final String[] args) {

        Sequence sequence = null;
        try {
            sequence = new Sequence(Examples.SEQ20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Generation generation = new Generation(sequence);
        //Protein protein = new Protein(sequence);

        Renderer r = new Renderer();
        r.renderProtein(generation.getBestCandidate());

        Logger l = new Logger();
        l.log(generation);

    }
}
