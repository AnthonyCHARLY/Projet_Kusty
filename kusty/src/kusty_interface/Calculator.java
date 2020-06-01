package kusty_interface;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneLayout;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kusty_data.Ingredient;
import kusty_data.IngredientSet;
import kusty_data.Kudata;
import kusty_data.Recipe;
import kusty_data.RecipeSet;
import kusty_data.Subject;


public class Calculator{
	
	public static void main(String[] args) throws IOException {
		
		Map<String,Subject> subSet = new HashMap<String,Subject>();
		Map<String, Recipe> recSet = new HashMap<String,Recipe>();
		Map<String,Ingredient> ingSet = new HashMap<String, Ingredient>();
		List<String> kuIngSet = new ArrayList<String>();
		
		
		
		try {
			FileInputStream fis = new FileInputStream("globalIngSave.txt");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ingSet.clear();
			while(fis.available() != 0) {
				try {
					Ingredient ing = (Ingredient) ois.readObject();
					ingSet.put(ing.getName(), ing);
				}
				catch (ClassNotFoundException e1){
					e1.printStackTrace();
				}
			}
			ois.close();
		}
		catch (FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			FileInputStream fis2 = new FileInputStream("kuIngSave.txt");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			kuIngSet.clear();
			while(fis2.available() != 0) {
				try {
					String kuIng = (String) ois2.readObject();
					kuIngSet.add(kuIng);
				}
				catch (ClassNotFoundException e1){
					e1.printStackTrace();
				}
			}
			ois2.close();
		}
		catch (FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		try {
			FileInputStream fis3 = new FileInputStream("recSave.txt");
			ObjectInputStream ois3 = new ObjectInputStream(fis3);
			recSet.clear();
			while(fis3.available() != 0) {
				try {
					Recipe recipe = (Recipe) ois3.readObject();
					recSet.put(recipe.getName(),recipe);
				}
				catch (ClassNotFoundException e1){
					e1.printStackTrace();
				}
			}
			ois3.close();
		}
		catch (FileNotFoundException e1){
			e1.printStackTrace();
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		
		
		JFrame kulcalculator = new JFrame();
		kulcalculator.setTitle("KulCalculator");
		kulcalculator.setSize(1500,500);
		//kulcalculator.pack();
		//kulcalculator.setDefaultLookAndFeelDecorated(true);
		//kulcalculator.setExtendedState(kulcalculator.MAXIMIZED_BOTH);
		kulcalculator.setLocationRelativeTo(null);
		kulcalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		kulcalculator.setResizable(false);
		GridLayout gl = new GridLayout();
		gl.setColumns(1);
		gl.setRows(4);
		kulcalculator.setLayout(gl);
		JMenuBar menu = new JMenuBar();
		
		/////////////////////////////////////////////////////////////////
		//PANEL
		/////////////////////////////////////////////////////////////////
		
		String titles[] = {"Nom","Prenom","Poids","Taille","Age","Sex","Activité","Morpho","Projet","Régime particulier"};
		final Object obj[][] = {{"","","","","","","","","",""}};
		
		JTable tab = new JTable(obj,titles);
		
		tab.setVisible(false);
		tab.setEnabled(false);
		tab.getTableHeader().setEnabled(false);
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(new JScrollPane(tab));
		kulcalculator.getContentPane().add(p1);
		JButton modifier = new JButton("Modifier");
		JButton clientAlergies = new JButton("Alergies");
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		kulcalculator.getContentPane().add(p2);
		
		
		String titles2[] = {"Energétiques(kcal)","Protéines(g)","Lipides(g)","Glucides(g)","IMC","Statut IMC"};
		final Object obj2[][] = {{"","","","","",""}};
		
		JTable tab2 = new JTable(obj2, titles2);
		tab2.setVisible(false);
		tab2.setEnabled(false);
		tab2.getTableHeader().setEnabled(false);
		JPanel p3 = new JPanel();
		p3.setLayout(new BorderLayout());
		p3.add(new JScrollPane(tab2));
		kulcalculator.getContentPane().add(p3);
		
		
		p2.add(modifier);
		/////////////////////////////////////////////////////////////////
		//MOD CLIENT
		/////////////////////////////////////////////////////////////////
		modifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewClientPanel(tab,tab2,obj,obj2,subSet,false);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//MOD CLIENT
		/////////////////////////////////////////////////////////////////
		
		p2.add(clientAlergies);
		/////////////////////////////////////////////////////////////////
		//CLIENT ALERGIES
		/////////////////////////////////////////////////////////////////
		clientAlergies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Subject curClient = subSet.get(obj[0][0] + " " + obj[0][1]);
				for(int i = 0 ; i < Kudata.getallergiesList().size() ; i++) {
					if(curClient.getAlergies(Kudata.allAlergies()[i])) { System.out.println(Kudata.getAlergie(i)); }
				}
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//CLIENT ALERGIES
		/////////////////////////////////////////////////////////////////
		
		
		/////////////////////////////////////////////////////////////////
		//PANEL
		/////////////////////////////////////////////////////////////////
		
		
		/////////////////////////////////////////////////////////////////
		//MENU 
		/////////////////////////////////////////////////////////////////
		JMenu button1 = new JMenu("Fichier");
		JMenu button2 = new JMenu("Clients");
		JMenu button3 = new JMenu("Ingrédients");
		JMenu button4 = new JMenu("Recettes");
		
		JMenuItem item1_1 = new JMenuItem("Ouvrir");
		JMenuItem item1_2 = new JMenuItem("Enregistrer");
		JMenuItem item1_3 = new JMenuItem("Enregistrer sous");
		
		JMenuItem item2_1 = new JMenuItem("Nouveau");
		JMenuItem item2_2 = new JMenuItem("Répertoire");
		
		JMenuItem item3_1 = new JMenuItem("Actualiser la base de données");
		JMenuItem item3_2 = new JMenuItem("Base de données globale");
		JMenuItem item3_3 = new JMenuItem("KuBase de données");
		
		JMenuItem item4_1 = new JMenuItem("Nouveau");
		JMenuItem item4_2 = new JMenuItem("Base de données Recettes");
		
		
		button1.add(item1_1);
		/////////////////////////////////////////////////////////////////
		//LOAD
		/////////////////////////////////////////////////////////////////
		item1_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fcload = new JFileChooser("C:/Users/achar/Desktop/kuku/kukusaves");
				int returnVal = fcload.showOpenDialog(item1_1);
				Kudata.setLocalPath(fcload.getSelectedFile().getPath());
				try {
					FileInputStream fis = new FileInputStream(Kudata.getLocalPath());
					ObjectInputStream ois = new ObjectInputStream(fis);
					subSet.clear();
					while (fis.available() != 0) {
						try {
							Subject sub = (Subject) ois.readObject();
							tab.setVisible(false);
							tab2.setVisible(false);
							subSet.put(sub.getName() + " " + sub.getFirstName(), sub);
						}
						catch (ClassNotFoundException e1){
							e1.printStackTrace();
						}
					}
					ois.close();
				}
				catch (FileNotFoundException e1){
					e1.printStackTrace();
				}
				catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//LOAD
		/////////////////////////////////////////////////////////////////
		
		
		
		button1.add(item1_2);
		/////////////////////////////////////////////////////////////////
		//SAVE
		/////////////////////////////////////////////////////////////////
		item1_2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					FileOutputStream fos = new FileOutputStream(Kudata.getLocalPath());
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					for(Subject s : subSet.values()) {
						oos.writeObject(s);
					}
					oos.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
				
				
				
				try {
					FileOutputStream fos2 = new FileOutputStream("globalIngSave.txt");
					ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
					for(Ingredient i : ingSet.values()) {
						oos2.writeObject(i);
					}
					oos2.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
				
				
				
				try {
					FileOutputStream fos3 = new FileOutputStream("kuIngSave.txt");
					ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
					for(String s : kuIngSet) {
						oos3.writeObject(s);
					}
					oos3.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
				
				
				try {
					FileOutputStream fos4 = new FileOutputStream("recSave.txt");
					ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
					for(Recipe r : recSet.values()) {
						oos4.writeObject(r);
					}
					oos4.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//SAVE
		/////////////////////////////////////////////////////////////////
		
		
		button1.add(item1_3);
		/////////////////////////////////////////////////////////////////
		//SAVE AS
		/////////////////////////////////////////////////////////////////
		item1_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fcsave = new JFileChooser("C:/Users/achar/Desktop/kuku/kukusaves");
				fcsave.showSaveDialog(item1_3);
				System.out.println(fcsave.getSelectedFile().getPath());
				Kudata.setLocalPath(fcsave.getSelectedFile().getPath());
				try {
					FileOutputStream fos = new FileOutputStream(Kudata.getLocalPath() + ".txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					for(Subject s : subSet.values()) {
						oos.writeObject(s);
					}
					oos.close();
					}
					catch(FileNotFoundException e1) {
						e1.printStackTrace();
					}
					catch(IOException e2) {
						e2.printStackTrace();
					}
				
				
				try {
					FileOutputStream fos2 = new FileOutputStream("globalIngSave.txt");
					ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
					for(Ingredient i : ingSet.values()) {
						oos2.writeObject(i);
					}
					oos2.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
				
				
				
				try {
					FileOutputStream fos3 = new FileOutputStream("kuIngSave.txt");
					ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
					for(String s : kuIngSet) {
						oos3.writeObject(s);
					}
					oos3.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
				
				
				try {
					FileOutputStream fos4 = new FileOutputStream("recSave.txt");
					ObjectOutputStream oos4 = new ObjectOutputStream(fos4);
					for(Recipe r : recSet.values()) {
						oos4.writeObject(r);
					}
					oos4.close();
				}
				catch(FileNotFoundException e1) {
					e1.printStackTrace();
				}
				catch(IOException e2) {
					e2.printStackTrace();
				}
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//SAVE AS
		/////////////////////////////////////////////////////////////////
		
		
			
		button2.add(item2_1);
		/////////////////////////////////////////////////////////////////
		//NEW CLIENT PANEL
		/////////////////////////////////////////////////////////////////
		item2_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewClientPanel(tab,tab2,obj,obj2,subSet,true);
			}	
				
		});
		/////////////////////////////////////////////////////////////////
		//NEW CLIENT PANEL
		/////////////////////////////////////////////////////////////////
		
		
		
		button2.add(item2_2);
		/////////////////////////////////////////////////////////////////
		//SEARCH PANEL
		/////////////////////////////////////////////////////////////////
		item2_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SearchPanel(subSet,tab,tab2,obj,obj2);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//SEARCH PANEL
		/////////////////////////////////////////////////////////////////
		
		
		
		button3.add(item3_1);
		/////////////////////////////////////////////////////////////////
		//ACTUALIZE INGREDIENTS
		/////////////////////////////////////////////////////////////////
		item3_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("C:/Users/achar/Desktop/kuku/kudatas");
				int returnVal = fc.showOpenDialog(item3_1);
				try {
					IngredientsReader(fc.getSelectedFile().getPath(),ingSet);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//ACTUALIZE INGREDIENTS
		/////////////////////////////////////////////////////////////////
		
		
		
		button3.add(item3_2);
		/////////////////////////////////////////////////////////////////
		//GLOBAL INGREDIENTS PANEL
		/////////////////////////////////////////////////////////////////
		item3_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				GlobalIngredientsPanel(ingSet,kuIngSet);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//GLOBAL INGREDIENTS PANEL
		/////////////////////////////////////////////////////////////////
		
		
		
		button3.add(item3_3);
		/////////////////////////////////////////////////////////////////
		//KUINGREDIENTS PANEL
		/////////////////////////////////////////////////////////////////
		item3_3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KuIngredientPanel(kuIngSet, ingSet,false);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//KUINGREDIENTS PANEL
		/////////////////////////////////////////////////////////////////
		
		
		
		button4.add(item4_1);
		/////////////////////////////////////////////////////////////////
		//RECIPES DATA BASE
		/////////////////////////////////////////////////////////////////
		item4_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NewRecipePanel(ingSet, kuIngSet, recSet, true);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//RECIPES DATA BASE
		/////////////////////////////////////////////////////////////////
		
		
		
		button4.add(item4_2);
		/////////////////////////////////////////////////////////////////
		//RECIPES DATA BASE
		/////////////////////////////////////////////////////////////////
		item4_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				RecipesPanel(ingSet,kuIngSet,recSet);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//RECIPES DATA BASE
		/////////////////////////////////////////////////////////////////
		
		menu.add(button1);
		menu.add(button2);
		menu.add(button3);
		menu.add(button4);
		
		kulcalculator.setJMenuBar(menu);
		kulcalculator.setVisible(true);
		/////////////////////////////////////////////////////////////////
		//MENU
		/////////////////////////////////////////////////////////////////
		
	}
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//NEW CLIENTS
	/////////////////////////////////////////////////////////////////
	public static void NewClientPanel(JTable tab, JTable tab2, Object[][] obj, Object[][] obj2, Map<String,Subject> subSet, boolean isNewClient) {
		boolean[] sportPeriod = new boolean[5];
		boolean[] alergiesRecap = new boolean[Kudata.getallergiesList().size()];
		boolean[] eatingPeriod = new boolean[5];
	
		JFrame clientFrame = new JFrame();
		clientFrame.setTitle("Création Client");
		clientFrame.setSize(470,250);
		clientFrame.setLocationRelativeTo(null);
		clientFrame.setResizable(false);
		clientFrame.setAlwaysOnTop(true);
		clientFrame.setVisible(true);
		
		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		JLabel label_1 = new JLabel("Nom :");
		JLabel label_2 = new JLabel("Prenom :");
		JLabel label_3 = new JLabel("Poids(kg) :");
		JLabel label_4 = new JLabel("Taille(cm) :");
		JLabel label_5 = new JLabel("Age :");
		JLabel label_6 = new JLabel("Sex :");
		JLabel label_7 = new JLabel("Type d'activité :");
		JLabel label_8 = new JLabel("Morphotype :");
		JLabel label_9 = new JLabel("Objectif :");
		JLabel label_10 = new JLabel("Regime :");
		
		JTextField name = new JTextField();
		name.setColumns(15);
		
		JTextField firstname = new JTextField();
		firstname.setColumns(15);
		firstname.setName("prenom :");
	
		JTextField weight = new JTextField();
		weight.setColumns(3);
	
		JTextField height = new JTextField();
		height.setColumns(3);
	
		JTextField age = new JTextField();
		age.setColumns(3);
		
		JButton alergies = new JButton("Alergies");
		alergies.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame alergiesPanel = new JFrame();
				alergiesPanel.setTitle("Selection périodes");
				alergiesPanel.setSize(500,150);
				alergiesPanel.setLocationRelativeTo(null);
				alergiesPanel.setResizable(false);
				clientFrame.setAlwaysOnTop(false);
				alergiesPanel.setAlwaysOnTop(true);
				alergiesPanel.setVisible(true);
				alergiesPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
				
				JCheckBoxMenuItem al0 = new JCheckBoxMenuItem(Kudata.getAlergie(0));
				JCheckBoxMenuItem al1 = new JCheckBoxMenuItem(Kudata.getAlergie(1));
				JCheckBoxMenuItem al2 = new JCheckBoxMenuItem(Kudata.getAlergie(2));
				JCheckBoxMenuItem al3 = new JCheckBoxMenuItem(Kudata.getAlergie(3));
				JCheckBoxMenuItem al4 = new JCheckBoxMenuItem(Kudata.getAlergie(4));
				JCheckBoxMenuItem al5 = new JCheckBoxMenuItem(Kudata.getAlergie(5));
				JCheckBoxMenuItem al6 = new JCheckBoxMenuItem(Kudata.getAlergie(6));
				JCheckBoxMenuItem al7 = new JCheckBoxMenuItem(Kudata.getAlergie(7));
				JCheckBoxMenuItem al8 = new JCheckBoxMenuItem(Kudata.getAlergie(8));
				JCheckBoxMenuItem[] items = {al0,al1,al2,al3,al4,al5,al6,al7,al8};
				
				for(int i = 0 ; i < Kudata.getallergiesList().size() ; i++) {
					items[i].setSelected(alergiesRecap[i]);
				}
				
				if(!isNewClient) { for(int i = 0 ; i < Kudata.getallergiesList().size() ; i ++) {
					if(subSet.get(Kudata.getCurrentClient()).getAlergies(Kudata.allAlergies()[i])) {
						items[i].setSelected(true);
					}
				}}
				
				alergiesPanel.add(al0);
				alergiesPanel.add(al1);
				alergiesPanel.add(al2);
				alergiesPanel.add(al3);
				alergiesPanel.add(al4);
				alergiesPanel.add(al5);
				alergiesPanel.add(al6);
				alergiesPanel.add(al7);
				alergiesPanel.add(al8);
				
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						for(int i = 0 ; i < Kudata.getallergiesList().size() ; i++) {
							alergiesRecap[i] = items[i].isSelected();
						}
						if(!isNewClient) {
							for(int i = 0 ; i < Kudata.getallergiesList().size() ; i++) {
								if(alergiesRecap[i]) {
									subSet.get(Kudata.getCurrentClient()).addAlergie(Kudata.allAlergies()[i]);
								}
							}
						}
						clientFrame.setAlwaysOnTop(true);
						alergiesPanel.setVisible(false);
						alergiesPanel.dispose();
					}
					
				});
				alergiesPanel.add(ok);
			}
			
		});
		
