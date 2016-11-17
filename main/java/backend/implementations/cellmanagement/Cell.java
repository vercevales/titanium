package backend.implementations.cellmanagement;


import backend.enums.State;
import backend.implementations.Space2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kdziegie on 2016-04-18.
 */
public class Cell implements Serializable{
    private State state;
    /*
    internal variables
    */
     List<Cell> neighbours;
     int row;
     int col;
     Long grainId;
     Space2D owningSpace;
     boolean isOnGrainBoundary;


    Cell(){
        this.state= State.EMPTY;
        this.neighbours = new ArrayList<>();
    }

    Cell(Space2D owningSpace, State state, int row, int col, Long grainId) {
        this.state = state;
        this.neighbours = new ArrayList<>();
        this.row = row;
        this.col = col;
        this.grainId = grainId;
        this.owningSpace = owningSpace;
        this.isOnGrainBoundary = false;
    }

    Cell(Space2D owningSpace, int row, int col) {
        this.state= State.EMPTY;
        this.neighbours = new ArrayList<>();
        this.row = row;
        this.col = col;
        this.owningSpace = owningSpace;
        this.isOnGrainBoundary = false;
    }

    public State getState(){
        return state;
    }


    public void setState(State state){
        this.state = state;
    }

    public void setState(int state){
        this.state = (state == 1) ? State.GRAIN : State.EMPTY;
    }

    public List<Cell> getNeighbours(){
        return neighbours;
    }

//    public boolean isNeighbourOf(Cell startCell) {
//        for(Cell n : startCell.getNeighbours()){
//            if(this == n ) return true;
//        }
//        return false;
//    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Long getGrainId() {
        return grainId;
    }



    void setGrainId(long grainId) {
        this.owningSpace.incrementNumberOfCellsWhichBelongToGrain();
        this.owningSpace.incrementPhaseCellsNumber(grainId);
        this.state = State.GRAIN;
        this.grainId = new Long(grainId);
    }
    
    void setNewGrainId(long grainId){
    	if(grainId >= 0){
    		this.owningSpace.setAlphaCells(this.owningSpace.getAlphaCells() - 1);
    		this.owningSpace.incrementPhaseCellsNumber(grainId);
    		this.grainId = new Long(grainId);
    	}
    	if(grainId < 0){
    		this.owningSpace.setBetaCells(this.owningSpace.getBetaCells() - 1);
    		this.owningSpace.incrementPhaseCellsNumber(grainId);
    		this.grainId = new Long(grainId);
    	}
    }

    @Override
    public int hashCode(){
        int num1 = (this.state==State.EMPTY) ? 7 : 13;
        int num2 = this.neighbours.size();
        return num1 * num2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Cell.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Cell givenCell = (Cell) obj;
        return (this.state==givenCell.getState() );
    }

    public Space2D getOwningSpace() {
        return owningSpace;
    }

    
    
    void setOnGrainBoundary(boolean onGrainBoundary) {
        isOnGrainBoundary = onGrainBoundary;
    }

    public Cell clone(Space2D owningSpace) {
        State state1 = this.state;
        int row1 = this.row;
        int col1= this.col;
        Long grainId1 = this.grainId;
        Cell cell1 = new Cell(owningSpace, state1, row1, col1, grainId1);
        cell1.setOnGrainBoundary(this.isOnGrainBoundary);
        return cell1;
    }

	@Override
	public String toString() {
		return "\n"+ row + "\n" + col + "\n"
				+ grainId;
	}
    
	
    
}
