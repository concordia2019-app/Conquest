package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.ReturnInstruction;

public class Map {
	final int FirstArmies = 7;
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
			
		for(int i = 0; i < CountriesList.size(); i++) 
			System.out.format(table, showCountryID(i), showCountryName(i), showArmy(i), showContinentID(i), 
					Arrays.toString(showAdjacentCountriesID(i)), showPlayerName(i));
		
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	/*public void AttackMap(Editor editor, Player player) {
		String table = "|%-14d|%-39s|%-24d|%-41s|%-23d|%n";

		System.out.println("This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to attack!");

		System.out.format(
				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");
		System.out.format(
				"| Country's ID | No. of armies inside player's country | Opponent's country' ID | No. of armies inside opponent's country | Opponent's player' ID |%n");
		System.out.format(
				"+--------------+---------------------------------------+------------------------+-----------------------------------------+-----------------------+%n");

		for(int i = 0; i < player.getCountryID().; i++ ) 
			System.out.format(table, player.getCountryID().get(i), editor.getCountry().get(i).getArmy(),
					editor.getCountry().get(i).getArmy(), Arrays.toString(editor.getCountry().get(i).getAdjacentCountriesID()));
	}*/

	public void MoveMap(Editor editor, Player player) {

		String table = "|%-14d|%-39d|%-33d|%-42d|%n";

		System.out.println("This is player no. " + player.getPlayerID() + " - " + player.getPlayerName() + " turn to move!");

		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");
		System.out.format(
				"| Country's ID | No. of armies inside player's country | Player's adjacent countries' ID | No. of armies inside adjacence's country |%n");
		System.out.format(
				"+--------------+---------------------------------------+---------------------------------+------------------------------------------+%n");
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
				updateCountryById(i + 1, countriesIds[cCounter],playerNames.get(i));
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
				updateCountryById(rechargePlayer.getPlayerID(), newCountryIdForPlayer,rechargePlayer.getPlayerName());
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
			int playerArmies = FirstArmies;
			int[] playerCountries = playereItem.getCountryID();
			while (playerArmies > 0) {
				for (int i = 0; i < playerCountries.length; i++) {
					for (int j = 0; j < CountriesList.size(); j++) {
						int currentCountryId = CountriesList.get(j).getCountryID();
						if (playerCountries[i] == currentCountryId) {
							int dividedArmies = CountriesList.get(j).getArmy() + 1;
							CountriesList.get(j).setArmy(dividedArmies);
							playerArmies--;
						}
					}
				}
			}

		}
	}

	private void updateCountryById(int playerId, int countryId,String playerName) {
		for (Country countryItem : CountriesList) {
			int countryItemId = countryItem.getCountryID();
			if (countryItemId == countryId)
				countryItem.setPlayer(playerId,playerName);
		}
	}
	
	public void setCountries() {
		Editor editor = new Editor();
		CountriesList = editor.setCountries(CountriesList);
	}
	
	public int showCountryID(int counter) {
		return CountriesList.get(counter).getCountryID();
	}
	
	public String showCountryName(int counter) {
		return CountriesList.get(counter).getCountryName();
	}
	
	public int showArmy(int counter) {
		return CountriesList.get(counter).getArmy();
	}
	
	public int showContinentID(int counter) {
		return CountriesList.get(counter).getContinentID();
	}
	
	public int[] showAdjacentCountriesID(int counter) {
		return CountriesList.get(counter).getAdjacentCountriesID();
	}
	
	public String showPlayerName(int counter) {
		return CountriesList.get(counter).getPlayerName();
	}
	
	public void setPlayer(int countryID, int playerID, String playerName) {
		CountriesList.get(countryID).setPlayer(playerID, playerName);
	}
	
	public void setArmy(int countryID, int army) {
		CountriesList.get(countryID).setArmy(army);
	}
	
	public ArrayList<Country> getCountries(){
		return CountriesList;
	}
}