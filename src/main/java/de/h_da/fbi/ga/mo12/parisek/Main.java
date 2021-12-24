package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

public class Main {
    public static void main(final String[] args) {
        String sequence = "10100110100101100101";
        Sequence s = null;
        try {
             s = new Sequence(sequence);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Protein p = new Protein(s, true);

        //System.out.println("Current fitness" + p.calculateFitness());

        Renderer r = new Renderer();
        r.renderProtein(p);

    }


}
