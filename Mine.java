
public class Mine {
	
	private int numero;
	private String type;
	private int capacite;
	private int row;
	private Secteur secteur;
	
	public Mine()
	{
		this.numero = 0;
		this.type="inconnu";
		this.capacite=0;
		this.row=0;
		this.secteur=new Secteur();
	}
	
	public Mine(int n, String t, int c, int x, Secteur sec)
	{
		this.numero = n;
		this.type=t;
		this.capacite=c;
		this.row=x;
		this.secteur= sec;
	}
	
	public int getCoord()
	{
		return this.row;
	}

}
