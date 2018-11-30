package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
  	    BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane,1600,900);
      
        // top pane
        HBox HBoxTop = new HBox(1300);
        Label title = new Label("NomNom Meal Prep Program.");
        Button loadFoodButton = new Button();
        loadFoodButton.setText("Load Food");
        HBoxTop.getChildren().addAll(title,loadFoodButton);
        bPane.setTop(HBoxTop);
      
        // right pane
        Label VBoxRightLabel = new Label("Meal");
        VBox VBoxRight = new VBox(10,VBoxRightLabel);
        bPane.setRight(VBoxRight);
        
        // left pane
        Label VBoxLeftLabel = new Label("Food List");
        VBox VBoxLeft = new VBox(10, VBoxLeftLabel);
        bPane.setLeft(VBoxLeft);
  
        // bottom pane
        HBox HBoxBottom = new HBox(400);
        Label bottomLeft = new Label("Add Food.");
        Label ItemDetailsLabel = new Label("Item Details");
        HBox CaloriesBox = new HBox();
        Label CaloriesLabel = new Label("Calories");
        TextField CaloriesField = new TextField("Calories");
        CaloriesBox.getChildren().add(CaloriesLabel);
        CaloriesBox.getChildren().add(CaloriesField);
        
        VBox ItemDetails = new VBox(10,ItemDetailsLabel);
        ItemDetails.getChildren().addAll(CaloriesBox);
        
        HBoxBottom.getChildren().add(ItemDetails);
        
        
        VBox bottomCenter = new VBox(10);
        HBox filter = new HBox(10);
        HBox macroSelect = new HBox(1);
        TextField macro = new TextField("Select Macro");
        Button macroDropDown = new Button("V");
        macroSelect.getChildren().addAll(macro, macroDropDown);
        HBox comparatorSelect = new HBox(1);
        TextField comparator = new TextField("Add comparator");
        Button comparatorDropDown = new Button("V");
        comparatorSelect.getChildren().addAll(comparator, comparatorDropDown);
        TextField value = new TextField("Enter value");
        Label bottomRight = new Label("Nutrition Links");
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        bottomCenter.getChildren().add(filter);
        HBoxBottom.getChildren().addAll(bottomLeft, bottomCenter, bottomRight);
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
