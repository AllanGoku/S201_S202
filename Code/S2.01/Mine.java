package application;

public class Mine extends Ressource {
	
	private int capacite;
	
	public Mine(int x, int y)
	{
		setNumero(0);
		setTypeMinerai("inconnu");
		this.capacite=0;
		this.setSonSecteur(new Secteur(x,y));
	}
	
	public Mine(int n, String t, int c, Secteur sec)
	{
		setNumero(n);
		setTypeMinerai(t);
		this.capacite=c;
		this.setSonSecteur(sec);
	}


	public int getNbrMinerai() {
		return capacite;
	}

	
	public void setNbrMinerai(int n) {
		this.capacite=n;
	}
	

}
