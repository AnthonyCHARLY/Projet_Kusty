package kusty_data;

import java.util.Map;
import java.io.Serializable;
import java.util.HashMap;

public class Recipe implements Serializable{
	
	private static int nextRecipeId=1;
	private int id;
	
	private String name;
	private float cost;
	private int portions;
	private Map<Ingredient,Float> ingredients;
	private Map<Ingredient,Float> mainIngredients;
	private boolean[] periodes;
	
	
	public Recipe(String name, float cost, int portions) {
		super();
		this.id = Recipe.nextRecipeId++;
		this.name = name;
		this.cost = cost;
		this.portions = portions;
		this.ingredients = new HashMap<Ingredient,Float>();
		this.mainIngredients = new HashMap<Ingredient,Float>();
		this.periodes = new boolean[5];
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
	
	
	public int getPortions() {
		return portions;
	}


	public Map<Ingredient, Float> getIngredients() {
		return ingredients;
	}
	
	public Map<Ingredient, Float> getMainIngredients(){
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
	
	
	public void setPortions(int portions) {
		this.portions = portions;
	}
	
	
	public void addIngredient(Ingredient ingredient, float quantity) {
		ingredients.put(ingredient, quantity);
	}
	
	
	public void removeIngredient(Ingredient ingredient) {
		ingredients.remove(ingredient);
	}
	
	
	public void addMainIngredient(Ingredient ingredient, float quantity) {
		mainIngredients.put(ingredient, quantity);
	}
	
	
	public void removeMainIngredient(Ingredient ingredient) {
		mainIngredients.remove(ingredient);
	}
	
	
	public void changeQuantity(Ingredient ingredient, float quantity) {
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
