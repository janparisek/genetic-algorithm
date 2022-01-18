package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;

import java.util.Random;

public class Aminoacid {
    public Boolean isHydrophobic = null;
    public Direction nextDirection = Direction.EAST;
    public final static Random rng = new Random();

    Aminoacid(Boolean isHydrophobic) {
        this.isHydrophobic = isHydrophobic;
    }

    public Aminoacid(Boolean isHydrophobic, Direction direction) {
        this(isHydrophobic);
        nextDirection = direction;
    }

    Aminoacid(Aminoacid aminoacid) {
        this(aminoacid.isHydrophobic, aminoacid.nextDirection);
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void randomizeDirection() {
        int optionCount = Direction.class.getEnumConstants().length;
        int randomNumber = rng.nextInt(optionCount);
        nextDirection = Direction.class.getEnumConstants()[randomNumber];
    }
}
