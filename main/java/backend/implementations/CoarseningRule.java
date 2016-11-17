package backend.implementations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;
import backend.interfaces.Rule;

public class CoarseningRule implements Rule {

	private double temperatureK;
	private double probability;
	private double meanAlphaGrainDiameter;
	private boolean isAlphaAboveEquilibrium;

	public CoarseningRule(double temperatureC, boolean isAlphaAboveEquilibrium) {
		super();
		this.temperatureK = temperatureC + 273.15;
		this.isAlphaAboveEquilibrium = isAlphaAboveEquilibrium;
	}

	@Override
	public Cell nextStepCell(Cell previousCell, Cell currentCell) {
		int minNeighbours = 1;
		if (Math.random() * 10 != 0)
			minNeighbours = (int) (Math.random() * 10) % 5 + 4;

		if (CellOperationsFacade.isOnGrainBoundary(previousCell, minNeighbours)) {
			meanAlphaGrainDiameter = previousCell.getOwningSpace().getMeanAlphaGrainDiameter();

			if (CellOperationsFacade.isOnSamePhaseBoundary(previousCell)) {
				probability = Operations.calculateSamePhaseProbability(temperatureK, meanAlphaGrainDiameter);
				// System.out.println("Same phase probability: " + probability);
			}

			if (CellOperationsFacade.isOnPhaseBoundary(previousCell)) {
				probability = Operations.calculateDifferentPhaseProbability(temperatureK, meanAlphaGrainDiameter);
				// System.out.println("Different phase probability: " +
				// probability);
			}

			if (Math.random() < probability) {
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
					if ((isAlphaAboveEquilibrium && maxKey >= 0) 
							|| (isAlphaAboveEquilibrium == false && maxKey < 0))
						CellOperationsFacade.setNewGrainId(currentCell, maxKey);

				}
			}
		}
		return currentCell;
	}

}
