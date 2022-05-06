
public class Mine extends Ressource {
	
	private int capacite;
	
	public Mine()
	{
		setNumero(0);
		setTypeMinerai("inconnu");
		this.capacite=0;
		this.setSonSecteur(new Secteur());
	}
	
	public Mine(int n, String t, int c, int x, Secteur sec)
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
