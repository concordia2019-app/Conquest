/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.ConquestController;
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
	 * aggressive attack behaviosr choosing the best country's army and attack by
	 * maximum armies
	 * 
	 */
	public ArrayList<Country> aggressiveAttack() {
		ArrayList<Country> playersCountries = ConquestController.getInstance().getPlayerCountries(this.getPlayerID());
		ArrayList<Country> allCountries = Map.getInstance().getCountries();

		Country maxArmyCountry = playersCountries.get(0);
		// finding max maxArmyCountry from player countries
		for (int i = 1; i < playersCountries.size(); i++) {
			if (playersCountries.get(i).getArmy() > maxArmyCountry.getArmy()) {
				maxArmyCountry = playersCountries.get(i);
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

}