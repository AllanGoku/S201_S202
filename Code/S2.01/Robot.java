package application;
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
	
	public void extract_minerais() 
	{
		if (this.getSonSecteur().haveMine() == true) 
		{
			if (this.getSonSecteur().getMine().getNbrMinerai() > 0) 
			{
				if (capacite_minage > this.getSonSecteur().getMine().getNbrMinerai()) 
				{
					while ((this.getSonSecteur().getMine().getNbrMinerai() > 0) && (this.getCapacite() + this.getSonSecteur().getMine().getNbrMinerai() > this.getCapaciteStockageMax())) {
						this.getSonSecteur().getMine().setNbrMinerai(getCapaciteMinage()-1);
						this.setCapacite(getCapacite()+1);
					}
				}
				else 
				{
					this.getSonSecteur().getMine().setNbrMinerai(getCapaciteMinage()-capacite_minage);
					this.setCapacite(getCapacite()+capacite_minage);
				}
			}
		}
	}
	
	public int[] seDeplacer(String mouv){
		int [] ancAndNewCord = new int[4];
		int maxLigne = 9;
		int maxColonne = 9;
		if (mouv.contains("S"))
		{
			if (this.getSonSecteur().getX() == maxLigne || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].getEau() || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].haveRobot())
			{
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
