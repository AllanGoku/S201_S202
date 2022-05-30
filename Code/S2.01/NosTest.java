package application;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import junit.framework.TestCase;

class NosTest extends TestCase 
{
	protected static Monde world;
	static Secteur secteur;
	protected static Robot robot1;
	protected static Robot robot2;
	protected static Mine mine1;
	protected static Mine mine2;

	@BeforeAll
	static void initAll() 
	{
		world = new Monde();
		secteur = new Secteur(0, 0, world);
	}

	@AfterAll
	static void reinitAll() 
	{
		world = null;
		mine1 = null;
		robot1 = null;
		mine2 = null;
		robot2 = null;
		secteur.setMine(null);
		secteur.setRobot(null);
	}

	@Test
	void testExtractMineraisN1() 
	{
		mine1 = new Mine(1, "OR", 10, secteur);
		mine1.setCap(0);
		robot1 = new Robot(1, "OR", 2, secteur, 3);
		secteur.setMine(mine1);
		secteur.setRobot(robot1);
		assertThrows(ExceptionContenu.class, () -> {
			robot1.extract_minerais();
		});
	}
	
	@Test
	void testExtractMineraisN2() 
	{
		robot1 = new Robot(1,"OR",2,secteur,3);
		secteur.setRobot(robot1);
		assertThrows(ExceptionContenu.class, () -> {
			robot1.extract_minerais();
		});
	}
	
	@Test
	void testExtractMineraisN3() throws ExceptionContenu 
	{
		mine1 = new Mine(1, "NI",10, secteur);
		mine1.setCap(2);
		robot1 = new Robot(1,"NI",2,secteur,5);
		secteur.setMine(mine1);
		secteur.setRobot(robot1);
		assertTrue((robot1.extract_minerais() == true));
		}
	
	@Test
	void testSetTypeManquantN1() 
	{
		world = new Monde();
		mine1 = new Mine(1, "OR",10,secteur);
		mine2 = new Mine(2,"OR",10,secteur);
		robot1 = new Robot(1,"OR",2,secteur,3);
		robot2 = new Robot(2,"OR",2,secteur,3);
		
		world.addMine(mine1);
		world.addMine(mine2);
		world.addRobot(robot1);
		world.addRobot(robot2);
		
		world.setTypeManquant();
		
		assertTrue((robot1.getTypeMinerai() == "OR" & robot2.getTypeMinerai()=="NI")||(robot2.getTypeMinerai() == "OR" & robot1.getTypeMinerai()=="NI"));
		assertTrue((mine1.getTypeMinerai() == "OR" & mine2.getTypeMinerai()=="NI")||(mine2.getTypeMinerai() == "OR" & mine1.getTypeMinerai()=="NI"));
	}
	
	@Test
	void testSetTypeManquantN2() 
	{
		world = new Monde();
		mine1 = new Mine(1, "NI",10,secteur);
		mine2 = new Mine(2,"NI",10,secteur);
		robot1 = new Robot(1,"NI",2,secteur,3);
		robot2 = new Robot(2,"NI",2,secteur,3);
		
		world.addMine(mine1);
		world.addMine(mine2);
		world.addRobot(robot1);
		world.addRobot(robot2);
		
		world.setTypeManquant();
		
		assertTrue((robot1.getTypeMinerai() == "OR" & robot2.getTypeMinerai()=="NI")||(robot2.getTypeMinerai() == "OR" & robot1.getTypeMinerai()=="NI"));
		assertTrue((mine1.getTypeMinerai() == "OR" & mine2.getTypeMinerai()=="NI")||(mine2.getTypeMinerai() == "OR" & mine1.getTypeMinerai()=="NI"));
	}
}
