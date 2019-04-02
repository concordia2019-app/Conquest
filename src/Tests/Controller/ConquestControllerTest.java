/**
 * 
 */
package Tests.Controller;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;

import Controller.ConquestController;
import Model.AttackResponse;
import Model.Editor;
import Model.Map;
import Model.Player;

/**
 * @author Amirhossein
 *
 */
public class ConquestControllerTest {
	Editor editor;
	Map map;

	@Before
	public void createContext() {
		editor = new Editor();
		map = Map.getInstance();
		editor.setCountries(map.CountriesList);
	}

	/**
	 * Test method for
	 * {@link Controller.ConquestController#playerPercentageCalculation(Model.Player, java.util.ArrayList)}.
	 */
	@Test
	public void testPlayerPercentageCalculation() {
		editor = new Editor();
		map = Map.getInstance();
		editor.resetCountries(map.CountriesList);
		Player player = new Player(1, "player1", new int[] { 1, 2 });
		int percentage = (int) ConquestController.getInstance().playerPercentageCalculation(player, map.getCountries());
		assertEquals(percentage, 10);
	}

	@Test
	public void attackCalculationTest() {
		ConquestController conquestController = ConquestController.getInstance();
		AttackResponse attackResponse = conquestController.attackCalculation(1000, 1);
		assertTrue(attackResponse.getAttackStatus());
	}

}