		JButton sportPeriods = new JButton("Périodes de sport");
		sportPeriods.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame sportPeriods = new JFrame();
				sportPeriods.setTitle("Selection périodes");
				sportPeriods.setSize(500,100);
				sportPeriods.setLocationRelativeTo(null);
				sportPeriods.setResizable(false);
				clientFrame.setAlwaysOnTop(false);
				sportPeriods.setAlwaysOnTop(true);
				sportPeriods.setVisible(true);
				sportPeriods.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
				JCheckBoxMenuItem apd = new JCheckBoxMenuItem("Avant petit-dejeuner");
				JCheckBoxMenuItem ac1 = new JCheckBoxMenuItem("Avant collotion 1");
				JCheckBoxMenuItem adj = new JCheckBoxMenuItem("Avant dejeuner");
				JCheckBoxMenuItem ac2 = new JCheckBoxMenuItem("Avant collation 2");
				JCheckBoxMenuItem adn = new JCheckBoxMenuItem("Avant diner");
				JCheckBoxMenuItem[] items = {apd,ac1,adj,ac2,adn};
				
				for(int i = 0 ; i < 5 ; i++) {
					items[i].setSelected(sportPeriod[i]);
				}
				
				sportPeriods.add(apd);
				sportPeriods.add(ac1);
				sportPeriods.add(adj);
				sportPeriods.add(ac2);
				sportPeriods.add(adn);
		
