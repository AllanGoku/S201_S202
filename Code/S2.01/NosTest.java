import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;

class NosTest extends TestCase {
	protected Monde world;
	static Secteur secteur;
	protected Robot robot1;
	protected Robot robot2;
	protected Mine mine1;
	protected Mine mine2;

	@BeforeEach
	void init() {
		world = new Monde();
		secteur = new Secteur(0, 0, world);
	}

	@BeforeAll
	static void initPourTest1() {
		Mine mine1 = new Mine(1, "OR", 0, secteur);
		Robot robot1 = new Robot(1, "OR", 2, secteur, 3);
	}

	@Test
	void testExtractMineraisN1() {
		assertThrows(Exception.class, () -> {
			robot1.extract_minerais();
		});
	}
}
