public class Robot extends Ressource {

	private int capacite;
	
	public Robot(int x, int y)
	{
		setNumero(0);
		aleaMinerai();
		this.capacite=0;
		this.setSonSecteur(new Secteur(x,y));
	}
	
	public Robot(int n, String t, int c, Secteur sec)
	{
		setNumero(n);
		setTypeMinerai(t);
		this.capacite=c;
		this.setSonSecteur(sec);
	}
	

	public int getNbrMinerai() {
		return this.capacite;
	}

	public void setNbrMinerai(int n) {
		this.capacite = n;
	}
	
	
}
