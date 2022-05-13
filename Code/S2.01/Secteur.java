package application;

public class Secteur {
	private boolean eau;
	private Ressource[][] contenu;
	private Mine mine;
	private Entrepot entrepot;
	private Robot robot;
	private int[] coord;
	private Monde sonMonde;
	
	public Secteur(int x, int y, Monde w)
	{
		this.eau = false;
		Ressource[] first = new Ressource[2]; Ressource[] second = new Ressource[2];
		this.contenu = new Ressource[][] {first, second};
		this.mine = null; this.entrepot = null;	this.robot = null;
		this.coord = new int[2];
		this.coord[0] = x;
		this.coord[1] = y;
		this.setSonMonde(w);
	}
	
	public Secteur(boolean water,int x, int y, Monde w)
	{
		this.eau = water;
		this.coord = new int[2];
		this.coord[0] = x;
		this.coord[1] = y;
		this.setSonMonde(w);
	}
	
	public Secteur(boolean water, Mine M, Entrepot E, Robot R, int x, int y, Monde w)
	{
		this.coord = new int[2];
		if(water)
		{
			this.eau = true;
			this.mine = null; this.entrepot = null; this.robot = null;
			Ressource[] first = new Ressource[2]; Ressource[] second = new Ressource[2];
			this.contenu = new Ressource[][] {first, second};
			this.contenu[0] = first; this.contenu[1] = second;
			this.sonMonde = w;
		}
		else
		{
			this.eau = false;
			this.mine = M; this.entrepot = E; this.robot = R;
		}
		this.coord = new int[2];
		this.coord[0] = x;
		this.coord[1] = y;
	}
	
	public boolean getEau()
	{
		return this.eau;
	}
	
	public void setEau(boolean e) {
		this.eau = e;
	}
	
	public Ressource[][] getContenu() {
		return this.contenu;
	}
	
	public void setContenu(Ressource[][] c) {
		this.contenu = c;
	}
	
	public boolean haveMine()
	{
		if(this.mine == null) return false;
		else return true;
	}
	
	public boolean haveEntrepot()
	{
		if(this.entrepot == null) return false;
		else return true;
	}
	
	public boolean haveRobot()
	{
		if(this.robot == null) return false;
		else return true;
	}
	
	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}

	public Entrepot getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}

	public int getX() {
		return coord[0];
	}
	
	public int getY() {
		return coord[1];
	}
	
	public int[] getCoord() {
		return coord;
	}

	public void setCoord(int x, int y) {
		this.coord[0] = x;
		this.coord[1] = y;
	}

	public Monde getSonMonde() {
		return sonMonde;
	}

	public void setSonMonde(Monde sonMonde) {
		this.sonMonde = sonMonde;
	}
	public void setRobot(Robot rob) {
		this.robot=rob;
	}
	
	
}
