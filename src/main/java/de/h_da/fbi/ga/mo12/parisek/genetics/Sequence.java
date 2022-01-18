package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final List<Boolean> sequence;

    public Sequence (String sequence) throws Exception {
        this.sequence = new ArrayList<>();

        // Parse sequence from string
        for ( Character s : sequence.toCharArray() ) {

            Boolean isHydrophobic = null;
            if(s.equals('0')) {
                isHydrophobic = false;
            } else if (s.equals('1')) {
                isHydrophobic = true;
            } else {
                throw new Exception("Invalid character spotted when trying to parse sequence.");
            }
            this.sequence.add(isHydrophobic);

        }

    }

    public List<Boolean> getSequence() {
        return sequence;
    }


}
