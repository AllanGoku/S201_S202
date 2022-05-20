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
		for(int i=0;i<10;i++) {
			for(int u =0; u<10;u++ ) {
				this.lesSecteurs[i][u] = new Secteur(false,i,u,this);
			}
		}
		this.alea();
		this.setTypeManquant();
		lesMines.forEach(lesMines -> System.out.println());
		return this;
	}
	

	
	public Secteur[][] getSecteurs() {
		return this.lesSecteurs;
	}
	
	public void alea() {
		Random ran = new Random();
		int comp = 0;
		int compEau = 0;
		while(this.lesEntrepots.size()<2) {
			int x = ran.nextInt(9); int y = ran.nextInt(9);
			if(this.lesEntrepots.size()==0) {
				Entrepot ent = new Entrepot(comp+1,"OR",this.lesSecteurs[x][y]);
				this.lesEntrepots.add(ent);
				this.lesSecteurs[x][y].setEntrepot(ent);
				comp+=1;
			} else {
				Entrepot ent = new Entrepot(comp+1,"NI",this.lesSecteurs[x][y]);
				this.lesEntrepots.add(ent);
				this.lesSecteurs[x][y].setEntrepot(ent);
				comp+=1;
			}
		}
		while(compEau<ran.nextInt(7)+3) {
			int x = ran.nextInt(9); int y = ran.nextInt(9);
			if(!this.lesSecteurs[x][y].haveEntrepot()) {
				this.lesSecteurs[x][y].setEau(true);
				compEau+=1;
			}
		}
		comp=0;
		while(this.lesRobots.size()<ran.nextInt(3)+2) {
			int x = ran.nextInt(9); int y = ran.nextInt(9);
			if(!this.lesSecteurs[x][y].getEau() && !this.lesSecteurs[x][y].haveRobot()) {
				Robot rob = new Robot(comp+1,"OR",0,this.lesSecteurs[x][y],0);
				this.lesRobots.add(rob);
				this.lesSecteurs[x][y].setRobot(rob);
				int alea = ran.nextInt(4)+5;
				rob.setCapaciteStockageMax(alea);
				alea = ran.nextInt(2);
				alea+=1;
				rob.setCapaciteMinage(alea);
				rob.setCapacite(0);
				rob.aleaMinerai();
				comp+=1;
			}
		}
		comp=0;
		while(this.lesMines.size()<ran.nextInt(2)+2) {
			int x = ran.nextInt(9); int y = ran.nextInt(9);
			if(!this.lesSecteurs[x][y].getEau() && !this.lesSecteurs[x][y].haveRobot() && !this.lesSecteurs[x][y].haveEntrepot() && !this.lesSecteurs[x][y].haveMine()) {
				Mine min = new Mine(comp+1,"OR",0,this.lesSecteurs[x][y]);
				this.lesMines.add(min);
				this.lesSecteurs[x][y].setMine(min);
				int alea = ran.nextInt(50)+50;
				min.setCapaciteStockageMax(alea);
				min.setCapacite(alea);
				min.aleaMinerai();
				comp+=1;
			}
		}
	}
	
	public void setTypeManquant(){
		int compNickel =0;
		int compOr = 0;
		Random ran = new Random();
		for(int i =0; i<this.lesRobots.size();i++) {
			if(this.lesRobots.get(i).getTypeMinerai()=="NI") {
				compNickel +=1;
			}
			else compOr+=1;
		}
		if(compNickel<1) {
			this.lesRobots.get(ran.nextInt(this.lesRobots.size())).setTypeMinerai("NI");
		}
		else if(compOr<1) {
			this.lesRobots.get(ran.nextInt(this.lesRobots.size())).setTypeMinerai("OR");
		}
		compNickel = 0;
		compOr = 0;
		for(int i =0; i<this.lesMines.size();i++) {
			if(this.lesMines.get(i).getTypeMinerai()=="NI") {
				compNickel +=1;
			}
			else compOr+=1;
		}
		if(compNickel<1) {
			this.lesMines.get(ran.nextInt(this.lesMines.size())).setTypeMinerai("NI");
		}
		else if(compOr<1) {
			this.lesMines.get(ran.nextInt(this.lesMines.size())).setTypeMinerai("OR");
		}
	}
	public ArrayList<Robot> getRobots(){
		return this.lesRobots;
	}
	public ArrayList<Entrepot> getEntrepots(){
		return this.lesEntrepots;
	}
	public ArrayList<Mine> getMines(){
		return this.lesMines;
	}
}
