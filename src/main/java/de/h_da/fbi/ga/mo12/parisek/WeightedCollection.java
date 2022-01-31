package de.h_da.fbi.ga.mo12.parisek;

import java.util.ArrayList;
import java.util.List;

public class WeightedCollection<T> {
    private final List<WeightedPair<T>> data = new ArrayList<>();
    private Double totalWeight = 0d;

    public WeightedCollection(List<WeightedPair<T>> data) {

        for(WeightedPair<T> datum : data) {
            totalWeight += datum.weight;
            WeightedPair<T> pair = new WeightedPair<>(totalWeight, datum.object);
            this.data.add(pair);
        }

    }


    public List<T> sample(Integer amount) {
        List<T> buffer = new ArrayList<>();

        for(int i = 0; i < amount; ++i) {
            T a = sample();

            buffer.add(a);
        }

        return buffer;
    }

    public T sample() {
        Double randomDouble = Utils.getRandomDouble(totalWeight);

        for (WeightedPair<T> datum : data) {
            if(randomDouble < datum.weight) {
                return datum.object;
            }
        }

        return null;
    }

    public static class WeightedPair<T> {
        public final Double weight;
        public final T object;

        public WeightedPair(Double cumulativeWeight, T object) {
            this.weight = cumulativeWeight;
            this.object = object;
        }
    }

}
