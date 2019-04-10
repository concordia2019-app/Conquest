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
		return countryList;
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
                 
                Country maxArmyCountry = playerCountries.get(0);
		// finding max maxArmyCountry from player countries
		for (int i = 1; i < playerCountries.size(); i++) {
			if (playerCountries.get(i).getArmy() > maxArmyCountry.getArmy()) {
				maxArmyCountry = playerCountries.get(i);
			}
		}
                
                // finding max adjaecent Country from player countries

 		int armiesNumberToAdd =  (maxArmyCountry.getArmy() - 1);   //  QUESTION : why should be ( -1 ) and does it check the exceptions
		CountryHelper countryHelper = new CountryHelper();
		ArrayList<Country> adjacentCountries = countryHelper.getFamilyCountryAdjacencies(countryList,maxArmyCountry, this);

                
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
					int selectedCountryArmies = playerCountry.getArmy() * 2;
					int numberRandomArmies = this.getReinforcementPlayerArmies();
					this.setReinforcementPlayerArmies((this.getReinforcementPlayerArmies() - numberRandomArmies));
					int armiesToAdd = (selectedCountryArmies + numberRandomArmies);
					countryItem.setArmy(armiesToAdd);
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