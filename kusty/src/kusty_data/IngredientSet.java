package kusty_data;

import java.util.HashSet;
import java.util.Set;

public class IngredientSet {
	
	
	private Set<Ingredient> ingredientSet;
	
	
	public IngredientSet() {
		ingredientSet = new HashSet<Ingredient>();
	}
	
	
	public void addIngredient(Ingredient newIngredient) {
		ingredientSet.add(newIngredient);
	}

	
	public void removeIngredient(Ingredient oldIngredient) {
		ingredientSet.remove(oldIngredient);
	}
	
	public int size() {
		return ingredientSet.size();
	}

}
