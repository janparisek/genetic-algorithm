package de.h_da.fbi.ga.mo12.parisek;

import java.util.List;
import java.util.Random;

public class Utils {
    private final static Random rng = new Random();

    public static Integer getRandomInt(Integer boundary) {
        if(boundary == null) {
            return rng.nextInt();
        } else {
            return rng.nextInt(boundary);
        }
    }

    public static Double getRandomDouble(Double boundary) {
        if(boundary == null) {
            return rng.nextDouble();
        } else {
            return rng.nextDouble() * boundary;
        }
    }

    public static <T> T getRandomListElement(List<T> list) {
        return list.get(getRandomInt(list.size()));
    }

}
