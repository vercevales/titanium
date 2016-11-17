package frontend;

import backend.enums.BoundaryCondition;
import backend.exceptions.OutOfBoundariesException;
import backend.implementations.*;
import backend.implementations.cellmanagement.Cell;
import backend.implementations.cellmanagement.CellOperationsFacade;
import backend.enums.NeighbourhoodType;
import backend.enums.State;
import backend.interfaces.Rule;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.swing.text.InternationalFormatter;

public class Controller {

	@FXML
	SplitPane splitPane;
	@FXML
	AnchorPane leftAnchorPane;
	@FXML
	GridPane canvas;
	@FXML
	Button createButton, startButton, resetButton, coarseningButton, importSpaceButton, exportSpaceButton;
	@FXML
	TextField canvasWidthTextField, canvasHeightTextField;
	@FXML
	TextField alphaNucleiNumberTextField, betaNucleiNumberTextField;
	@FXML
	Label nucleiNumberLabel;
	@FXML
	TextField meanAlphaGrainDiameterTextField, meanBetaGrainDiameterTextField;
	@FXML
	Label meanGrainDiameterLabel;
	@FXML
	TextField alphaPhaseTextField;
	@FXML
	CheckBox noCorrectionCheckBox, drawBordersCheckBox, isothermalCheckBox;
	@FXML
	RadioButton mooreRadioButton, hexagonalRandomRadioButton;
	@FXML
	Label alphaPhaseLabel;
	@FXML
	CheckBox periodicCheckBox;
	@FXML
	Label alphaLabel1, alphaLabel2, betaLabel1, betaLabel2;
	@FXML
	TextField betaGrowthStartingPointTextField;
	@FXML
	Label betaGrowthStartingPointLabel;
	@FXML
	TextField startTemperatureTextField, endTemperatureTextField, iterationsTextField;
	@FXML
	Label temperatureLabel, startTemperatureLabel, endTemperatureLabel, iterationsLabel;

	private Space2D space2D;
	private int canvasWidth, canvasHeight;
	private BoundaryCondition boundaryCondition = null;
	private NeighbourhoodType neighbourhoodType = null;
	private Rule rule;
	private boolean isSpaceCreated;
	private double givenAlphaPhaseFraction;
	private int alphaNucleiNumber;
	private double meanAlphaGrainDiameter;
	private double meanAlphaGrainSize;
	private int betaNucleiNumber;
	private double meanBetaGrainSize;
	private double meanBetaGrainDiameter;
	private double betaGrowthStartingPoint;

	@FXML
	private void initialize() {
		leftAnchorPane.setMaxWidth(250);
		leftAnchorPane.setMinWidth(250);
		startButton.setVisible(false);
		alphaNucleiNumberTextField.setVisible(false);
		betaNucleiNumberTextField.setVisible(false);
		nucleiNumberLabel.setVisible(false);
		meanAlphaGrainDiameterTextField.setVisible(false);
		meanBetaGrainDiameterTextField.setVisible(false);
		meanGrainDiameterLabel.setVisible(false);
		noCorrectionCheckBox.setVisible(true);
		drawBordersCheckBox.setVisible(false);
		alphaPhaseTextField.setVisible(false);
		alphaPhaseLabel.setVisible(false);
		hexagonalRandomRadioButton.setSelected(true);
		noCorrectionCheckBox.setVisible(true);
		alphaLabel1.setVisible(false);
		alphaLabel2.setVisible(false);
		betaLabel1.setVisible(false);
		betaLabel2.setVisible(false);
		alphaNucleiNumberTextField.setEditable(true);
		betaNucleiNumberTextField.setEditable(true);
		meanAlphaGrainDiameterTextField.setEditable(true);
		meanBetaGrainDiameterTextField.setEditable(true);
		alphaPhaseTextField.setEditable(true);
		canvasHeightTextField.setEditable(true);
		canvasWidthTextField.setEditable(true);
		startButton.setText("START");
		canvas.setVisible(false);
		betaGrowthStartingPointLabel.setVisible(false);
		betaGrowthStartingPointTextField.setVisible(false);
		startTemperatureTextField.setVisible(false);
		endTemperatureTextField.setVisible(false);
		iterationsTextField.setVisible(false);
		temperatureLabel.setVisible(false);
		startTemperatureLabel.setVisible(false);
		endTemperatureLabel.setVisible(false);
		iterationsLabel.setVisible(false);
		coarseningButton.setVisible(false);
		isothermalCheckBox.setVisible(false);
		noCorrectionCheckBox.setVisible(false);
		neighbourhoodType = NeighbourhoodType.HEXAGONAL_RANDOM;
		exportSpaceButton.setVisible(false);
		
	}

