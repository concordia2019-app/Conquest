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
public class CheaterPlayer extends Player {

    public CheaterPlayer(int playerID, String playerName, int[] countryID) {
        super(playerID, playerName, countryID);
}    
	/*
	 * cheater attack behaviosr choosing the best country's army and 
	 * and find the best adjeacent and attack on it
	 * behavior on maximum armies
	 */
	public ArrayList<Country> cheaterAttackPlayer(ArrayList<Country> countryList) {
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
ArrayList<Country> adjacentCountries = ConquestController.getInstance().getAdjacentCountries(maxArmyCountry.getAdjacentCountriesID(),countryList);
 


 if(adjacentCountries.size()<1)
	  adjacentCountries.add(countryList.get(maxArmyCountry.getAdjacentCountriesID()[0]));
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
	 * cheater move behaviosr choosing the best country's army 
	 * and find the best adjeacent and move on it
	 * behavior on maximum armies
	 */
        public void cheaterMovePlayer(ArrayList<Country> countryList) {
             
            	// TEST MOVE
		System.out.print("Teest Move 1");
		MapView mapView = new MapView();
		mapView.printMainMap(countryList);
            	// TEST MOVE
	        PlayerHelper playerHelper = new PlayerHelper();

		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		// finding the best country wich has more  adjaecent Country from player countries

		Country selectedCountry = ConquestController.getInstance(). findTheBestAdjeacentPlayer(playerCountries);

		// finding max adjaecent Country from player countries

		int armiesNumberToAdd = (selectedCountry.getArmy() - 1);  54
		CountryHelper countryHelper = new CountryHelper();
		ArrayList<Country> adjacentCountries = ConquestController.getInstance().getAdjacentCountries( selectedCountry.getAdjacentCountriesID(),countryList);


		 if(adjacentCountries.size()<1)
			  adjacentCountries.add(countryList.get(selectedCountry.getAdjacentCountriesID()[0]));
		Country maxArmy = adjacentCountries.get(0);

		for (int i = 1; i < adjacentCountries.size(); i++) {
			if (adjacentCountries.get(i).getArmy() > maxArmy.getArmy()) {
				maxArmy = adjacentCountries.get(i);
			}
		}

		ArrayList<Country> updatedCountries = countryHelper.updateCountriesForMove(countryList, selectedCountry,
				maxArmy, armiesNumberToAdd);

		boolean updateSucceed = false;
		while (!updateSucceed) {
			updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountries);
		}
                
            	// TEST MOVE
		System.out.print("Test Move 2");
		mapView = new MapView();
		mapView.printMainMap(countryList);
            	// TEST MOVE
            
            
            ///...
	}

	 /*
	 * aggressive reinforcement behaviosr choosing the best country's army 
	 * and find the best country and move on it
	 * behavior on maximum armies
	 */
	public ArrayList<Country> cheaterReinforcementPlayer(ArrayList<Country> countryList) {
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());

		Country playerCountry;
		// finding max maxArmyCountry from player countries
		for (int i = 0; i < playerCountries.size(); i++) {
			 
		 playerCountry = playerCountries.get(i); 
		if (playerHelper.playerUseCardDecide(playerCards)) {

			for (Country countryItem : countryList) {
				if (countryItem.getCountryID() == playerCountry.getCountryID()) {
                                    // doubles the army count
					countryItem.setArmy(countryItem.getArmy() * 2);
				}
			}
                }
 
			boolean updateSucceed = false;
			CountryHelper countryHelper = new CountryHelper();
			while (!updateSucceed) {
				updateSucceed = countryHelper.updateSourceCountriesArmies(countryList);
			} 
                } 
 		return countryList;
	}

    
}