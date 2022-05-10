
public class Entrepot extends Ressource {
	
	public Entrepot(int n, String t, int c,Secteur sec)
	{
		setNumero(n);
		setTypeMinerai(t);
		this.setCapaciteStockageMax(c);
		this.setCapacite(0);
		this.setSonSecteur(sec);
	}
	

	public int getNbrMinerai() {
		return this.getCapacite();
	}

	public void setNbrMinerai(int n) {
		this.setCapacite(n);
	}
}
