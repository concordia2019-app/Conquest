/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

import Controller.CardController;
import Controller.ConquestController;
import Helper.CountryHelper;
import Helper.PlayerHelper;
import View.MapView;

/**
 * This class is created for benovolent player type
 * 
 * @author FarzadShamriz
 *
 */
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

	/**
	 * Move behaviour for benovolent player
	 */
	@Override
	public void movePlayer(ArrayList<Country> countryList) {
		// test
		MapView mapView = new MapView();
		System.out.print("befor move benevolent");
		mapView.printMainMap(countryList);
		// test
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Country playerWeakerCountry = playerHelper.getWeakerCountry(playerCountries);
		Country playerSourceCountryForMoving = playerHelper.getFamilyAdjacentWithMoreThanOneArmy(playerCountries,
				playerWeakerCountry);
		if (playerSourceCountryForMoving != null) {
			ArrayList<Country> updatedCountryList = playerHelper.moveCalculationForBenovolentPlayer(countryList,
					playerSourceCountryForMoving, playerWeakerCountry);
			CountryHelper countryHelper = new CountryHelper();
			boolean updateSucceed = false;
			while (!updateSucceed) {
				updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountryList);
			}
		}
		// test
		System.out.print("after move benovolent");
		mapView.printMainMap(countryList);
		// test
	}

	/**
	 * reinforcement of belonovent player
	 */
	@Override
	public ArrayList<Country> reinforcementPlayer(ArrayList<Country> countryList) {
		// test
		MapView mapView = new MapView();
		System.out.print("befor rinforcement benovolent");
		mapView.printMainMap(countryList);
		// test
		ArrayList<Card> playerCards = this.getCards();
		PlayerHelper playerHelper = new PlayerHelper();
		ArrayList<Country> playerCountries = playerHelper.getPlayerCountries(countryList, this.getPlayerID());
		Country playerWeakerCountry = playerHelper.getWeakerCountry(playerCountries);
		CardController cardController = new CardController();
		ArrayList<Country> updatedCountryList = new ArrayList<Country>();
		for (Country countryItem : countryList) {
			if (countryItem.getCountryID() == playerWeakerCountry.getCountryID()) {
				int weakerCountryArmies = countryItem.getArmy();
				int armiesToAdd = (weakerCountryArmies + 3);
				this.setReinforcementPlayerArmies(0);
				countryItem.setArmy(armiesToAdd);
			}
			updatedCountryList.add(countryItem);
		}

		if (playerHelper.playerUseCardDecide(playerCards)) {
			updatedCountryList = new ArrayList<Country>();
			for (Country countryItem : updatedCountryList) {
				if (countryItem.getCountryID() == playerWeakerCountry.getCountryID()) {
					int weakerCountryArmies = countryItem.getArmy();
					int armiesToAdd = (weakerCountryArmies + (cardController.calculateArmiesCount(playerCards)));
					this.setReinforcementPlayerArmies(0);
					countryItem.setArmy(armiesToAdd);
				}
				updatedCountryList.add(countryItem);
			}
		}
		boolean updateSucceed = false;
		CountryHelper countryHelper = new CountryHelper();
		while (!updateSucceed) {
			updateSucceed = countryHelper.updateSourceCountriesArmies(updatedCountryList);
		}

		// test
		System.out.print("after rinforcement benovolent");
		mapView.printMainMap(countryList);
		// test
		return countryList;
	}

}