	@FXML
	public void hexagonalClicked() {
		mooreRadioButton.setSelected(false);
		neighbourhoodType = NeighbourhoodType.HEXAGONAL_RANDOM;
	}

	@FXML
	public void mooreClicked() {
		hexagonalRandomRadioButton.setSelected(false);
		neighbourhoodType = NeighbourhoodType.MOORE;
	}

	@FXML
	public void createButtonClicked() {
		boundaryCondition = (periodicCheckBox.isSelected()) ? BoundaryCondition.PERIODIC
				: BoundaryCondition.NONPERIODIC;
		long start = System.nanoTime();
		createSpace();
		System.out.println("Space creation time: " + (System.nanoTime() - start) + "nanoseconds");
		if (isSpaceCreated) {
			createButton.setVisible(false);
			alphaNucleiNumberTextField.setVisible(true);
			betaNucleiNumberTextField.setVisible(true);
			nucleiNumberLabel.setVisible(true);
			meanAlphaGrainDiameterTextField.setVisible(true);
			meanBetaGrainDiameterTextField.setVisible(true);
			meanGrainDiameterLabel.setVisible(true);
			alphaPhaseTextField.setVisible(true);
			alphaPhaseLabel.setVisible(true);
			noCorrectionCheckBox.setVisible(false);
			startButton.setVisible(true);
			drawBordersCheckBox.setVisible(true);
			canvasHeightTextField.setEditable(false);
			canvasWidthTextField.setEditable(false);
			createButton.setVisible(true);
			alphaLabel1.setVisible(true);
			alphaLabel2.setVisible(true);
			betaLabel1.setVisible(true);
			betaLabel2.setVisible(true);
			betaGrowthStartingPointLabel.setVisible(true);
			betaGrowthStartingPointTextField.setVisible(true);
			noCorrectionCheckBox.setVisible(true);
		}
	}

