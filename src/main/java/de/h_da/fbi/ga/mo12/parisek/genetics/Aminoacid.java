package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;

public class Aminoacid {
    public Boolean isHydrophilic = null;
    public Direction nextDirection = Direction.EAST;

    Aminoacid(Boolean isHydrophilic) {
        this.isHydrophilic = isHydrophilic;
    }

    public Aminoacid(Boolean isHydrophilic, Direction direction) {
        this(isHydrophilic);
        nextDirection = direction;
    }

}
