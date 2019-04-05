package Helper;

import java.util.ArrayList;

import Model.Card;
import Model.Country;
import Model.Editor;
import Model.Map;
import Model.Player;

/**
 * This class is created for most of calculations and processing of Player
 * class.
 * 
 * @author FarzadShamriz
 *
 */
public class PlayerHelper {

	/**
	 * This method is calculating number of armies for increase and decrease form
	 * and to a country
	 * 
	 * @param fromCountryId  ID of source country
	 * @param toCountryId    ID of target country
	 * @param armyNumbToMove number of armies to decrease or increase
	 * @return
	 */
	public boolean calculationOfMovement(int fromCountryId, int toCountryId, int armyNumbToMove) {
		UIHelper uiHelper = new UIHelper();
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		Country sourceCountry = uiHelper.getCountryById(countries, fromCountryId);
		int sourceCountryArmies = sourceCountry.getArmy();
		Country targegCountry = uiHelper.getCountryById(countries, toCountryId);

		if ((sourceCountryArmies - 1) < armyNumbToMove) {
			return false;
		} else {
			decreaseCountryArmies(fromCountryId, armyNumbToMove);
			increaseCountryArmies(toCountryId, armyNumbToMove);
			return true;
		}

	}

	/**
	 * this method update cardsCounter of layer which find by id
	 * 
	 * @param playerItem  player which should update
	 * @param playerArray list of players to find player
	 * @return updated player array
	 */
	public Player[] updatePlayerCardsCounterById(Player playerItem, Player[] playerArray) {
		playerArray = Map.getInstance().getPlayers();
		for (int i = 0; i < playerArray.length; i++) {
			if (playerItem.getPlayerID() == playerArray[i].getPlayerID()) {
				playerArray[i].setCardCounts(playerItem.getCardCounts());
				break;
			}
		}
		Map.getInstance().setPlayers(playerArray);
		return playerArray;
	}

	/**
	 * Create and add a countryid to the player array and increase the countriesID
	 * 
	 * @param countrID the id of country
	 * @param playerID the id of player
	 * @param players  it is the array of players for modifications
	 */
	public Player[] addCountryIDToPlayer(Player[] players, int countryID, int playerID) {
		if (players == null)
			return null;

		Player[] tempPlayers = new Player[players.length];

		for (int i = 0; i < players.length; i++) {
			if (players[i].getPlayerID() == playerID) {
				Player player = players[i];

				int[] countriesID = new int[player.getCountryID().length + 1];
				for (int j = 0; j < player.getCountryID().length; j++) {
					countriesID[j] = player.getCountryID()[j];
				}
				countriesID[player.getCountryID().length] = countryID;
				player.setCountryId(countriesID);
				tempPlayers[i] = player;
			} else
				tempPlayers[i] = players[i];
		}
		return tempPlayers;
	}

	/**
	 * Remove countryId from the players array and decrease the countriesID
	 * 
	 * @param countrID the id of the country
	 * @param playerID the id of the player
	 * @param players  it is the array of players for modifications
	 */
	public Player[] removeCountryIDToPlayer(Player[] players, int countryID, int playerID) {
		if (players == null)
			return null;

		Player[] tempPlayers = new Player[players.length];

		for (int i = 0; i < players.length; i++) {
			if (players[i].getPlayerID() == playerID) {
				Player player = players[i];

				int[] countriesID = new int[player.getCountryID().length - 1];
				int countryIndex = 0;
				for (int j = 0; j < player.getCountryID().length; j++) {
					if (player.getCountryID()[j] != countryID) {
						countriesID[countryIndex] = player.getCountryID()[j];
						countryIndex++;
					}
				}
				player.setCountryId(countriesID);
				tempPlayers[i] = player;
			} else
				tempPlayers[i] = players[i];
		}
		return tempPlayers;

	}

	/**
	 * This method increase armies of country which retrieved by Cid (Country ID)
	 * 
	 * @param Cid          Country ID
	 * @param armiesNumber Number of Armies for increasing
	 */
	public void increaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies + armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	/**
	 * This method decrease armies of country which retrieved by Cid (Country ID)
	 * 
	 * @param Cid          Country ID
	 * @param armiesNumber Number of Armies for decreasing
	 */
	public void decreaseCountryArmies(int Cid, int armiesNumber) {
		Map map = Map.getInstance();
		ArrayList<Country> countries = map.getCountries();
		for (Country country : countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies - armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	/**
	 * Return Player's countries
	 * 
	 * @param countryList - list of all countries
	 * @param playerId    - ID of player
	 * @return list of player's countries
	 */
	public ArrayList<Country> getPlayerCountries(ArrayList<Country> countryList, int playerId) {
		ArrayList<Country> resultCountries = new ArrayList<Country>();
		for (Country countryItem : countryList) {
			if (countryItem.getPlayerID() == playerId) {
				resultCountries.add(countryItem);
			}
		}
		return resultCountries;
	}

	/**
	 * return country from list with the minimum armies
	 * 
	 * @param countryList - list of countries
	 * @return - weaker country with minimum armies
	 */
	public Country getWeakerCountry(ArrayList<Country> countryList) {
		Country weakerCountry = countryList.get(0);
		for (Country countryItem : countryList) {
			if (countryItem.getArmy() < weakerCountry.getArmy())
				weakerCountry = countryItem;
		}
		return weakerCountry;
	}

	public Country getFamilyAdjacentWithMoreThanOneArmy(ArrayList<Country> familyAdjacentCountries, Country country) {
		for (Country countryFamilyItem : familyAdjacentCountries) {
			if (checkNumberIsInList(countryFamilyItem.getAdjacentCountriesID(), country.getCountryID())
					&& countryFamilyItem.getArmy() > 1) {
				return countryFamilyItem;
			}
		}
		return null;
	}

	public boolean checkNumberIsInList(int[] intArrays, int specificNumber) {
		for (int i = 0; i < intArrays.length; i++) {
			if (intArrays[i] == specificNumber)
				return true;
		}
		return false;
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

	public boolean playerUseCardDecide(ArrayList<Card> playerCards) {
		int infantryCardCounter = 0;
		int cavalryCardCounter = 0;
		int artilleryCardCounter = 0;
		if (playerCards.size() > 5)
			return true;
		for (Card card : playerCards) {
			switch (card.getCardType()) {
			case ARTILLERY:
				artilleryCardCounter++;
				break;
			case CAVALRY:
				cavalryCardCounter++;
				break;
			case INFANTRY:
				infantryCardCounter++;
				break;
			}
		}
		if (artilleryCardCounter >= 3 || infantryCardCounter >= 3 || cavalryCardCounter >= 3) {
			return true;
		}
		return false;
	}
}
