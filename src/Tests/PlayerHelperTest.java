/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Helper.PlayerHelper;
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
	 * Test method for {@link Helper.PlayerHelper#calculationOfMovement(int, int, int)}.
	 */
//	@Test
 	//public void testCalculationOfMovement() {
		//fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link Helper.PlayerHelper#addCountryIDToPlayer(Model.Player[], int, int)}.
	 */
	@Test
	public void testAddCountryIDToPlayer() {
		
 		Player[] players= new Player[1];
		players[0]=new Player(1,"player1",new int[] {1,2});
		PlayerHelper helper=new PlayerHelper();
		assertEquals( helper.addCountryIDToPlayer(players,3,1)[0].getCountryID()[2],3);
 	}

	/**
	 * Test method for {@link Helper.PlayerHelper#removeCountryIDToPlayer(Model.Player[], int, int)}.
	 */
 	@Test
 	public void testRemoveCountryIDToPlayer() {
 		Player[] players= new Player[1];
		players[0]=new Player(1,"player1",new int[] {1,2});
		PlayerHelper helper=new PlayerHelper();
		assertEquals( helper.removeCountryIDToPlayer(players, 1, 1)[0].getCountryID()[0],2);
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
	//	fail("Not yet implemented");
//	}

}
