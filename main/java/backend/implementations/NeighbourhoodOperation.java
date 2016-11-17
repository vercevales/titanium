package backend.implementations;

import backend.enums.BoundaryCondition;
import backend.exceptions.OutOfBoundariesException;
import backend.enums.NeighbourhoodType;

import java.util.Random;

/**
 * Created by kdziegie on 2016-05-10.
 */
public class NeighbourhoodOperation {

    public static Space2D generateNeighbourhood(Space2D space) {
        NeighbourhoodType neighbourhoodType = space.getNeighbourhoodType();
        BoundaryCondition boundaryCondition = space.getBoundaryCondition();
        if(neighbourhoodType == NeighbourhoodType.MOORE && boundaryCondition == BoundaryCondition.NONPERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    //top boudary
                    if (i == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            //space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.MOORE && boundaryCondition == BoundaryCondition.PERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {

                    //top boudary
                    if (i == 0) {

                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.VON_NEUMANN && boundaryCondition == BoundaryCondition.NONPERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    //top boudary
                    if (i == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            //space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.VON_NEUMANN && boundaryCondition == BoundaryCondition.PERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {

                    //top boudary
                    if (i == 0) {

                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            } else if (j == space.getColumns() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            } else {
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            } else if (j == space.getColumns() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            } else {
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_RIGHT && boundaryCondition == BoundaryCondition.NONPERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    //top boudary
                    if (i == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            //space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_RIGHT && boundaryCondition == BoundaryCondition.PERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {

                    //top boudary
                    if (i == 0) {

                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        } else  if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_LEFT && boundaryCondition == BoundaryCondition.NONPERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    //top boudary
                    if (i == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            //space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));

                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            //space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_LEFT && boundaryCondition == BoundaryCondition.PERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {

                    //top boudary
                    if (i == 0) {

                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //bottom boundary
                    if (i == space.getRows() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                            space.get(i, j).getNeighbours().add(space.get(0, j));
                            if (j == 0) {
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                            } else if (j == space.getColumns()-1) {
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }else{
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //left boundary
                    if (j == 0) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //right boundary
                    if (j == space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i, 0));
                            if (i != 0 && i != space.getRows() - 1) {
                                space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                space.get(i, j).getNeighbours().add(space.get(i+1, j));
                            }
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                    //the rest
                    if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                        try {
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                            space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                        } catch (OutOfBoundariesException e) {
                            System.out.print(e.getMessage());
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_RANDOM && boundaryCondition == BoundaryCondition.NONPERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    Random random = new Random();
                    float randomNumber = random.nextFloat();
                    if (randomNumber >= 0.5) {
                        //nonperiodic hexagonal right
                        if (i == 0) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //bottom boundary
                        if (i == space.getRows() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                                //space.get(i, j).getNeighbours().add(space.get(0, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //left boundary
                        if (j == 0) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //right boundary
                        if (j == space.getColumns() - 1) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(i, 0));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //the rest
                        if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    } else {
                        //nonperiodyc hexagonal left
                        //top boudary
                        if (i == 0) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //bottom boundary
                        if (i == space.getRows() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                                //space.get(i, j).getNeighbours().add(space.get(0, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));

                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //left boundary
                        if (j == 0) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //right boundary
                        if (j == space.getColumns() - 1) {
                            try {
                                //space.get(i, j).getNeighbours().add(space.get(i, 0));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //the rest
                        if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    }
                }
            }
        }else if(neighbourhoodType == NeighbourhoodType.HEXAGONAL_RANDOM && boundaryCondition == BoundaryCondition.PERIODIC) {
            for (int i = 0; i < space.getRows(); i++) {
                for (int j = 0; j < space.getColumns(); j++) {
                    Random random = new Random();
                    float randomNumber = random.nextFloat();
                    if (randomNumber >= 0.5) {
                        //nonperiodic hexagonal right
                        if (i == 0) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //bottom boundary
                        if (i == space.getRows() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                                space.get(i, j).getNeighbours().add(space.get(0, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //left boundary
                        if (j == 0) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //right boundary
                        if (j == space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i, 0));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //the rest
                        if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    } else {
                        //nonperiodyc hexagonal left
                        //top boudary
                        if (i == 0) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //bottom boundary
                        if (i == space.getRows() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(space.getRows() - 2, j));
                                space.get(i, j).getNeighbours().add(space.get(0, j));
                                if (j == 0) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));

                                } else if (j == space.getColumns()-1) {
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                }else{
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //left boundary
                        if (j == 0) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i, space.getColumns() - 1));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i, j+1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j+1));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //right boundary
                        if (j == space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i, 0));
                                if (i != 0 && i != space.getRows() - 1) {
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j));
                                    space.get(i, j).getNeighbours().add(space.get(i-1, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i, j-1));
                                    space.get(i, j).getNeighbours().add(space.get(i+1, j));
                                }
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                        //the rest
                        if (i != 0 && i != space.getRows() - 1 && j != 0 && j != space.getColumns() - 1) {
                            try {
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i - 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i, j - 1));
                                space.get(i, j).getNeighbours().add(space.get(i, j + 1));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j));
                                space.get(i, j).getNeighbours().add(space.get(i + 1, j + 1));
                            } catch (OutOfBoundariesException e) {
                                System.out.print(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
        return  space;
    }

}
