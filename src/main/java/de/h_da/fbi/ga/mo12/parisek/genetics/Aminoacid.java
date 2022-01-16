package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;

public class Aminoacid {
    public Boolean isHydrophobic = null;
    public Direction nextDirection = Direction.EAST;

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
}
