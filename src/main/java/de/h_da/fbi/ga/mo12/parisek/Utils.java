package de.h_da.fbi.ga.mo12.parisek;

import java.awt.*;

public class Utils {
    public static Point getDelta(Direction nextDirection) {
        Point delta = new Point();

        delta.x = switch (nextDirection) {
            case EAST -> 1;
            case WEST -> -1;
            default -> 0;
        };
        delta.y = switch (nextDirection) {
            case SOUTH -> 1;
            case NORTH -> -1;
            default -> 0;
        };

        return delta;
    }


}
