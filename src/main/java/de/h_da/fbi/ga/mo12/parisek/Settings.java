package de.h_da.fbi.ga.mo12.parisek;

public class Settings {
    public final static Integer GENERATIONS = 10000;
    public final static Integer POPULATION_SIZE = 100;
    public final static String SEQUENCE = Examples.SEQ20;

    // Lab 3
    public final static Integer CROSSOVER_COUNT = POPULATION_SIZE / 8;
    public final static Integer MUTATION_RATE = POPULATION_SIZE / 800;
}
