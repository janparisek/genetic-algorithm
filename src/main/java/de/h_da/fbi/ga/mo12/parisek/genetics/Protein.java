package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.genetics.Aminoacid;
import de.h_da.fbi.ga.mo12.parisek.genetics.Sequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

public class Protein {
    ArrayList<Aminoacid> elements;

    public Protein(Sequence sequence) {
        elements = new ArrayList<>();

        Iterator<Boolean> iter = sequence.getSequence().iterator();
        while (iter.hasNext()) {
            Aminoacid current = new Aminoacid(iter.next(), Direction.EAST);
            elements.add(current);
        }
    }

    public Protein() {
        elements = null;
    }

    public Protein(Sequence sequence, Boolean randomize) {
        this(sequence);
        if(randomize) { fold(); }
    }

    public void fold() {
        Random random = new Random();

        Iterator<Aminoacid> iter = elements.iterator();
        while(iter.hasNext()) {
            Aminoacid current = iter.next();
            Direction randomDirection = Direction.class.getEnumConstants()[random.nextInt(Direction.class.getEnumConstants().length)];
            current.nextDirection = randomDirection;
        }
    }

    public Double calculateFitness() {
        ListIterator<Aminoacid> iter = elements.listIterator();
        Double fitness = 0d;

        Integer x = 0;
        Integer y = 0;
        while(iter.hasNext()) {

            getHHBonds(iter, x, y);

        }

        return fitness;
    }

    private ProteinInfo getHHBonds(ListIterator<Aminoacid> iter, Integer x, Integer y) {
        ListIterator<Aminoacid> collectionIter = elements.listIterator(iter.nextIndex());
        Aminoacid current = iter.next();

        // Skip next amino acid
        if(collectionIter.hasNext()) { collectionIter.next(); }

        // Count
        ProteinInfo info = new ProteinInfo();
        Integer x2 = x;
        Integer y2 = y;
        while(collectionIter.hasNext()) {
            Aminoacid other = collectionIter.next();

            if(areBothHydrophobic(current, other) && areNeighbors(x, y, x2, y2)) {
                info.hhBonds += 1;
            }
            if(areOverlapping(x, y, x2, y2)) {
                info.overlaps += 1;
            }

            x2 = getDeltaX(current);
            y2 = getDeltaY(current);

        }

        return info;
    }

    private Integer getDeltaX(Aminoacid current) {
        Integer x2;
        x2 = switch (current.nextDirection) {
            case EAST -> 1;
            case WEST -> -1;
            default -> 0;
        };
        return x2;
    }

    private Integer getDeltaY(Aminoacid current) {
        Integer y2;
        y2 = switch (current.nextDirection) {
            case SOUTH -> 1;
            case NORTH -> -1;
            default -> 0;
        };
        return y2;
    }

    private boolean areBothHydrophobic(Aminoacid current, Aminoacid other) {
        return current.isHydrophilic.equals(other.isHydrophilic);
    }

    private Boolean areNeighbors(Integer x, Integer y, Integer x2, Integer y2) {
        if(y.equals(y2)) {
            if(x.equals(x2 - 1) || x.equals(x2 + 1)) {
                return true;
            } else {
                return false;
            }
        } else if(x.equals(x2)) {
            if(y.equals(y2 - 1) || y.equals(y2 + 1)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private Boolean areOverlapping(Integer x, Integer y, Integer x2, Integer y2) {
        return (x.equals(x2) && y.equals(y2));
    }

    public Double getFitness() {
        return 0d;
    }

    private class ProteinInfo {
        public Integer hhBonds = 0;
        public Integer overlaps = 0;
    }
}
