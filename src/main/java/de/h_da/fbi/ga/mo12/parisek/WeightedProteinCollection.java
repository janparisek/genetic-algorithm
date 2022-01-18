package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedProteinCollection {
    private final List<Weight> data = new ArrayList<>();
    private final Random rng = new Random();
    private Double accumulatedFitness = 0d;

    public WeightedProteinCollection(List<Protein> data) {

        for(Protein datum : data) {
            accumulatedFitness += datum.getFitness();
            Weight select = new Weight(accumulatedFitness, datum);
            this.data.add(select);
        }

    }

    public List<Protein> sample(Integer amount) {
        List<Protein> buffer = new ArrayList<>();

        for(int i = 0; i < amount; ++i) {
            buffer.add(new Protein(sample()));
        }

        return buffer;
    }

    public Protein sample() {
        Double randomDouble = getRandomDouble(accumulatedFitness);

        for (Weight datum : data) {
            if(randomDouble < datum.cumulativeWeight) {
                return datum.protein;
            }
        }

        return null;
    }

    private Double getRandomDouble(Double rangeMax) {
        return rangeMax * rng.nextDouble();
    }

    private static class Weight {
        Double cumulativeWeight;
        Protein protein;

        Weight(Double cumulativeWeight, Protein protein) {
            this.cumulativeWeight = cumulativeWeight;
            this.protein = protein;
        }
    }

}
