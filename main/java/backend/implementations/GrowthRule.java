package backend.implementations;

import backend.enums.State;
import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;
import backend.interfaces.Rule;

import java.util.*;

/**
 * Created by kdziegie on 2016-05-17.
 */
public class GrowthRule implements Rule {
	int i = 0;
	int j = 0;
    @Override
    public Cell nextStepCell(Cell previousCell, Cell currentCell) {
        if (previousCell.getState() == State.EMPTY) {
            Set<Long> neighbourGrainIds = new HashSet<>();

            for (Cell neighbour : previousCell.getNeighbours()) {
                if(neighbour.getState() == State.GRAIN){
                    neighbourGrainIds.add(neighbour.getGrainId());
                }
            }
            if(!neighbourGrainIds.isEmpty()) {
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
                    if(grainIdToAmountOfGrainsMap.get(key).longValue() >= grainIdToAmountOfGrainsMap.get(maxKey).longValue() ) maxKey = key;
                    else if (grainIdToAmountOfGrainsMap.get(key).longValue() == (grainIdToAmountOfGrainsMap.get(maxKey).longValue())) {
                        Random random = new Random();
                        float indicator = random.nextFloat();
                        if(indicator >= 0.5) maxKey = key;
                    }
                }
                CellOperationsFacade.setGrainId(currentCell, maxKey);
            }
        }
        return currentCell;
    }
}
