package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
    
    public void clearFoodData() {
    	foodItemList.clear();
    	calorieTree.clear();
    	fatTree.clear();
    	carbTree.clear();
    	fiberTree.clear();
    	proteinTree.clear();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath){ 
    	clearFoodData();
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
    		if (calorieCount<0||fatCount<0||carbCount<0||fiberCount<0||proteinCount<0) {
    		  continue;
    		}
    		
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
        	
        } finally {
        	inStream.close();
        }
    	} catch (FileNotFoundException e) {
    		System.out.println("File was not found.");
    	} catch (IOException e) {
    	  System.out.print("Could not write to file.");
    	} 
      
    }
    

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
    	if (substring.isEmpty()) {
    		return foodItemList;
    	}
    	List <FoodItem> filterList = new ArrayList<FoodItem>();
        for (FoodItem food : foodItemList) {
        	String foodName = food.getName().toUpperCase();
        	if (foodName.contains(substring)) {
        		filterList.add(food);
        	}
        }
        return filterList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	List <FoodItem> filteredList = new ArrayList<FoodItem>();
    	for (FoodItem food : foodItemList) {
    		filteredList.add(food);
    	}
        for (String rule : rules) {
        	List<FoodItem> singleRuleResult;
        	String[] ruleParts = rule.split(" ");
        	String macro = ruleParts[0].trim();
        	switch(macro.charAt(2)) {
        	case 'r':
        		macro = "carbohydrate";
        		break;
        	case 'l':
        		macro = "calories";
        		break;
        	case 't':
        		macro = "fat";
        		break;
        	case 'b':
        		macro = "fiber";
        		break;
        	default:
        		macro = "protein";
        	}
        	String comparator = ruleParts[1].trim();
        	comparator = (comparator.equals("="))? "==": comparator;
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
        return filteredList;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
      foodItemList.add(foodItem);
      calorieTree.insert(foodItem.getNutrientValue("calories"), foodItem);
      fatTree.insert(foodItem.getNutrientValue("fat"), foodItem);
      carbTree.insert(foodItem.getNutrientValue("carbohydrate"), foodItem);
      fiberTree.insert(foodItem.getNutrientValue("fiber"), foodItem);
      proteinTree.insert(foodItem.getNutrientValue("protein"), foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }
    
    /**
     * Save the list of food items in ascending order by name
     * 
     * @param filename name of the file where the data needs to be saved 
     */
    public void saveFoodItems(String filePath) {
      Iterator<FoodItem> itr = this.foodItemList.iterator();
      FoodItem currFood = null;
      String saveString = new String();
      TreeMap<String, FoodItem> map = new TreeMap<String, FoodItem>();
      while (itr.hasNext()) {
    	  currFood = itr.next();
    	  map.put(currFood.getName(), currFood);
      }
      
      try {
        BufferedWriter Writer = new BufferedWriter(new FileWriter(filePath));
        Set<Map.Entry<String, FoodItem>> foodItems = map.entrySet();
        Iterator<Map.Entry<String, FoodItem>> itr2 = foodItems.iterator();
        while (itr2.hasNext()) {
          StringBuilder sb = new StringBuilder();
          currFood = itr2.next().getValue();
          sb.append(currFood.getID()+",");
          sb.append(currFood.getName()+",");
          sb.append("calories"+","+currFood.getNutrientValue("calories")+",");
          sb.append("fat"+","+currFood.getNutrientValue("fat")+",");
          sb.append("carbohydrate"+","+currFood.getNutrientValue("carbohydrate")+",");
          sb.append("fiber"+","+currFood.getNutrientValue("fiber")+",");
          sb.append("protein"+","+currFood.getNutrientValue("protein"));
          saveString = sb.toString();
          Writer.write(saveString+"\n");
        } 
        Writer.close();
      } catch (Exception e) {
        System.out.println("Could not write to file.");
      }
    }
}

