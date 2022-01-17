package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.*;

import de.h_da.fbi.ga.mo12.parisek.WeightedProteinCollection;

public class Generation {
    private List<Protein> candidates = new ArrayList<>();
    private Integer number = 1;
    private Double averageFitness = 0d;
    private Protein bestCandidate;
    private final static Integer GENERATION_SIZE = 100;
    private final static Random rng = new Random();

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
        for(int i = 0; i < GENERATION_SIZE; ++i) {
            Protein protein = new Protein(sequence);
            candidates.add(protein);
        }

        recalculateMetrics();

    }

    private void recalculateMetrics() {
        bestCandidate = candidates.get(0);
        Double totalFitness = 0d;

        for(Protein candidate : candidates) {
            totalFitness += candidate.getFitness();

            if(candidate.getFitness() > bestCandidate.getFitness()) {
                bestCandidate = candidate;
            }
        }

        averageFitness = totalFitness / (double) GENERATION_SIZE;
    }

    public void generateNext(Algorithm algorithm) {
        ++number;
        switch (algorithm) {
            case LAB3:
                generateNextLab3();
            case LAB4:
            default:
                break;
        }
        recalculateMetrics();
    }


    private void generateNextLab3() {
        final Integer CROSSOVER_COUNT = GENERATION_SIZE;

        // Fitness proportional selection
        WeightedProteinCollection fpSelector = new WeightedProteinCollection(candidates);
        candidates = fpSelector.sample(GENERATION_SIZE);

        // 1-point crossovers
        for(int i = 0; i < CROSSOVER_COUNT; ++i) {
            Protein candidate1 = getRandomCandidate();
            Protein candidate2 = getRandomCandidate();
            if(candidate1.equals(candidate2)) { break; }
            //candidate1.
        }

        // Random mutation

    }

    private Protein getRandomCandidate() {
        return candidates.get(rng.nextInt(candidates.size()));
    }

    public List<Protein> getCandidates() {
        return candidates;
    }

    public static enum Algorithm {
        LAB3,
        LAB4
    }

}
