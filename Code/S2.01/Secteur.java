import java.util.*;
public class Secteur {
	private boolean eau;
	private Ressource[][] contenu;
	private Mine mine;
	private Entrepot entrepot;
	private Robot robot;
	private int[] coord;
	
	public Secteur(int x, int y)
	{
		this.eau = false;
		Ressource[] first = new Ressource[2]; Ressource[] second = new Ressource[2];
		this.contenu = new Ressource[][] {first, second};
		this.mine = null; this.entrepot = null;	this.robot = null;
		this.coord = new int[2];
		this.coord[0] = x;
		this.coord[1] = y;
	}
	
	public Secteur(boolean water,int x, int y)
	{
		this.eau = water;
		this.coord[0] = x;
		this.coord[1] = y;
	}
	
	public Secteur(boolean water, Mine M, Entrepot E, Robot R, int x, int y)
	{
		if(water)
		{
			this.eau = true;
			this.mine = null; this.entrepot = null; this.robot = null;
			Ressource[] first = new Ressource[2]; Ressource[] second = new Ressource[2];
			this.contenu = new Ressource[][] {first, second};
			this.contenu[0] = first; this.contenu[1] = second;
		}
		else
		{
			this.eau = false;
			this.mine = M; this.entrepot = E; this.robot = R;
		}
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
	
	
}
