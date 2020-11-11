package application;
	
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private TextField numberTextField;
	private Button goButton;
	private Button NextButton;
	private Label messageLabel;
	private Label fibonacciLabel;
	private Label nthLabel;
	private Label nthFibonacciLabel;
	
	   private long n1 = 0; // initialize with Fibonacci of 0
	   private long n2 = 1; // initialize with Fibonacci of 1
	   private int number = 1; // current Fibonacci number to display
	   
	private Parent createContent() {
		VBox root = new VBox();
		root.setPrefSize(500, 500);
		numberTextField = new TextField();
		goButton = new Button("Go");
		NextButton = new Button("Next");
		messageLabel = new Label();
		fibonacciLabel = new Label();
		nthLabel = new Label();
		nthFibonacciLabel = new Label();
		goButton.setOnAction(e -> {
			try {
		         int input = Integer.parseInt(numberTextField.getText());

		         // create, configure and launch FibonacciTask
		         FibonacciTask task = new FibonacciTask(input);

		         // display task's messages in messageLabel
		         messageLabel.textProperty().bind(task.messageProperty());

		         // clear fibonacciLabel when task starts
		         task.setOnRunning((succeededEvent) -> {
		            goButton.setDisable(true);
		            fibonacciLabel.setText(""); 
		         });
		         
		         // set fibonacciLabel when task completes successfully
		         task.setOnSucceeded((succeededEvent) -> {
		            fibonacciLabel.setText(task.getValue().toString());
		            goButton.setDisable(false);
		         });

		         // create ExecutorService to manage threads
		         ExecutorService executorService = 
		            Executors.newFixedThreadPool(1); // max pool thread is 1
		         executorService.execute(task); // start the task
		         executorService.shutdown();
		      }
		      catch (NumberFormatException e1) {
		         numberTextField.setText("Enter an integer");
		         numberTextField.selectAll();
		         numberTextField.requestFocus();
		      }
		});
		
		NextButton.setOnAction(e -> {
			// display the next Fibonacci number
		      nthLabel.setText("Fibonacci of " + number + ": ");
		      nthFibonacciLabel.setText(String.valueOf(n2));
		      long temp = n1 + n2;
		      n1 = n2;
		      n2 = temp;
		      ++number;
		});
		
		root.getChildren().addAll(
				numberTextField, goButton, messageLabel, fibonacciLabel, nthLabel, nthFibonacciLabel, NextButton);
		return root;
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		primaryStage.setScene(new Scene(createContent()));
		primaryStage.show();
//		try {
//			Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
////			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