	@FXML
	public void startButtonClicked() {
		alphaNucleiNumberTextField.setEditable(false);
		betaNucleiNumberTextField.setEditable(false);
		meanAlphaGrainDiameterTextField.setEditable(false);
		meanBetaGrainDiameterTextField.setEditable(false);
		alphaPhaseTextField.setEditable(false);
		if (!alphaNucleiNumberTextField.getText().isEmpty()) {
			givenAlphaPhaseFraction = Double.parseDouble(alphaPhaseTextField.getText()) / 100;
			alphaNucleiNumber = Integer.parseInt(alphaNucleiNumberTextField.getText());
			meanAlphaGrainSize = (canvasWidth * canvasHeight * givenAlphaPhaseFraction) / (alphaNucleiNumber);
			meanAlphaGrainDiameter = Math.sqrt((4 * meanAlphaGrainSize) / Math.PI);
			System.out.print("Alpha grain diameter: " + meanAlphaGrainDiameter);
			meanAlphaGrainDiameterTextField.setText(meanAlphaGrainDiameter + "");
		}
		if (!meanAlphaGrainDiameterTextField.getText().isEmpty()) {
			givenAlphaPhaseFraction = Double.parseDouble(alphaPhaseTextField.getText()) / 100;
			meanAlphaGrainDiameter = Double.parseDouble(meanAlphaGrainDiameterTextField.getText());
			meanAlphaGrainSize = Math.pow(Double.parseDouble(meanAlphaGrainDiameterTextField.getText()), 2) * 0.25
					* Math.PI;
			alphaNucleiNumber = (int) ((canvasWidth * canvasHeight * givenAlphaPhaseFraction) / (meanAlphaGrainSize));
			alphaNucleiNumberTextField.setText(alphaNucleiNumber + "");
		}
		if (!meanBetaGrainDiameterTextField.getText().isEmpty()) {
			givenAlphaPhaseFraction = Double.parseDouble(alphaPhaseTextField.getText()) / 100;
			meanBetaGrainDiameter = Double.parseDouble(meanBetaGrainDiameterTextField.getText());
			meanBetaGrainSize = Math.pow(Double.parseDouble(meanBetaGrainDiameterTextField.getText()), 2) * 0.25
					* Math.PI;
			betaNucleiNumber = (int) ((canvasWidth * canvasHeight * (1 - givenAlphaPhaseFraction))
					/ (meanBetaGrainSize));
			betaNucleiNumberTextField.setText(betaNucleiNumber + "");
		}
		if (!betaNucleiNumberTextField.getText().isEmpty()) {
			givenAlphaPhaseFraction = Double.parseDouble(alphaPhaseTextField.getText()) / 100;
			betaNucleiNumber = Integer.parseInt(betaNucleiNumberTextField.getText());
			meanBetaGrainSize = (canvasWidth * canvasHeight * (1 - givenAlphaPhaseFraction)) / (betaNucleiNumber);
			meanBetaGrainDiameter = Math.sqrt((4 * meanBetaGrainSize) / Math.PI);
			meanBetaGrainDiameterTextField.setText(meanBetaGrainDiameter + "");
		}
		runSimpleMode();
		startButton.setVisible(false);
		isothermalCheckBox.setVisible(true);
		temperatureLabel.setVisible(true);
		startTemperatureLabel.setVisible(true);
		startTemperatureLabel.setVisible(false);
		startTemperatureTextField.setVisible(true);
		iterationsLabel.setVisible(true);
		iterationsTextField.setVisible(true);
		coarseningButton.setVisible(true);
		exportSpaceButton.setVisible(true);

	}

	@FXML
	public void isothermalCheckBoxClicked() {
		if (isothermalCheckBox.isSelected()) {
			endTemperatureLabel.setVisible(false);
			endTemperatureTextField.setVisible(false);
			startTemperatureLabel.setVisible(false);
			iterationsLabel.setText("Iterations:");
		}
		if (!isothermalCheckBox.isSelected()) {
			endTemperatureLabel.setVisible(true);
			endTemperatureTextField.setVisible(true);
			startTemperatureLabel.setVisible(true);
			iterationsLabel.setText("Temp. rate [C/s]:");
		}
	}

	@FXML
	public void resetButtonClicked() {
		initialize();
	}

