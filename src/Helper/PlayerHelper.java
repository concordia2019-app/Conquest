package Helper;

import java.util.ArrayList;

import Model.Country;
import Model.Editor;
import Model.Map;

/**
 * This class is created for most of calculations and processing of Player
 * class.
 * 
 * @author FarzadShamriz
 *
 */
public class PlayerHelper {

	/**
	 * This method is calculating number of armies for increase and decrease form and to a country
	 * @param fromCountryId ID of source country
	 * @param toCountryId ID of target country
	 * @param armyNumbToMove number of armies to decrease or increase
	 * @return
	 */
	public boolean calculationOfMovement(int fromCountryId, int toCountryId, int armyNumbToMove) {
		UIHelper uiHelper = new UIHelper();
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		Country sourceCountry = uiHelper.getCountryById(countries, fromCountryId);
		int sourceCountryArmies = sourceCountry.getArmy();
		Country targegCountry = uiHelper.getCountryById(countries, toCountryId);

		if ((sourceCountryArmies - 1) < armyNumbToMove) {
			return false;
		} else {
			decreaseCountryArmies(fromCountryId, armyNumbToMove);
			increaseCountryArmies(toCountryId, armyNumbToMove);
			return true;
		}

	}

	/**
	 * This method increase armies of country which retrieved by Cid (Country ID)
	 * @param Cid Country ID
	 * @param armiesNumber Number of Armies for increasing
	 */
	public void increaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies + armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	/**
	 * This method decrease armies of country which retrieved by Cid (Country ID)
	 * @param Cid Country ID
	 * @param armiesNumber Number of Armies for decreasing
	 */
	public void decreaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies - armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

}
