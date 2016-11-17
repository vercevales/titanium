package backend.implementations.cellmanagement;

import backend.enums.State;
import backend.implementations.Space2D;

/**
 * The class is responsible for all operations done with cells. The reason for
 * that is managing grains. By performing operations on cells it will use them
 * to keep grains up to date
 * 
 * @author karol
 *
 */
public class CellOperationsFacade {

	public static void setGrainId(Cell cell, long grainId) {
		cell.setGrainId(grainId);
	}

	public static boolean isOnPhaseBoundary(Cell cell) {
		boolean result = false;
		long alphaPhaseNeighbours = cell.neighbours.stream().filter(c -> c.grainId < 0).count();
		long betaPhaseNeighbours = cell.neighbours.stream().filter(c -> c.grainId >= 0).count();
		if (cell.grainId >= 0 && alphaPhaseNeighbours > 0)
			result = true;
		if (cell.grainId < 0 && betaPhaseNeighbours > 0)
			result = true;
		return result;
	}

	public static boolean isOnGrainBoundary(Cell cell, int minDifferentGrainNeighboursNumber) {
		boolean result = false;
		long anotherGrainNeighbours = cell.neighbours.stream().filter(c -> !c.grainId.equals(cell.grainId)).count();
		if (anotherGrainNeighbours >= minDifferentGrainNeighboursNumber)
			result = true;
		return result;
	}

	public static boolean isOnSamePhaseBoundary(Cell cell) {
		boolean result = false;
		long alphaPhaseDifferentGrainNeighbours = cell.neighbours.stream().filter(c -> c.grainId < 0 && c.getGrainId() != cell.getGrainId()).count();
		long betaPhaseDifferentGrainNeighbours = cell.neighbours.stream().filter(c -> c.grainId >= 0 && c.getGrainId() != cell.getGrainId()).count();
		
		if(isOnGrainBoundary(cell)){
			if(cell.getGrainId() >= 0 && betaPhaseDifferentGrainNeighbours != 0 ) result = true;
			if(cell.getGrainId() < 0 && alphaPhaseDifferentGrainNeighbours != 0) result = true;
		}

		return result;
	}

	public static boolean isOnGrainBoundary(Cell cell) {
		cell.setOnGrainBoundary(false);
		for (Cell neighbour : cell.getNeighbours()) {
			if (neighbour.getState() == State.GRAIN && cell.getState() == State.GRAIN
					&& !neighbour.getGrainId().equals(cell.getGrainId())) {
				cell.setOnGrainBoundary(true);
			}
		}
		return cell.isOnGrainBoundary;
	}

	public static void setOnGrainBoundary(Cell cell, boolean onGrainBoundary) {
		cell.isOnGrainBoundary = onGrainBoundary;
	}

	public static Cell getNewCell() {
		return new Cell();
	}

	public static Cell getNewCell(Space2D owningSpace, State state, int row, int col, Long grainId) {
		return new Cell(owningSpace, state, row, col, grainId);
	}

	public static Cell getNewCell(Space2D owningSpace, int row, int col) {
		return new Cell(owningSpace, row, col);
	}

	public static void setNewGrainId(Cell cell, Long newGrainId) {
		cell.setNewGrainId(newGrainId);
	}
	
	public static void setState(Cell cell, State state){
		cell.setState(state);
	}

}
