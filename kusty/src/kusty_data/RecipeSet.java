package kusty_data;

import java.util.HashSet;
import java.util.Set;

public class RecipeSet {
	
	
	private Set<Recipe> RecipeSet;
	
	public RecipeSet() {
		RecipeSet = new HashSet<Recipe>();
	}
	
	
	public void addRecipe(Recipe newRecipe) {
		RecipeSet.add(newRecipe);
	}

	
	public void removeRecipe(Recipe oldRecipe) {
		RecipeSet.remove(oldRecipe);
	}
	
	public int size() {
		return RecipeSet.size();
	}
	

}
