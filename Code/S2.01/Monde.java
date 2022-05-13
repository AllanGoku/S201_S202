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
		int compMine = 0;
		int compEau = 0;
		int compRob = 0;
		for(int i=0;i<10;i++) {
			for(int u =0; u<10;u++ ) {
				int ver = random.nextInt(15);
				Secteur sec = new Secteur(i,u,this);
				if(ver == 4 && compEau <= 10) {
					sec = new Secteur(i,u,this);
					sec.setEau(true);
					compEau+=1;
				}
				if(ver == 6 && compEnt < 2) {
					if(compEnt>1) {
						this.lesEntrepots.add(new Entrepot(i,"Or",u,sec));
						sec.setEntrepot(this.lesEntrepots.get(compEnt));
					}
					else {
						this.lesEntrepots.add(new Entrepot(i,"Nickel",u,sec));
						sec.setEntrepot(this.lesEntrepots.get(compEnt));
					}
					compEnt+=1;
				}
				if(ver == 8 && compMine < 4) {
					this.lesMines.add(new Mine(i,"Or",u,sec));
					sec.setMine(this.lesMines.get(compMine));
					compMine+=1;
				}
				if(ver == 10 && compRob<5) {
					this.lesRobots.add(new Robot(i,"Or",u,sec,0));
					sec.setRobot(this.lesRobots.get(compRob));
					compRob+=1;
				}
				this.lesSecteurs[i][u] = sec;
			}
		
		return this;
	}
	
	public Secteur[][] getSecteurs() {
		return this.lesSecteurs;
	}
	
	public void alea() {
		Random ran = new Random();
		for(int i=0;i<this.lesMines.size();i++) {
			Mine obj = this.lesMines.get(i);
			int alea = ran.nextInt(50);
			alea+=50;
			obj.setCapaciteStockageMax(alea);
			obj.aleaMinerai();
			System.out.println("Mines: "+obj.getTypeMinerai()+"["+obj.getSonSecteur().getX()+","+obj.getSonSecteur().getY()+"]");
		}
		for(int i=0;i<this.lesRobots.size();i++) {
			Robot obj = this.lesRobots.get(i);
			int alea = ran.nextInt(8);
			alea+=1;
			obj.setCapaciteStockageMax(alea);
			obj.aleaMinerai();
			System.out.println("Robots: "+obj.getTypeMinerai()+"["+obj.getSonSecteur().getX()+","+obj.getSonSecteur().getY()+"]");
		}
	}
	
	public void setTypeManquant(){
		int compNickel =0;
		int compOr = 0;
		Random ran = new Random();
		for(int i =0; i<this.lesRobots.size();i++) {
			if(this.lesRobots.get(i).getTypeMinerai()=="Nickel") {
				compNickel +=1;
			}
			else compOr+=1;
		}
		if(compNickel<=1) {
			this.lesRobots.get(ran.nextInt(this.lesRobots.size())).setTypeMinerai("Nickel");
		}
		else if(compOr<=1) {
			this.lesRobots.get(ran.nextInt(this.lesRobots.size())).setTypeMinerai("Or");
		}
		compNickel = 0;
		compOr = 0;
		for(int i =0; i<this.lesMines.size();i++) {
			if(this.lesMines.get(i).getTypeMinerai()=="Nickel") {
				compNickel +=1;
			}
			else compOr+=1;
		}
		if(compNickel<=1) {
			this.lesMines.get(ran.nextInt(this.lesMines.size())).setTypeMinerai("Nickel");
		}
		else if(compOr<=1) {
			this.lesMines.get(ran.nextInt(this.lesMines.size())).setTypeMinerai("Or");
		}
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
