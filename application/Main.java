package application;
	
import application.Main.FoodItem;
import application.Main.FoodListItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
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
        ObservableList<FoodItem> menuList = FXCollections.observableArrayList();
        menuList.add(new FoodItem("First Food", 1,2,3,4,5,"Add"));
        menuList.add(new FoodItem("Second Food", 6,7,8,9,10,"Add"));
        menuList.add(new FoodItem("Third Food", 11,12,13,14,15,"Add"));
        ListView<FoodItem> listViewRight = new ListView<>(menuList);
        
        listViewRight.setCellFactory(param -> new FoodListItem());
		VBox VBoxRight = new VBox();
		VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        //Label VBoxRightLabel = new Label("Meal");
        //VBox VBoxRight = new VBox(10,VBoxRightLabel);
        bPane.setRight(VBoxRight);
        
        // left pane
        ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
        foodList.add(new FoodItem("First Food", 1,2,3,4,5,"Remove"));
        foodList.add(new FoodItem("Second Food", 6,7,8,9,10,"Remove"));
        foodList.add(new FoodItem("Third Food", 11,12,13,14,15,"Remove"));
        ListView<FoodItem> listViewLeft = new ListView<>(foodList);
        
        listViewLeft.setCellFactory(param -> new FoodListItem());
		VBox VBoxLeft = new VBox();
		VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        //Label VBoxLeftLabel = new Label("Food List");
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
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        bottomCenter.getChildren().add(filter);
        HBoxBottom.getChildren().addAll(bottomCenter, bottomRight);
        bPane.setBottom(HBoxBottom);
        
  
        primaryStage.setTitle("NomNomNom");
        //show stage
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	public Node getHeader() {
		GridPane gPane = new GridPane();
		int numCols=7;
		for (int col = 0 ; col < numCols; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100/(numCols*1.0));
            //cc.setFillWidth(true);
            //cc.setHgrow(Priority.ALWAYS);
            gPane.getColumnConstraints().add(cc);
		}
		gPane.add(new Label("Name"), 0, 0);
		gPane.add(new Label("Cals"), 1, 0);
		gPane.add(new Label("Fat"), 2, 0);
		gPane.add(new Label("Carbs"), 3, 0);
		gPane.add(new Label("Fiber"), 4, 0);
		gPane.add(new Label("Protien"), 5, 0);
		gPane.add(new Label(""), 6, 0);
		return gPane;
	}
	public static class FoodItem {
		String name;
		int cals;
		int fat;
		int carbs;
		int fiber;
		int protien;
		String buttonText;
		FoodItem(String s, int cal, int fa , int carb,int fib,int pro,String b){
			name=s;
			cals=cal;
			fat = fa;
			carbs = carb;
			fiber = fib;
			protien = pro;
			buttonText=b;
		}
		public String getName() {
			return name;
		}
		public int getCals() {
			return cals;
		}
		public int getFat() {
			return fat;
		}
		public int getCarbs() {
			return carbs;
		}
		public int getFiber() {
			return fiber;
		}
		public int getProtien() {
			return protien;
		}
		public String getButtonText() {
			return buttonText;
		}
		
	}
	public static class FoodListItem extends ListCell<FoodItem>{
		//HBox hBox = new HBox();
		GridPane gPane = new GridPane();
		Label nameLabel = new Label();
		Label calsLabel = new Label();
		Label fatLabel = new Label();
		Label carbsLabel = new Label();
		Label fiberLabel = new Label();
		Label protienLabel = new Label();
		Button addButton = new Button();
		public FoodListItem () {
			super();
			//hBox.getChildren().addAll(nameLabel,calsLabel,addButton);
			int numCols=7;
			for (int col = 0 ; col < numCols; col++ ) {
	            ColumnConstraints cc = new ColumnConstraints();
	            cc.setPercentWidth(100/(numCols*1.0));
	            //cc.setFillWidth(true);
	            //cc.setHgrow(Priority.ALWAYS);
	            gPane.getColumnConstraints().add(cc);
			}
			gPane.add(nameLabel, 0, 0);
			gPane.add(calsLabel, 1, 0);
			gPane.add(fatLabel, 2, 0);
			gPane.add(carbsLabel, 3, 0);
			gPane.add(fiberLabel, 4, 0);
			gPane.add(protienLabel, 5, 0);
			gPane.add(addButton, 6, 0);
		}
		@Override
        protected void updateItem(FoodItem item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                setGraphic(null);
            } else {
                nameLabel.setText(item.getName()!=null ? item.getName() : "<null>");
                calsLabel.setText(item.getCals()+"");
                fatLabel.setText(item.getFat()+"");
                carbsLabel.setText(item.getCarbs()+"");
                fiberLabel.setText(item.getFiber()+"");
                protienLabel.setText(item.getProtien()+"");
                addButton.setText("Add");
                //setGraphic(hBox);
                setGraphic(gPane);
            }
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
