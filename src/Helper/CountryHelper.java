package Helper;

import java.util.ArrayList;

import Model.Country;
import Model.Map;
import Model.Player;

/**
 * This class is working for calculating or checking something for countries.
 * 
 * @author FarzadShamriz
 *
 */
public class CountryHelper {

	/**
	 * Updating the countries details
	 * 
	 * @param countriesForUpdate
	 */
	public boolean updateSourceCountriesArmies(ArrayList<Country> countriesForUpdate) {
		Map map = Map.getInstance();
		ArrayList<Country> sourceCountries = map.getCountries();
		map.setCountries(countriesForUpdate);
		boolean isCountriesUpToDate = isTheCountryListUpToDate(sourceCountries, countriesForUpdate);
		if (!isCountriesUpToDate) {
			map.setCountries(countriesForUpdate);
		}
		sourceCountries = map.getCountries();
		isCountriesUpToDate = isTheCountryListUpToDate(sourceCountries, countriesForUpdate);
		return isCountriesUpToDate;
	}

	/**
	 * Check the countries are up to date
	 * 
	 * @param sourceList
	 * @param targetList
	 * @return
	 */
	public boolean isTheCountryListUpToDate(ArrayList<Country> sourceList, ArrayList<Country> targetList) {
		boolean syncCountriesResult = true;
		for (Country sourceCountry : sourceList) {
			for (Country targetCountry : targetList) {
				int sourceCountryId = sourceCountry.getCountryID();
				int targetCountryId = targetCountry.getCountryID();
				if (sourceCountryId == targetCountryId) {
					if (!isCountryAttributesValueTheSame(sourceCountry, targetCountry)) {
						syncCountriesResult = false;
					}
				}
			}
		}
		return syncCountriesResult;
	}

	/**
	 * check the attributes if countries are up to date
	 * 
	 * @param sourceCountry
	 * @param targetCountry
	 * @return
	 */
	public boolean isCountryAttributesValueTheSame(Country sourceCountry, Country targetCountry) {
		// get All source Country attributes
		String sourceCountryName = sourceCountry.getCountryName();
		int sourceCountryContinentId = sourceCountry.getContinentID();
		int sourceCountryArmies = sourceCountry.getArmy();
		int[] sourceCountryAdjacencies = sourceCountry.getAdjacentCountriesID();

		// get All target Country attributes
		String targetCountryName = sourceCountry.getCountryName();
		int targetCountryContinentId = sourceCountry.getContinentID();
		int targetCountryArmies = sourceCountry.getArmy();
		int[] targetCountryAdjacencies = sourceCountry.getAdjacentCountriesID();

		if ((sourceCountryArmies != targetCountryArmies) || (sourceCountryContinentId != targetCountryContinentId)
				|| (sourceCountryName != targetCountryName)
				|| (!isCountryAdjacenciesTheSame(sourceCountryAdjacencies, targetCountryAdjacencies))) {
			return false;
		}
		return true;

	}

	/**
	 * check the adjacancies of country is up to date
	 * 
	 * @param sourceAdjacencies
	 * @param targetAdjacencies
	 * @return
	 */
	public boolean isCountryAdjacenciesTheSame(int[] sourceAdjacencies, int[] targetAdjacencies) {
		boolean countryAdjacenciesSyncStatus = false;
		if (sourceAdjacencies == null && targetAdjacencies == null)
			return true;
		for (int i = 0; i < sourceAdjacencies.length; i++) {
			for (int j = 0; j < targetAdjacencies.length; j++) {
				if (sourceAdjacencies[i] == targetAdjacencies[j]) {
					countryAdjacenciesSyncStatus = true;
					break;
				}
			}
			if (!countryAdjacenciesSyncStatus) {
				return false;
			}
			countryAdjacenciesSyncStatus = false;
		}
		return true;
	}

	public ArrayList<Country> getFamilyCountryAdjacencies(ArrayList<Country> countryList, Country country,
			Player player) {

		ArrayList<Country> familyCountriesAdjacencies = new ArrayList<Country>();

		int[] adjacenciesIds = country.getAdjacentCountriesID();
		for (Country countryItem : countryList) {
			for (int i = 0; i < countryList.size(); i++) {
				if (adjacenciesIds[i] == countryItem.getCountryID()) {
					familyCountriesAdjacencies.add(countryItem);
					break;
				}
			}
		}
		return familyCountriesAdjacencies;
	}

	public boolean updateCountryArmiesByObject(Country countryForUpdate) {
		Map map = Map.getInstance();
		ArrayList<Country> countryList = map.getCountries();
		ArrayList<Country> updatedCountries = new ArrayList<Country>();
		for (Country countryItem : countryList) {
			if (countryItem.getCountryID() == countryForUpdate.getCountryID()) {
				countryItem.setArmy(countryForUpdate.getArmy());
			}
			updatedCountries.add(countryItem);
		}
		map.setCountries(updatedCountries);
		countryList = map.getCountries();
		if (isTheCountryListUpToDate(countryList, updatedCountries)) {
			return true;
		}
		return false;
	}

	public boolean updateCountryPlayerIdByObject(Country countryForUpdate) {
		Map map = Map.getInstance();
		ArrayList<Country> countryList = map.getCountries();
		ArrayList<Country> updatedCountries = new ArrayList<Country>();
		for (Country countryItem : countryList) {
			if (countryItem.getCountryID() == countryForUpdate.getCountryID()) {
				countryItem.setPlayer(countryForUpdate.getPlayerID(), countryForUpdate.getPlayerName());
			}
			updatedCountries.add(countryItem);
		}
		map.setCountries(updatedCountries);
		countryList = map.getCountries();
		if (isTheCountryListUpToDate(countryList, updatedCountries)) {
			return true;
		}
		return false;
	}

	public ArrayList<Country> updateCountriesForMove(ArrayList<Country> countryList, Country sourceCountry,
			Country targetCountry, int numberOfArmiesToMove) {
		int updatedNumberOfArmiesForSourceCountry = (sourceCountry.getArmy() - numberOfArmiesToMove);
		int updatedNumberOfArmiesForTargetCountry = (targetCountry.getArmy() + numberOfArmiesToMove);
		for (Country countryItem : countryList) {
			if (countryItem.getCountryID() == sourceCountry.getCountryID())
				countryItem.setArmy(updatedNumberOfArmiesForSourceCountry);
			if (countryItem.getCountryID() == targetCountry.getCountryID())
				countryItem.setArmy(updatedNumberOfArmiesForTargetCountry);
		}
		return countryList;
	}
}
