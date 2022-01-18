package de.h_da.fbi.ga.mo12.parisek.genetics;

import java.util.*;

import de.h_da.fbi.ga.mo12.parisek.WeightedProteinCollection;

public class Population {
    private List<Protein> population = new ArrayList<>();
    private Integer generationNumber = 1;
    private Double averageFitness = 0d;
    private Protein bestCandidate;
    private final static Integer POPULATION_SIZE = 1000;
    private final static Random rng = new Random();
    private final Sequence sequence;

    public Integer getGenerationNumber() {
        return generationNumber;
    }

    public Double getAverageFitness() {
        return averageFitness;
    }

    public Protein getBestCandidate() {
        return bestCandidate;
    }

    public Population(Sequence sequence) {
        this.sequence = sequence;
        for(int i = 0; i < POPULATION_SIZE; ++i) {
            Protein protein = new Protein(sequence);
            population.add(protein);
        }

        recalculateMetrics();

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

    public void generateNext(Algorithm algorithm) {
        ++generationNumber;

        if((generationNumber % 100) == 1) {
            System.out.println("Creating generation " + generationNumber);
        }

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

        final Integer CROSSOVER_COUNT = POPULATION_SIZE / 8;//125;
        final Integer MUTATION_COUNT = POPULATION_SIZE * sequence.getSequence().size() / 800; //20 => 20
        //final Integer MUTATIONS_PER_CANDIDATE = 2;

        doFitnessProportionalSelection();

        doSinglepointCrossoversRandomlyDistributed(CROSSOVER_COUNT);

        // Random mutation
        //doMutationsForEach(MUTATIONS_PER_CANDIDATE);
        doMutationsRandomlyDistributed(MUTATION_COUNT);
        //doSingleMutationOnRandomCandidate();

        for(Protein candidate : population) {
            candidate.update();
        }

    }

    private void doMutationsForEach(Integer iterationsPerCandidate) {
        for(Protein candidate : population) {
            for(int i = 0; i < iterationsPerCandidate; ++i){
                Integer segment = rng.nextInt(candidate.getGenotype().size());
                Aminoacid mutateMe = candidate.getGenotype().get(segment);
                mutateMe.randomizeDirection();
            }
        }
    }

    private void doMutationsRandomlyDistributed(Integer iterations) {
        for(int i = 0; i < iterations; ++i) {
            doSingleMutationOnRandomCandidate();
        }
    }

    private void doSingleMutationOnRandomCandidate() {
        Protein candidate = getRandomCandidate();
        Integer segment = rng.nextInt(candidate.getGenotype().size());
        Aminoacid mutateMe = candidate.getGenotype().get(segment);
        mutateMe.randomizeDirection();
    }

    private void doSinglepointCrossoversRandomlyDistributed(Integer iterations) {
        // 1-point crossovers
        for(int i = 0; i < iterations; ++i) {
            Protein candidate1 = getRandomCandidate();
            Protein candidate2 = getRandomCandidate();
            if(candidate1.equals(candidate2)) { break; }
            candidate1.crossoverWith(candidate2);
        }
    }

    private void doFitnessProportionalSelection() {
        // Fitness proportional selection
        WeightedProteinCollection fpSelector = new WeightedProteinCollection(population);
        population = fpSelector.sample(POPULATION_SIZE);
    }

    private Protein getRandomCandidate() {
        return population.get(rng.nextInt(population.size()));
    }

    public List<Protein> getPopulation() {
        return population;
    }

    public static enum Algorithm {
        LAB3,
        LAB4
    }

}