				JButton ok = new JButton("OK");
				ok.addActionListener(new ActionListener() {
			
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						for(int i = 0 ; i < 5 ; i++) {
							sportPeriod[i] = items[i].isSelected();
						}
						clientFrame.setAlwaysOnTop(true);
						sportPeriods.setVisible(false);
						sportPeriods.dispose();
					}
				});
				
				sportPeriods.add(ok);
			}
			
		});
	
		String sexs[] = {"Homme","Femme"};
		JComboBox sex = new JComboBox(sexs);
		
		String activities[] = Kudata.allActivities();
		JComboBox activityType = new JComboBox(activities);
		
		String morphos[] = Kudata.allMorphos();
		JComboBox morpho = new JComboBox(morphos);
		
		String projects[] = Kudata.allProjects();
		JComboBox project = new JComboBox(projects);
		
		String regimes[] = Kudata.allRegimes();
		JComboBox regime = new JComboBox(regimes);
		
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String newName = name.getText();
				
				String newFirstname = firstname.getText();
				
				int newWeight = Integer.parseInt(weight.getText());
				
				int newHeight = Integer.parseInt(height.getText());
				
				int newAge = Integer.parseInt(age.getText());
				
				boolean newSex = false;
				if(sex.getSelectedItem() == "Homme") { newSex = true; }
				
				int newActivityType = Kudata.getActivitiesList().indexOf((String)activityType.getSelectedItem());
				
				int newMorpho = Kudata.getMorphosList().indexOf((String)morpho.getSelectedItem());
				
				int newProject = Kudata.getProjectsList().indexOf((String)project.getSelectedItem());
				
				int newRegime = Kudata.getRegimesList().indexOf((String)regime.getSelectedItem());
				
				boolean alergie = false;
				boolean testbool[] = {false,false,false,false,false,false,false,false,false};
				if( alergiesRecap == testbool ) { alergie = true;}
				
				
				if(isNewClient) {
					Subject newSubject = new Subject(newName, newFirstname, newWeight, newHeight, newAge, newSex, newActivityType, newMorpho, newProject,newRegime);
					for(int i = 0 ; i < Kudata.getallergiesList().size() ; i++) {
						if(alergiesRecap[i]) {
							newSubject.addAlergie(Kudata.allAlergies()[i]);
						}
					}
					subSet.put(newSubject.getName() + " " + newSubject.getFirstName(), newSubject);
					Kudata.setCurrentClient(newSubject);
				}
				else {
					Subject sub = subSet.get(Kudata.getCurrentClient());
					sub.actualizeAllDatas(newName, newFirstname, newWeight, newHeight, newAge, newSex, newActivityType, newMorpho, newProject, newRegime);
					subSet.remove(Kudata.getCurrentClient());
					subSet.put(newName + " " + newFirstname, sub);
					Kudata.setCurrentClient(subSet.get(newName + " " + newFirstname));
				}
				
				tab.setVisible(false);
				tab2.setVisible(false);
				clientFrame.dispose();
				ActualizePanelClientData(subSet.get(Kudata.getCurrentClient()),obj);
				ActualizePanelClientNeeds(subSet.get(Kudata.getCurrentClient()),obj2);
				tab.setVisible(true);
				tab2.setVisible(true);
				
			}
			
		});
		
		if(!isNewClient) {
			name.setText(subSet.get(Kudata.getCurrentClient()).getName());
			firstname.setText(subSet.get(Kudata.getCurrentClient()).getFirstName());
			weight.setText("" + subSet.get(Kudata.getCurrentClient()).getWeight());
			height.setText("" + subSet.get(Kudata.getCurrentClient()).getHeight());
			age.setText("" + subSet.get(Kudata.getCurrentClient()).getAge());
			
			//sport
			
			if(!subSet.get(Kudata.getCurrentClient()).getSex()) { sex.setSelectedIndex(1); }
			activityType.setSelectedIndex(subSet.get(Kudata.getCurrentClient()).getActivity());
			morpho.setSelectedIndex(subSet.get(Kudata.getCurrentClient()).getMorpho());
			project.setSelectedIndex(subSet.get(Kudata.getCurrentClient()).getProject());
			regime.setSelectedIndex(subSet.get(Kudata.getCurrentClient()).getRegime());
		}
		
		clientPanel.add(label_1);
		clientPanel.add(name);
		clientPanel.add(label_2);
		clientPanel.add(firstname);
		clientPanel.add(label_3);
		clientPanel.add(weight);
		clientPanel.add(label_4);
		clientPanel.add(height);
		clientPanel.add(label_5);
		clientPanel.add(age);
		clientPanel.add(sportPeriods);
		clientPanel.add(alergies);
		clientPanel.add(label_6);
		clientPanel.add(sex);
		clientPanel.add(label_7);
		clientPanel.add(activityType);
		clientPanel.add(label_8);
		clientPanel.add(morpho);
		clientPanel.add(label_9);
		clientPanel.add(project);
		clientPanel.add(label_10);
		clientPanel.add(regime);
		clientPanel.add(ok);
		
		clientFrame.add(clientPanel);
		
	}	
	/////////////////////////////////////////////////////////////////
	//NEW CLIENTS
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//CLIENTS DATAS
	/////////////////////////////////////////////////////////////////
	public static void ActualizePanelClientData(Subject currentClient, Object[][] obj) {
		obj[0][0] = currentClient.getName(); 
		obj[0][1] = currentClient.getFirstName();
		obj[0][2] =	"" + currentClient.getWeight();
		obj[0][3] = "" + currentClient.getHeight(); 
		obj[0][4] = "" + currentClient.getAge();
		obj[0][5] = "" + Kudata.getSex(currentClient.getSex());
		obj[0][6] = "" + Kudata.getActivities(currentClient.getActivity());
		obj[0][7] = "" + Kudata.getMorphos(currentClient.getMorpho());
		obj[0][8] = "" + Kudata.getProjects(currentClient.getProject());
		obj[0][9] = "" + Kudata.getRegime(currentClient.getRegime());
	}
	/////////////////////////////////////////////////////////////////
	//CLIENTS DATAS
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//CLIENTS NEEDS
	/////////////////////////////////////////////////////////////////
	public static void ActualizePanelClientNeeds(Subject currentClient, Object[][] obj) {
		obj[0][0] = "" + currentClient.getEnergieNeeds();
		obj[0][1] = "" + currentClient.getProteinsNeeds();
		obj[0][2] = "" + currentClient.getLipidesNeeds();
		obj[0][3] = "" + currentClient.getGlucidesNeeds();
		obj[0][4] = "" + currentClient.getIMC();
		obj[0][5] = Kudata.getIMCStatut(currentClient.getIMC());
	}
	/////////////////////////////////////////////////////////////////
	//CLIENTS NEEDS
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//SEARCH CLIENT PANEL
	/////////////////////////////////////////////////////////////////
	public static void SearchPanel(Map<String,Subject> subSet, JTable tab,JTable tab2, Object[][] obj,Object[][] obj2) {
		JFrame searchFrame = new JFrame();
		searchFrame.setSize(250, 350);
		searchFrame.setVisible(true);
		searchFrame.setLocationRelativeTo(null);
		searchFrame.setResizable(false);
		searchFrame.setAlwaysOnTop(true);
		
		if(subSet.size() > 0) {
			JPanel searchPanel = new JPanel();
			searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.PAGE_AXIS));
			
			String clients[] = new String[subSet.size()];
			int i = 0;
			for(Subject client : subSet.values()) { clients[i] = client.getName() + " " + client.getFirstName(); i++;}
			JList<String> clientsNames = new JList<String>(clients);
			JPanel p1 = new JPanel();
			p1.setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(clientsNames);
			p1.add(pane);
			searchPanel.add(p1);
			
			
			JButton ok = new JButton("OK");
			JButton supr = new JButton("Supprimer");
			JPanel p2 = new JPanel();
			p2.setLayout(new FlowLayout());
			p2.add(ok);
			p2.add(supr);
			
			ok.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(clientsNames.getSelectedValue() != null) {
						tab.setVisible(false);
						tab2.setVisible(false);
						String currentClient = clientsNames.getSelectedValue();
						ActualizePanelClientData(subSet.get(currentClient), obj);
						ActualizePanelClientNeeds(subSet.get(currentClient),obj2);
						Kudata.setCurrentClient(subSet.get(currentClient));
						tab.setVisible(true);
						tab2.setVisible(true);
					}
					searchFrame.dispose();
				}
				
			});
			
			supr.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(clientsNames.getSelectedValue() != null) {
						
						tab.setVisible(false);
						tab2.setVisible(false);
						subSet.remove(clientsNames.getSelectedValue());
						SearchPanel(subSet,tab,tab2,obj,obj2);
						searchFrame.dispose();
					}
				}
				
			});
			searchPanel.add(p2);
			
			searchFrame.setContentPane(searchPanel);
		}
	}
	/////////////////////////////////////////////////////////////////
	//SEARCH CLIENT PANEL
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//GLOBAL INGREDIENTS PANEL
	/////////////////////////////////////////////////////////////////
	public static void GlobalIngredientsPanel(Map<String,Ingredient> ingSet, List<String> kuIngSet) {
		JFrame globalIngredientsFrame = new JFrame();
		globalIngredientsFrame.setSize(600, 800);
		globalIngredientsFrame.setVisible(true);
		globalIngredientsFrame.setLocationRelativeTo(null);
		globalIngredientsFrame.setResizable(false);
		globalIngredientsFrame.setAlwaysOnTop(true);
		
		if(ingSet.size() > 0) {
			JPanel globalIngredientPanel = new JPanel();
			globalIngredientPanel.setLayout(new BoxLayout(globalIngredientPanel, BoxLayout.PAGE_AXIS));
		
			String ingredients[] =  new String[ingSet.size()];
			int i = 0;
			for(Ingredient ingredient : ingSet.values()) { ingredients[i] = ingredient.getName(); i++;}
			JList<String> ingredientsNames = new JList<String>(ingredients);
			JPanel p1 = new JPanel();
			p1.setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(ingredientsNames);
			Kudata.setIngredientsCurrentList(ingredientsNames);
			p1.add(pane);
			
			JTextField searchBar = new JTextField();
			searchBar.setColumns(25);
			JButton search = new JButton("Rechercher");
			JButton ajouterKulist = new JButton("Ajouter KuDB");
			
			JPanel p2 = new JPanel();
			p2.setLayout(new FlowLayout());
			p2.add(searchBar);
			
			
			
			p2.add(search);
			/////////////////////////////////////////////////////////////////
			//RESEARCH BAR
			/////////////////////////////////////////////////////////////////
			search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(searchBar.getText().length() > 0) {
					Map<String,Ingredient> searchedIngredients = new HashMap<String,Ingredient>();
					p1.setVisible(false);
					
					for(String s : ingSet.keySet()) {
						int count = searchBar.getText().length();
						for(int i = 0 ; i < searchBar.getText().length() ; i++) {
							if(s.length() >= searchBar.getText().length() && s.charAt(i) == searchBar.getText().charAt(i)) {
								count--;
							}
						}
						if(count == 0) {
							searchedIngredients.put(ingSet.get(s).getName(), ingSet.get(s));
						}
					}
					
					String searchedIngredientsNames[] =  new String[searchedIngredients.size()];
					int i = 0;
					for(Ingredient ingredient : searchedIngredients.values()) { searchedIngredientsNames[i] = ingredient.getName(); i++;}
					JList<String> searchedIngredientsNamesJlist = new JList<String>(searchedIngredientsNames);
					JScrollPane searchedPane = new JScrollPane(searchedIngredientsNamesJlist);
					Kudata.setIngredientsCurrentList(searchedIngredientsNamesJlist);
					p1.remove(p1.getComponent(0));
					p1.add(searchedPane);
					p1.setVisible(true);
				}
			}
			
			});
			/////////////////////////////////////////////////////////////////
			//RESEARCH BAR
			/////////////////////////////////////////////////////////////////
			
			
			
			p2.add(ajouterKulist);
			/////////////////////////////////////////////////////////////////
			//ADD KULIST
			/////////////////////////////////////////////////////////////////
			ajouterKulist.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Ingredient curIng = ingSet.get(Kudata.getIngredientsCurrentList().getSelectedValue());
					if(!kuIngSet.contains(curIng.getName())) {
						Kudata.setGlobalIngToKuing(curIng);
						KuIngPanel(ingSet, false,kuIngSet,false);
					}
				}
			});
			/////////////////////////////////////////////////////////////////
			//ADD KULIST
			/////////////////////////////////////////////////////////////////
			
			
			
			globalIngredientPanel.add(p1);
			globalIngredientPanel.add(p2);
			
			globalIngredientsFrame.setContentPane(globalIngredientPanel);
			
		}
			
	}
	/////////////////////////////////////////////////////////////////
	//GLOBAL INGREDIENTS PANEL
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//KUINGREDIENT PANEL
	/////////////////////////////////////////////////////////////////
	public static void KuIngredientPanel(List<String> kuIngSet, Map<String,Ingredient> ingSet, boolean isAddRecipe) {
		JFrame kuIngredientsFrame = new JFrame();
		kuIngredientsFrame.setSize(700, 800);
		kuIngredientsFrame.setVisible(true);
		kuIngredientsFrame.setLocationRelativeTo(null);
		kuIngredientsFrame.setResizable(false);
		kuIngredientsFrame.setAlwaysOnTop(true);
		
		if(kuIngSet.size() > 0) {
			JPanel kuIngredientPanel = new JPanel();
			kuIngredientPanel.setLayout(new BoxLayout(kuIngredientPanel, BoxLayout.PAGE_AXIS));
			
			JPanel p1 = new JPanel();
			p1.setLayout(new FlowLayout());
			
			JPanel p2 = new JPanel();
			p2.setLayout(new BorderLayout());
			
			JPanel p3 = new JPanel();
			p3.setLayout(new FlowLayout());
			
			String ingredientsNames[] = new String[kuIngSet.size()];
			int i = 0;
			for(String ingName : kuIngSet) { ingredientsNames[i] = ingName; i++; }
			JList<String> kuIngredientsNames = new JList<String>(ingredientsNames);
			Kudata.setKuIngredientsCurrentList(kuIngredientsNames);
			JScrollPane kuIngScrollPane = new JScrollPane(kuIngredientsNames);
			
			p2.add(kuIngScrollPane);
			
			List<String> species = new ArrayList<String>();
			species.add("-");
			for(String ing : kuIngSet) { 
				if(!species.contains((String)ingSet.get(ing).getSpecies())) {
					species.add(ingSet.get(ing).getSpecies()); 
					}
				}
			String[] speciesNames = new String[species.size()];
			int j = 0;
			for(String s : species) { speciesNames[j] = s; j++; }
			JComboBox speciesBox = new JComboBox(speciesNames);
			
			JButton search = new JButton("Rechercher");
			
			p1.add(speciesBox);
			
			p1.add(search);
			/////////////////////////////////////////////////////////////////
			//SPECIES RESEARCH
			/////////////////////////////////////////////////////////////////
			search.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(speciesBox.getSelectedItem() != "-") {
						p2.setVisible(false);
					
						String selectedSpecies = (String)speciesBox.getSelectedItem();
						List<String> searchedIngList = new ArrayList<String>();
						for(String i : kuIngSet) { if(ingSet.get(i).getSpecies() == selectedSpecies) { searchedIngList.add(i);  } }
						String searchedIngredientsNames[] = new String[searchedIngList.size()];
						int i = 0;
						for(String ing : searchedIngList) { searchedIngredientsNames[i] = ing; i++; }
						JList<String> searchedIngredients = new JList<String>(searchedIngredientsNames);
						Kudata.setKuIngredientsCurrentList(searchedIngredients);
						JScrollPane searchedPane = new JScrollPane(searchedIngredients);
					
						p2.remove(p2.getComponent(0));
						p2.add(searchedPane);
					
						p2.setVisible(true);
					}
					
				}
				
			});
			/////////////////////////////////////////////////////////////////
			//SPECIES RESEARCH
			/////////////////////////////////////////////////////////////////
			
			JButton modifier = new JButton("Modifier");
			JButton supprimer = new JButton("Supprimer");
			
			p3.add(modifier);
			/////////////////////////////////////////////////////////////////
			//MOD KUING
			/////////////////////////////////////////////////////////////////
			modifier.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Ingredient curIng = ingSet.get(Kudata.getKuIngredientsCurrentList().getSelectedValue());
					Kudata.setGlobalIngToKuing(curIng);
					KuIngPanel(ingSet, false,kuIngSet,true);
				}
				
			});
			/////////////////////////////////////////////////////////////////
			//MOD KUING
			/////////////////////////////////////////////////////////////////
			
			
			p3.add(supprimer);
			/////////////////////////////////////////////////////////////////
			//DEL KUING
			/////////////////////////////////////////////////////////////////
			supprimer.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					Ingredient curIng = ingSet.get(Kudata.getKuIngredientsCurrentList().getSelectedValue());
					kuIngSet.remove(curIng.getName());
					KuIngredientPanel(kuIngSet,ingSet,false);
					kuIngredientsFrame.dispose();
				}
				
			});
			/////////////////////////////////////////////////////////////////
			//DEL KUING
			/////////////////////////////////////////////////////////////////
			
			if(isAddRecipe) {
				JButton addRecipe = new JButton("Ajouter Ingredient");
				JButton mainAddRecipe = new JButton("Ajouter Ingredient Principal");
				JButton ok = new JButton("OK");
				
				p3.add(addRecipe);
				addRecipe.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JFrame qtyFrame = new JFrame();
						qtyFrame.setSize(200, 100);
						qtyFrame.setVisible(true);
						qtyFrame.setLocationRelativeTo(null);
						qtyFrame.setResizable(false);
						qtyFrame.setAlwaysOnTop(true);
						
						JPanel qPane = new JPanel();
						qPane.setLayout(new FlowLayout());
						
						JLabel label_1 = new JLabel("Quantité :");
						JTextField qty = new JTextField();
						qty.setColumns(3);
						
						JButton ok2 = new JButton("OK");
						ok2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								qtyFrame.dispose();
								Ingredient curIng = ingSet.get(Kudata.getKuIngredientsCurrentList().getSelectedValue());
								Kudata.addCurrentRecipeIngredient(curIng.getName(),Float.parseFloat(qty.getText()));
							}
							
						});
						
						qPane.add(label_1);
						qPane.add(qty);
						qPane.add(ok2);
						
						qtyFrame.setContentPane(qPane);
						
					}
					
				});
				
				p3.add(mainAddRecipe);
				mainAddRecipe.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JFrame qtyFrame = new JFrame();
						qtyFrame.setSize(200, 100);
						qtyFrame.setVisible(true);
						qtyFrame.setLocationRelativeTo(null);
						qtyFrame.setResizable(false);
						qtyFrame.setAlwaysOnTop(true);
						
						JPanel qPane = new JPanel();
						qPane.setLayout(new FlowLayout());
						
						JLabel label_1 = new JLabel("Quantité :");
						JTextField qty = new JTextField();
						qty.setColumns(3);
						
						JButton ok2 = new JButton("OK");
						ok2.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								qtyFrame.dispose();
								Ingredient curIng = ingSet.get(Kudata.getKuIngredientsCurrentList().getSelectedValue());
								Kudata.addCurrentRecipeMainIngredient(curIng.getName(),Float.parseFloat(qty.getText()));
							}
							
						});
						
						qPane.add(label_1);
						qPane.add(qty);
						qPane.add(ok2);
						
						qtyFrame.setContentPane(qPane);
						
					}
					
				});
				
				p3.add(ok);
				ok.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						kuIngredientsFrame.dispose();
					}
					
				});
			}
			
			kuIngredientPanel.add(p1);
			kuIngredientPanel.add(p2);
			kuIngredientPanel.add(p3);
			
			kuIngredientsFrame.setContentPane(kuIngredientPanel);
			
		}
	}
	/////////////////////////////////////////////////////////////////
	//KUINGREDIENT PANEL
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//INGREDIENTS READER
	/////////////////////////////////////////////////////////////////
	@SuppressWarnings("deprecation")
	public static void IngredientsReader(String path, Map<String,Ingredient> ingSet) throws IOException {
		ingSet.clear();
		FileInputStream fileIn = new FileInputStream(new File(path));
		Workbook workbook = new XSSFWorkbook(fileIn);
		Sheet dataSheet = workbook.getSheetAt(2);
		int listInd[]  = {4,7,8,9,10,11,12,13,14,15,16};
		for(int i = 1 ; i < 2808 ; i++) {
			String[] dataRow = new String[listInd.length];
			for(int j = 0 ; j < listInd.length ; j++ ) {
				Cell curCell = dataSheet.getRow(i).getCell(listInd[j]);
				if(curCell.getCellType() == CellType.NUMERIC) {
					double res = curCell.getNumericCellValue();
					dataRow[j] = "" + res;
				}
				else {
					dataRow[j] = (curCell.getStringCellValue()).replace(",", ".");
				}
			}
			
			Ingredient newIng = new Ingredient(dataRow[0],dataRow[1],Kudata.allAlergies()[0],Float.valueOf(dataRow[2]),Float.valueOf(dataRow[3]),Float.valueOf(dataRow[4]),Float.valueOf(dataRow[5]),Float.valueOf(dataRow[6]),Float.valueOf(dataRow[7]),Float.valueOf(dataRow[8]),Float.valueOf(dataRow[9]),Float.valueOf(dataRow[10]),0);
			ingSet.put(dataRow[1], newIng);
				
		}
	}
	/////////////////////////////////////////////////////////////////
	//INGREDIENTS READER
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//KUING ADD PANEL
	/////////////////////////////////////////////////////////////////
	public static void KuIngPanel(Map<String,Ingredient> ingSet, boolean isNewIngredient, List<String> kuIngSet, boolean isModification) {
		JFrame kuingFrame = new JFrame();
		kuingFrame.setTitle("KuIngrédients");
		kuingFrame.setSize(470,250);
		kuingFrame.setLocationRelativeTo(null);
		kuingFrame.setResizable(false);
		kuingFrame.setAlwaysOnTop(true);
		kuingFrame.setVisible(true);
		
		JPanel kuingPanel = new JPanel();
		kuingPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		
		JLabel label_1 = new JLabel("Nom :");
		JLabel label_2 = new JLabel("Prix :");
		JLabel label_3 = new JLabel("Protéines :");
		JLabel label_4 = new JLabel("Glucides :");
		JLabel label_5 = new JLabel("Lipides :");
		JLabel label_6 = new JLabel("Sucres :");
		JLabel label_7 = new JLabel("Fibres Alimentaires :");
		JLabel label_8 = new JLabel("AgSat :");
		JLabel label_9 = new JLabel("AgMonoInsat :");
		JLabel label_10 = new JLabel("AgPolyInsat :");
		JLabel label_11 = new JLabel("eReg :");
		
		List<String> species = new ArrayList<String>();
		for(Ingredient i : ingSet.values()) { if(!species.contains(i.getSpecies())) { species.add(i.getSpecies()); }}
		String[] speciesNames = new String[species.size()];
		int i = 0;
		for(String s : species) { speciesNames[i] = s; i++; }
		JComboBox speciesBox = new JComboBox(speciesNames);
		
		
		List<String> allergenes = new ArrayList<String>();
		for(String s : Kudata.getallergiesList()) { allergenes.add(s); }
		String[] allergenesNames = new String[allergenes.size()];
		int j = 0;
		for(String s : allergenes) { allergenesNames[j] = s; j++; }
		JComboBox allergenesBox = new JComboBox(allergenesNames);
		
		
		JTextField name = new JTextField();
		name.setColumns(25);
		
		
		JTextField price = new JTextField();
		price.setColumns(3);
	
		JTextField prot = new JTextField();
		prot.setColumns(3);
	
		JTextField gluc = new JTextField();
		gluc.setColumns(3);
	
		JTextField lip = new JTextField();
		lip.setColumns(3);
		
		JTextField sucres = new JTextField();
		sucres.setColumns(3);
		
		JTextField fibAl = new JTextField();
		fibAl.setColumns(3);
		
		JTextField agSat = new JTextField();
		agSat.setColumns(3);
		
		JTextField agMono = new JTextField();
		agMono.setColumns(3);
		
		JTextField agPoly = new JTextField();
		agPoly.setColumns(3);
		
		JTextField eReg = new JTextField();
		eReg.setColumns(3);
		
		if(!isNewIngredient) {
			Ingredient curIng = ingSet.get(Kudata.getGlobalIngToKuing());
			name.setText(Kudata.getGlobalIngToKuing());
			speciesBox.setSelectedIndex(species.indexOf(curIng.getSpecies()));
			price.setText("" + curIng.getPrize());
			prot.setText("" + curIng.getProteins());
			gluc.setText("" + curIng.getCarbohydrates());
			lip.setText("" + curIng.getLipids());
			sucres.setText("" + curIng.getSugars());
			fibAl.setText("" + curIng.getDietaryFiber());
			agSat.setText("" + curIng.getAgSat());
			agMono.setText("" + curIng.getAgMonoInsat());
			agPoly.setText("" + curIng.getAgPolyInsat());
			eReg.setText("" + curIng.getEReg());
		}
		
		kuingPanel.add(label_1);
		kuingPanel.add(name);
		kuingPanel.add(speciesBox);
		kuingPanel.add(label_2);
		kuingPanel.add(price);
		kuingPanel.add(label_3);
		kuingPanel.add(prot);
		kuingPanel.add(label_4);
		kuingPanel.add(gluc);
		kuingPanel.add(label_5);
		kuingPanel.add(lip);
		kuingPanel.add(label_6);
		kuingPanel.add(sucres);
		kuingPanel.add(label_7);
		kuingPanel.add(fibAl);
		kuingPanel.add(label_8);
		kuingPanel.add(agSat);
		kuingPanel.add(label_9);
		kuingPanel.add(agMono);
		kuingPanel.add(label_10);
		kuingPanel.add(agPoly);
		kuingPanel.add(label_11);
		kuingPanel.add(eReg);
		
		JButton ok = new JButton("OK");
		kuingPanel.add(ok);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String newSpecies = (String)speciesBox.getSelectedItem();
				
				String newName = name.getText();
				
				String newAllergene = "bonjours";
				
				float newPrice = Float.parseFloat(price.getText());
				
				float newProt = Float.parseFloat(prot.getText());
				
				float newGluc = Float.parseFloat(gluc.getText());
				
				float newLip = Float.parseFloat(lip.getText());
				
				float newSucres = Float.parseFloat(sucres.getText());
				
				float newFibAl = Float.parseFloat(fibAl.getText());
				
				float newAgSat = Float.parseFloat(agSat.getText());
				
				float newAgMono = Float.parseFloat(agMono.getText());
				
				float newAgPoly = Float.parseFloat(agPoly.getText());
				
				float newEReg = Float.parseFloat(eReg.getText());
				
				if(!isNewIngredient) {
					ingSet.get(Kudata.getGlobalIngToKuing()).actualizeAllDatas(newSpecies, newName, newAllergene, newEReg, newProt, newGluc, newLip, newSucres, newFibAl, newAgSat, newAgMono, newAgPoly, newPrice);
					if(!isModification) {
						kuIngSet.add(newName);
					}
				}
				else {
					Ingredient curIng = new Ingredient(newSpecies, newName, newAllergene, newEReg, newProt, newGluc, newLip, newSucres, newFibAl, newAgSat, newAgMono, newAgPoly, newPrice);
					ingSet.put(newName, curIng);
					kuIngSet.add(newName);
				}
				kuingFrame.dispose();
			}
			
		});
		
		kuingFrame.setContentPane(kuingPanel);
		
		
		
	}
	/////////////////////////////////////////////////////////////////
	//KUING ADD PANEL
	/////////////////////////////////////////////////////////////////
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//RECIPE DATABASE
	/////////////////////////////////////////////////////////////////
	public static void RecipesPanel(Map<String,Ingredient> ingSet, List<String> kuIngSet, Map<String,Recipe> recSet) {
		JFrame recipeFrame = new JFrame();
		recipeFrame.setSize(450, 650);
		recipeFrame.setVisible(true);
		recipeFrame.setLocationRelativeTo(null);
		recipeFrame.setResizable(false);
		recipeFrame.setAlwaysOnTop(true);
		
		JPanel recipePanel = new JPanel();
		recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.PAGE_AXIS));
		
		if(recSet.size() > 0) {
		
			String recipes[] = new String[recSet.size()];
			int i = 0;
			for(Recipe recipe : recSet.values()) { recipes[i] = recipe.getName(); i++;}
			JList<String> recipesNames = new JList<String>(recipes);
			Kudata.setRecipesCurrentList(recipesNames);
			JPanel p1 = new JPanel();
			p1.setLayout(new BorderLayout());
			JScrollPane pane = new JScrollPane(recipesNames);
			p1.add(pane);
			recipePanel.add(p1);
			
			recipePanel.add(p1);
		}
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
			
		JButton supr = new JButton("Supprimer");
		JButton modifier = new JButton("Modifier");
		
		
		
		
		p2.add(supr);
		/////////////////////////////////////////////////////////////////
		//DELETE RECIPE
		/////////////////////////////////////////////////////////////////
		supr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Recipe rec = recSet.get(Kudata.getRecipesCurrentJList().getSelectedValue());
				recSet.remove(rec.getName());
				RecipesPanel(ingSet,kuIngSet,recSet);
				recipeFrame.dispose();
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//DELETE RECIPE
		/////////////////////////////////////////////////////////////////
		
		
		
		
		
		p2.add(modifier);
		/////////////////////////////////////////////////////////////////
		//MOD RECIPE
		/////////////////////////////////////////////////////////////////
		modifier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Kudata.setCurrentRecipe(Kudata.getRecipesCurrentJList().getSelectedValue());
				for(String s : recSet.get(Kudata.getCurrentRecipe()).getIngredients().keySet()) { Kudata.addCurrentRecipeIngredient(s, recSet.get(Kudata.getCurrentRecipe()).getIngredients().get(s)); }
				for(String s : recSet.get(Kudata.getCurrentRecipe()).getMainIngredients().keySet()) { Kudata.addCurrentRecipeMainIngredient(s, recSet.get(Kudata.getCurrentRecipe()).getMainIngredients().get(s)); }
				NewRecipePanel(ingSet,kuIngSet,recSet,false);
			}
			
		});
		/////////////////////////////////////////////////////////////////
		//MOD RECIPE
		/////////////////////////////////////////////////////////////////
		
		
		
		recipePanel.add(p2);
		
		recipeFrame.setContentPane(recipePanel);
	}
	/////////////////////////////////////////////////////////////////
	//RECIPE DATABASE
	/////////////////////////////////////////////////////////////////
	
	
	
	
	/////////////////////////////////////////////////////////////////
	//NEW RECIPE
	/////////////////////////////////////////////////////////////////
	public static void NewRecipePanel(Map<String,Ingredient> ingSet, List<String> kuIngSet, Map<String,Recipe> recSet,boolean isNewRecipe) {
		// TODO Auto-generated method stub
		JFrame newRecipeFrame = new JFrame();
		newRecipeFrame.setTitle("Nouvelle Recettes");
		newRecipeFrame.setSize(500,200);
		newRecipeFrame.setLocationRelativeTo(null);
		newRecipeFrame.setResizable(false);
		newRecipeFrame.setAlwaysOnTop(true);
		newRecipeFrame.setVisible(true);
		
		JPanel newRecipePanel = new JPanel();
		newRecipePanel.setLayout(new FlowLayout());
		
		JLabel label_1 = new JLabel("Nom :");
		JLabel label_2 = new JLabel("Prix :");
		JLabel label_3 = new JLabel("Cout :");
		
		JTextField name = new JTextField();
		name.setColumns(15);
		
		JTextField price = new JTextField();
		price.setColumns(5);
		
		JTextField cost = new JTextField();
		cost.setColumns(5);
		cost.setText("" + 0);
		cost.setEditable(false);
		
		JButton ingList = new JButton("Ingredients");
		
		if(!isNewRecipe) {
			for(String s : recSet.get(Kudata.getCurrentRecipe()).getIngredients().keySet()) {
				Kudata.addCurrentRecipeIngredient(s,recSet.get(Kudata.getCurrentRecipe()).getIngredients().get(s));
				}
			for(String s : recSet.get(Kudata.getCurrentRecipe()).getMainIngredients().keySet()) { Kudata.addCurrentRecipeMainIngredient(s,recSet.get(Kudata.getCurrentRecipe()).getMainIngredients().get(s)); }
			name.setText(Kudata.getCurrentRecipe());
			price.setText("" + recSet.get(Kudata.getCurrentRecipe()).getPrice());
			float c = 0;
			if(Kudata.getCurrentRecipeIngredients().size() > 0) {
				for(String s : Kudata.getCurrentRecipeIngredients().keySet()) { c += ingSet.get(s).getPrize()*Kudata.getCurrentRecipeIngredients().get(s); }
			}
			if(Kudata.getCurrentRecipeMainIngredients().size() > 0) {
				for(String s : Kudata.getCurrentRecipeMainIngredients().keySet()) { c += ingSet.get(s).getPrize()*Kudata.getCurrentRecipeMainIngredients().get(s); }
			}
			cost.setText("" + c);
		}
		
		newRecipePanel.add(label_1);
		newRecipePanel.add(name);
		newRecipePanel.add(label_2);
		newRecipePanel.add(price);
		newRecipePanel.add(label_3);
		newRecipePanel.add(cost);
		newRecipePanel.add(ingList);
		ingList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame ingredientsFrame = new JFrame();
				ingredientsFrame.setSize(250, 350);
				ingredientsFrame.setVisible(true);
				ingredientsFrame.setLocationRelativeTo(null);
				ingredientsFrame.setResizable(false);
				ingredientsFrame.setAlwaysOnTop(true);
				
				if(Kudata.getCurrentRecipeIngredients().size() + Kudata.getCurrentRecipeMainIngredients().size() > 0) {
					
					JPanel ingPanel = new JPanel();
					ingPanel.setLayout(new BorderLayout());
					
					String ingredients[] = new String[Kudata.getCurrentRecipeIngredients().size() + Kudata.getCurrentRecipeMainIngredients().size()];
					int i = 0;
					for(String ingredient : Kudata.getCurrentRecipeIngredients().keySet()) { ingredients[i] = ingredient; i++; }
					for(String ingredient : Kudata.getCurrentRecipeMainIngredients().keySet()) { ingredients[i] = ingredient; i++; }
					JList<String> ingredientsNames = new JList<String>(ingredients);
					JScrollPane pane = new JScrollPane(ingredientsNames);
					ingPanel.add(pane);
					ingredientsFrame.setContentPane(ingPanel);
				}
			}
			
		});
		
		JButton mainIngList = new JButton("Ingredients Principaux");
		mainIngList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame ingredientsFrame = new JFrame();
				ingredientsFrame.setSize(250, 350);
				ingredientsFrame.setVisible(true);
				ingredientsFrame.setLocationRelativeTo(null);
				ingredientsFrame.setResizable(false);
				ingredientsFrame.setAlwaysOnTop(true);
				
				if(Kudata.getCurrentRecipeMainIngredients().size() > 0) {
					
					JPanel ingPanel = new JPanel();
					ingPanel.setLayout(new BorderLayout());
					
					String ingredients[] = new String[Kudata.getCurrentRecipeMainIngredients().size()];
					int i = 0;
					for(String ingredient : Kudata.getCurrentRecipeMainIngredients().keySet()) { ingredients[i] = ingredient; i++; }
					JList<String> ingredientsNames = new JList<String>(ingredients);
					JScrollPane pane = new JScrollPane(ingredientsNames);
					ingPanel.add(pane);
					ingredientsFrame.setContentPane(ingPanel);
				}
			}
			
		});
		
		JButton addIngredient = new JButton("Ajouter Ingredients");
		JButton ok = new JButton("OK");
		
		
		newRecipePanel.add(addIngredient);
		addIngredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				KuIngredientPanel(kuIngSet,ingSet,true);
			}
			
		});
		
		
		newRecipePanel.add(ok);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String newName = name.getText();
				float newPrice = Float.parseFloat(price.getText());
				Recipe newRecipe = new Recipe(newName,newPrice);
				for(String s : Kudata.getCurrentRecipeIngredients().keySet()) { newRecipe.addIngredient(s, Kudata.getCurrentRecipeIngredients().get(s));}
				for(String s : Kudata.getCurrentRecipeMainIngredients().keySet()) { newRecipe.addMainIngredient(s, Kudata.getCurrentRecipeMainIngredients().get(s));}
				recSet.put(newName, newRecipe);
				Kudata.clearCurrentRecipeIngredients();
				Kudata.clearCurrentRecipeMainIngredient();
				newRecipeFrame.dispose();
			}
			
		});
		
		newRecipeFrame.setContentPane(newRecipePanel);
	}
	/////////////////////////////////////////////////////////////////
	//NEW RECIPE
	/////////////////////////////////////////////////////////////////
}




