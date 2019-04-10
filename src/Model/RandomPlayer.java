/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Random;
import Controller.ConquestController;
import Helper.CountryHelper;
import Helper.PlayerHelper;
import View.MapView;

/**
 * Implementing Random player behavior
 * 
 * @author FarzadShamriz
 *
 */
public class RandomPlayer extends Player {

	public RandomPlayer(int playerID, String playerName, int[] countryID) {
		super(playerID, playerName, countryID);
	}

	/**
	 * implementing attack of Random player
	 */
	@Override
	public ArrayList<Country> attackPlayer(ArrayList<Country> countryList) {
		// test
		System.out.print("before attack random");
		MapView mapView = new MapView();
		mapView.printMainMap(countryList);
		// test
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Random random = new Random();
		int attackCountryIndex = 0;
		if (playerCountries.size() > 1)
			attackCountryIndex = random.nextInt((playerCountries.size()));
		Country attackCountry = playerCountries.get(attackCountryIndex);
		CountryHelper countryHelper = new CountryHelper();
		ArrayList<Country> enemyCountries = countryHelper.getEnemyAdjacencies(countryList, attackCountry, this);

		if (enemyCountries.size() > 0) {
			int enemyCountryIndex = 0;
			if (enemyCountries.size() > 1)
				enemyCountryIndex = random.nextInt((enemyCountries.size()));
			Country enemyCountry = enemyCountries.get(enemyCountryIndex);

			int attackerArmies = attackCountry.getArmy();
			int enemyArmies = enemyCountry.getArmy();
			if (enemyArmies > 1 && attackerArmies > 1) {
				int attackerRandomArmy = random.nextInt(attackerArmies);
				if (attackerRandomArmy == 0)
					attackerRandomArmy = 1;
				int restOfAttackerRandomArmy = ((attackerRandomArmy));
				attackCountry.setArmy(restOfAttackerRandomArmy);
				AttackResponse attackResponse = ConquestController.getInstance().attackCalculation(attackerRandomArmy,
						enemyArmies);
				enemyCountry.setArmy(attackResponse.getRestOfArmies());
				if (attackResponse.getAttackStatus()) {
					enemyCountry.setPlayer(this.getPlayerID(), this.getPlayerName());
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
		}
		// test
		System.out.print("after attack random");
		mapView = new MapView();
		mapView.printMainMap(countryList);
		// test
		return countryList;
	}

	/**
	 * Implementing Move of Random player
	 */
	@Override
	public void movePlayer(ArrayList<Country> countryList) {
		// test
		System.out.print("before move random");
		MapView mapView = new MapView();
		mapView.printMainMap(countryList);
		// test
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Random random = new Random();
		if (playerCountries.size() > 0) {
			int sourceCountryIndex = random.nextInt((playerCountries.size()));
			Country sourceCountry = playerCountries.get(sourceCountryIndex);
			if (sourceCountry.getArmy() > 0) {
				int armiesNumberToAdd = random.nextInt((sourceCountry.getArmy()));
				CountryHelper countryHelper = new CountryHelper();
				ArrayList<Country> familyCountryAdjacencies = countryHelper.getFamilyCountryAdjacencies(countryList,
						sourceCountry, this);

				if (familyCountryAdjacencies.size() > 0) {
					int indexForTargetCountry = random.nextInt((familyCountryAdjacencies.size()));
					Country targetCountry = familyCountryAdjacencies.get(indexForTargetCountry);
					ArrayList<Country> updatedCountries = countryHelper.updateCountriesForMove(countryList,
							sourceCountry, targetCountry, armiesNumberToAdd);

					boolean updateSucceed = false;
					while (!updateSucceed) {
						updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountries);
					}
				}
			}
		}
		// test
		System.out.print("before move random");
		mapView = new MapView();
		mapView.printMainMap(countryList);
		// test
	}

	/**
	 * Implementing reinforcement of Random player
	 */
	public ArrayList<Country> reinforcementPlayer(ArrayList<Country> countryList) {
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		Random random = new Random();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		int playerReinforcementCountryIndex = random.nextInt(playerCountries.size() - 1);
		Country playerRandomCountry = playerCountries.get(playerReinforcementCountryIndex);
		ArrayList<Country> updatedCountryList = new ArrayList<Country>();
		if (playerHelper.playerUseCardDecide(playerCards)) {

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

		return updatedCountryList;
	}

}
