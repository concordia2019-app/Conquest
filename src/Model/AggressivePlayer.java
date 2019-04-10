/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConquestController;
import Helper.CountryHelper;
import Helper.PlayerHelper;
import java.util.ArrayList;

/**
 *
 * @author Amirhossein
 */
public class AggressivePlayer extends Player {

	public AggressivePlayer(int playerID, String playerName, int[] countryID) {
		super(playerID, playerName, countryID);
	}

	/*
	 * aggressive attack behaviosr choosing the best country's army and and find the
	 * best adjeacent and attack on it behavior on maximum armies
	 */
	public ArrayList<Country> aggressiveAttackPlayer() {
		ArrayList<Country> playerCountries = ConquestController.getInstance().getPlayerCountries(this.getPlayerID());
		ArrayList<Country> allCountries = Map.getInstance().getCountries();

		Country maxArmyCountry = playerCountries.get(0);
		// finding max maxArmyCountry from player countries
		for (int i = 1; i < playerCountries.size(); i++) {
			if (playerCountries.get(i).getArmy() > maxArmyCountry.getArmy()) {
				maxArmyCountry = playerCountries.get(i);
			}
		}
		ArrayList<Country> adjacentCountries = ConquestController.getInstance()
				.getAdjacentCountries(maxArmyCountry.getAdjacentCountriesID());
		// attacking adjacentCountries from player countries
		Country maxAdjeacentArmyCountry = adjacentCountries.get(0);

		for (int i = 1; i < adjacentCountries.size(); i++) {
			if (adjacentCountries.get(i).getArmy() > maxAdjeacentArmyCountry.getArmy()) {
				maxAdjeacentArmyCountry = adjacentCountries.get(i);
			}
		}
		for (int i = 1; i < allCountries.size(); i++) {
			if (allCountries.get(i).getCountryID() == maxAdjeacentArmyCountry.getCountryID()) {
				allCountries.get(i).setArmy((allCountries.get(i).getArmy() - maxArmyCountry.getArmy()));
				// TODO : checking country status and if player wins or not
				// => GoTo ConquestController.attackCalculation
			}
		}
		return allCountries;
	}

	/*
	 * aggressive move behaviosr choosing the best country's army and find the best
	 * adjeacent and move on it behavior on maximum armies
	 */
	public void aggressiveMovePlayer(ArrayList<Country> countryList) {
		PlayerHelper playerHelper = new PlayerHelper();

		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());

		Country maxArmyCountry = playerCountries.get(0);
		// finding max maxArmyCountry from player countries
		for (int i = 1; i < playerCountries.size(); i++) {
			if (playerCountries.get(i).getArmy() > maxArmyCountry.getArmy()) {
				maxArmyCountry = playerCountries.get(i);
			}
		}

		// finding max adjaecent Country from player countries

		int armiesNumberToAdd = (maxArmyCountry.getArmy() - 1); // QUESTION : why should be ( -1 ) and does it check the
																// exceptions
		CountryHelper countryHelper = new CountryHelper();
		ArrayList<Country> adjacentCountries = countryHelper.getFamilyCountryAdjacencies(countryList, maxArmyCountry,
				this);

		Country maxAdjeacentArmyCountry = adjacentCountries.get(0);

		for (int i = 1; i < adjacentCountries.size(); i++) {
			if (adjacentCountries.get(i).getArmy() > maxAdjeacentArmyCountry.getArmy()) {
				maxAdjeacentArmyCountry = adjacentCountries.get(i);
			}
		}

		ArrayList<Country> updatedCountries = countryHelper.updateCountriesForMove(countryList, maxArmyCountry,
				maxAdjeacentArmyCountry, armiesNumberToAdd);

		boolean updateSucceed = false;
		while (!updateSucceed) {
			updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountries);
		}
	}

	/*
	 * aggressive reinforcement behaviosr choosing the best country's army and find
	 * the best country and move on it behavior on maximum armies
	 */
	public ArrayList<Country> aggressiveReinforcementPlayer(ArrayList<Country> countryList) {
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());

		Country maxArmyCountry = playerCountries.get(0);
		// finding max maxArmyCountry from player countries
		for (int i = 1; i < playerCountries.size(); i++) {
			if (playerCountries.get(i).getArmy() > maxArmyCountry.getArmy()) {
				maxArmyCountry = playerCountries.get(i);
			}
		}
		ArrayList<Country> updatedCountryList = new ArrayList<Country>();

		if (playerHelper.playerUseCardDecide(playerCards)) {

			for (Country countryItem : countryList) {
				if (countryItem.getCountryID() == maxArmyCountry.getCountryID()) {
					int selectedCountryArmies = maxArmyCountry.getArmy();
					int numberRandomArmies = this.getReinforcementPlayerArmies();
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

 		return updatedCountryList;
	}

         
}