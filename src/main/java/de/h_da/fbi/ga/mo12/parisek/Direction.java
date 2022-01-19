package de.h_da.fbi.ga.mo12.parisek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.h_da.fbi.ga.mo12.parisek.Direction.Global.*;

public class Direction {

    public enum Global {
        NORTH,
        SOUTH,
        EAST,
        WEST;

        public static Integer getSize() {
            return Global.class.getEnumConstants().length;
        }

        public static Global getRandom(){
            Integer itemIndex = Utils.getRandomInt(getSize());
            return Global.class.getEnumConstants()[itemIndex];
        }

        public static Global turn(Global global, Local local) {

            switch (global) {
                case NORTH:
                    switch (local) {
                        case LEFT: return WEST;
                        case RIGHT: return EAST;
                        default:
                        case FORWARD: return NORTH;
                    }
                case EAST:
                    switch (local) {
                        case LEFT: return NORTH;
                        case RIGHT: return SOUTH;
                        default:
                        case FORWARD: return EAST;
                    }
                case SOUTH:
                    switch (local) {
                        case LEFT: return EAST;
                        case RIGHT: return WEST;
                        default:
                        case FORWARD: return SOUTH;
                    }
                default:
                case WEST:
                    switch (local) {
                        case LEFT: return SOUTH;
                        case RIGHT: return NORTH;
                        default:
                        case FORWARD: return WEST;
                    }
            }

        }

    }

    public enum Local {
        LEFT,
        FORWARD,
        RIGHT;

        public static Integer getSize() {
            return Local.class.getEnumConstants().length;
        }

        public static Local getRandom(){
            Integer itemIndex = Utils.getRandomInt(getSize());
            return Local.class.getEnumConstants()[itemIndex];
        }

        public static Local getRandomNot(Local avoid) {
            final List<Local> notLeft = Arrays.asList(FORWARD, RIGHT);
            final List<Local> notRight = Arrays.asList(LEFT, FORWARD);
            final List<Local> notForward = Arrays.asList(LEFT, RIGHT);

            switch (avoid) {
                case LEFT: return Utils.getRandomListElement(notLeft);
                case RIGHT: return Utils.getRandomListElement(notRight);
                default:
                case FORWARD: return Utils.getRandomListElement(notForward);
            }
        }

    }


}
