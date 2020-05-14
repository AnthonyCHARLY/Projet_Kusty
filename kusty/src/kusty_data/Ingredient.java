package kusty_data;

public class Ingredient {
	
	private static int nextIngredientId=1;
	private int id;
	
	
	private String name;
	private float prize;
	private float proteins;
	private float carbohydrates;
	private float lipids;
	private float sugars;
	private float dietaryFiber;
	private float agSat;
	private float agMonoInsat;
	private float agPolyInsat;
		
	private float eReg;
	

	public Ingredient(String name, float prize, float eReg, float proteins, float carbohydrates, float lipids, float sugars,
			float dietaryFiber, float agSat, float agMonoInsat, float agPolyInsat) {
		super();
		this.id = Ingredient.nextIngredientId++;
		this.name = name;
		this.prize = prize;
		this.proteins = proteins;
		this.carbohydrates = carbohydrates;
		this.lipids = lipids;
		this.sugars = sugars;
		this.dietaryFiber = dietaryFiber;
		this.agSat = agSat;
		this.agMonoInsat = agMonoInsat;
		this.agPolyInsat = agPolyInsat;
		this.eReg = eReg;
	}


	public int getId() {
		return id;
	}


	public String getName() {
		return name;
	}
	
	
	public float getPrize() {
		return prize;
	}


	public float getProteins() {
		return proteins;
	}


	public float getCarbohydrates() {
		return carbohydrates;
	}


	public float getLipids() {
		return lipids;
	}


	public float getSugars() {
		return sugars;
	}


	public float getDietaryFiber() {
		return dietaryFiber;
	}


	public float getAgSat() {
		return agSat;
	}
	
	
	public float getAgMonoInsat() {
		return agMonoInsat;
	}
	
	
	public float getAgPolyInsat() {
		return agPolyInsat;
	}


	public float getEReg() {
		return eReg;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	public void setPrize(float prize) {
		this.prize = prize;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ingredient [id=" + id + "]";
	}
	
	

}
