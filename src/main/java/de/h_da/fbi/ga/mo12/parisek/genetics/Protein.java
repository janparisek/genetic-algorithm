package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.Position;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;


public class Protein {
    protected ArrayList<Aminoacid> genotype;
    protected ArrayList<PositionedAminoacid> phenotype = null;
    protected Double fitness = 0d;
    protected ProteinInfo properties = new ProteinInfo();

    public ProteinInfo getProperties() {
        return properties;
    }

    public ArrayList<PositionedAminoacid> getPhenotype() {
        return phenotype;
    }

    public Protein(Sequence sequence) {
        this(sequence, true);
    }

    public Protein(Sequence sequence, Boolean randomize) {
        // Parse sequence
        genotype = new ArrayList<>();
        Iterator<Boolean> iter = sequence.getSequence().iterator();
        while (iter.hasNext()) {
            Aminoacid current = new Aminoacid(iter.next(), Direction.EAST);
            genotype.add(current);
        }

        if(randomize) { foldRandomly(); }
    }

    public void foldRandomly() {
        Random random = new Random();
        Iterator<Aminoacid> iter = genotype.iterator();
        while(iter.hasNext()) {
            Aminoacid current = iter.next();
            Integer optionCount = Direction.class.getEnumConstants().length;
            Integer randomNumber = random.nextInt(optionCount);
            Direction randomDirection = Direction.class.getEnumConstants()[randomNumber];
            current.nextDirection = randomDirection;
        }

        calculatePositions();
        calculateFitness();
    }

    private void calculatePositions() {
        phenotype = new ArrayList<>();
        Position cursor = new Position(0, 0);

        for(Aminoacid element : genotype) {
            PositionedAminoacid aminoacid = new PositionedAminoacid(element, cursor);
            phenotype.add(aminoacid);
            cursor.move(element.getNextDirection());
        }
    }

    private void calculateFitness() {
        fitness = 0d;
        properties.hhBonds = 0;
        properties.overlaps = 0;

        ListIterator<PositionedAminoacid> iter = phenotype.listIterator();
        while(iter.hasNext()) {
            compareWithRemaining(iter);
        }

        fitness = (double) properties.hhBonds / (double) (1 + properties.overlaps);
    }

    private void compareWithRemaining(ListIterator<PositionedAminoacid> iter) {
        ListIterator<PositionedAminoacid> accumulator = phenotype.listIterator(iter.nextIndex());
        PositionedAminoacid current = iter.next();

        // Skip next (one) amino acid
        if(accumulator.hasNext()) { accumulator.next(); } else { return; }

        // Count
        while(accumulator.hasNext()) {
            PositionedAminoacid other = accumulator.next();

            if(current.isOverlappingWith(other)) {
                // 0 apart
                ++properties.overlaps;
            } else if(current.isNeighborsWith(other) && current.isHydrophobic && other.isHydrophobic) {
                // 1 apart
                ++properties.hhBonds;
            } /*else {

            }*/


        }
    }

    public Double getFitness() {
        return fitness;
    }

    public class ProteinInfo {
        protected Integer hhBonds = 0;
        protected Integer overlaps = 0;

        public Integer getHhBonds() {
            return hhBonds;
        }

        public Integer getOverlaps() {
            return overlaps;
        }
    }
}
