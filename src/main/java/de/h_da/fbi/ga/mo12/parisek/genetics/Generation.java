package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.ArrayList;
import java.util.List;

public class Generation {
    private List<Protein> candidates = new ArrayList<>();
    private Integer number = 0;
    private Double averageFitness = 0d;
    private Protein bestCandidate;
    private final static Integer GENERATION_SIZE = 100;

    public Integer getNumber() {
        return number;
    }

    public Double getAverageFitness() {
        return averageFitness;
    }

    public Protein getBestCandidate() {
        return bestCandidate;
    }

    public Generation(Sequence sequence) {
        Protein protein = new Protein(sequence);
        bestCandidate = protein;
        candidates.add(protein);
        averageFitness += protein.getFitness();

        for(int i = 1; i < GENERATION_SIZE; ++i) {
            protein = new Protein(sequence);
            candidates.add(protein);
            averageFitness += protein.getFitness();

            if(protein.getFitness() > bestCandidate.getFitness()) {
                bestCandidate = protein;
            }

        }

        averageFitness = averageFitness / (double) GENERATION_SIZE;

    }

    public Generation generateNext() {
        //TODO: Genetischer Algorithmus hier
        return null;
    }

}
