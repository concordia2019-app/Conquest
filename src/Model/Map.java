package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Map {
	final int FirstArmies = 21;
	public Player[] Players;
	public ArrayList<Country> CountriesList = new ArrayList<Country>();

	public Map() {
		setCountries();
	}

	public void MainMap() {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");

		for (int i = 0; i < CountriesList.size(); i++)
			System.out.format(table, showCountryID(CountriesList, i), showCountryName(CountriesList, i), showArmy(CountriesList, i),
					showContinentID(CountriesList, i), Arrays.toString(showAdjacentCountriesID(CountriesList, i)), 
					showPlayerName(CountriesList, i));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}
	
	public void PlayerMap(Player player) {
		
		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";


		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
			
		ArrayList<Country> playerCountries = getPlayerCorrespondingCountries(player.getCountryID());
		
		for(int i = 0; i < playerCountries.size(); i++) 
			System.out.format(table, showCountryID(playerCountries, i), showCountryName(playerCountries, i), showArmy(playerCountries, i), 
					showContinentID(playerCountries, i), Arrays.toString(showAdjacentCountriesID(playerCountries, i)), 
					showPlayerName(playerCountries, i));
		
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");		
	}


	public void AttackMap(Player player, Country country) { 
		
		String table = "|%-24d|%-26s|%-41d|%-23d|%n";

		System.out.println("You have selected " + country.getCountryName() + " country to attack! There are " + country.getArmy() + " army inside this country.");

		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n"); 
		System.out.format(
				"| Opponent's country' ID | Opponent's country' Name | No. of armies inside opponent's country | Opponent's player' ID |%n"); 
		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n");
		
		ArrayList<Country> specificCountryAdjacentsForAttack = getSpecificCountryAdjacentsForAttack(player.getCountryID(), country.getCountryID());
		
		for(int i = 0; i < specificCountryAdjacentsForAttack.size(); i++) 
			System.out.format(table, showCountryID(specificCountryAdjacentsForAttack, i), showCountryName(specificCountryAdjacentsForAttack, i), 
					showArmy(specificCountryAdjacentsForAttack, i), showPlayerID(specificCountryAdjacentsForAttack, i));
		
		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n");
	}

	public void MoveMap(Player player, Country country) {

		String table = "|%-39d|%-33d|%-42d|%n";

		System.out.println("You have selected " + country.getCountryName() + " country to move! There are " + country.getArmy() + " army inside this country.");

		System.out.format(
				"+---------------------------------------+---------------------------------+------------------------------------------+%n");
		System.out.format(
				"| No. of armies inside player's country | Player's adjacent countries' ID | No. of armies inside adjacence's country |%n");
		System.out.format(
				"+---------------------------------------+---------------------------------+------------------------------------------+%n");
		
		ArrayList<Country> specificCountryAdjacentsForMove = getSpecificCountryAdjacentsForMove(player.getCountryID(), country.getCountryID());
	
		for(int i = 0; i < specificCountryAdjacentsForMove.size(); i++) 
			System.out.format(table, showCountryID(specificCountryAdjacentsForMove, i), showCountryName(specificCountryAdjacentsForMove, i), 
					showArmy(specificCountryAdjacentsForMove, i), showPlayerID(specificCountryAdjacentsForMove, i));
		
		System.out.format(
				"+---------------------------------------+---------------------------------+------------------------------------------+%n");
	}

	public void assigningPlayerCountries(ArrayList<String> playerNames, int playerCount) {
		Players = new Player[playerCount];
		ArrayList<Country> countryTempList = new ArrayList<Country>();
		for (Country country : CountriesList) {
			countryTempList.add(country);
		}
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
			Players[i] = new Player(i + 1, playerNames.get(i), countriesIds);
			for (int cCounter = 0; cCounter < countriesIds.length; cCounter++) {
				updateCountryById(i + 1, countriesIds[cCounter], playerNames.get(i));
			}
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
				updateCountryById(rechargePlayer.getPlayerID(), newCountryIdForPlayer, rechargePlayer.getPlayerName());
				countryTempList.remove(randomCountryId);
				if (countryTempList.size() == 0) {
					break;
				}
			}
		}
		assignArmies();
		// Just for test
		System.out.println("List of players");

		for (Player playerTemp : Players) {
			System.out.println(
					"**Player Id: " + playerTemp.getPlayerID() + " **Player Name: " + playerTemp.getPlayerName());
		}
		// Just for test
		System.out.print("\n\n");
		for (Country countryTemp : CountriesList) {
			System.out.println(
					"**Country Id: " + countryTemp.getCountryID() + " **Country Name: " + countryTemp.getCountryName()
					+ " **PlayerId:" + countryTemp.getPlayerID() + " **Armies:" + countryTemp.getArmy());
		}
	}

	public void assignArmies() {
		for (Player playereItem : Players) {
			int playerArmies = 0;
			int[] playerCountries = playereItem.getCountryID();
			int armiesPerCountry = FirstArmies / (playerCountries.length);
			for (int i = 0; i < CountriesList.size(); i++) {
				int currentCountryId = CountriesList.get(i).getCountryID();
				if (isIdExistInList(playerCountries, currentCountryId)) {
					CountriesList.get(i).setArmy(armiesPerCountry);
					playerArmies += armiesPerCountry;
				}
			}
			if (playerArmies < FirstArmies) {
				int restOfArmies = FirstArmies - playerArmies;
				for (int i = 0; i < CountriesList.size(); i++) {
					int currentCountryId = CountriesList.get(i).getCountryID();
					if (isIdExistInList(playerCountries, currentCountryId) && (playerArmies < FirstArmies)) {
						restOfArmies += CountriesList.get(i).getArmy();
						CountriesList.get(i).setArmy(restOfArmies);
						playerArmies += restOfArmies;
					}
				}
			}
		}
	}

	private boolean isIdExistInList(int[] listId, int id) {
		for (int i = 0; i < listId.length; i++) {
			if (listId[i] == id) {
				return true;
			}
		}
		return false;
	}

	private void updateCountryById(int playerId, int countryId, String playerName) {
		for (Country countryItem : CountriesList) {
			int countryItemId = countryItem.getCountryID();
			if (countryItemId == countryId)
				countryItem.setPlayer(playerId, playerName);
		}
	}

	public void setCountries() {
		Editor editor = new Editor();
		CountriesList = editor.setCountries(CountriesList);
	}

	public void setCountries(ArrayList<Country> countryList) {
		this.CountriesList = countryList;
	}

	public void setPlayers(Player[] players) {
		this.Players = players;
	}

	public int showCountryID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryID();
	}

	public String showCountryName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryName();
	}

	public int showArmy(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getArmy();
	}

	public int showContinentID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getContinentID();
	}

	public int[] showAdjacentCountriesID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getAdjacentCountriesID();
	}

	public int showPlayerID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerID();
	}
	
	public String showPlayerName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerName();
	}

	public void setPlayer(int countryID, int playerID, String playerName) {
		CountriesList.get(countryID).setPlayer(playerID, playerName);
	}

	public void setArmy(int countryID, int army) {
		CountriesList.get(countryID).setArmy(army);
	}

	public ArrayList<Country> getCountries() {
		return CountriesList;
	}

	public Player[] getPlayers() {
		return (this.Players);
	}
	
	public ArrayList<Country> getPlayerCorrespondingCountries(int[] countriesID) {
		ArrayList<Country> playerCountries = new ArrayList<Country>();
		
		for(int i = 0; i < countriesID.length; i++)
			for(int j = 0; j < CountriesList.size(); j++)
				if(showCountryID(CountriesList, j) == countriesID[i])
					playerCountries.add(CountriesList.get(j));
		
		return playerCountries;
	}
	
	public ArrayList<Country> getSpecificCountryAdjacentsForAttack(int[] playerCountries, int specificCountryID) {
		
		ArrayList<Country> specificCountryAdjacentsForAttack = new ArrayList<Country>();
		
		for(int i = 0; i < CountriesList.size(); i++) 
			for(int j = 0; j < showAdjacentCountriesID(CountriesList, i).length; j++) 
				if(showAdjacentCountriesID(CountriesList, i)[j] == specificCountryID)
					specificCountryAdjacentsForAttack.add(CountriesList.get(i));
		
		for(int i = 0; i < specificCountryAdjacentsForAttack.size(); i++)
			for(int j = 0; j < playerCountries.length; j++)
				if(showCountryID(specificCountryAdjacentsForAttack, i) == playerCountries[j])
					specificCountryAdjacentsForAttack.remove(i);
		
		return specificCountryAdjacentsForAttack;
	}
	
	public ArrayList<Country> getSpecificCountryAdjacentsForMove(int[] playerCountries, int specificCountryID) {
		
		ArrayList<Country> specificCountryAdjacentsForMove = new ArrayList<Country>();
		
		for(int i = 0; i < CountriesList.size(); i++) 
			for(int j = 0; j < showAdjacentCountriesID(CountriesList, i).length; j++) 
				if(showAdjacentCountriesID(CountriesList, i)[j] == specificCountryID)
					specificCountryAdjacentsForMove.add(CountriesList.get(i));
		
		for(int i = 0; i < specificCountryAdjacentsForMove.size(); i++)
			for(int j = 0; j < playerCountries.length; j++)
				if(showCountryID(specificCountryAdjacentsForMove, i) != playerCountries[j])
					specificCountryAdjacentsForMove.remove(i);
		
		return specificCountryAdjacentsForMove;
	}
}