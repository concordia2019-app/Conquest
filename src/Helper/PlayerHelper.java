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

	public boolean calculationOfMovement(int fromCountryId, int toCountryId, int armyNumbToMove) {
		UIHelper uiHelper = new UIHelper();
		Map map = new Map();
		ArrayList<Country> countries = map.getCountries();
		Country sourceCountry = uiHelper.getCountryById(countries, fromCountryId);
		int sourceCountryArmies = sourceCountry.getArmy();
		Country targegCountry = uiHelper.getCountryById(countries, toCountryId);
		int targetCountryArmies = targegCountry.getArmy();

		if ((sourceCountryArmies - 1) < armyNumbToMove) {
			return false;
		} else {
			decreaseCountryArmies(fromCountryId, armyNumbToMove);
			increaseCountryArmies(toCountryId, armyNumbToMove);
			return true;
		}

	}

	public void increaseCountryArmies(int Cid, int armiesNumber) {
		Map map = new Map();
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

	public void decreaseCountryArmies(int Cid, int armiesNumber) {
		Map map = new Map();
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

}
