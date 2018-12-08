package application;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    private BPTree<Double, FoodItem> calorieTree = new BPTree<Double, FoodItem>(5);
    private BPTree<Double, FoodItem> fatTree = new BPTree<Double, FoodItem>(5);
    private BPTree<Double, FoodItem> carbTree = new BPTree<Double, FoodItem>(5);
    private BPTree<Double, FoodItem> fiberTree = new BPTree<Double, FoodItem>(5);
    private BPTree<Double, FoodItem> proteinTree = new BPTree<Double, FoodItem>(5);
    
    
    /**
     * Public constructor
     */
    public FoodData() {
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        indexes.put("calories", calorieTree);
        indexes.put("fat", fatTree);
        indexes.put("carbohydrate", carbTree);
        indexes.put("fiber", fiberTree);
        indexes.put("protein", proteinTree);
        foodItemList = new ArrayList<FoodItem>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath){ 
    	foodItemList.clear();
      try {
        BufferedReader inStream = new BufferedReader(new FileReader(filePath));
        String inLine;
        try {
    	while ((inLine = inStream.readLine()) != null) {
    		String[] foodInfo = inLine.split(",");
    		if (foodInfo.length != 12) {
    			continue;
    		}
    		String id = foodInfo[0].trim();
    		String name = foodInfo[1].trim();
    		String calorieLabel = foodInfo[2].trim();
    		String fatLabel = foodInfo[4].trim();
    		String carbLabel = foodInfo[6].trim();
    		String fiberLabel = foodInfo[8].trim();
    		String proteinLabel = foodInfo[10].trim();
    		if (!calorieLabel.equals("calories") || !fatLabel.equals("fat") || !carbLabel.equals("carbohydrate") || !fiberLabel.equals("fiber") || !proteinLabel.equals("protein")) {
    			continue;
    		}
    		try {
    		double calorieCount = Double.parseDouble(foodInfo[3].trim());
    		double fatCount = Double.parseDouble(foodInfo[5].trim());
    		double carbCount = Double.parseDouble(foodInfo[7].trim());
    		double fiberCount = Double.parseDouble(foodInfo[9].trim());
    		double proteinCount = Double.parseDouble(foodInfo[11].trim());
    		FoodItem newFood = new FoodItem(id, name);
    		newFood.addNutrient(calorieLabel, calorieCount);
    		newFood.addNutrient(fatLabel, fatCount);
    		newFood.addNutrient(carbLabel, carbCount);
    		newFood.addNutrient(fiberLabel, fiberCount);
    		newFood.addNutrient(proteinLabel, proteinCount);
    		calorieTree.insert(calorieCount, newFood);
    		fatTree.insert(fatCount, newFood);
    		carbTree.insert(carbCount, newFood);
    		fiberTree.insert(fiberCount, newFood);
    		proteinTree.insert(proteinCount, newFood);
    		foodItemList.add(newFood);
    		
    		} catch (NumberFormatException e) {
    			continue;
    		}
    		
    	}
        } catch(IOException e) {
        	filePath ="ERROR";
        } finally {
        	inStream.close();
        }
    	} catch (FileNotFoundException e) {
    		filePath = "ERROR";
    	} catch (IOException e) {
    		filePath = "ERROR";
    	}
    }
    

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List <FoodItem> filteredList = foodItemList;
        for (String rule : rules) {
        	List<FoodItem> singleRuleResult;
        	String[] ruleParts = rule.split("^");
        	String macro = ruleParts[0].trim();
        	String comparator = ruleParts[1].trim();
        	double value = Double.parseDouble(ruleParts[2].trim());
        	switch(macro.charAt(2)) {
        	case 'r':
        		singleRuleResult = carbTree.rangeSearch(value, comparator);
        		break;
        	case 't':
        		singleRuleResult = fatTree.rangeSearch(value, comparator);
        		break;
        	case 'b':
        		singleRuleResult = fiberTree.rangeSearch(value, comparator);
        		break;
        	case 'l':
        		singleRuleResult = calorieTree.rangeSearch(value, comparator);
        		break;
        	default:
        		singleRuleResult = proteinTree.rangeSearch(value, comparator);
        	}
        	
        	filteredList.retainAll(singleRuleResult);
        	
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
      foodItemList.add(foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {

        return foodItemList;
    }
    public void saveFoodItems(String inputFile) {
    	
    }
}
