/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import Controller.ConquestController;
import Model.Map;
import Model.Player;

/**
 * @author Amirhossein
 *
 */
public class ConquestControllerTest {

	/**
	 * Test method for {@link Controller.ConquestController#playerPercentageCalculation(Model.Player, java.util.ArrayList)}.
	 */
	@Test
	public void testPlayerPercentageCalculation() {
		 Map map = Map.getInstance();
		 map.setCountries();
         Player player=new Player(1,"player1",new int[] {1,2});
         int percentage=(int)ConquestController.getInstance().playerPercentageCalculation(player,map.getCountries());
         assertEquals(percentage,10);
	}

	

}
