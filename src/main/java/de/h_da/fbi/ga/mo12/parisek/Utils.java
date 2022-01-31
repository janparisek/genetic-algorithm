package de.h_da.fbi.ga.mo12.parisek;

import java.util.List;
import java.util.Random;

public class Utils {
    private final static Random rng = new Random();


    public static int getRandomInt(int boundary) {
        return rng.nextInt(boundary);
    }

    public static double getRandomDouble() {
        return rng.nextDouble();
    }

    public static double getRandomDouble(double boundary) {
        return rng.nextDouble() * boundary;
    }

    public static <T> T getRandomListElement(List<T> list) {
        return list.get(getRandomInt(list.size()));
    }

}
