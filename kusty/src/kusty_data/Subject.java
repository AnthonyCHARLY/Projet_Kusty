package kusty_data;

import java.io.Serializable;
import java.util.Map;

public class Subject implements Serializable{
	
	private static int nextSubjectId=1;
	private int id;
	
	private String firstName;
	private String name;
	private int weight;
	private int height;
	private int age;
	private boolean sex;
	private float sexInd;
	private int activity;
	private int project;
	private int morpho;
	private int regime;
	
	private boolean[] alergies;
	private boolean[] sportPeriodes;
	private boolean[] feedPeriodes;
	
	private float energieNeeds;
	private float proteinsNeeds;
	private float lipidesNeeds;
	private float glucidesNeeds;
	private float imc;
	
	
	public Subject(String name, String firstName, int weight, int height, int age, boolean sex, int activity,
			int morpho, int project, int regime, boolean[] alergies) {
		super();
		
		this.id = Subject.nextSubjectId++;
		this.name = name;
		
		this.firstName = firstName;
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.sex = sex;
		if(sex) { this.sexInd = (float) ((float) 13.707*weight + (492.3*height/100) - 6.673*age + 77.607); } 
		else { this.sexInd =  (float) ((float) 9.74*weight + (172.9*height/100) - 4.737*age + 667.051);}
		this.activity = activity;
		this.morpho = morpho;
		this.project = project;
		this.regime = regime;
		
		this.alergies = alergies;
		
		Map<String,Float> map = Kudata.getNAPMap();
		String[] keys = Kudata.allActivities();
		energieNeeds = map.get(keys[activity])*sexInd;
		switch(project) {
		case 1:
			energieNeeds -= 250;
			break;
		case 2:
			energieNeeds -= 500;
			break;
		case 3:
			energieNeeds += 250;
			break;
		case 4:
			energieNeeds += 500;
			break;
		default:
			
		}
		
		
		
		this.sportPeriodes = new boolean[5];
		this.feedPeriodes = new boolean[5];
		
		Map<String,Float[]> actMap = Kudata.getActMap();
		Map<String,Float[]> morMap = Kudata.getMorMap();
		String[] actKeys = Kudata.allActivities();
		String[] morKeys = Kudata.allMorphos();
		proteinsNeeds = ((actMap.get(actKeys[activity])[0] + morMap.get(morKeys[morpho])[0])/800)*energieNeeds;
		lipidesNeeds = ((actMap.get(actKeys[activity])[1] + morMap.get(morKeys[morpho])[1])/1800)*energieNeeds;
		glucidesNeeds = (energieNeeds - proteinsNeeds*4 - lipidesNeeds*9)/4;
		this.imc = (float) ((float)weight/Math.pow((float)height/100, 2));
	}
	
	
	
	public void actualizeAllDatas(String name, String firstName, int weight, int height, int age, boolean sex, int activity,
			int morpho, int project, int regime, boolean[] alergies) {
		this.name = name;
		
		this.firstName = firstName;
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.sex = sex;
		if(sex) { this.sexInd = (float) ((float) 13.707*weight + (492.3*height/100) - 6.673*age + 77.607); } 
		else { this.sexInd =  (float) ((float) 9.74*weight + (172.9*height/100) - 4.737*age + 667.051);}
		this.activity = activity;
		this.morpho = morpho;
		this.project = project;
		this.regime = regime;
		
		this.alergies = alergies;
		
		Map<String,Float> map = Kudata.getNAPMap();
		String[] keys = Kudata.allActivities();
		energieNeeds = map.get(keys[activity])*sexInd;
		switch(project) {
		case 1:
			energieNeeds -= 250;
			break;
		case 2:
			energieNeeds -= 500;
			break;
		case 3:
			energieNeeds += 250;
			break;
		case 4:
			energieNeeds += 500;
			break;
		default:
			
		}
		
		
		
		this.sportPeriodes = new boolean[5];
		this.feedPeriodes = new boolean[5];
		
		Map<String,Float[]> actMap = Kudata.getActMap();
		Map<String,Float[]> morMap = Kudata.getMorMap();
		String[] actKeys = Kudata.allActivities();
		String[] morKeys = Kudata.allMorphos();
		proteinsNeeds = ((actMap.get(actKeys[activity])[0] + morMap.get(morKeys[morpho])[0])/800)*energieNeeds;
		lipidesNeeds = ((actMap.get(actKeys[activity])[1] + morMap.get(morKeys[morpho])[1])/1800)*energieNeeds;
		glucidesNeeds = (energieNeeds - proteinsNeeds*4 - lipidesNeeds*9)/4;
		this.imc = (float) ((float)weight/Math.pow((float)height/100, 2));
		
	}

	

