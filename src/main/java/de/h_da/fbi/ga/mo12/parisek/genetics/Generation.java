package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generation {
    private List<Protein> candidates = new ArrayList<>();
    private Integer number = 0;
    private Double averageFitness = 0d;
    private Double totalFitness = 0d;
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
        totalFitness += protein.getFitness();

        for(int i = 1; i < GENERATION_SIZE; ++i) {
            protein = new Protein(sequence);
            candidates.add(protein);
            totalFitness += protein.getFitness();

            if(protein.getFitness() > bestCandidate.getFitness()) {
                bestCandidate = protein;
            }

        }

        averageFitness = totalFitness / (double) GENERATION_SIZE;

    }

    public Generation generateNext() {
        List<Protein> nextCandidates = new ArrayList<>();

        nextCandidates.add()


        return null;
    }

    private Double getRandomDouble(Double rangeMin, Double rangeMax) {
        Random r = new Random();
        Double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

}
