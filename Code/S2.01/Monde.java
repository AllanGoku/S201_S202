import java.util.ArrayList;

public class Monde {
	public Secteur[][] lesSecteurs;
	
	public Monde() {
		this.lesSecteurs = new Secteur[10][10];
	}
	
	public void addSecteur(Secteur sec) {
	}
	
	public Monde createWorld()
	{
		for(int i=0;i<=100;i++) {
			this.addSecteur(new Secteur());
		}
		return this;
	}
}
