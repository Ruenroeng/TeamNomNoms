package application;
	
import application.Main.FoodItem;
import application.Main.FoodListItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
  	    BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane,1600,900);
      
        // top pane
        HBox HBoxTop = new HBox(1150);
        Label title = new Label("NomNom Meal Prep Program.");
        title.setUnderline(true);
        title.setFont(new Font("Arial",20));
        title.setMinWidth(350);
        Button loadFoodButton = new Button();
        loadFoodButton.setText("Load Food");
        HBoxTop.getChildren().addAll(title,loadFoodButton);
        HBox.setHgrow(title, Priority.ALWAYS);
        HBox.setHgrow(loadFoodButton, Priority.ALWAYS);
        HBoxTop.setMinHeight(50);
        bPane.setTop(HBoxTop);
      
        // right pane
        ObservableList<FoodItem> menuList = FXCollections.observableArrayList();
        menuList.add(new FoodItem("First Food", 1,2,3,4,5,"Add"));
        menuList.add(new FoodItem("Second Food", 6,7,8,9,10,"Add"));
        menuList.add(new FoodItem("Third Food", 11,12,13,14,15,"Add"));
        ListView<FoodItem> listViewRight = new ListView<>(menuList);
        
        listViewRight.setCellFactory(param -> new FoodListItem());
		//VBox VBoxRight = new VBox();
		//VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        Label VBoxRightLabel = new Label("Meal");
        VBoxRightLabel.setFont(new Font("Arial",18));
        VBox VBoxRight = new VBox(10,VBoxRightLabel);
        VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        VBoxRight.setAlignment(Pos.TOP_CENTER);
        bPane.setRight(VBoxRight);
        
        // left pane
        ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
        foodList.add(new FoodItem("First Food", 1,2,3,4,5,"Remove"));
        foodList.add(new FoodItem("Second Food", 6,7,8,9,10,"Remove"));
        foodList.add(new FoodItem("Third Food", 11,12,13,14,15,"Remove"));
        ListView<FoodItem> listViewLeft = new ListView<>(foodList);
        
        listViewLeft.setCellFactory(param -> new FoodListItem());
		//VBox VBoxLeft = new VBox();
		//VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        Label VBoxLeftLabel = new Label("Food List");
        VBoxLeftLabel.setFont(new Font("Arial",18));
        VBox VBoxLeft = new VBox(10, VBoxLeftLabel);
        VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        VBoxLeft.setAlignment(Pos.TOP_CENTER);
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
        
        Label filterLabel = new Label("Filter List Options");
        VBox bottomCenter = new VBox(10, filterLabel);
        bottomCenter.setAlignment(Pos.CENTER);
        GridPane filterArea = new GridPane();
        HBox filter = new HBox(10);
        ComboBox<String> macroSelect = new ComboBox<String>();
        macroSelect.getItems().addAll("Carbs", "Calories", "Fat", "Fiber", "Protein");
        macroSelect.setPromptText("Macro");
        macroSelect.setMinWidth(100);
        ComboBox<String> comparatorSelect = new ComboBox<String>();
        comparatorSelect.getItems().addAll("=", ">", "<", ">=", "<=");
        comparatorSelect.setPromptText("Comparator");
        TextField value = new TextField("Enter value");
        Label bottomRight = new Label("Nutrition Links");
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        Button filterButton = new Button("Add Filter");
        filterButton.setMinWidth(100);
        Button clearButton = new Button("Clear Filters");
        clearButton.setMinWidth(100);
        Button applyButton = new Button ("Apply Filters");
        applyButton.setMinWidth(100);
        filterArea.add(filter, 0, 0);
        filterArea.add(filterButton, 1, 0);
        filterArea.add(clearButton, 1, 1);
        filterArea.add(applyButton, 1, 2);
        filterArea.setHgap(10);
        filterArea.setVgap(10);
        bottomCenter.getChildren().add(filterArea);
        HBoxBottom.getChildren().addAll(bottomCenter, bottomRight);
        bPane.setBottom(HBoxBottom);
        VBox appliedFilters = new VBox(5);
        filterButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	applyFilter(macroSelect, comparatorSelect, value, appliedFilters);
        }});
        
        clearButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	appliedFilters.getChildren().clear();
        }});
        
        applyButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	System.out.println("Not implemented yet");
        }});
        filterArea.add(appliedFilters, 0, 1, 2, 1);
        
  
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
	
	public void applyFilter(ComboBox<String> macro, ComboBox<String> comparator, TextField value, VBox filters) {
		int filterValue;
		String filter;
		if (macro.getValue() == null || macro.getValue().toString().isEmpty() || comparator.getValue() == null || comparator.getValue().toString().isEmpty()){
                    System.out.println("No value selected for macro or comparator");
                    return;
		}
        try {
        	filterValue = Integer.parseInt(value.getText());
        } catch(NumberFormatException e) {
        	System.out.println("Filter value must be numeric");
        	return;
        }
        
        if (filterValue < 0) {
        	System.out.println("Negative values not allowed");
        	return;
        }
        filter = macro.getValue() + " " + comparator.getValue() + " " + filterValue;
        filters.getChildren().add(new Label(filter));
        
		
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
