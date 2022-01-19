package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Position;

import static java.lang.Math.abs;

public class Aminoacid {
    private final Position position;
    private final Boolean isHydrophobic;

    public Aminoacid(Aminoacid that) {
        position = new Position(that.position);
        isHydrophobic = that.isHydrophobic;
    }
    /**
     * Creates a new amino acid at the provided coordinates.
     * @param position The coordinates this amino acid is to be placed at.
     * @param isHydrophobic Whether this amino acid is hydrophobic.
     */
    public Aminoacid(Position position, Boolean isHydrophobic) {
        this.position = new Position(position);
        this.isHydrophobic = isHydrophobic;
    }

    public Position getPosition() {
        return position;
    }
    public Boolean isHydrophobic() {
        return isHydrophobic;
    }

    /**
     * Calculates the two-dimensional offset between two amino acids.
     * @param other The amino acid to compare to.
     * @return The offset.
     */
    public Position calculateOffset(Aminoacid other) {
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
    public Integer getDistanceTo(Aminoacid other) {
        Position delta = calculateOffset(other);
        return abs(delta.x) + abs(delta.y);
    }

    /**
     * Determines if this amino acid is right next to another amino acid.
     * @param other The amino acid to check for.
     * @return Whether this amino acid is right next to another amino acid.
     */
    public Boolean isNeighborsWith(Aminoacid other) {
        Integer distance = getDistanceTo(other);
        return distance.equals(1);
    }

    /**
     * Determines if this amino acid shares a spot with another amino acid.
     * @param other The amino acid to check for.
     * @return Whether this amino acid shares a spot with another amino acid.
     */
    public Boolean isOverlappingWith(Aminoacid other) {
        return position.equals(other.position);
    }


}
