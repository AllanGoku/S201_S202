import java.util.ArrayList;
import java.util.Random;

public abstract class Ressource{
	private String typeMinerai;
	private int CapaciteStockageMax;
	private int capacite;
	private int numero;
	private Secteur sonSecteur;
	
	public String getTypeMinerai() {
		return typeMinerai;
	}
	public void setTypeMinerai(String typeMinerai) {
		this.typeMinerai = typeMinerai;
	}
	public void aleaMinerai() {
		Random random = new Random();
		int ver = random.nextInt(2);
		ArrayList<String> min = new ArrayList<String>();
		min.add("Nickel"); min.add("Or");
		typeMinerai = min.get(ver);
	}
	public int getCapaciteStockageMax() {
		return CapaciteStockageMax;
	}
	public void setCapaciteStockageMax(int nbrMinerai) {
		this.CapaciteStockageMax = nbrMinerai;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public Secteur getSonSecteur() {
		return sonSecteur;
	}
	public void setSonSecteur(Secteur sonSecteur) {
		this.sonSecteur = sonSecteur;
	}
	public int getCapacite() {
		return capacite;
	}
	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
}
