import java.util.ArrayList;

public class Monde {
	private ArrayList<Secteur> lesSecteurs;
	
	public Monde() {
		this.lesSecteurs = new ArrayList<Secteur>();
	}
	
	public void addSecteur(Secteur sec) {
		this.lesSecteurs.add(sec);
	}
}
