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
  
        /* bottom pane
         * This will have 3 sections:
         * 1) Add Item Box.
         * 2) Filters Box.
         * 3) Additional Resources
        */
        
        HBox HBoxBottom = new HBox(400);
        
        Label ItemDetailsLabel = new Label("Item Details");
        
      //Name Field Build
        HBox NameBox = new HBox();
        Label NameLabel = new Label("Name");
        TextField NameField = new TextField("Enter Name");
        NameBox.getChildren().addAll(NameLabel,NameField);
        
      //Calorie Field Build
        HBox CaloriesBox = new HBox(20);
        Label CaloriesLabel = new Label("Calories");
        TextField CaloriesField = new TextField("Enter Calories");
        CaloriesBox.getChildren().addAll(CaloriesLabel,CaloriesField);
        
      //Fat Field Build
        HBox FatBox = new HBox(20);
        Label FatLabel = new Label("Fat");
        TextField FatField = new TextField("Enter Fats");
        FatBox.getChildren().addAll(FatLabel,FatField);
        
      //Carbs Field Build
        HBox CarbsBox = new HBox(20);
        Label CarbsLabel = new Label("Carbs");
        TextField CarbsField = new TextField("Enter Carbs");
        CarbsBox.getChildren().addAll(CarbsLabel,CarbsField);
        //CarbsBox.setMargin(CarbsBox, 20);

      //Fiber Field Build
        HBox FiberBox = new HBox(20);
        Label FiberLabel = new Label("Fiber");
        TextField FiberField = new TextField("Enter Fiber");  
        FiberBox.getChildren().addAll(FiberLabel,FiberField);
        
      //Protein Field Build
        HBox ProteinBox = new HBox(20);
        Label ProteinLabel = new Label("Protein");
        TextField ProteinField = new TextField("Enter Protein");
        ProteinBox.getChildren().addAll(ProteinLabel,ProteinField);
             
        
        VBox ItemDetails = new VBox(10,ItemDetailsLabel);
        ItemDetails.getChildren().addAll(NameBox,CaloriesBox,FatBox,FiberBox,ProteinBox);
        
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
<<<<<<< HEAD
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        bottomCenter.getChildren().add(filter);
        HBoxBottom.getChildren().addAll(bottomLeft, bottomCenter, bottomRight);
=======
        HBoxBottom.getChildren().addAll(bottomCenter, bottomRight);
>>>>>>> refs/remotes/origin/master
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
