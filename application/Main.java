package application;
	
import application.Main.FoodListItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
  	    BorderPane bPane = new BorderPane();
        Scene scene = new Scene(bPane,1600,750);
      
        // top pane
        HBox HBoxTop = new HBox(500);
        Label title = new Label("NomNom Meal Prep Program.");
        title.setUnderline(true);
        title.setFont(new Font("Arial",20));
        title.setMinWidth(350);
        Button loadFoodButton = new Button();
        loadFoodButton.setText("Load Food");
        HBoxTop.getChildren().addAll(title,loadFoodButton);
        HBoxTop.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(title, Priority.ALWAYS);
        HBox.setHgrow(loadFoodButton, Priority.ALWAYS);
        HBoxTop.setMinHeight(50);
        bPane.setTop(HBoxTop);
      
        // right pane
        ObservableList<FoodItem> menuList = FXCollections.observableArrayList();
        //TODO add menu food here
        ListView<FoodItem> listViewRight = new ListView<>(menuList);
        
        listViewRight.setCellFactory(param -> new FoodListItem());
        //VBox VBoxRight = new VBox();
				//VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        Label VBoxRightLabel = new Label("Meal");
        VBoxRightLabel.setFont(new Font("Arial",18));
        //VBoxRightLabel.setAlignment(Pos.TOP_CENTER);
        VBox VBoxRight = new VBox(10,VBoxRightLabel);
        VBoxRight.getChildren().addAll(getHeader(),listViewRight);
        VBoxRight.setAlignment(Pos.TOP_CENTER);
        VBoxRight.setMinWidth(600);
        //bPane.setRight(VBoxRight);
        BorderPane.setAlignment(VBoxRight, Pos.CENTER_LEFT);
        
        
        // left pane
        ObservableList<FoodItem> foodList = FXCollections.observableArrayList();
        //TODO add food items here
        ListView<FoodItem> listViewLeft = new ListView<>(foodList);
        
        listViewLeft.setCellFactory(param -> new FoodListItem());
        //VBox VBoxLeft = new VBox();
        //VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        Label VBoxLeftLabel = new Label("Food List");
        VBoxLeftLabel.setFont(new Font("Arial",18));
        //VBoxLeftLabel.setAlignment(Pos.TOP_CENTER);
        VBox VBoxLeft = new VBox(10, VBoxLeftLabel);
        VBoxLeft.getChildren().addAll(getHeader(),listViewLeft);
        VBoxLeft.setAlignment(Pos.TOP_CENTER);
        VBoxLeft.setMinWidth(600);
        //bPane.setLeft(VBoxLeft);
        
        GridPane centerPane = new GridPane();
		int numCols=2;
		for (int col = 0 ; col < numCols; col++ ) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100/(numCols*1.0));
            //cc.setFillWidth(true);
            //cc.setHgrow(Priority.ALWAYS);
            centerPane.getColumnConstraints().add(cc);
		}
		//centerPane.getChildren().addAll(VBoxLeft,VBoxRight);
		centerPane.add(VBoxLeft, 0, 0);
		centerPane.add(VBoxRight, 1, 0);
		bPane.setCenter(centerPane);
        
        /* bottom pane
         * This will have 3 sections:
         * 1) Add Item Box.
         * 2) Filters Box.
         * 3) Additional Resources
        */
        
        HBox HBoxBottom = new HBox(200);
        
        //Begin Code on Item Details Box
        
        GridPane ItemDetailsBox = new GridPane();
        constructItemDetailsBox(ItemDetailsBox);
        
        HBoxBottom.getChildren().add(ItemDetailsBox);        
        HBox.setMargin(ItemDetailsBox, new Insets(10,10,10,10));
        
        // Filter Options
        Label filterLabel = new Label("Filter List Options");
        filterLabel.setFont(new Font("Arial",18));
        HBox bottomCenter = new HBox(10);  //Add to the bottom center of the screen
        bottomCenter.setAlignment(Pos.TOP_CENTER);
        
        // setup grid for filter area
        GridPane filterArea = new GridPane();
        
        // setup HBox for filter entries
        HBox filter = new HBox(10);
        // Drop down list for Macro field options to choose from 
        ComboBox<String> macroSelect = new ComboBox<String>();
        macroSelect.getItems().addAll("Carbs", "Calories", "Fat", "Fiber", "Protein");
        macroSelect.setPromptText("Macro");
        macroSelect.setMinWidth(100);
        // Drop down list for Comparator field options to choose from
        ComboBox<String> comparatorSelect = new ComboBox<String>();
        comparatorSelect.getItems().addAll("=", ">", "<", ">=", "<=");
        comparatorSelect.setPromptText("Comparator");
        comparatorSelect.setMinWidth(100);
        // Text field for numeric value to compare to
        TextField value = new TextField();
        value.setPromptText("Value");
        
        //Nutrition Links Box
        VBox NutritionLinksBox = new VBox();
        Label NutritionLinksLabel = new Label("Nutrition Links");
        NutritionLinksLabel.setFont(new Font("Arial",18));
        NutritionLinksBox.getChildren().add(NutritionLinksLabel);
        Hyperlink link = new Hyperlink("https://tinyurl.com/yccjpbok");
        NutritionLinksBox.getChildren().add(new HBox(link));
        HBox.setMargin(NutritionLinksBox, new Insets(10,10,10,10));

        
        filter.getChildren().addAll(macroSelect, comparatorSelect, value);
        
        // create filter buttons
        VBox buttons = new VBox(10);
        Button filterButton = new Button("Add Filter");
        filterButton.setMinWidth(100);
        Button clearButton = new Button("Clear Filters");
        clearButton.setMinWidth(100);
        Button applyButton = new Button ("Apply Filters");
        applyButton.setMinWidth(100);
        buttons.getChildren().addAll(filterButton, clearButton, applyButton);
        
        // Add HBox and buttons to the filter area grid
        filterArea.add(filterLabel, 0, 0);
        filterArea.add(filter, 0, 1);
        //filterArea.add(buttons, 1, 1);
        GridPane.setHalignment(filterLabel, HPos.CENTER);
        //filterArea.add(clearButton, 1, 1);
        //filterArea.add(applyButton, 1, 2);
        filterArea.setHgap(10);
        filterArea.setVgap(10);
        
        bottomCenter.getChildren().addAll(filterArea, buttons);
        HBoxBottom.getChildren().addAll(bottomCenter, NutritionLinksBox);
        bPane.setBottom(HBoxBottom);
       
        // Create VBox for list of currently applied filters
        VBox appliedFilters = new VBox(5);
        appliedFilters.setAlignment(Pos.CENTER);
        
        // create actions for when add filter button is pressed
        filterButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	addFilter(macroSelect, comparatorSelect, value, appliedFilters);
        }});
        
        // create action for when clear filters button is pressed
        clearButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	appliedFilters.getChildren().clear();
        }});
        
        // create actions for when apply filters button is pressed
        applyButton.setOnAction( new EventHandler<ActionEvent>(){
        	@Override
            public void handle(ActionEvent e) {
        	Alert notImplementedYetAlert = new Alert(AlertType.INFORMATION, "Functionality not yet implemented.");
        	notImplementedYetAlert.show();
        }});
        filterArea.add(appliedFilters, 0, 2);
        //RowConstraints rr = new RowConstraints();
        
        
        
  
        primaryStage.setTitle("NomNomNom");
        //show stage
        primaryStage.setScene(scene);
        primaryStage.show();
	}
  /**
   * @param ItemDetailsBox
   */
  private void constructItemDetailsBox(GridPane ItemDetailsBox) {
    ColumnConstraints cc1 = new ColumnConstraints();
    //Column 1 setup
    cc1.setMinWidth(50);
    cc1.setHalignment(HPos.LEFT);
    ItemDetailsBox.getColumnConstraints().add(cc1);
    
  //Column 2 setup
    ColumnConstraints cc2 = new ColumnConstraints();
    cc2.setMinWidth(200);
    cc2.setFillWidth(true);
    cc2.setHalignment(HPos.CENTER);
    ItemDetailsBox.getColumnConstraints().add(cc2);
    
    //Overall Table Setup
    ItemDetailsBox.setVgap(10);
    GridPane.setMargin(ItemDetailsBox, new Insets(10,10,10,10));
    
    Button AddItemButton = new Button("Add Item");
    ItemDetailsBox.add(AddItemButton, 0, 1);

   // page.add(Node, colIndex, rowIndex, colSpan, rowSpan):
    Label ItemDetailsBoxLabel = new Label("Add New Food Item ");
    ItemDetailsBoxLabel.setFont(new Font("Arial",18));
    ItemDetailsBox.add(ItemDetailsBoxLabel, 0, 0, 2, 1);
    
    String LabelString;
    TextField LabelField;
    int row = 1;
    
    //Name Field Build
    LabelString = "Name";
    TextField NameField = new TextField("Enter " + LabelString);
    LabelField = NameField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    
    //Calories Field Build
    LabelString = "Calories";
    TextField CaloriesField = new TextField();
    CaloriesField.setPromptText("Enter " + LabelString);
    LabelField = CaloriesField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Fats Field Build
    LabelString = "Fats";
    TextField FatsField = new TextField("Enter " + LabelString);
    LabelField = FatsField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Carbs Field Build
    LabelString = "Carbs";
    TextField CarbsField = new TextField("Enter " + LabelString);
    LabelField = CarbsField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);

    //Fiber Field Build
    LabelString = "Fiber";
    TextField FiberField = new TextField("Enter " + LabelString);
    LabelField = FiberField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
    //Protein Field Build
    LabelString = "Protein";
    TextField ProteinField = new TextField("Enter " + LabelString);
    LabelField = ProteinField;
    row++;
            
    addItemDetailsRow(ItemDetailsBox, LabelString, LabelField, row);
    
  }
  /**
   * @param ItemDetailsBox
   * @param LabelString
   * @param LabelField
   * @param row
   */
  private void addItemDetailsRow(GridPane ItemDetailsBox, String LabelString, TextField LabelField, int row) {
    ItemDetailsBox.add(new Label(LabelString), 0, row);
    ItemDetailsBox.add(LabelField, 1, row);
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
	
	/**
	 * Validation checks and follow up actions for when add filter button is pressed
	 * 
	 * @param macro is the value from the macro field
	 * @param comparator is the value from the comparator field
	 * @param value is the value from the value field
	 * @param filters is the VBox section that current filter will be added to if all check pass
	 */
	public void addFilter(ComboBox<String> macro, ComboBox<String> comparator, TextField value, VBox filters) {
		Float filterValue;
		String filter;
		
		// check if all fields were passed in. create warning popup if not
		if (macro.getValue() == null || macro.getValue().toString().isEmpty() || comparator.getValue() == null || comparator.getValue().toString().isEmpty()){
                    Alert missingValueAlert = new Alert(AlertType.WARNING, "All Fields must be filled out before applying a filter");
                    missingValueAlert.show();
                    return;
		}
		
		// check if a valid number was put in the numeric field area. create error popup if not
        try {
        	filterValue = Float.parseFloat(value.getText());
        } catch(NumberFormatException e) {
        	 Alert nonNumericValueAlert = new Alert(AlertType.ERROR, "Filter value " + value.getText() + " is non-numeric.");
             nonNumericValueAlert.show();
             return;
        }
        
        if (filterValue < 0) {
        	Alert negativeValueAlert = new Alert(AlertType.ERROR, "Filter value " + value.getText() + " is not a positive number.");
            negativeValueAlert.show();
            return;
        }
        filter = macro.getValue() + " " + comparator.getValue() + " " + filterValue;
        filters.getChildren().add(new Label(filter));
        
		
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
                calsLabel.setText(item.getNutrientValue("Cals")+"");
                fatLabel.setText(item.getNutrientValue("Fat")+"");
                carbsLabel.setText(item.getNutrientValue("Carbs")+"");
                fiberLabel.setText(item.getNutrientValue("Fiber")+"");
                protienLabel.setText(item.getNutrientValue("Protein")+"");
                addButton.setText("Add/Remove");
                //setGraphic(hBox);
                setGraphic(gPane);
            }
        }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
