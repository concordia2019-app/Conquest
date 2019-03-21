package Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Test;
import Model.Country;
import Model.FinishGame;

/**
 * @author Pegah
 *
 */
public class FinishGameTest {
	FinishGame finishGame = new FinishGame();
	@Test
	public void testisGameFinished() {
		int[] adj = {1,2,3};
		Country c = new Country("Iran", 1, 1, 3, adj, 1,"Pegah");
		Country c1 = new Country("Iraq", 1, 1, 3, adj, 1,"Pegah");
		ArrayList<Country> countries = new ArrayList<Country>();
		countries.add(c);
		countries.add(c1);
		boolean b = finishGame.isGameFinished(countries);
		assertTrue(b);
	}

}
