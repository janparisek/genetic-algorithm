package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.Position;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;


public class Protein {
    private ArrayList<Aminoacid> genotype;
    //private ArrayList<Direction> genotype;    // TODO: this
    private ArrayList<PositionedAminoacid> phenotype = null;
    private Double fitness = 0d;
    private ProteinInfo properties = new ProteinInfo(0, 0);
    private final static Random rng = new Random();

    public ArrayList<Aminoacid> getGenotype() {
        return genotype;
    }

    public ProteinInfo getProperties() {
        return properties;
    }

    public ArrayList<PositionedAminoacid> getPhenotype() {
        return phenotype;
    }

    public Protein(Sequence sequence) {
        this(sequence, true);
    }

    public Protein(Protein protein){
        genotype = new ArrayList<>();
        for(Aminoacid segment : protein.genotype) {
            genotype.add(new Aminoacid(segment));
        }
        phenotype = new ArrayList<>();
        for(PositionedAminoacid segment : protein.phenotype) {
            phenotype.add(new PositionedAminoacid(segment));
        }
        fitness = protein.fitness;
        properties = new ProteinInfo(
            protein.properties.hhBonds,
            protein.properties.overlaps
        );


    }

    public Protein(Sequence sequence, Boolean randomize) {
        // Parse sequence
        genotype = new ArrayList<>();
        for (Boolean aBoolean : sequence.getSequence()) {
            Aminoacid current = new Aminoacid(aBoolean, Direction.EAST);
            genotype.add(current);
        }

        if(randomize) { foldRandomly(); }
    }

    public void foldRandomly() {
        for (Aminoacid current : genotype) {
            current.randomizeDirection();
        }

        update();

    }

    public void update() {
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

    public void crossoverWith(Protein other) {
        Integer segments = genotype.size();
        Integer crossoverPoint = rng.nextInt(segments);


        ArrayList<Aminoacid> newGenotypeThis = new ArrayList<>();
        ArrayList<Aminoacid> newGenotypeOther = new ArrayList<>();

        for(int i = 0; i < crossoverPoint; ++i) {
            newGenotypeThis.add(genotype.get(i));
            newGenotypeOther.add(other.genotype.get(i));
        }
        for(int i = crossoverPoint; i < segments; ++i) {
            newGenotypeThis.add(other.genotype.get(i));
            newGenotypeOther.add(genotype.get(i));
        }

        genotype = newGenotypeThis;
        other.genotype = newGenotypeOther;

    }

    public static class ProteinInfo {
        protected Integer hhBonds = 0;
        protected Integer overlaps = 0;

        public ProteinInfo(Integer hhBonds, Integer overlaps) {
            this.hhBonds = hhBonds;
            this.overlaps = overlaps;
        }

        public Integer getHhBonds() {
            return hhBonds;
        }

        public Integer getOverlaps() {
            return overlaps;
        }
    }

}
