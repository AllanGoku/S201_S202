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
