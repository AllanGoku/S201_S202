import java.util.ArrayList;

public class Monde {
	private ArrayList<Secteur> lesSecteurs;
	
	public Monde() {
		this.lesSecteurs = new ArrayList<Secteur>();
	}
	
	public void addSecteur(Secteur sec) {
		this.lesSecteurs.add(sec);
	}
	
	public Monde createWorld()
	{
		for(int i=0;i<=100;i++) {
			this.addSecteur(new Secteur());
		}
		return this;
	}
}
