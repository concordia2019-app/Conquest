/**
 * 
 */
package Tests.Helper;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Helper.PlayerHelper;
import Model.Card;
import Model.CardType;
import Model.Country;
import Model.Map;
import Model.Player;

/**
 * @author Amirhossein
 *
 */
public class PlayerHelperTest {

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
	 * Test method for
	 * {@link Helper.PlayerHelper#calculationOfMovement(int, int, int)}.
	 */
//	@Test
	// public void testCalculationOfMovement() {
	// fail("Not yet implemented");
//	}

	/**
	 * Test method for
	 * {@link Helper.PlayerHelper#addCountryIDToPlayer(Model.Player[], int, int)}.
	 */
	@Test
	public void testAddCountryIDToPlayer() {

		Player[] players = new Player[1];
		players[0] = new Player(1, "player1", new int[] { 1, 2 });
		PlayerHelper helper = new PlayerHelper();
		assertEquals(helper.addCountryIDToPlayer(players, 3, 1)[0].getCountryID()[2], 3);
	}

	/**
	 * Test method for
	 * {@link Helper.PlayerHelper#removeCountryIDToPlayer(Model.Player[], int, int)}.
	 */
	@Test
	public void testRemoveCountryIDToPlayer() {
		Player[] players = new Player[1];
		players[0] = new Player(1, "player1", new int[] { 1, 2 });
		PlayerHelper helper = new PlayerHelper();
		assertEquals(helper.removeCountryIDToPlayer(players, 1, 1)[0].getCountryID()[0], 2);
	}

	/**
	 * Test method for {@link Helper.PlayerHelper#increaseCountryArmies(int, int)}.
	 */
//	@Test
// void testIncreaseCountryArmies() {
//		//fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link Helper.PlayerHelper#decreaseCountryArmies(int, int)}.
	 */
//	@Test
//	public void testDecreaseCountryArmies() {
	// fail("Not yet implemented");
//	}

	@Test
	public void getPlayerCountriesTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> allCountries = Map.getInstance().getCountries();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(allCountries, 1);
		assertTrue((playerCountries.size() > 0));
	}

	@Test
	public void getWeakerCountryTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> countryList = new ArrayList<Country>();
		Country estimatedWeakerCountry = new Country("Country1", 1, 1, 1, null, 0, null);
		countryList.add(estimatedWeakerCountry);
		countryList.add(new Country("Country2", 2, 2, 10, null, 0, null));
		countryList.add(new Country("Country3", 3, 3, 100, null, 0, null));
		Country weakerCountry = playerHelper.getWeakerCountry(countryList);

		assertEquals(estimatedWeakerCountry, weakerCountry);
	}

	@Test
	public void checkNumberIsInListTrueTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		int[] numberList = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int specificNumber = 4;
		assertTrue(playerHelper.checkNumberIsInList(numberList, specificNumber));
	}

	@Test
	public void checkNumberIsInListFalseTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		int[] numberList = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int specificNumber = 42;
		assertFalse(playerHelper.checkNumberIsInList(numberList, specificNumber));
	}

	@Test
	public void playerUseCardDecideTrueTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = new Card(CardType.ARTILLERY);
		Card card2 = new Card(CardType.ARTILLERY);
		Card card3 = new Card(CardType.ARTILLERY);
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		assertTrue(playerHelper.playerUseCardDecide(cards));
	}

	@Test
	public void playerUseCardDecideTrueMoreThanFiveTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = new Card(CardType.CAVALRY);
		Card card2 = new Card(CardType.ARTILLERY);
		Card card3 = new Card(CardType.INFANTRY);
		Card card4 = new Card(CardType.CAVALRY);
		Card card5 = new Card(CardType.ARTILLERY);
		Card card6 = new Card(CardType.INFANTRY);
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);
		cards.add(card6);
		assertTrue(playerHelper.playerUseCardDecide(cards));
	}

	@Test
	public void playerUseCardDecidealseTest() {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = new Card(CardType.ARTILLERY);
		Card card2 = new Card(CardType.CAVALRY);
		Card card3 = new Card(CardType.ARTILLERY);
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		assertFalse(playerHelper.playerUseCardDecide(cards));
	}
}
