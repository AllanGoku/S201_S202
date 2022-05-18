package application;

import java.util.concurrent.TimeUnit;

public class Robot extends Ressource {

	private int capacite_minage;
	
	public Robot(int n, String t, int cm, Secteur sec,int csm )
	{
		setNumero(n);
		setTypeMinerai(t);
		this.capacite_minage=cm;
		this.setCapaciteStockageMax(csm);
		this.setCapacite(0);
		this.setSonSecteur(sec);
	}
	

	public int getCapaciteMinage() {
		return this.capacite_minage;
	}

	public void setCapaciteMinage(int n) {
		this.capacite_minage = n;
	}
	
	public boolean extract_minerais() 
	{
		if(this.getSonSecteur().haveMine()) {
			if(this.getCapacite()==this.getCapaciteStockageMax()) {
				new ExceptionContenu(false, false);
				return false;
			} else if(this.getSonSecteur().getMine().getCapacite()==0) {
				new ExceptionContenu(false, true);
				return false;
			}
			else if (this.getSonSecteur().getMine().getTypeMinerai() == this.getTypeMinerai()) 
			{
				int compt = 0;
				System.out.println("Debut minage...");
				while (this.getSonSecteur().getMine().getCap() > 0 && this.getCapacite() < this.getCapaciteStockageMax() && compt < capacite_minage) 
				{
					this.getSonSecteur().getMine().setCap(this.getSonSecteur().getMine().getCap()-1);
					this.setCapacite(this.getCapacite()+1);
					compt+=1;	
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch(Exception e) {
					System.out.println("Interrompu!");
				}
				System.out.println("Fin minage...");
				return true;
			}
		}
		return false;
	}
	
	public boolean deposer() 
	{
		if(this.getCapacite()==0) {
			new ExceptionContenu(true, false);
			return false;
		}
		if (this.getSonSecteur().haveEntrepot() == true && this.getSonSecteur().getEntrepot().getTypeMinerai() == this.getTypeMinerai())
		{
			System.out.println("Debut deposage...");
			this.getSonSecteur().getEntrepot().setCap(this.getCapacite() + this.getSonSecteur().getEntrepot().getCap());
			this.setCapacite(0);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch(Exception e) {
				System.out.println("Interrompu!");
			}
			System.out.println("Fin deposage...");
			return true;
		}
		return false;
	}
	
	public int[] seDeplacer(String mouv){
		int [] ancAndNewCord = new int[4];
		int maxLigne = 9;
		int maxColonne = 9;
		if (mouv.contains("S"))
		{
			if (this.getSonSecteur().getX() == maxLigne || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].getEau() || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].haveRobot())
			{
				new ExceptionDeplacement();
				return null;
			}
			else
			{
				ancAndNewCord[0] = this.getSonSecteur().getX();
				ancAndNewCord[1] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(null);
				this.setSonSecteur(this.getSonSecteur().getSonMonde().getSecteurs()[ancAndNewCord[0]+1][ancAndNewCord[1]]);
				ancAndNewCord[2] = this.getSonSecteur().getX();
				ancAndNewCord[3] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(this);
				return ancAndNewCord;
			}
		}
		else if (mouv.contains("O")) 
		{
			if (this.getSonSecteur().getY() == 0 || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()][this.getSonSecteur().getY()-1].getEau()|| this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()][this.getSonSecteur().getY()-1].haveRobot())
			{
				new ExceptionDeplacement();
				return null;
			}
			else
			{
				ancAndNewCord[0] = this.getSonSecteur().getX();
				ancAndNewCord[1] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(null);
				this.setSonSecteur(this.getSonSecteur().getSonMonde().getSecteurs()[ancAndNewCord[0]][ancAndNewCord[1]-1]);
				ancAndNewCord[2] = this.getSonSecteur().getX();
				ancAndNewCord[3] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(this);
				return ancAndNewCord;
			}
		}
		else if (mouv.contains("N")) 
		{
			if (this.getSonSecteur().getX() == 0 || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()-1][this.getSonSecteur().getY()].getEau()|| this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()-1][this.getSonSecteur().getY()].haveRobot())
			{
				new ExceptionDeplacement();
				return null;
			}
			else
			{
				ancAndNewCord[0] = this.getSonSecteur().getX();
				ancAndNewCord[1] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(null);
				this.setSonSecteur(this.getSonSecteur().getSonMonde().getSecteurs()[ancAndNewCord[0]-1][ancAndNewCord[1]]);
				ancAndNewCord[2] = this.getSonSecteur().getX();
				ancAndNewCord[3] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(this);
				return ancAndNewCord;
			}
		}
		else if (mouv.contains("E")) 
		{
			if (this.getSonSecteur().getY() == maxColonne || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()][this.getSonSecteur().getY()+1].getEau() || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()][this.getSonSecteur().getY()+1].haveRobot())
			{
				new ExceptionDeplacement();
				return null;
			}
			else
			{
				ancAndNewCord[0] = this.getSonSecteur().getX();
				ancAndNewCord[1] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(null);
				this.setSonSecteur(this.getSonSecteur().getSonMonde().getSecteurs()[ancAndNewCord[0]][ancAndNewCord[1]+1]);
				ancAndNewCord[2] = this.getSonSecteur().getX();
				ancAndNewCord[3] = this.getSonSecteur().getY();
				this.getSonSecteur().setRobot(this);
				return ancAndNewCord;
			}
		}
		else return null;
	}
	public int[] getCoord()
	{
		return this.getSonSecteur().getCoord();
	}
}

