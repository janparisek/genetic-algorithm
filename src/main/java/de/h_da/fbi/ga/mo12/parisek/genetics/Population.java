package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Utils;
import de.h_da.fbi.ga.mo12.parisek.WeightedProteinCollection;
import java.util.ArrayList;
import java.util.List;

import static de.h_da.fbi.ga.mo12.parisek.Settings.*;

public class Population {
    private List<Protein> population = new ArrayList<>();
    private Integer generationNumber = 0;
    private Double averageFitness = 0d;
    private Protein bestCandidate;
    private final Sequence sequence;

    public Population(Sequence sequence) {
        this.sequence = sequence;
        for(int i = 0; i < POPULATION_SIZE; ++i) {
            Protein protein = new Protein(sequence);
            population.add(protein);
        }

        recalculateMetrics();

    }

    public Integer getGenerationNumber() {
        return generationNumber;
    }
    public Double getAverageFitness() {
        return averageFitness;
    }
    public Protein getBestCandidate() {
        return bestCandidate;
    }
    public List<Protein> getPopulation() {
        return population;
    }

    public void generateNext(Algorithm algorithm) {
        ++generationNumber;

        if((generationNumber % 100) == 0) {
            System.out.println("Creating generation " + generationNumber);
        }

        switch (algorithm) {
            case LAB3:
                generateNextLab3();
            case LAB4:
            default:
                generateNextLab3();
        }

        recalculateMetrics();
    }

    private void reconstructPhenotypes() {
        for(Protein candidate : population) {
            candidate.reconstructPhenotype();
        }
    }

    private void generateNextLab3() {
        final Integer MUTATION_COUNT = MUTATION_RATE * sequence.getSequence().size();

        doFitnessProportionalSelection();
        doSingleCrossoversRandomlyDistributed(CROSSOVER_COUNT);
        doMutationsRandomlyDistributed(MUTATION_COUNT);

        reconstructPhenotypes();

    }

    private void doMutationsRandomlyDistributed(Integer iterations) {
        for(int i = 0; i < iterations; ++i) {
            doSingleMutationOnRandomCandidate();
        }
    }

    private void doSingleMutationOnRandomCandidate() {
        Protein candidate = selectRandomCandidate();
        Integer randomGeneIndex = Utils.getRandomInt(candidate.getGenotype().size());
        candidate.mutateAt(randomGeneIndex);
    }

    private void doSingleCrossoversRandomlyDistributed(Integer iterations) {
        // 1-point crossovers
        for(int i = 0; i < iterations; ++i) {
            Protein candidate1 = selectRandomCandidate();
            Protein candidate2 = selectRandomCandidate();
            if(candidate1.equals(candidate2)) { break; }
            candidate1.crossoverWith(candidate2);
        }
    }

    private void doFitnessProportionalSelection() {
        // Fitness proportional selection
        // TODO: Refactor everything in here
        WeightedProteinCollection fpSelector = new WeightedProteinCollection(population);
        population = fpSelector.sample(POPULATION_SIZE);
    }

    private Protein selectRandomCandidate() {
        return population.get(Utils.getRandomInt(population.size()));
    }

    private void recalculateMetrics() {
        bestCandidate = population.get(0);
        Double totalFitness = 0d;

        for(Protein candidate : population) {
            totalFitness += candidate.getFitness();

            if(candidate.getFitness() > bestCandidate.getFitness()) {
                bestCandidate = candidate;
            }
        }

        averageFitness = totalFitness / (double) POPULATION_SIZE;
    }

    public enum Algorithm {
        LAB3,
        LAB4
    }

}
