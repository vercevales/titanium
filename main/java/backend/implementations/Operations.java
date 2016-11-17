package backend.implementations;

import backend.enums.BoundaryCondition;
import backend.exceptions.OutOfBoundariesException;
import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;
import backend.interfaces.Rule;
import backend.enums.NeighbourhoodType;
import backend.enums.State;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kdziegie on 2016-04-18.
 */
public class Operations {

	public static Space2D generateNextSpace2D(Space2D space, Rule rule) {

		// copying current space
		Space2D previousStepSpace = space.clone();
		Operations.generateNeighbourhood2(previousStepSpace);
		space.getSpaceAsList().stream().forEach(list -> list.stream().forEach(Operations::isOnGrainBoundary));

		class GeneratingThread extends Thread {
			int start, end;

			GeneratingThread(int startIncluded, int endExcluded) {
				this.start = startIncluded;
				this.end = endExcluded;
			}

			@Override
			public void run() {
				for (int i = start; i < end; i++) {
					for (int j = 0; j < space.getColumns(); j++) {
						try {
							// space.get(i,j).setState(rule.nextCellState(previousStepSpace.get(i,j)));
							// System.out.println("jestem , start= "+ start + "
							// coord: " + i + ", " + j);
							rule.nextStepCell(previousStepSpace.get(i, j), space.get(i, j));
							if (space.get(i, j).getState() != backend.enums.State.GRAIN) {
								// System.out.println("nie jest ziarnem: (" +i
								// +", " + j +")" );
							}
						} catch (OutOfBoundariesException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}

		// applying the rules
		List<Thread> threads = new ArrayList(4);

		for (int k = 0; k < 3; k++) {
			threads.add(new GeneratingThread(k * (space.getRows() / 3), (k + 1) * (space.getRows() / 3)));
		}

		threads.add(new Thread(() -> {
			for (int i = 3 * (space.getRows() / 3); i < 3 * (space.getRows() / 3) + (space.getRows() % 3); i++) {
				for (int j = 0; j < space.getColumns(); j++) {
					try {
						// space.get(i,j).setState(rule.nextCellState(previousStepSpace.get(i,j)));
						// System.out.println("jestem , start= 7" + " coord: " +
						// i + ", " + j);

						rule.nextStepCell(previousStepSpace.get(i, j), space.get(i, j));
					} catch (OutOfBoundariesException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}));

		threads.stream().forEach(t -> t.start());
		threads.stream().forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		/*
		 * for(int i=0; i<space.getRows();i++) { for(int j=0;
		 * j<space.getColumns(); j++) { try {
		 * //space.get(i,j).setState(rule.nextCellState(previousStepSpace.get(i,
		 * j))); rule.nextStepCell(previousStepSpace.get(i, j), space.get(i,
		 * j)); } catch (OutOfBoundariesException e) {
		 * System.out.println(e.getMessage()); } } }
		 */

		return space;
	}

	public static void isOnGrainBoundary(Cell cell) {
		CellOperationsFacade.setOnGrainBoundary(cell, false);
		for (Cell neighbour : cell.getNeighbours()) {
			if (neighbour.getState() == State.GRAIN && cell.getState() == State.GRAIN
					&& !neighbour.getGrainId().equals(cell.getGrainId())) {
				CellOperationsFacade.setOnGrainBoundary(cell, true);
			}
		}
	}

	public static Space2D generateNeighbourhood(Space2D space, NeighbourhoodType neighbourhoodType,
			BoundaryCondition boundaryCondition) {
		space.setBoundaryCondition(boundaryCondition);
		space.setNeighbourhoodType(neighbourhoodType);
		return NeighbourhoodOperation.generateNeighbourhood(space);
	}

	public static Space2D generateNeighbourhood2(Space2D space) {
		return NeighbourhoodOperation.generateNeighbourhood(space);
	}

	public static Map<Long, Integer> checkGrainSizes(Space2D space) {
		Map<Long, Integer> grainSizes = new ConcurrentHashMap<>(space.getRows() * space.getColumns());

		/*
		 * Cell c = null; for(int i=0; i < space.getRows(); i++){ for(int j=0;
		 * j< space.getColumns(); j++){ try { c = space.get(i, j); } catch
		 * (OutOfBoundariesException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } Integer value =
		 * grainSizes.get(c.getGrainId()); if(value != null){
		 * grainSizes.replace(c.getGrainId(), grainSizes.get(c.getGrainId())+1);
		 * }else{ grainSizes.putIfAbsent(c.getGrainId(), 0); } } }
		 */

		class CheckingGrainSizesThread extends Thread {
			int start, end;

			CheckingGrainSizesThread(int startIncluded, int endExcluded) {
				this.start = startIncluded;
				this.end = endExcluded;
			}

			@Override
			public void run() {
				Cell c = null;
				for (int i = start; i < end; i++) {
					for (int j = 0; j < space.getColumns(); j++) {
						try {
							c = space.get(i, j);
						} catch (OutOfBoundariesException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Integer value = grainSizes.get(c.getGrainId());
						if (value != null) {
							grainSizes.replace(c.getGrainId(), grainSizes.get(c.getGrainId()) + 1);
						} else {
							grainSizes.putIfAbsent(c.getGrainId(), 0);
						}
					}
				}
			}
		}

		List<Thread> threads = new ArrayList<>(4);

		for (int k = 0; k < 3; k++) {
			threads.add(new CheckingGrainSizesThread(k * (space.getRows() / 3), (k + 1) * (space.getRows() / 3)));
		}

		threads.add(new Thread(() -> {
			Cell c = null;
			for (int i = 3 * (space.getRows() / 3); i < 3 * (space.getRows() / 3) + (space.getRows() % 3); i++) {
				for (int j = 0; j < space.getColumns(); j++) {
					try {
						c = space.get(i, j);
					} catch (OutOfBoundariesException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Integer value = grainSizes.get(c.getGrainId());
					if (value != null) {
						grainSizes.replace(c.getGrainId(), grainSizes.get(c.getGrainId()) + 1);
					} else {
						grainSizes.putIfAbsent(c.getGrainId(), 0);
					}
				}
			}
		}));

		threads.stream().forEach(t -> t.start());
		threads.stream().forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		return grainSizes;
	}

	public static BufferedImage generateImage(Space2D space, boolean withBorders) {
		BufferedImage bufferedImage = new BufferedImage(space.getRows(), space.getColumns(),
				BufferedImage.TYPE_INT_RGB);
		final int RED = 16711680;
		final int BLUE = 255;
		final int BLACK = 0;
		for (int i = 0; i < space.getRows(); i++) {
			for (int j = 0; j < space.getColumns(); j++) {
				try {
					bufferedImage.setRGB(i, j, (space.get(i, j).getGrainId() >= 0) ? RED : BLUE);
				} catch (OutOfBoundariesException e) {
					e.printStackTrace();
				}
			}
		}

		if (withBorders) {
			generateNeighbourhood(space, NeighbourhoodType.MOORE, space.getBoundaryCondition());
			for (int j = 0; j < space.getRows(); j++) {
				for (int k = 0; k < space.getColumns(); k++) {
					try {
						Cell c = space.get(j, k);
						// System.out.println(c.getGrainId());
						if (CellOperationsFacade.isOnGrainBoundary(c)) {
							bufferedImage.setRGB(j, k, BLACK);
						}
					} catch (OutOfBoundariesException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return bufferedImage;
	}

	public static void saveAsPng(Space2D space, boolean withBorders, String customFileName) {
		String fileName;
		if (customFileName != null) {
			fileName = customFileName + ((withBorders) ? " with borders" : " without borders") + ".png";
		} else {
			fileName = "microstructure " + space.getColumns() + " x " + space.getRows()
					+ ((withBorders) ? " with borders" : " without borders") + ".png";
		}
		File file = new File(fileName);
		try {
			ImageIO.write(generateImage(space, withBorders), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void exportSpace(Space2D space) {
		String filename = "space.spc";
		File file = new File(filename);
		System.out.println("Exporting space to file: " + filename);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(filename);
			fileOutputStream.write(space.toString().getBytes());
		} catch (Exception ex) {
		}

	}

	public static Space2D importSpace() {
		Space2D space = null;
		String filename = "space.spc";
		System.out.println("Importing space from file: " + filename);
		try {
			FileReader fr = new FileReader(filename);

			BufferedReader br = new BufferedReader(fr);
			String line;
			space = new Space2D(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
			line = br.readLine();
			if(line.equals("PERIODIC")) space.setBoundaryCondition(BoundaryCondition.PERIODIC);
			if(line.equals("NONPERIODIC")) space.setBoundaryCondition(BoundaryCondition.NONPERIODIC);
			line = br.readLine();
			if(line.equals("MOORE")) space.setNeighbourhoodType(NeighbourhoodType.MOORE);
			if(line.equals("HEXAGONAL_RANDOM")) space.setNeighbourhoodType(NeighbourhoodType.HEXAGONAL_RANDOM);
			
			for(int i =0; i< space.getRows()*space.getColumns(); i++){
				try {
					Cell c = space.get(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));
					CellOperationsFacade.setState(c, State.GRAIN);
					CellOperationsFacade.setNewGrainId(c, Long.parseLong(br.readLine()));
				} catch (NumberFormatException | OutOfBoundariesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			br.close();
			fr.close();
			Operations.generateNeighbourhood(space, space.getNeighbourhoodType(), space.getBoundaryCondition());
			//Operations.calculateMeanAlphaGrainDiameter(space);
			space.getCellsOnGrainBoundaries();
			
			space.setAlphaPhaseFraction(Operations.calculateAlphaPhaseFraction(space));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return space;
	}
	
	public static double calculateAlphaPhaseFraction(Space2D space){
		long alpha = space.getSpaceAsList().stream()
				.mapToLong(list -> list.stream().filter(cell -> cell.getGrainId().compareTo(0L) == -1).count()).sum();
		return  (double)alpha / (double)(space.getRows()*space.getColumns());
	}

	public static double calculateSamePhaseProbability(double temperatureK, double meanAlphaGrainDiameter) {
		// constants
		final int q = 150000; // 150kJ/mol
		final double r = 8.314459848; // universal gas constant
		final double k1 = 2.44 * Math.pow(10, -6);
		final double k2 = 266000;
		final double po = 5 * Math.pow(10, 9);

		double pt = Math.exp(-1 * q / (r * temperatureK));
		double pi = 1 / Math.sqrt((Math.pow(meanAlphaGrainDiameter, 3.08) - Math.pow(k1, 3.08))
				/ (4.38 * 10000000 * 2 * Math.exp(-1 * k2 / (r * temperatureK))));
		return po * pi * pt;
	}

	public static double calculateDifferentPhaseProbability(double temperatureK, double meanAlphaGrainDiameter) {
		final int q = 150000; // 150kJ/mol
		final double r = 8.314459848; // universal gas constant
		final double k1 = 2.44 * Math.pow(10, -6);
		final double k2 = 266000;
		final double p1 = Math.pow(10, 4);
		
		double temperatureC = temperatureK - 273.15;
		// System.out.println(meanAlphaGrainDiameter);
		double pi = 1 / Math.sqrt((Math.pow(meanAlphaGrainDiameter, 3.08) - Math.pow(k1, 3.08))
				/ (4.38 * 10000000 * 2 * Math.exp(-1 * k2 / (r * temperatureK))));
		double pv = 1.41457151974597 * Math.pow(10, -8) * Math.exp(0.0180836045 * temperatureC);

		return p1 * pi * pv;
	}

	// very time consuming
	public static double calculateMeanAlphaGrainDiameter(Space2D space) {
		List<Long> alphaIds = new ArrayList();
		// long start = System.nanoTime();
		alphaIds.addAll(space.getGrainSizes().keySet());
		// System.out.println("getGrainSizes time: " + (System.nanoTime() -
		// start));
		// start = System.nanoTime();
		alphaIds.removeIf(id -> id.longValue() >= 0);
		// System.out.println("removeif time: " + (System.nanoTime() - start));

		// sum of alpha grain sizes
		double tmp[] = { 0 };
		// start = System.nanoTime();
		space.getGrainSizes().forEach((k, v) -> tmp[0] += v);
		// for(Long id : alphaIds){
		// tmp += space.getGrainSizes().get(id);
		// }

		// System.out.println("for loop time: " + (System.nanoTime() - start));

		// mean alpha grain size
		// start = System.nanoTime();
		tmp[0] = tmp[0] / alphaIds.size();
		space.setMeanAlphaGrainDiameter(Math.sqrt((4 * tmp[0]) / Math.PI));
		// System.out.println("setting meanalphagraindiam time: " +
		// (System.nanoTime() - start));
		return Math.sqrt((4 * tmp[0]) / Math.PI);
	}

	public static boolean isAlphaAboveEquilibrium(double alphaPhaseFraction, double temperatureC) {
		//double temperatureC = temperatureK - 273.15;
		double maxBetaPhase = (temperatureC < 600)? 0.055 : (0.000000016 * Math.pow(temperatureC, 3)
				- 2.95258748691339 * Math.pow(10, -5) * Math.pow(temperatureC, 2) + 0.0182257383 * temperatureC
				- 3.7091228409);
		double maxAlphaPhase = 1 - maxBetaPhase;
		return (alphaPhaseFraction > maxAlphaPhase) ? true : false;
	}
}
