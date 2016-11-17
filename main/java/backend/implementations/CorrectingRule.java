//package backend.implementations;
//
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Random;
//import java.util.Set;
//
//import backend.implementations.cellmanagement.Cell;
//import backend.implementations.cellmanagement.CellOperationsFacade;
//import backend.interfaces.Rule;
//
//public class CorrectingRule implements Rule {
//	// static int counter = 0;
//	// int minNeighbours = 4;
//	// static long previousCells = 0;
//
//	@Override
//	public Cell nextStepCell(Cell previousCell, Cell currentCell) {
//		/*
//		 * if(currentCell.getOwningSpace().isEnoughAlpha()){ if(previousCells !=
//		 * currentCell.getOwningSpace().getAlphaCells()){ previousCells =
//		 * currentCell.getOwningSpace().getAlphaCells(); } else{ counter++; }
//		 * if(counter == currentCell.getOwningSpace().getAlphaCells() )
//		 * minNeighbours--; } if(currentCell.getOwningSpace().isEnoughBeta()){
//		 * if(previousCells != currentCell.getOwningSpace().getBetaCells()){
//		 * previousCells = currentCell.getOwningSpace().getBetaCells(); } else{
//		 * counter++; } if(counter ==
//		 * currentCell.getOwningSpace().getBetaCells() ) minNeighbours--; }
//		 */
//		int minNeighbours = 1;
//		if (Math.random() * 10 != 0)
//			minNeighbours = (int) (Math.random() * 10) % 5 + 4;
//		if (CellOperationsFacade.isOnGrainBoundary(previousCell, minNeighbours)) {
//			// System.out.println(minNeighbours);
//			// System.out.println("Alpha " +
//			// currentCell.getOwningSpace().getAlphaCells());
//			// if((currentCell.getOwningSpace().isEnoughAlpha() &&
//			// currentCell.getGrainId().compareTo(0L) == -1)
//			// || (currentCell.getOwningSpace().isEnoughBeta() &&
//			// currentCell.getGrainId().compareTo(0L) != -1)){
//			Set<Long> neighbourGrainIds = new HashSet<>();
//
//			for (Cell neighbour : previousCell.getNeighbours()) {
//				if ((neighbour.getGrainId() >= 0 && previousCell.getGrainId() < 0)
//						|| (neighbour.getGrainId() < 0 && previousCell.getGrainId() >= 0)) {
//					neighbourGrainIds.add(neighbour.getGrainId());
//				}
//			}
//			if (!neighbourGrainIds.isEmpty()) {
//				Long newGrainId = neighbourGrainIds.iterator().next();
//
//				Map<Long, Integer> grainIdToAmountOfGrainsMap = new HashMap<>();
//				for (Long gId : neighbourGrainIds) {
//					grainIdToAmountOfGrainsMap.put(gId, 0);
//				}
//				for (Cell neighbour : previousCell.getNeighbours()) {
//					Long key = neighbour.getGrainId();
//					Integer value;
//					if (grainIdToAmountOfGrainsMap.containsKey(key)) {
//						value = grainIdToAmountOfGrainsMap.get(key);
//						value++;
//						grainIdToAmountOfGrainsMap.replace(key, value);
//					}
//				}
//				Long maxKey = newGrainId;
//				for (Long key : neighbourGrainIds) {
//					if (grainIdToAmountOfGrainsMap.get(key).longValue() >= grainIdToAmountOfGrainsMap.get(maxKey)
//							.longValue())
//						maxKey = key;
//					else if (grainIdToAmountOfGrainsMap.get(key)
//							.longValue() == (grainIdToAmountOfGrainsMap.get(maxKey).longValue())) {
//						Random random = new Random();
//						float indicator = random.nextFloat();
//						if (indicator >= 0.5)
//							maxKey = key;
//					}
//				}
//				if ((previousCell.getOwningSpace().isEnoughAlpha() && maxKey >= 0)
//						|| (previousCell.getOwningSpace().isEnoughBeta() && maxKey < 0))
//					CellOperationsFacade.setNewGrainId(currentCell, maxKey);
//			}
//			// }
//		}
//		return currentCell;
//	}
//
//}
package backend.implementations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;
import backend.interfaces.Rule;

public class CorrectingRule implements Rule {

	private double givenAlphaPhaseFraction;
	private double actualAlphaPhaseFraction;

	public CorrectingRule(double givenAlphaPhaseFraction, double actualAlphaPhaseFraction) {
		this.givenAlphaPhaseFraction = givenAlphaPhaseFraction;
		this.actualAlphaPhaseFraction = actualAlphaPhaseFraction;
	}

	@Override
	public Cell nextStepCell(Cell previousCell, Cell currentCell) {
		int minNeighbours = 1;
		if (Math.random() * 10 != 0)
			minNeighbours = (int) (Math.random() * 10) % 5 + 4;

		if (CellOperationsFacade.isOnGrainBoundary(previousCell, minNeighbours)) {

			Set<Long> neighbourGrainIds = new HashSet<>();

			for (Cell neighbour : previousCell.getNeighbours()) {
				if ((neighbour.getGrainId() >= 0 && previousCell.getGrainId() < 0)
						|| (neighbour.getGrainId() < 0 && previousCell.getGrainId() >= 0)) {
					neighbourGrainIds.add(neighbour.getGrainId());
				}
			}
			if (!neighbourGrainIds.isEmpty()) {
				Long newGrainId = neighbourGrainIds.iterator().next();

				Map<Long, Integer> grainIdToAmountOfGrainsMap = new HashMap<>();
				for (Long gId : neighbourGrainIds) {
					grainIdToAmountOfGrainsMap.put(gId, 0);
				}
				for (Cell neighbour : previousCell.getNeighbours()) {
					Long key = neighbour.getGrainId();
					Integer value;
					if (grainIdToAmountOfGrainsMap.containsKey(key)) {
						value = grainIdToAmountOfGrainsMap.get(key);
						value++;
						grainIdToAmountOfGrainsMap.replace(key, value);
					}
				}
				Long maxKey = newGrainId;
				for (Long key : neighbourGrainIds) {
					if (grainIdToAmountOfGrainsMap.get(key).longValue() >= grainIdToAmountOfGrainsMap.get(maxKey)
							.longValue())
						maxKey = key;
					else if (grainIdToAmountOfGrainsMap.get(key)
							.longValue() == (grainIdToAmountOfGrainsMap.get(maxKey).longValue())) {
						Random random = new Random();
						float indicator = random.nextFloat();
						if (indicator >= 0.5)
							maxKey = key;
					}
				}

				if (!(actualAlphaPhaseFraction > givenAlphaPhaseFraction && maxKey < 0)
						|| !((1 - actualAlphaPhaseFraction) > (1 - givenAlphaPhaseFraction) && maxKey >= 0))
					CellOperationsFacade.setNewGrainId(currentCell, maxKey);

			}

		}
		return currentCell;
	}

}
