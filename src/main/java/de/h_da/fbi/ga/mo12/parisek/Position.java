package de.h_da.fbi.ga.mo12.parisek;

import java.util.Objects;

public class Position {
    public Integer x = null;
    public Integer y = null;

    public Position() {}

    public Position(Position p) {
        this(p.x, p.y);
    }

    public Position(Integer x, Integer y) {
        this();
        this.x = x;
        this.y = y;
    }

    public void move(Direction direction) {
        switch (direction) {
            case NORTH:
                y -= 1;
                break;
            case SOUTH:
                y += 1;
                break;
            case EAST:
                x += 1;
                break;
            case WEST:
                x -=1 ;
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x.equals(position.x) && y.equals(position.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}