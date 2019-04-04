/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import Helper.CountryHelper;
import Helper.PlayerHelper;

public class BenovolentPlayer extends Player {

	public BenovolentPlayer(int playerID, String playerName, int[] countryID) {
		super(playerID, playerName, countryID);
	}

	@Override
	/**
	 * This type of player never attack
	 */
	public ArrayList<Country> attackPlayer(ArrayList<Country> countryList) {
		Map map = Map.getInstance();
		return map.getCountries();
	}

	@Override
	public void movePlayer(ArrayList<Country> countryList) {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Country playerWeakerCountry = playerHelper.getWeakerCountry(playerCountries);
		Country playerSourceCountryForMoving = playerHelper.getFamilyAdjacentWithMoreThanOneArmy(playerCountries,
				playerWeakerCountry);
		if (playerSourceCountryForMoving != null) {
			ArrayList<Country> updatedCountryList = moveCalculationForBenovolentPlayer(countryList,
					playerSourceCountryForMoving, playerWeakerCountry);
			CountryHelper countryHelper = new CountryHelper();
			boolean updateSucceed = false;
			while (!updateSucceed) {
				updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountryList);
			}
		}
	}

	public ArrayList<Country> moveCalculationForBenovolentPlayer(ArrayList<Country> countryList,
			Country sourcePlayerCountry, Country destinationPlayerCountry) {
		int destinationArmies = destinationPlayerCountry.getArmy();
		int sourceArmies = sourcePlayerCountry.getArmy();
		int armiesToAdd = ((sourceArmies - destinationArmies) + destinationArmies);
		int restOfArmiesForSource = destinationArmies;
		destinationPlayerCountry.setArmy((armiesToAdd));
		sourcePlayerCountry.setArmy(restOfArmiesForSource);

		for (Country countryItem : countryList) {
			if (countryItem.getCountryID() == sourcePlayerCountry.getCountryID())
				countryItem.setArmy(sourcePlayerCountry.getArmy());
			if (countryItem.getCountryID() == destinationPlayerCountry.getCountryID())
				countryItem.setArmy(destinationPlayerCountry.getArmy());
		}
		return countryList;
	}

	public ArrayList<Country> reinforcementPlayer(ArrayList<Country> countryList) {
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Country playerWeakerCountry = playerHelper.getWeakerCountry(playerCountries);

		if (playerHelper.playerUseCardDecide(playerCards)) {
			ArrayList<Country> updatedCountryList = new ArrayList<Country>();
			for (Country countryItem : countryList) {
				if (countryItem.getCountryID() == playerWeakerCountry.getCountryID()) {
					int weakerCountryArmies = playerWeakerCountry.getArmy();
					int armiesToAdd = (weakerCountryArmies + this.getReinforcementPlayerArmies());
					countryItem.setArmy(armiesToAdd);
				}
				updatedCountryList.add(countryItem);
			}

			boolean updateSucceed = false;
			CountryHelper countryHelper = new CountryHelper();
			while (!updateSucceed) {
				updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountryList);
			}
		}

		return countryList;
	}

}
