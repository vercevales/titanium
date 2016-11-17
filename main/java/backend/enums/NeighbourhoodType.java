package backend.enums;

import java.io.Serializable;

/**
 * Created by Karol Dziegiel on 22.04.16.
 */
public enum NeighbourhoodType implements Serializable{
    VON_NEUMANN,
    HEXAGONAL_LEFT,
    HEXAGONAL_RIGHT,
    HEXAGONAL_RANDOM,
    MOORE
}
