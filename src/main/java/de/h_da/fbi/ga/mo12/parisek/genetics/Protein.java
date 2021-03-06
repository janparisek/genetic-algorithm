package de.h_da.fbi.ga.mo12.parisek.genetics;

import de.h_da.fbi.ga.mo12.parisek.Direction;
import de.h_da.fbi.ga.mo12.parisek.Position;
import de.h_da.fbi.ga.mo12.parisek.Utils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Protein implements Callable<Boolean> {
    private ArrayList<Direction.Local> genotype;
    private ArrayList<Aminoacid> phenotype = null;
    private Double fitness = 0d;
    private Integer hhBonds = 0;
    private Integer overlaps = 0;
    private final Sequence sequence;

    public Protein(Protein that){
        genotype = new ArrayList<>(that.genotype);
        phenotype = new ArrayList<>();
        for(Aminoacid segment : that.phenotype) {
            phenotype.add(new Aminoacid(segment));
        }
        fitness = that.fitness;
        hhBonds = that.hhBonds;
        overlaps = that.overlaps;
        sequence = that.sequence;
    }
    /**
     * Creates a new, randomly folded protein with the provided sequence.
     * @param sequence The sequence to use.
     */
    public Protein(Sequence sequence) {
        this(sequence, true);
    }
    /**
     * Creates a new protein with the provided sequence.
     * @param sequence The sequence to use.
     * @param randomize Whether to randomly fold the protein.
     */
    public Protein(Sequence sequence, Boolean randomize) {
        // Parse sequence
        this.sequence = sequence;
        if(randomize) { foldRandomly(); }
    }

    public ArrayList<Direction.Local> getGenotype() {
        return genotype;
    }
    public ArrayList<Aminoacid> getPhenotype() {
        return phenotype;
    }
    public Double getFitness() {
        return fitness;
    }
    public Integer getHhBonds() {
        return hhBonds;
    }
    public Integer getOverlaps() {
        return overlaps;
    }

    /**
     * Randomly generates a new genotype sequence.
     */
    public void foldRandomly() {
        genotype = new ArrayList<>();
        Integer genotypeSize = sequence.getSequence().size() - 1;
        for(int i = 0; i < genotypeSize; ++i) {
            genotype.add(Direction.Local.getRandom());
        }

        reconstructPhenotype();

    }

    /**
     * Forces the recalculation of the phenotype of this protein.
     */
    public void reconstructPhenotype() {
        phenotype = new ArrayList<>();
        Position cursorPosition = new Position(0, 0);
        Direction.Global cursorRotation = Direction.Global.EAST;

        // Do for all elements except last in sequence
        for(int i = 0; i < (sequence.getSequence().size() - 1); ++i) {
            Boolean isHydrophobic = sequence.getSequence().get(i);
            Aminoacid aminoacid = new Aminoacid(cursorPosition, isHydrophobic);
            phenotype.add(aminoacid);

            Direction.Local gene = genotype.get(i);
            cursorRotation = Direction.Global.turn(cursorRotation, gene);
            cursorPosition.move(cursorRotation);
        }

        // Last element in sequence
        Boolean isHydrophobic = sequence.getSequence().get(sequence.getSequence().size() - 1);
        Aminoacid aminoacid = new Aminoacid(cursorPosition, isHydrophobic);
        phenotype.add(aminoacid);

        recalculateStatistics();

    }

    /**
     * Forces the recalculation of the statistics (fitness, hhBonds, overlaps) of this protein.
     */
    private void recalculateStatistics() {
        fitness = 0d;
        hhBonds = 0;
        overlaps = 0;

        for(int i = 0; i < phenotype.size(); ++i) {
            compareAminoacidsWithRest(i);
        }

        fitness = (double) hhBonds / (double) (1 + overlaps);
    }

    /**
     * Compares the amino acid at the provided index with all remaining amino acids in the phenotype.
     * @param aminoacidIndex The index of the amino acid to compare with.
     */
    private void compareAminoacidsWithRest(Integer aminoacidIndex) {
        Aminoacid aminoacid = phenotype.get(aminoacidIndex);
        int otherAminoacidIndex = aminoacidIndex;

        // Skip next (one) amino acid
        otherAminoacidIndex += 2;

        // Count statistics
        for( ; otherAminoacidIndex < phenotype.size(); ++otherAminoacidIndex) {
            Aminoacid otherAminoacid = phenotype.get(otherAminoacidIndex);
            Integer distance = aminoacid.getDistanceTo(otherAminoacid);
            if(distance > 2) {
                // Amino acids are too far apart for them to connect next turn. Skip unnecessary checks.
                otherAminoacidIndex += (distance - 2);
            } else if (distance.equals(0)) {
                // Amino acids are 0 apart, which means they're overlapping.
                ++overlaps;
            } else if (distance.equals(1) &&
                    aminoacid.isHydrophobic() &&
                    otherAminoacid.isHydrophobic()
            ) {
                // Amino acids are 1 apart, which means they're neighbors
                ++hhBonds;
            }
        }

    }

    /**
     * Does a crossover at a random point in the genotype between this protein and another provided protein.
     * @param other The protein to do the crossover with.
     */
    public void crossoverWith(Protein other) {
        int crossoverPoint = Utils.getRandomInt(genotype.size());

        ArrayList<Direction.Local> thisNewGenotype = new ArrayList<>();
        ArrayList<Direction.Local> otherNewGenotype = new ArrayList<>();

        for(int i = 0; i < crossoverPoint; ++i) {
            thisNewGenotype.add(genotype.get(i));
            otherNewGenotype.add(other.genotype.get(i));
        }
        for(int i = crossoverPoint; i < genotype.size(); ++i) {
            thisNewGenotype.add(other.genotype.get(i));
            otherNewGenotype.add(genotype.get(i));
        }

        genotype = thisNewGenotype;
        other.genotype = otherNewGenotype;

        //recalculatePositions();
        //other.recalculatePositions();

    }

    /**
     * Mutates a gene at the provided point in the genotype.
     * @param index The index of the point to mutate at.
     */
    public void mutateAt(Integer index) {
        Direction.Local mutatedGene = genotype.get(index);
        mutatedGene = Direction.Local.getRandomNot(mutatedGene);
        genotype.set(index, mutatedGene);
    }

    @Override
    public Boolean call() throws Exception {
        reconstructPhenotype();
        return null;
    }
}
