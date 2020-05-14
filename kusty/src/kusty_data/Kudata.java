package kusty_data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kudata {
	
	private static String localPath;
	private static String currentClient;
	
	private static List<String> activities = new ArrayList<String>() {{add("Base");add("Loisir");add("Renforcement musculaire");add("Prise de force");add("Endurance");add("Haut-niveau");add("Marathonoien");}};
	private static Map<String,Float> NAPMap = new HashMap<String,Float>(){{put("Base",(float)1.1);put("Loisir",(float)1.2);put("Renforcement musculaire",(float)1.3);put("Prise de force",(float)1.4);put("Endurance",(float)1.4);put("Haut-niveau",(float)1.5);put("Marathonoien",(float)1.5);}};
	private static Float[] base = {(float) 20,(float) 30,(float) 50};
	private static Float[] loisir = {(float) 20,(float) 25,(float) 55};
	private static Float[] renf = {(float) 25,(float) 20,(float) 55};
	private static Float[] end = {(float) 15,(float) 20,(float) 60};
	private static Float[] force = {(float) 25,(float) 20,(float) 55};
	private static Float[] mara = {(float) 15,(float) 15,(float) 70};
	private static Float[] hn = {(float) 20,(float) 20,(float) 60};
	private static Map<String,Float[]> actMap = new HashMap<String,Float[]>(){{put("Base",base);put("Loisir",loisir);put("Renforcement musculaire",renf);put("Prise de force",force);put("Endurance",end);put("Haut-niveau",hn);put("Marathonoien",mara);}};
	
	private static List<String> morphos = new ArrayList<String>() {{add("N1");add("N2");add("N3");}};
	private static Float[] n1 = {(float) 30,(float) 30,(float) 40};
	private static Float[] n2 = {(float) 35,(float) 32,(float) 33};
	private static Float[] n3 = {(float) 40,(float) 35,(float) 25};
	private static Map<String,Float[]> morMap = new HashMap<String,Float[]>() {{put("N1",n1);put("N2",n2);put("N3",n3);}};
	
	private static List<String> alergies = new ArrayList<String>() {{add("Aucune");add("Cacahuète");add("Amandes");add("Noix");add("Noix de cajou");add("Noisette");add("Pistache");add("Lactose");add("Gluten");}};
	
	private static List<String> projects = new ArrayList<String>() {{add("Alimentation equilibré");add("Perte de poids");add("Perte de poids avancé");add("prise de muscle");add("Prise de muscle avancé");}};
	
	private static List<String> regimes = new ArrayList<String>() {{add("aucun");add("Végétarien");add("Vegan");}};
	
	public static void setLocalPath(String newLocalPath) {
		localPath = newLocalPath;
	}
	
	public static void setCurrentClient(Subject sub) {
		currentClient = sub.getName() + " " + sub.getFirstName();
	}
	
	public static String getLocalPath() {
		return localPath;
	}
	
	public static String getCurrentClient() {
		return currentClient;
	}
	
	public static List<String> getActivitiesList(){
		return activities;
	}
	
	public static List<String> getMorphosList(){
		return morphos;
	}
	
	public static List<String> getalergiesList(){
		return alergies;
	}
	
	public static List<String> getProjectsList(){
		return projects;
	}
	
	public static List<String> getRegimesList(){
		return regimes;
	}
	
	public static String getActivities(int i){
		return activities.get(i);
	}
	
	public static Map<String,Float[]> getActMap(){
		return actMap;
	}
	
	public static String getMorphos(int i){
		return morphos.get(i);
	}
	
	public static Map<String,Float[]> getMorMap(){
		return morMap;
	}
	
	public static String getAlergie(int i) {
		return alergies.get(i);
	}
	
	public static String[] getAlergies(boolean[] l) {
		List<String> alergiesList = new ArrayList<String>();
		for(int i = 0 ; i < 9 ; i++) { if(l[i]) { alergiesList.add(alergies.get(i)); } }
		String returnList[] = new String[alergiesList.size()];
		for(int i = 0 ; i < alergiesList.size() ; i++) { returnList[i] = alergiesList.get(i); }
		return returnList;
	}
	
	public static String getProjects(int i){
		return projects.get(i);
	}
	
	public static String getSex(boolean sex) {
		if(sex) {return "Homme";} else {return "Femme";}
	}
	
	public static String getRegime(int i) {
		return regimes.get(i);
	}
	
	public static Map<String,Float> getNAPMap(){
		return NAPMap;
	}
	
	public static String[] allActivities() {
		String l[] = new String[activities.size()];
		int i = 0;
		for(String s : activities) { l[i]=s; i++; }
		return l;
	}
	
	public static String[] allMorphos() {
		String l[] = new String[morphos.size()];
		int i = 0;
		for(String s : morphos) { l[i]=s; i++; }
		return l;
	}
	
	public static String[] allAlergies() {
		String l[] = new String[alergies.size()];
		int i = 0;
		for(String s : alergies) { l[i] = s; i++; }
		return l;
	}
	
	public static String[] allProjects() {
		String l[] = new String[projects.size()];
		int i = 0;
		for(String s : projects) { l[i]=s; i++; }
		return l;
	}
	
	public static String[] allRegimes() {
		String l[] = new String[regimes.size()];
		int i = 0;
		for(String s : regimes) { l[i]=s; i++; }
		return l;
	}
	
	public static String getIMCStatut(float imc) {
		if(imc <= 18.5) {return "Insuffisance pondérale";}
		if(imc < 25) {return "Poids normal";}
		if(imc < 30) {return "Surpoids";}
		if(imc < 35) {return "Obésité modérée";}
		if(imc < 40) {return "Obésité sévère";}
		return "Obéisté morbide";
	}
	
}
