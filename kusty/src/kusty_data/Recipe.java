package kusty_data;

import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

public class Recipe implements Serializable{
	
	private static int nextRecipeId=1;
	private int id;
	
	private String name;
	private float cost;
	private float price;
	private Map<String,Float> ingredients = new HashMap<String,Float>();
	private Map<String,Float> mainIngredients = new HashMap<String,Float>();
	private boolean[] periodes;
	
	
	public Recipe(String name, float price) {
		super();
		this.id = Recipe.nextRecipeId++;
		this.name = name;
		this.price = price;
		this.periodes = new boolean[5];
		this.cost = 0;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public float getCost() {
		return cost;
	}
	
	
	public float getPrice() {
		return price;
	}

	
	public Map<String, Float> getIngredients() {
		return ingredients;
	}
	
	
	public Map<String, Float> getMainIngredients(){
		return mainIngredients;
	}
	
	
	public boolean[] getPeriodes() {
		return periodes;
	}

	
	
	

	public void setName(String name) {
		this.name = name;
	}


	public void setCost(float cost) {
		this.cost = cost;
	}
	
	
	public void addIngredient(String ingredient, float quantity) {
		ingredients.put(ingredient, quantity);
	}
	
	
	public void removeIngredient(String ingredient) {
		ingredients.remove(ingredient);
	}
	
	
	public void addMainIngredient(String ingredient, float quantity) {
		mainIngredients.put(ingredient, quantity);
	}
	
	
	public void removeMainIngredient(String ingredient) {
		mainIngredients.remove(ingredient);
	}
	
	
	public void changeQuantity(String ingredient, float quantity) {
		mainIngredients.replace(ingredient, ingredients.get(ingredient), quantity);
	}
	
	
	public void addPeriode(int p) {
		periodes[p] = true;
	}
	
	
	public void removePeriode(int p) {
		periodes[p] = false;
	}
	
	
	public void bilan() {
		// 
	}
	
	
	
}
