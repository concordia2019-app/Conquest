/**
 * 
 */
package Tests.Helper;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Helper.UIHelper;
import Model.Country;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

/**
 * @author Amirhossein
 *
 */
public class UIHelperTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testTryParseInt() {
		UIHelper helper = new UIHelper();
		assertEquals(helper.tryParseInt("1"), true);
	}

	/**
	 * Test method for
	 * {@link Helper.UIHelper#getCountryById(java.util.ArrayList, java.lang.Integer)}.
	 */
	@Test
	public void testGetCountryById() {
		UIHelper helper = new UIHelper();
		Map map = Map.getInstance();
		map.setCountries();
		assertEquals(helper.getCountryById(map.getCountries(), 1), map.getCountries().get(0));
	}

	/**
	 * 111 * Test method for {@link Helper.UIHelper#isIdExistInList(int[], int)}.
	 */
	@Test
	public void testIsIdExistInList() {
		UIHelper helper = new UIHelper();
		assertEquals(helper.isIdExistInList(new int[] { 1, 2, 3 }, 1), true);
	}

	/**
	 * Test method for
	 * {@link Helper.UIHelper#addArmiesToCountryById(int, java.util.ArrayList, int)}.
	 */
	@Test
	public void testAddArmiesToCountryById() {
		UIHelper helper = new UIHelper();
		Map map = Map.getInstance();
		map.setCountries();
		assertEquals(helper.addArmiesToCountryById(1, map.getCountries(), 1), true);
	}

	public void getPlayerByIdTest() {
		UIHelper uiHelper = new UIHelper();
		Player[] players = new Player[2];
		Player player = new Player(1, "Player1", null);
		players[0] = player;
		players[1] = new Player(2, "Player2", null);
		Map.getInstance().setPlayers(players);
		Player playerResult = uiHelper.getPlayerById(1);
		assertTrue(playerResult.getPlayerID() == 1);
	}
}
