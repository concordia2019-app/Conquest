package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Date: Feb 15-2019
 * <p>
 * This class is for initializing the country class and putting the map values
 * into it.
 * 
 * @author AHasheminezhad
 */

public class Editor {
	public Player[] Players;

	public ArrayList<Country> setCountries(ArrayList<Country> country) {
		
		country.add(new Country("Norway", 1, 1, 2, new int[] { 2, 3, 20 }, 1, "Player 1"));
		country.add(new Country("Denmark", 2, 1, 2, new int[] { 1, 3, 4 }, 1, "Player 1"));
		country.add(new Country("Sweden", 3, 1, 2, new int[] { 1, 2, 14 }, 1, "Player 1"));
		country.add(new Country("Germany", 4, 2, 2, new int[] { 2, 5, 7, 8, 9, 10, 14, 16 }, 2, "Player 2"));
		country.add(new Country("Netherlands", 5, 2, 2, new int[] { 4, 6, 7 }, 2, "Player 2"));
		country.add(new Country("UK", 6, 2, 2, new int[] { 5, 7, 9 }, 2, "Player 2"));
		country.add(new Country("Belgium", 7, 2, 2, new int[] { 4, 5, 6, 8, 9 }, 2, "Player 2"));
		country.add(new Country("Luxemburg", 8, 2, 2, new int[] { 4, 7, 9 }, 2, "Player 2"));
		country.add(new Country("France", 9, 2, 2, new int[] { 4, 6, 7, 8, 10, 11, 12, 18 }, 2, "Player 2"));
		country.add(new Country("Switzerland", 10, 2, 2, new int[] { 4, 9, 11, 16 }, 2, "Player 2"));
		country.add(new Country("Italy", 11, 2, 2, new int[] { 9, 10, 16 }, 2, "Player 2"));
		country.add(new Country("Spain", 12, 3, 2, new int[] { 9, 13 }, 3, "Player 3"));
		country.add(new Country("Portugal", 13, 3, 2, new int[] { 12 }, 3, "Player 3"));
		country.add(new Country("Poland", 14, 4, 2, new int[] { 3, 4, 15, 18 }, 4, "Player 4"));
		country.add(new Country("Czechoslovakia", 15, 4, 2, new int[] { 14, 16, 17, 18 }, 4, "Player 4"));
		country.add(new Country("Austria", 16, 4, 2, new int[] { 4, 10, 11, 15, 17, 19 }, 4, "Player 4"));
		country.add(new Country("Hungary", 17, 4, 2, new int[] { 15, 16, 18, 19 }, 4, "Player 4"));
		country.add(new Country("Romania", 18, 4, 2, new int[] { 9, 14, 15, 17, 19 }, 4, "Player 4"));
		country.add(new Country("Yugoslavia", 19, 4, 2, new int[] { 16, 17, 18, 20 }, 4, "Player 4"));
		country.add(new Country("Albania", 20, 4, 2, new int[] { 1, 19 }, 4, "Player 4"));
		
		return country;
	}

	public void assigningPlayerCountries(ArrayList<String> playerNames, int playerCount) {
		Players = new Player[playerCount];
		ArrayList<Country> countryTempList = country;
		Random random = new Random();
		int counter = countryTempList.size() / playerCount;
		int restOfCountries = countryTempList.size() % playerCount;
		for (int i = 0; i < playerCount; i++) {
			int[] countriesIds = new int[counter];
			for (int j = 0; j < counter; j++) {
				int randomCountryId = random.nextInt(countryTempList.size());
				countriesIds[j] = countryTempList.get(randomCountryId).getCountryID();
				countryTempList.remove(randomCountryId);
				// updateCountryById(i, countriesIds[j]);
			}
			Players[i] = new Player(i, playerNames.get(i), countriesIds);
		}
		if (restOfCountries > 0) {
			for (Player rechargePlayer : Players) {
				int randomCountryId = random.nextInt(countryTempList.size());
				int playerCountryCount = rechargePlayer.getCountryID().length + 1;
				int[] currentPlayerCountries = rechargePlayer.getCountryID();
				int[] rechargedPlayerCountriesList = new int[playerCountryCount];
				for (int i = 0; i < playerCountryCount - 1; i++) {
					rechargedPlayerCountriesList[i] = currentPlayerCountries[i];
				}
				int newCountryIdForPlayer = countryTempList.get(randomCountryId).getCountryID();
				rechargedPlayerCountriesList[playerCountryCount - 1] = newCountryIdForPlayer;
				rechargePlayer.setCountryId(rechargedPlayerCountriesList);
				countryTempList.remove(randomCountryId);
				updateCountryById(rechargePlayer.getPlayerID(), newCountryIdForPlayer);
				if (countryTempList.size() == 0) {
					break;
				}
			}
		}
	}

	private void updateCountryById(int playerId, int countryId) {
		for (Country countryItem : country) {
			int countryItemId = countryItem.getCountryID();
			if (countryItemId == countryId)
				countryItem.setPlayerId(playerId);
		}
	}

}