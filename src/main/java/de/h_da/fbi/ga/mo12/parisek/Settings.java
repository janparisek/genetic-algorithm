package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Population.SelectionAlgorithm;
import de.h_da.fbi.ga.mo12.parisek.genetics.Population.DiversityAlgorithm;

public class Settings {
    public final static Integer GENERATIONS = 2_000;
    public final static Integer POPULATION_SIZE = 1_000;
    public final static String SEQUENCE = Examples.SEQ50;

    public final static SelectionAlgorithm SELECTION_ALGORITHM = SelectionAlgorithm.LAB4;
    public final static DiversityAlgorithm DIVERSITY_ALGORITHM = DiversityAlgorithm.CYCLIC;

    // Lab 3
    public final static Integer CROSSOVER_COUNT = POPULATION_SIZE / 8;
    public final static Double MUTATION_BASE_RATE = Double.valueOf(POPULATION_SIZE) / 800d;

    // Lab 4
    public final static Integer TOURNAMENT_CANDIDATE_COUNT = POPULATION_SIZE / 100;
}
