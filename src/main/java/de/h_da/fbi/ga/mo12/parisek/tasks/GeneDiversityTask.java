package de.h_da.fbi.ga.mo12.parisek.tasks;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class GeneDiversityTask implements Callable<Double> {
    private final List<Protein> population;
    private final double populationSize;
    private final Double geneCountFloorConstraint;
    private final int geneIndex;

    public GeneDiversityTask(List<Protein> population, double populationSize, Double geneCountFloorConstraint, int geneIndex) {
        this.population = population;
        this.populationSize = populationSize;
        this.geneCountFloorConstraint = geneCountFloorConstraint;
        this.geneIndex = geneIndex;
    }

    private Double getGeneDiversity() {
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

    @Override
    public Double call() throws Exception {
        return getGeneDiversity();
    }
}
