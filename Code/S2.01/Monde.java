package application;

import java.util.ArrayList;
import java.util.Random;

public class Monde {
	private Secteur[][] lesSecteurs;
	private ArrayList<Robot> lesRobots;
	private ArrayList<Entrepot> lesEntrepots;
	private ArrayList<Mine> lesMines;
	
	public Monde() {
		this.lesSecteurs = new Secteur[10][10];
		lesRobots = new ArrayList<Robot>();
		lesEntrepots = new ArrayList<Entrepot>();
		lesMines = new ArrayList<Mine>();
	}
	
	public Monde createWorld()
	{
		Random random = new Random();
		int compEnt = 0;
		int compRobot = 0;
		int compMine = 0;
		int compEau = 0;
		int qtEnt = 0;
		int compRob = 0;
		for(int i=0;i<10;i++) {
			for(int u =0; u<10;u++ ) {
				int ver = random.nextInt(21);
				Secteur sec = new Secteur(i,u);
				if(ver == 6 && compEau <= 10) {
					sec = new Secteur(true,i,u);
					compEau+=1;
				}
				if(ver == 8 && compEnt < 2) {
					this.lesEntrepots.add(new Entrepot(i,u));
					sec = new Secteur(false, null, this.lesEntrepots.get(compEnt), null,i,u);
					compEnt+=1;
				}
				if(ver == 10 && compMine < 4) {
					this.lesMines.add(new Mine(i,u));
					sec = new Secteur(false, this.lesMines.get(compMine), null, null,i,u);
					compMine+=1;
				}
				if(ver == 12 && compRob<5) {
					this.lesRobots.add(new Robot(i,u));
					sec = new Secteur(false, null, null, this.lesRobots.get(compRob),i,u);
					compRob+=1;
				}
				this.lesSecteurs[i][u] = sec;
			}
		}	
		
		return this;
	}
	
	public Secteur[][] getSecteurs() {
		return this.lesSecteurs;
	}
	
	public String getTypeManquantRobots(ArrayList<Robot> robots){
		int compNickel =0;
		int compOr = 0;
		for(int i =0; i<robots.size();i++) {
			if(robots.get(i).getTypeMinerai()=="Nickel") {
				compNickel +=1;
			}
			else compOr+=1;
		}
		if(compNickel==0) {
			return "Nickel";
		}
		else if(compOr==0) {
			return "Or";
		}
		return null;
	}
	
	public ArrayList<Entrepot> getEntrepots() {
		return this.lesEntrepots;
	}
	
	public ArrayList<Robot> getRobots() {
		return this.lesRobots;
	}
	
	public ArrayList<Mine> getMines() {
		return this.lesMines;
	}
}
