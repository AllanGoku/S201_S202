package application;
public class Entrepot extends Ressource {
	
	public Entrepot(int n, String t,Secteur sec)
	{
		setNumero(n);
		setTypeMinerai(t);
		this.setCapacite(0);
		this.setSonSecteur(sec);
	}
	

	public int getCap() {
		return this.getCapacite();
	}

	public void setCap(int n) {
		this.setCapacite(n);
	}
}
