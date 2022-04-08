import java.util.*;
public class Secteur {
	private boolean eau;
	private Object[] contenu;
	private Mine mine;
	private Entrepot entrepot;
	private Robot robot;
	
	public Secteur()
	{
		this.eau = false;
		this.contenu = new Object[2];
		Object[] first = new Object[2]; Object[] second = new Object[2];
		this.contenu[0] = first; this.contenu[1] = second;
		this.mine = null; this.entrepot = null;	this.robot = null;
	}
	
	public Secteur(boolean water)
	{
		this.eau = water;
	}
	
	public Secteur(boolean water, Mine M, Entrepot E, Robot R)
	{
		if(water)
		{
			this.eau = true;
			this.mine = null; this.entrepot = null; this.robot = null;
			String w = "eau";
			this.contenu = new Object[2];
			Object[] first = new Object[2]; Object[] second = new Object[2];
			first[0] = w ; first[1] = w; second[0] = w ; second[1] = w;
			this.contenu[0] = first; this.contenu[1] = second;
		}
		else
		{
			this.eau = false;
			this.mine = M; this.entrepot = E; this.robot = R;
		}
	}
	
	public boolean getEau()
	{
		return this.eau;
	}
	
	public int getCoordMine()
	{
		try
		{
			if(this.mine == null)
				throw new RuntimeException("Il n'y a pas de mine dans ce secteur");
		}catch(Exception e)
		{
			throw e;
		}
		return this.mine.getCoord();
	}
	
	public int getCoordEntrepot()
	{
		try
		{
			if(this.entrepot == null)
				throw new RuntimeException("Il n'y a pas d'entrepot dans ce secteur");
		}catch(Exception e)
		{
			throw e;
		}
		return this.entrepot.getCoord();
	}
	
	public int getCoordRobot()
	{
		try
		{
			if(this.robot == null)
				throw new RuntimeException("Il n'y a pas de robot dans ce secteur");
		}catch(Exception e)
		{
			throw e;
		}
		return this.robot.getCoord();
	}
}
