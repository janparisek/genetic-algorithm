package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;
import org.junit.jupiter.api.Test;

public class SequenceTest {

    @Test
    public void stringGetsParsedCorrectly() {
        String testSequence = "01111111000011110111110011111110";
        try {
            Sequence sequence = new Sequence(testSequence);
        } catch (Exception e) {
            assert false;
        }

    }

    @Test
    public void stringParserRecognizesInvalidCharacter() {
        String testSequence = "012";
        try {
            Sequence sequence = new Sequence(testSequence);
        } catch (Exception e) {
            assert true;
        }
        assert false;
    }

}