	@FXML
	public void coarseningButtonClicked() {
		double alphaPhaseFraction = Operations.calculateAlphaPhaseFraction(space2D);
		double temperatureC = Double.parseDouble(startTemperatureTextField.getText());
		Rule rule = new CoarseningRule(temperatureC,
				Operations.isAlphaAboveEquilibrium(alphaPhaseFraction, temperatureC));
		if (isothermalCheckBox.isSelected()) {
			for (int i = 0; i < Integer.parseInt(iterationsTextField.getText()); i++) {
				Operations.calculateMeanAlphaGrainDiameter(space2D);
				Operations.generateNextSpace2D(space2D, rule);
				System.out.println(
						"Iteration: " + i + " mean alpha grain diameter: " + space2D.getMeanAlphaGrainDiameter());
				alphaPhaseFraction = Operations.calculateAlphaPhaseFraction(space2D);
				rule = new CoarseningRule(temperatureC,
						Operations.isAlphaAboveEquilibrium(alphaPhaseFraction, temperatureC));
				System.out.println("Temperature: " + temperatureC + " Alpha phase: " + alphaPhaseFraction
						+ " Above eq: " + Operations.isAlphaAboveEquilibrium(alphaPhaseFraction, temperatureC));
			}
		}
		if(!isothermalCheckBox.isSelected()){
			for(double tempC = Double.parseDouble(startTemperatureTextField.getText());
				tempC <= Double.parseDouble(endTemperatureTextField.getText());
					tempC+= Double.parseDouble(iterationsTextField.getText())){
				Operations.calculateMeanAlphaGrainDiameter(space2D);
				Operations.generateNextSpace2D(space2D, rule);
				System.out.println(
						"Temperature: " + tempC + " mean alpha grain diameter: " + space2D.getMeanAlphaGrainDiameter());
				alphaPhaseFraction = Operations.calculateAlphaPhaseFraction(space2D);
				rule = new CoarseningRule(tempC,
						Operations.isAlphaAboveEquilibrium(alphaPhaseFraction, tempC));
				System.out.println("Temperature: " + tempC + " Alpha phase: " + alphaPhaseFraction
						+ " Above eq: " + Operations.isAlphaAboveEquilibrium(alphaPhaseFraction, tempC));
			}
		}
		Operations.saveAsPng(space2D, false,
				"after" + ((isothermalCheckBox.isSelected()) ? " isothermal" : "nonisothermal") + " "
						+ startTemperatureTextField.getText() + "C " + iterationsTextField.getText() + " iterations");
		Operations.saveAsPng(space2D, true,
				"after" + ((isothermalCheckBox.isSelected()) ? " isothermal" : "nonisothermal") + " "
						+ startTemperatureTextField.getText() + "C " + iterationsTextField.getText() + " iterations");
		System.out.println(space2D.getMeanAlphaGrainDiameter());
		// System.exit(0);
		Image image = SwingFXUtils.toFXImage(Operations.generateImage(space2D, drawBordersCheckBox.isSelected()), null);
		canvas.getChildren().add(new ImageView(image));
		canvas.setVisible(true);
		System.out.println("Alpha phase percent: " + Operations.calculateAlphaPhaseFraction(space2D));
	}

	@FXML
	public void exportSpaceButtonClicked() {
		Operations.exportSpace(space2D);

	}

	@FXML
	public void importSpaceButtonClicked() {
		space2D = null;
		space2D = Operations.importSpace();
		if (space2D != null) {
			Image image = SwingFXUtils.toFXImage(Operations.generateImage(space2D, drawBordersCheckBox.isSelected()),
					null);
			canvas.getChildren().add(new ImageView(image));
			canvas.setVisible(true);
			createButton.setVisible(false);
			/*
			 * alphaNucleiNumberTextField.setVisible(true);
			 * betaNucleiNumberTextField.setVisible(true);
			 * nucleiNumberLabel.setVisible(true);
			 * meanAlphaGrainDiameterTextField.setVisible(true);
			 * meanBetaGrainDiameterTextField.setVisible(true);
			 * meanGrainDiameterLabel.setVisible(true);
			 * alphaPhaseTextField.setVisible(true);
			 * alphaPhaseLabel.setVisible(true);
			 * noCorrectionCheckBox.setVisible(false);
			 * startButton.setVisible(true);
			 * drawBordersCheckBox.setVisible(true);
			 * canvasHeightTextField.setEditable(false);
			 * canvasWidthTextField.setEditable(false);
			 * createButton.setVisible(true); alphaLabel1.setVisible(true);
			 * alphaLabel2.setVisible(true); betaLabel1.setVisible(true);
			 * betaLabel2.setVisible(true);
			 * betaGrowthStartingPointLabel.setVisible(true);
			 * betaGrowthStartingPointTextField.setVisible(true);
			 * noCorrectionCheckBox.setVisible(true);
			 */
			startButton.setVisible(false);
			isothermalCheckBox.setVisible(true);
			temperatureLabel.setVisible(true);
			startTemperatureLabel.setVisible(true);
			startTemperatureLabel.setVisible(false);
			startTemperatureTextField.setVisible(true);
			iterationsLabel.setVisible(true);
			iterationsTextField.setVisible(true);
			coarseningButton.setVisible(true);
		}
	}

