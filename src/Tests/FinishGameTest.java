package Tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import Controller.ConquestController;
import Model.Map;
import Model.Player;

/**
 * @author Pegah
 *
 */
public class FinishGameTest {
	ConquestController conquestController = ConquestController.getInstance();

	@Test
	public void testisGameFinished() {
		Map map = Map.getInstance();
		Player[] players = new Player[] { new Player(0, "Test1", new int[] { 1, 2, 3 }),
				new Player(0, "Test2", new int[] {}), new Player(0, "Test3", new int[] {}),
				new Player(0, "Test4", new int[] {}), new Player(0, "Test5", new int[] {}) };
		map.setPlayers(players);
		boolean finishResult = conquestController.isGameFinish();
		assertTrue(finishResult);
	}

}
