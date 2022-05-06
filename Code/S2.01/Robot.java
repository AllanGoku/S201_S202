
public class Robot extends Ressource {

	private int capacite;
	
	public Robot()
	{
		setNumero(0);
		setTypeMinerai("inconnu");
		this.capacite=0;
		this.setSonSecteur(new Secteur());
	}
	
	public Robot(int n, String t, int c, int x, Secteur sec)
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
