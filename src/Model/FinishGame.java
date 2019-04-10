package Model;

import java.util.ArrayList;

/**
 * Checking the game is finished or not
 * 
 * @author FarzadShamriz
 *
 */
public class FinishGame {

	/**
	 * If the game is finished then return true
	 * 
	 * @param countries
	 * @return
	 */
	public boolean isGameFinished(ArrayList<Country> countries) {
		int previousCountryPlayerId = 0;
		for (Country country : countries) {
			if (previousCountryPlayerId != 0) {
				if (country.getPlayerID() == previousCountryPlayerId) {
				} else {
					return false;
				}
			} else {
				previousCountryPlayerId = country.getPlayerID();
			}
		}
		return true;
	}
}
