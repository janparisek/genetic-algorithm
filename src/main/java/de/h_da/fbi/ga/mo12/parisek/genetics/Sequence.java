package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.ArrayList;
import java.util.List;

public class Sequence {
    private final List<Boolean> sequence;

    public Sequence (String sequence) throws Exception {
        this.sequence = new ArrayList<Boolean>();

        // Parse sequence from string
        for (
            Character s : sequence.toCharArray()
        ) {

            Boolean isHydrophilic = null;
            if(s.equals('0')) {
                isHydrophilic = false;
            } else if (s.equals('1')) {
                isHydrophilic = true;
            }

            if(isHydrophilic == null) {
                throw new Exception("Invalid character spotted when trying to parse sequence.");
            }

            this.sequence.add(isHydrophilic);

        }

    }

    public List<Boolean> getSequence() {
        return sequence;
    }


}
