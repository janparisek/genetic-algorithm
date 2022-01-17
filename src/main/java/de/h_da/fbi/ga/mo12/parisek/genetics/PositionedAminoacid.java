package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Position;

import static java.lang.Math.abs;

public class PositionedAminoacid extends Aminoacid {
    private Position position = new Position();

    PositionedAminoacid(Aminoacid a) {
        super(a);
    }

    PositionedAminoacid(Aminoacid a, Position p) {
        this(a);
        position = new Position(p);
    }

    Position calculateOffset(PositionedAminoacid other) {
        // Simple (hemming?) distance
        Position delta = new Position();
        delta.x = position.x - other.position.x;
        delta.y = position.y - other.position.y;

        return delta;
    }

    /**
     * Calculates the Manhattan distance between two amino acids.
     * @param other The amino acid to compare to.
     * @return The Manhattan distance.
     */
    Integer getDistanceTo(PositionedAminoacid other) {
        Position delta = calculateOffset(other);
        return abs(delta.x) + abs(delta.y);
    }

    Boolean isNeighborsWith(PositionedAminoacid other) {
        Integer distance = getDistanceTo(other);

        return distance.equals(1);
    }

    Boolean isOverlappingWith(PositionedAminoacid other) {
        return position.equals(other.position);
    }

    public Position getPosition() {
        return position;
    }
}