	public int getId() {
		return id;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getName() {
		return name;
	}


	public int getWeight() {
		return weight;
	}


	public int getHeight() {
		return height;
	}


	public int getAge() {
		return age;
	}


	public boolean getSex() {
		return sex;
	}
	
	
	public float getSexInd() {
		return sexInd;
	}


	public int getActivity() {
		return activity;
	}


	public int getMorpho() {
		return morpho;
	}
	
	public int getProject() {
		return project;
	}
	
	public int getRegime() {
		return regime;
	}
	
	public boolean[] getAlergies() {
		return alergies;
	}
	
	public boolean[] getSportPeriodes() {
		return sportPeriodes;
	}
	
	
	public boolean[] getFeedPeriodes() {
		return feedPeriodes;
	}
	
	
	public float getEnergieNeeds() {
		return energieNeeds;
	}
	
	public float getProteinsNeeds() {
		return proteinsNeeds;
	}
	
	public float getLipidesNeeds() {
		return lipidesNeeds;
	}
	
	public float getGlucidesNeeds() {
		return glucidesNeeds;
	}
	
	public float getIMC() {
		return imc;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public void setSex(boolean sex) {
		this.sex = sex;
		if(sex) {this.sexInd = 1845;} else {this.sexInd = 1605;}
	}
	
	public void setRegime(int i) {
		this.regime = i;
	}
	
	public void addAlergie(int a) {
		alergies[a] = true;
	}
	
	public void removeAlergie(int a) {
		alergies[a] = false;
	}
	
	public void setAlergies(boolean[] alergies) {
		this.alergies = alergies;
	}
	
	public void addSportPeriode(int p) {
		sportPeriodes[p] = true;
	}
	
	
	public void removeSportPeriode(int p) {
		sportPeriodes[p] = false;
	}
	
	
	public void addFeedPeriode(int p) {
		feedPeriodes[p] = true;
	}
	
	
	public void removeFeedPeriode(int p) {
		feedPeriodes[p] = false;
	}
	
	public void setEnergieNeeds() {
		Map<String,Float> map = Kudata.getNAPMap();
		String[] keys = Kudata.allActivities();
		energieNeeds = map.get(keys[activity])*sexInd;
		switch(project) {
		case 1:
			energieNeeds -= 250;
			break;
		case 2:
			energieNeeds -= 500;
			break;
		case 3:
			energieNeeds += 250;
			break;
		case 4:
			energieNeeds += 500;
			break;
		default:		
		}
	}
	
	public void setStatsNeeds() {
		Map<String,Float[]> actMap = Kudata.getActMap();
		Map<String,Float[]> morMap = Kudata.getMorMap();
		String[] actKeys = Kudata.allActivities();
		String[] morKeys = Kudata.allMorphos();
		proteinsNeeds = ((actMap.get(actKeys[activity])[0]/100 + morMap.get(morKeys[activity])[0]/100)/200)*energieNeeds;
		lipidesNeeds = ((actMap.get(actKeys[activity])[1]/100 + morMap.get(morKeys[activity])[1]/100)/1800)*energieNeeds;
		glucidesNeeds = (energieNeeds - proteinsNeeds*4 - lipidesNeeds*9)/4;
	}
	
	public void setIMC() {
		imc = (float) ((float)weight/Math.pow((float)height/100, 2));
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
		Subject other = (Subject) obj;
		if (id != other.id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Subject [id=" + id + "]";
	}

	
	

}
