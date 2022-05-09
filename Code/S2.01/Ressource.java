package application;

import java.util.ArrayList;
import java.util.Random;

public abstract class Ressource{
	private String typeMinerai;
	private int nbrMinerai;
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
	public int getNbrMinerai() {
		return nbrMinerai;
	}
	public void setNbrMinerai(int nbrMinerai) {
		this.nbrMinerai = nbrMinerai;
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
}
