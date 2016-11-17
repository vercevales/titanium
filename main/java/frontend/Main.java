package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
    	Date date = new Date();
    	DateFormat dateFormat = new SimpleDateFormat("HH:mm");
    	String formattedDate = dateFormat.format(date);
		File file = new File("console " + formattedDate + ".log");
		PrintStream printStream = null;
		try {
			printStream = new PrintStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.setOut(printStream);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("Program starts!\n" + dateFormat.format(date) +"\n");
    	
        Parent root = FXMLLoader.load(getClass().getResource("/window.fxml"));
        primaryStage.getIcons().add(new Image("/icon.png"));
        primaryStage.setTitle("Dual-Phase Titanium by Karol Dziegiel");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.show();

        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
