package de.h_da.fbi.ga.mo12.parisek;

import de.h_da.fbi.ga.mo12.parisek.genetics.Aminoacid;
import de.h_da.fbi.ga.mo12.parisek.genetics.Protein;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MainTest {

    @Test
    public void isTrueTrue() {
        System.out.println("Hello Test!");
        Main main = new Main();
        assert true;
    }

    @Test
    public void somethingProtein() {
        Aminoacid a01 = new Aminoacid(false, Direction.WEST);
        Aminoacid a02 = new Aminoacid(false, Direction.NORTH);
        Aminoacid a03 = new Aminoacid(false, Direction.WEST);
        Aminoacid a04 = new Aminoacid(true, Direction.SOUTH);
        Aminoacid a05 = new Aminoacid(false, Direction.SOUTH);
        Aminoacid a06 = new Aminoacid(true, Direction.EAST);
        Aminoacid a07 = new Aminoacid(false, Direction.SOUTH);
        Aminoacid a08 = new Aminoacid(true, Direction.EAST);
        Aminoacid a09 = new Aminoacid(true, Direction.NORTH);
        Aminoacid a10 = new Aminoacid(false, Direction.EAST);
        Aminoacid a11 = new Aminoacid(true, Direction.NORTH);
        Aminoacid a12 = new Aminoacid(false, Direction.EAST);
        Aminoacid a13 = new Aminoacid(true, Direction.NORTH);
        Aminoacid a14 = new Aminoacid(true, Direction.WEST);
        Aminoacid a15 = new Aminoacid(false, Direction.NORTH);
        Aminoacid a16 = new Aminoacid(true, Direction.WEST);
        Aminoacid a17 = new Aminoacid(true, Direction.SOUTH);
        Aminoacid a18 = new Aminoacid(false, Direction.WEST);

        Protein p = new Protein();
        p.elements = new ArrayList<>();
        p.elements.add(a01);
        p.elements.add(a02);
        p.elements.add(a03);
        p.elements.add(a04);
        p.elements.add(a05);
        p.elements.add(a06);
        p.elements.add(a07);
        p.elements.add(a08);
        p.elements.add(a09);
        p.elements.add(a10);
        p.elements.add(a11);
        p.elements.add(a12);
        p.elements.add(a13);
        p.elements.add(a14);
        p.elements.add(a15);
        p.elements.add(a16);
        p.elements.add(a17);
        p.elements.add(a18);
    }

}
