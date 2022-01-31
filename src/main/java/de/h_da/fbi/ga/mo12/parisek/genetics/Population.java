package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.Utils;
import de.h_da.fbi.ga.mo12.parisek.WeightedCollection;

import java.util.*;

import static de.h_da.fbi.ga.mo12.parisek.Settings.*;
import static java.lang.Math.abs;

public class Population {
    private List<Protein> population = new ArrayList<>();
    private Integer generationNumber = 0;
    private Double averageFitness = 0d;
    private Protein bestCandidate;
    private final Sequence SEQUENCE;
    private final Queue<Double> fitnessHistory = new LinkedList<>();
    private Integer mutationCount = 0;

    public Population(Sequence sequence) {
        this.SEQUENCE = sequence;
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
    public Integer getMutationCount() { return mutationCount; }

    public void generateNext() {
        ++generationNumber;

        if((generationNumber % 100) == 0) {
            System.out.println("Creating generation " + generationNumber);
        }

        switch (SELECTION_ALGORITHM) {
            case LAB3:
                generateNextLab3();
                break;
            case LAB4:
            default:
                generateNextLab4();
                break;
        }

        recalculateMetrics();
    }

    private void reconstructPhenotypes() {
        for(Protein candidate : population) {
            candidate.reconstructPhenotype();
        }
    }

    private void generateNextLab3() {
        final Integer MUTATION_COUNT = calculateMutationCount().intValue();

        doFitnessProportionalSelection();
        doSingleCrossoversRandomlyDistributed();
        doMutationsRandomlyDistributed(MUTATION_COUNT);

        reconstructPhenotypes();

    }

    private void generateNextLab4() {
        final Integer MUTATION_COUNT = calculateMutationCount().intValue();

        doTournamentSelection();
        doSingleCrossoversRandomlyDistributed();
        doMutationsRandomlyDistributed(MUTATION_COUNT);

        reconstructPhenotypes();

    }

    private Double calculateMutationCount() {
        Double mutationRate = MUTATION_BASE_RATE * SEQUENCE.getSequence().size();
        Double diversity = calculateDiversity();
        mutationRate *= diversity;
        //System.out.println("Mutation count in generation " + generationNumber + ": " + mutationRate);
        mutationCount = mutationRate.intValue();
        return mutationRate;
    }

    private Double calculateDiversity() {
        Double diversity;

        switch (DIVERSITY_ALGORITHM) {
            case CYCLIC:
                diversity = calculatePeriodicMutationRate();
                break;
            case INTRA_GENERATIONAL_GENETIC_DIVERSITY:
                diversity = calculateIntraGenerationalGeneticDiversity();
                break;
            case INTER_GENERATIONAL_FITNESS_VARIANCE:
            default:
                diversity = calculateInterGenerationalFitnessVariance();
                break;
            case FIXED:
                diversity = 1d;
                break;
        }

        return diversity;
    }

    private Double calculateInterGenerationalFitnessVariance() {
        final Integer HISTORY_SIZE = 20;

        fitnessHistory.offer(averageFitness);
        if(fitnessHistory.size() < HISTORY_SIZE) { return 1.0d; }

        while(fitnessHistory.size() > HISTORY_SIZE) {
            fitnessHistory.poll();
        }

        Queue<Double> historyBuffer = new LinkedList<>(fitnessHistory);
        Double averageHistoryFitness = 0.0d;
        while(!historyBuffer.isEmpty()) {
            averageHistoryFitness += historyBuffer.poll();
        }
        averageHistoryFitness /= fitnessHistory.size();

        double totalFitnessDeviation = 0.0d;
        historyBuffer = new LinkedList<>(fitnessHistory);
        while(!historyBuffer.isEmpty()) {
            Double fitness = historyBuffer.poll();
            double fitnessDeviation = averageHistoryFitness - fitness;
            fitnessDeviation = abs(fitnessDeviation);
            totalFitnessDeviation += fitnessDeviation;
        }

        return (1d / totalFitnessDeviation) * 250d;
    }

    private Double calculateIntraGenerationalGeneticDiversity() {
        // Calculate the lowest possible number of times a gene could occur.
        Double populationSize = (double) population.size();
        Double enumSize = (double) Direction.Local.class.getEnumConstants().length;
        Double geneCountFloorConstraint = Math.ceil(populationSize / enumSize);

        Double totalDiversity = 0d;
        for(int geneIndex = 0; geneIndex < SEQUENCE.getSequence().size() - 1; ++geneIndex) {
            Double diversity = getGeneDiversity(populationSize, geneCountFloorConstraint, geneIndex);
            totalDiversity += diversity;
        }

        totalDiversity /= (double) SEQUENCE.getSequence().size();
        totalDiversity *= 50d;  //TODO: Mathematische Grundlage für Magic Number herausfinden.
        return totalDiversity;

    }

    private Double calculatePeriodicMutationRate() {
        final double HALF_PI = Math.PI / 2d;
        final double TWO_PI = Math.PI * 2d;
        final double PERIOD = 20d;
        Double mutationRate = Math.sin((generationNumber * TWO_PI / PERIOD) - HALF_PI);
        mutationRate += 1d; // Shift upwards so it doesn't go below zero
        mutationRate /= 2d; // Normalize
        mutationRate *= 40d;    // Attenuation
        return  mutationRate;
    }

    private Double getGeneDiversity(Double populationSize, Double geneCountFloorConstraint, int geneIndex) {
        // Count all different genes (L, R, F)
        Map<Direction.Local, Integer> geneCounter = new EnumMap<>(Direction.Local.class);
        for(Direction.Local direction : Direction.Local.class.getEnumConstants()) {
            geneCounter.putIfAbsent(direction, 0);
        }

        for(Protein candidate : population) {
            Direction.Local gene = candidate.getGenotype().get(geneIndex);
            Integer geneCount = geneCounter.get(gene);
            ++geneCount;
            geneCounter.put(gene, geneCount);
        }

        // Get the highest gene count
        Integer mostCommonGeneCount = 0;
        for(Integer geneCount : geneCounter.values()) {
            if(geneCount > mostCommonGeneCount) {
                mostCommonGeneCount = geneCount;
            }
        }

        // Calculate diversity
        Double biasedGeneCount = mostCommonGeneCount.doubleValue() - 2.5; //TODO: Daniel fragen wie genau die Magic Number hier berechnet werden kann. Wahrscheinlich irgendwas mit Fehlerfunktion oder Multinomialverteilung.
        Double constrainedGeneCount = ( biasedGeneCount < geneCountFloorConstraint) ? geneCountFloorConstraint : biasedGeneCount;
        Double diversity = ((constrainedGeneCount - geneCountFloorConstraint) / (populationSize - geneCountFloorConstraint));
        return diversity;
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

    private void doSingleCrossoversRandomlyDistributed() {
        // 1-point crossovers
        for(int i = 0; i < de.h_da.fbi.ga.mo12.parisek.Settings.CROSSOVER_COUNT; ++i) {
            Protein candidate1 = selectRandomCandidate();
            Protein candidate2 = selectRandomCandidate();
            if(candidate1.equals(candidate2)) { break; }
            candidate1.crossoverWith(candidate2);
        }
    }

    private void doFitnessProportionalSelection() {
        // Fitness proportional selection

        List<WeightedCollection.WeightedPair<Protein>> weightedProteins = new ArrayList<>();
        for(Protein candidate : population) {
            WeightedCollection.WeightedPair<Protein> current = new WeightedCollection.WeightedPair<>(candidate.getFitness(), candidate);
            weightedProteins.add(current);
        }

        WeightedCollection<Protein> fitnessProportionalSelector = new WeightedCollection<>(weightedProteins);
        List<Protein> nextCandidates = fitnessProportionalSelector.sample(population.size());

        // Populate
        population = new ArrayList<>();
        for(Protein candidate : nextCandidates) {
            population.add(new Protein(candidate));
        }

    }

    private void doTournamentSelection() {
        // Tournament selection

        List<Protein> nextCandidates = new ArrayList<>();

        for(int i = 0; i < population.size(); ++i) {
            Protein tournamentWinner = doOneTournament();
            nextCandidates.add(new Protein(tournamentWinner));
        }

        population = nextCandidates;

    }

    private Protein doOneTournament() {
        List<Protein> tournamentCandidates = new ArrayList<>();

        for(int i = 0; i < de.h_da.fbi.ga.mo12.parisek.Settings.TOURNAMENT_CANDIDATE_COUNT; ++i) {
            Protein randomCandidate = selectRandomCandidate();
            tournamentCandidates.add(randomCandidate);
        }

        Protein winner = tournamentCandidates.get(0);
        for(Protein candidate : tournamentCandidates) {
            if(candidate.getFitness() > winner.getFitness()) {
                winner = candidate;
            }
        }

        return winner;

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

    public enum SelectionAlgorithm {
        LAB3,
        LAB4
    }

    public enum DiversityAlgorithm {
        INTER_GENERATIONAL_FITNESS_VARIANCE,
        INTRA_GENERATIONAL_GENETIC_DIVERSITY,
        FIXED,
        CYCLIC
    }

}
