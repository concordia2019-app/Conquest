/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Random;

import Helper.CountryHelper;
import Helper.PlayerHelper;

public class RandomPlayer extends Player {

	public RandomPlayer(int playerID, String playerName, int[] countryID) {
		super(playerID, playerName, countryID);
	}

	@Override

	public ArrayList<Country> attackPlayer(ArrayList<Country> countryList) {

		return countryList;
	}

	@Override
	public void movePlayer(ArrayList<Country> countryList) {
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Random random = new Random();
		int sourceCountryIndex = random.nextInt((playerCountries.size() - 1));
		Country sourceCountry = playerCountries.get(sourceCountryIndex);
		int armiesNumberToAdd = random.nextInt((sourceCountry.getArmy() - 1));
		CountryHelper countryHelper = new CountryHelper();
		ArrayList<Country> familyCountryAdjacencies = countryHelper.getFamilyCountryAdjacencies(countryList,
				sourceCountry, this);
		int indexForTargetCountry = random.nextInt((familyCountryAdjacencies.size() - 1));
		Country targetCountry = familyCountryAdjacencies.get(indexForTargetCountry);
		ArrayList<Country> updatedCountries = countryHelper.updateCountriesForMove(countryList, sourceCountry,
				targetCountry, armiesNumberToAdd);

		boolean updateSucceed = false;
		while (!updateSucceed) {
			updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountries);
		}
	}

	public ArrayList<Country> reinforcementPlayer(ArrayList<Country> countryList) {
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		Random random = new Random();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		int playerReinforcementCountryIndex = random.nextInt(playerCountries.size() - 1);
		Country playerRandomCountry = playerCountries.get(playerReinforcementCountryIndex);
		if (playerHelper.playerUseCardDecide(playerCards)) {
			ArrayList<Country> updatedCountryList = new ArrayList<Country>();

			for (Country countryItem : countryList) {
				if (countryItem.getCountryID() == playerRandomCountry.getCountryID()) {
					int selectedCountryArmies = playerRandomCountry.getArmy();
					int numberRandomArmies = random.nextInt(this.getReinforcementPlayerArmies());
					this.setReinforcementPlayerArmies((this.getReinforcementPlayerArmies() - numberRandomArmies));
					int armiesToAdd = (selectedCountryArmies + numberRandomArmies);
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
