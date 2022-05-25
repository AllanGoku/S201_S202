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
	
	public boolean extract_minerais() throws ExceptionContenu
	{
		if(this.getSonSecteur().haveMine()) {
			if(this.getSonSecteur().getMine().getTypeMinerai()!=this.getTypeMinerai()) {
				throw new ExceptionContenu("La mine et le robot ont un type différent");
			} else if(this.getSonSecteur().getMine().getCapacite()==0) {
				throw new ExceptionContenu("La mine est vide, rien ne peut etre pris");
			} else if(this.getCapacite()==this.getCapaciteStockageMax()) {
				throw new ExceptionContenu("Le robot est plein, rien ne peut etre pris");
			}
			int compt = 0;
			while (this.getSonSecteur().getMine().getCap() > 0 && this.getCapacite() < this.getCapaciteStockageMax() && compt < capacite_minage) 
			{
				this.getSonSecteur().getMine().setCap(this.getSonSecteur().getMine().getCap()-1);
				this.setCapacite(this.getCapacite()+1);
				compt+=1;
			}
			return true;
			}
		throw new ExceptionContenu("Il n'y a pas de mine dans le secteur");
	}
	
	public boolean deposer() throws ExceptionContenu
	{
		if(this.getSonSecteur().getEntrepot().getTypeMinerai() != this.getTypeMinerai()) {
			throw new ExceptionContenu("L'entrepot et le robot n'ont pas le meme type");
		} else if(this.getCapacite()==0) {
				throw new ExceptionContenu("Votre robot est vide, echec de l'operation");
			}
		if (this.getSonSecteur().haveEntrepot())
		{
			this.getSonSecteur().getEntrepot().setCap(this.getCapacite() + this.getSonSecteur().getEntrepot().getCap());
			this.setCapacite(0);
			return true;
		}
		throw new ExceptionContenu("Il n'y a pas d'entreport dans le secteur");
	}
	
	public int[] seDeplacer(String mouv) throws ExceptionDeplacement{
		int [] ancAndNewCord = new int[4];
		int maxLigne = 9;
		int maxColonne = 9;
		if (mouv.contains("S"))
		{
			if (this.getSonSecteur().getX() == maxLigne || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].getEau() || this.getSonSecteur().getSonMonde().getSecteurs()[this.getSonSecteur().getX()+1][this.getSonSecteur().getY()].haveRobot())
			{
				throw new ExceptionDeplacement();
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
				throw new ExceptionDeplacement();
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
				throw new ExceptionDeplacement();
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
				throw new ExceptionDeplacement();
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
