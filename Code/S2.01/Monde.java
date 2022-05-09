package application;

import java.util.ArrayList;
import java.util.Random;

public class Monde {
	private Secteur[][] lesSecteurs;
	
	public Monde() {
		this.lesSecteurs = new Secteur[10][10];
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
		ArrayList<Robot> robots = new ArrayList<Robot>();
		for(int i=0;i<10;i++) {
			for(int u =0; u<10;u++ ) {
				int ver = random.nextInt(21);
				Secteur sec = new Secteur(i,u);
				if(ver == 6 && compEau <= 10) {
					sec = new Secteur(true,i,u);
					compEau+=1;
				}
				if(ver == 8 && compEnt < 2) {
					sec = new Secteur(false, null, new Entrepot(i,u), null,i,u);
					compEnt+=1;
				}
				if(ver == 10 && compMine < 4) {
					sec = new Secteur(false, new Mine(i,u), null, null,i,u);
					compMine+=1;
				}
				if(ver == 12 && compRob<5) {
					Robot rob = new Robot(i,u);
					sec = new Secteur(false, null, null, rob,i,u);
					robots.add(rob);
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
}
