/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConquestController;
import Helper.CountryHelper;
import Helper.PlayerHelper;
import View.MapView;
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
	public ArrayList<Country> aggressiveAttackPlayer(ArrayList<Country> countryList) {
      
		ArrayList<Country> playerCountries = ConquestController.getInstance().getPlayerCountries(this.getPlayerID());
                // TEST MAP
		System.out.print("Test Attack 1");
		MapView mapView = new MapView();
		mapView.printMainMap(countryList);
		// TEST MAP
                CountryHelper countryHelper = new CountryHelper();
                 
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
                 
                // here is the attacker and enemy which max armt 
                // from player is attacker and max adjeacent army is enemy
                        int attackerArmies = maxArmyCountry.getArmy();
			int enemyArmies = maxAdjeacentArmyCountry.getArmy();
 
                         if (enemyArmies > 1 && attackerArmies > 1) {
                              
				maxArmyCountry.setArmy(maxArmyCountry.getArmy() -1);
				AttackResponse attackResponse = ConquestController.getInstance().attackCalculation(maxArmyCountry.getArmy() -1,
						enemyArmies);
				maxAdjeacentArmyCountry.setArmy(attackResponse.getRestOfArmies());
				if (attackResponse.getAttackStatus()) {
					maxAdjeacentArmyCountry.setPlayer(this.getPlayerID(), this.getPlayerName());
					Player[] players = Map.getInstance().getPlayers();
					for (Player playerItem : players) {
						if (playerItem.getPlayerID() == this.getPlayerID()) {
							playerItem.setCountryId(this.getCountryID());
						}
						Map.getInstance().setPlayers(players);
					}
				}

				boolean updateSucceed = false;
				while (!updateSucceed) {
					updateSucceed = countryHelper.updateSourceCountriesArmies(countryList);
				}
			}
                            
                                                
                // test
		System.out.print("Test Attack 2");
		mapView = new MapView();
		mapView.printMainMap(countryList);
                return countryList;
		// test
	}

	/*
	 * aggressive move behaviosr choosing the best country's army and find the best
	 * adjeacent and move on it behavior on maximum armies
	 */
	public void aggressiveMovePlayer(ArrayList<Country> countryList) {
            
            	// TEST MAP
		System.out.print("Test Move 1");
		MapView mapView = new MapView();
		mapView.printMainMap(countryList);
		// TEST MAP
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
                
           	// TEST MAP
		System.out.print("Test Move 2");
		mapView = new MapView();
		mapView.printMainMap(updatedCountries);
		// TEST MAP
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