package backend.implementations;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import backend.enums.BoundaryCondition;
import backend.enums.NeighbourhoodType;
import backend.exceptions.OutOfBoundariesException;
import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;

/**
 * Created by kdziegie on 2016-04-21.
 */
public class Space2D implements Serializable {
	
	private int rows = 0;
    private int columns = 0;
	private List<List<Cell>> spaceAsList;
    private BoundaryCondition boundaryCondition;
    private NeighbourhoodType neighbourhoodType;
    private int numberOfCellsWhichBelongToGrain;
    private int cellsOnGrainBoundaries = 0;
    private boolean enoughAlpha;
    private double alphaPhaseFraction;
	private boolean enoughBeta;
    private int alphaCells;
    private int betaCells;
    //private List<Grain> grains;
	private double meanAlphaGrainDiameter;


    public Space2D(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        //grains = new ArrayList();
        spaceAsList = new ArrayList<>(rows);
        List<Cell> internal;
        for(int i=0; i<rows;i++) {
            internal = new ArrayList<>(columns);
            for(int j=0; j<columns; j++) {
                internal.add(CellOperationsFacade.getNewCell(this, i, j));
            }
            spaceAsList.add(internal);
        }
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Cell get(int row, int column) throws OutOfBoundariesException {
        List<Cell> internal = spaceAsList.get(row);
        return internal.get(column);
    }

    public List<List<Cell>> getSpaceAsList() {
        return spaceAsList;
    }

    public synchronized void setSpaceAsList(List<List<Cell>> spaceAsList) {
        this.spaceAsList = spaceAsList;
    }

    public NeighbourhoodType getNeighbourhoodType() {
        return neighbourhoodType;
    }

    public void setNeighbourhoodType(NeighbourhoodType neighbourhoodType) {
        this.neighbourhoodType = neighbourhoodType;
    }

    public BoundaryCondition getBoundaryCondition() {
        return boundaryCondition;
    }

    public void setBoundaryCondition(BoundaryCondition boundaryCondition) {
        this.boundaryCondition = boundaryCondition;
    }


    public boolean isFilled() {
        return (numberOfCellsWhichBelongToGrain == rows*columns);
    }

    public boolean isAtLeastFilledPercent(double percent) {
        return (((double)numberOfCellsWhichBelongToGrain)/(double)(rows*columns)) >= percent;
    }


    public synchronized void  incrementNumberOfCellsWhichBelongToGrain(){
        numberOfCellsWhichBelongToGrain++;
    }
    
    
    public synchronized void incrementPhaseCellsNumber(long grainId){
    	if(grainId >= 0) betaCells++;
    	else alphaCells++;
    	
    	if(alphaCells >= columns*rows*alphaPhaseFraction) enoughAlpha = true;
    	if(betaCells >= columns*rows*(1-alphaPhaseFraction)) enoughBeta = true;
    }
    
    @Override
    public Space2D clone() {

        Space2D space2D = new Space2D(this.getRows(), this.getColumns()/*, this.meanGrainDiameter*/);
        space2D.setNeighbourhoodType(this.getNeighbourhoodType());
        space2D.setBoundaryCondition(this.getBoundaryCondition());
        int rows = this.getRows();
        int columns = this.getColumns();
        int alphaCells = this.getAlphaCells();
        int betaCells = this.getBetaCells();
        boolean enoughAlpha = this.isEnoughAlpha();
        double alphaPhaseFraction = this.alphaPhaseFraction;
    	boolean enoughBeta = this.isEnoughBeta();
    	
    	/*grains.stream()
    		.forEach(g -> grains2.add(new Grain(g)));*/
        
        List<List<Cell>> previousStepSpaceAsList = new ArrayList<>(rows);
        List<Cell> internal;
        //space2D.setGrainSizes(this.grainSizes);
        for(int i=0; i<rows;i++) {
            internal = new ArrayList<>(columns);
            for(int j=0; j<columns; j++) {
                try {
                    Cell c = this.get(i, j).clone(space2D);
                    internal.add(c);
                } catch (OutOfBoundariesException e) {
                    System.out.println(e.getMessage());
                }
            }
            previousStepSpaceAsList.add(internal);
        }
        
        space2D.enoughAlpha = enoughAlpha;
        space2D.setAlphaPhaseFraction(alphaPhaseFraction);
        space2D.enoughBeta = enoughBeta;
        space2D.setNumberOfCellsWhichBelongToGrain(this.getNumberOfCellsWhichBelongToGrain());
        space2D.setCellsOnGrainBoundaries(this.getCellsOnGrainBoundaries());
        space2D.setSpaceAsList(previousStepSpaceAsList);
        space2D.setAlphaCells(alphaCells);
        space2D.setBetaCells(betaCells);
        space2D.setMeanAlphaGrainDiameter(this.meanAlphaGrainDiameter);
        
        return space2D;
    }

    public int getCellsOnGrainBoundaries() {
        int thoseCells = 0;
        for(int i=0; i < rows; i++) {
            for(int j=0; j<columns; j++) {
                Cell cell = null;
                try {
                    cell = get(i, j);
                } catch (OutOfBoundariesException e) {
                    e.printStackTrace();
                }
                if(CellOperationsFacade.isOnGrainBoundary(cell)){
                    thoseCells++;
                }
            }
        }
        cellsOnGrainBoundaries = thoseCells;
        return cellsOnGrainBoundaries;
    }

    public synchronized void setCellsOnGrainBoundaries(int cellsOnGrainBoundaries) {
        this.cellsOnGrainBoundaries = cellsOnGrainBoundaries;
    }

    public int getNumberOfCellsWhichBelongToGrain() {
        return numberOfCellsWhichBelongToGrain;
    }

    public synchronized void setNumberOfCellsWhichBelongToGrain(int numberOfCellsWhichBelongToGrain) {
        this.numberOfCellsWhichBelongToGrain = numberOfCellsWhichBelongToGrain;
    }

	public int getAlphaCells() {
		return alphaCells;
	}

	public synchronized void setAlphaCells(int alphaCells) {
		this.alphaCells = alphaCells;
	}

	public synchronized void setAlphaPhaseFraction(double alphaPhaseFraction) {
		this.alphaPhaseFraction = alphaPhaseFraction;
	}

	public int getBetaCells() {
		return betaCells;
	}

	public synchronized void setBetaCells(int betaCells) {
		this.betaCells = betaCells;
	}
	
    public boolean isEnoughAlpha() {
		return enoughAlpha;
	}

	public boolean isEnoughBeta() {
		return enoughBeta;
	}
	
	public Map<Long, Integer> getGrainSizes() {
		return Operations.checkGrainSizes(this);
	}
	
	public double getMeanAlphaGrainDiameter(){
		return meanAlphaGrainDiameter;
	}
	
	public synchronized void setMeanAlphaGrainDiameter(double meanAlphaGrainDiameter){
		this.meanAlphaGrainDiameter = meanAlphaGrainDiameter;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		spaceAsList.stream().forEach(list->list.stream().forEach(c->sb.append(c.toString())));
		return rows + "\n" + columns + "\n" + boundaryCondition + "\n" + neighbourhoodType + sb.toString() ;
	}
	
	


}
