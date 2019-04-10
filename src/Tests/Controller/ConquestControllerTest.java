/**
 * 
 */
package Tests.Controller;

import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;

import Controller.ConquestController;
import Model.AttackResponse;
import Model.Country;
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
		ArrayList<Country> countries = new ArrayList<Country>();
		countries.add(new Country("Norway", 1, 1, 0, new int[] { 2, 3, 20 }, 1, "Player 1"));
		countries.add(new Country("Denmark", 2, 1, 0, new int[] { 1, 3, 4 }, 1, "Player 1"));
		countries.add(new Country("Sweden", 3, 1, 0, new int[] { 1, 2, 14 }, 1, "Player 1"));
		Player player = new Player(1, "player1", new int[] { 1, 2, 3 });
		int percentage = (int) ConquestController.getInstance().playerPercentageCalculation(player, countries);
		assertEquals(percentage, 100);
	}

	@Test
	public void attackCalculationTest() {
		ConquestController conquestController = ConquestController.getInstance();
		AttackResponse attackResponse = conquestController.attackCalculation(1000, 1);
		assertTrue(attackResponse.getAttackStatus());
	}

	@Test
	public void setCardToPlayerTest() {
		ConquestController conquestController = ConquestController.getInstance();
		Player playerForUpdate = new Player(1, "TestPlayer", null);
		Player assignedCardPlayer = conquestController.setCardToPlayer(playerForUpdate);

		assertTrue(assignedCardPlayer.getCards().size() > 0);
	}

}
