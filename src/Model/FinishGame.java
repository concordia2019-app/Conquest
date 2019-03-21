package Model;
import java.util.ArrayList;

public class FinishGame {

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
