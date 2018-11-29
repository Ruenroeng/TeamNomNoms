package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
  	    BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane,1600,900);
      
        // top pane
        HBox HBoxTop = new HBox();
        HBoxTop.setSpacing(1300);
        Label title = new Label("NomNom Meal Prep Program.");
        Button loadFoodButton = new Button();
        loadFoodButton.setText("Load Food");
        HBoxTop.getChildren().add(title);
        HBoxTop.getChildren().add(loadFoodButton);
        bPane.setTop(HBoxTop);
      
        // right pane
        Label VBoxRightLabel = new Label("Meal");
        VBox VBoxRight = new VBox(10, VBoxRightLabel);
        bPane.setRight(VBoxRight);
        
        // left pane
        Label VBoxLeftLabel = new Label("Food List");
        VBox VBoxLeft = new VBox(10, VBoxLeftLabel);
        bPane.setLeft(VBoxLeft);
  
        // bottom pane
        HBox HBoxBottom = new HBox();
        HBoxBottom.setSpacing(400);
        Label bottomLeft = new Label("Add Food.");
        Label bottomCenter = new Label("Filter");
        Label bottomRight = new Label("Nutrition Links");
        HBoxBottom.getChildren().add(bottomLeft);
        HBoxBottom.getChildren().add(bottomCenter);
        HBoxBottom.getChildren().add(bottomRight);
        bPane.setBottom(HBoxBottom);
        
  
        primaryStage.setTitle("NomNomNom");
        //show stage
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
