package backend.interfaces;


import backend.implementations.cellmanagement.Cell;

/**
 * Created by kdziegie on 2016-04-20.
 */
public interface Rule {
   Cell nextStepCell(Cell previousCell, Cell currentCell);
}