	private void distributeAlphaNuclei() {
		Random random = new Random();
		List<Point> points = new ArrayList<>(alphaNucleiNumber);
		int x = 0, y = 0;
		double minDistance = Double.parseDouble(meanAlphaGrainDiameterTextField.getText()) / 2;
		double distance = 0;
		boolean shortDistance = true;
		for (int i = 0; i < alphaNucleiNumber; i++) {
			try {
				if (i == 0) {
					x = Math.abs(random.nextInt() % canvasWidth);
					y = Math.abs(random.nextInt() % canvasHeight);
					points.add(new Point(x, y));
				} else {
					while (shortDistance) {
						x = Math.abs(random.nextInt() % canvasWidth);
						y = Math.abs(random.nextInt() % canvasHeight);
						for (Point point : points) {
							distance = point.getDistance(x, y);
							if (distance < minDistance) {
								shortDistance = true;
								break;
							} else
								shortDistance = false;
						}
						points.add(new Point(x, y));
					}
					shortDistance = true;
				}

				Cell c = space2D.get(x, y);
				Long newGrainId = Math.abs(random.nextLong());
				newGrainId = -1 * newGrainId;
				CellOperationsFacade.setGrainId(c, newGrainId);
				/*
				 * if (!noCorrectionCheckBox.isSelected()) { Color color =
				 * Color.WHITE; if (c.getState() == State.GRAIN) { if
				 * (c.getGrainId() >= 0) { color = Color.RED; } else { color =
				 * Color.BLUE; } } squares.get(x).get(y).setFill(color); }
				 */
			} catch (OutOfBoundariesException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	private void distributeBetaNuclei() {
		Random random = new Random();
		List<Point> points = new ArrayList<>(betaNucleiNumber);
		int x = 0, y = 0;
		double minDistance = Math
				.sqrt(((1 - givenAlphaPhaseFraction) * space2D.getRows() * space2D.getColumns() / betaNucleiNumber)
						/ Math.PI);
		// minDistance = meanAlphaGrainDiameter / 4;
		double distance = 0;
		boolean shortDistance = true;
		for (int i = 0; i < betaNucleiNumber; i++) {
			try {
				if (i == 0) {
					do {
						x = Math.abs(random.nextInt() % canvasWidth);
						y = Math.abs(random.nextInt() % canvasHeight);
					} while (space2D.get(x, y).getState() == State.GRAIN);
					points.add(new Point(x, y));
				} else {
					while (shortDistance) {
						do {
							x = Math.abs(random.nextInt() % canvasWidth);
							y = Math.abs(random.nextInt() % canvasHeight);
						} while (space2D.get(x, y).getState() == State.GRAIN);
						for (Point point : points) {
							distance = point.getDistance(x, y);
							if (distance < minDistance) {
								shortDistance = true;
								break;
							} else
								shortDistance = false;
						}
						points.add(new Point(x, y));
					}
					shortDistance = true;
				}

				Cell c = space2D.get(x, y);
				Long newGrainId = Math.abs(random.nextLong());
				CellOperationsFacade.setGrainId(c, newGrainId);
				/*
				 * if (!noCorrectionCheckBox.isSelected()) { Color color =
				 * Color.WHITE; if (c.getState() == State.GRAIN) { if
				 * (c.getGrainId() >= 0) { color = Color.RED; } else { color =
				 * Color.BLUE; } } squares.get(x).get(y).setFill(color); }
				 */
			} catch (OutOfBoundariesException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	private void runSimpleMode() {

		noCorrectionCheckBox.setVisible(true);
		space2D.setAlphaPhaseFraction(givenAlphaPhaseFraction);
		System.out.println("Distributing " + alphaNucleiNumber + " alpha nuclei");
		distributeAlphaNuclei();
		while (!space2D.isAtLeastFilledPercent(givenAlphaPhaseFraction * betaGrowthStartingPoint)) {
			Operations.generateNextSpace2D(space2D, rule);
		}
		System.out.println("Distributing " + betaNucleiNumber + " beta nuclei");
		distributeBetaNuclei();
		while (!space2D.isFilled()) {
			Operations.generateNextSpace2D(space2D, rule);
		}

		// correcting
		System.out.println(
				"Before correction:\nalpha = " + space2D.getAlphaCells() + " beta = " + space2D.getBetaCells());
		long start = System.nanoTime();
		Operations.calculateMeanAlphaGrainDiameter(space2D);
		System.out.println("Mean alhpa grain diameter before correction " + space2D.getMeanAlphaGrainDiameter());
		if (!noCorrectionCheckBox.isSelected()) {
			System.out.println("Correction begins");
			// rule = new CorrectingRule();
			/*
			 * while ((space2D.isEnoughAlpha() && Math.abs(
			 * space2D.getAlphaCells() - givenAlphaPhaseFraction *
			 * space2D.getRows() * space2D.getColumns()) > 0.01) ||
			 * (space2D.isEnoughBeta() && Math.abs(space2D.getBetaCells() - (1 -
			 * givenAlphaPhaseFraction) * space2D.getRows() *
			 * space2D.getColumns()) > 0.01)) {
			 * Operations.generateNextSpace2D(space2D, rule); System.out.
			 * println("Correction in progress. Alpha phase fraction: " +
			 * Operations.calculateAlphaPhaseFraction(space2D));
			 * 
			 * }
			 */
			double actualAlphaPhaseFraction;
			final double beginAlphaPhaseFraction = Operations.calculateAlphaPhaseFraction(space2D);
			do {
				actualAlphaPhaseFraction = Operations.calculateAlphaPhaseFraction(space2D);
				rule = new CorrectingRule(givenAlphaPhaseFraction, actualAlphaPhaseFraction);
				Operations.generateNextSpace2D(space2D, rule);
				System.out.println("Correction in progress. Alpha phase fraction: " + actualAlphaPhaseFraction);
			} while ((beginAlphaPhaseFraction > givenAlphaPhaseFraction &&  actualAlphaPhaseFraction > givenAlphaPhaseFraction)
					|| (beginAlphaPhaseFraction < givenAlphaPhaseFraction && actualAlphaPhaseFraction < givenAlphaPhaseFraction));

			// setting borders after correction
			space2D.getSpaceAsList().stream().forEach(list -> list.stream().forEach(Operations::isOnGrainBoundary));
			Operations.calculateMeanAlphaGrainDiameter(space2D);
			System.out.println("Mean alhpa grain diameter after correction " + space2D.getMeanAlphaGrainDiameter());
		}

		alphaPhaseTextField.setText(String.valueOf(Operations.calculateAlphaPhaseFraction(space2D)));

		Image image = SwingFXUtils.toFXImage(Operations.generateImage(space2D, drawBordersCheckBox.isSelected()), null);
		canvas.getChildren().add(new ImageView(image));
		canvas.setVisible(true);
		Operations.saveAsPng(space2D, true,
				"before" + ((isothermalCheckBox.isSelected()) ? " isothermal" : "nonisothermal") + " "
						+ startTemperatureTextField.getText() + "C " + iterationsTextField.getText() + " iterations");
		Operations.saveAsPng(space2D, false,
				"before" + ((isothermalCheckBox.isSelected()) ? " isothermal" : "nonisothermal") + " "
						+ startTemperatureTextField.getText() + "C " + iterationsTextField.getText() + " iterations");
		meanAlphaGrainDiameterTextField.setText(space2D.getMeanAlphaGrainDiameter() + "");
	}

	private void createSpace() {
		rule = new GrowthRule();
		if (canvasHeightTextField.getText() != null & canvasWidthTextField.getText() != null) {
			createButton.setVisible(false);
			canvasWidth = Integer.parseInt(canvasWidthTextField.getText());
			canvasHeight = Integer.parseInt(canvasHeightTextField.getText());
			betaGrowthStartingPoint = Double.parseDouble(betaGrowthStartingPointTextField.getText());
			space2D = new Space2D(canvasWidth, canvasHeight);
			Operations.generateNeighbourhood(space2D, neighbourhoodType, boundaryCondition);
			// if (!noCorrectionCheckBox.isSelected()) {
			canvas.setMaxSize(canvasWidth, canvasHeight);
			canvas.setMinSize(canvasWidth, canvasHeight);

			isSpaceCreated = true;
		}
	}

}
