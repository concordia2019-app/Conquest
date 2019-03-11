package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * 
 * This class is for showing different kinds of map for specific reasons.
 * 
 * @author AHasheminezhad
 */
public class Map {
	final int FirstArmies = 21;
	public Player[] Players;
	public ArrayList<Country> CountriesList = new ArrayList<Country>();

	public Map() {
		setCountries();
	}

	/**
	 * MainMap is printing every country with its details and player it belongs to in the console.
	 */
	public void printMainMap() {

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

	/**
	 * PlayerMap is printing only countries the player we send owns.
	 * 
	 * @param player The player who we want to show his/her countries.
	 */
	public void printPlayerMap(Player player) {

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


	/**
	 * AttackMap is printing adjacencies of the specific country that player wants to attack with 
	 * and excludes countries that are his/her from the list.
	 * 
	 * @param player The player who wants to attack.
	 * @param country The country that the player has chosen to attack with.
	 */
	public void printAttackMap(Player player, Country country) { 

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

	/**
	 * MoveMap is printing adjacencies of the specific country that player wants to move with
	 * and excludes countries that are not his/her from the list.
	 * 
	 * @param player The player who wants to move.
	 * @param country The country that the player has chosen to move with.
	 */
	public void printMoveMap(Player player, Country country) {

		String table = "|%-39d|%-33d|%-43d|%n";

		System.out.println("You have selected " + country.getCountryName() + " country to move! There are " + country.getArmy() + " army inside this country.");

		System.out.format(
				"+---------------------------------------+---------------------------------+-------------------------------------------+%n");
		System.out.format(
				"| No. of armies inside player's country | Player's adjacent countries' ID | No. of armies inside the adjacent country |%n");
		System.out.format(
				"+---------------------------------------+---------------------------------+-------------------------------------------+%n");

		ArrayList<Country> specificCountryAdjacentsForMove = getSpecificCountryAdjacentsForMove(player.getCountryID(), country.getCountryID());

		for(int i = 0; i < specificCountryAdjacentsForMove.size(); i++) 
			System.out.format(table, showArmy(specificCountryAdjacentsForMove, i), Arrays.toString(showAdjacentCountriesID(specificCountryAdjacentsForMove, i)));

		System.out.format(
				"+---------------------------------------+---------------------------------+-------------------------------------------+%n");
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
		printMainMap();
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

	/**
	 * Sends an ArrayList to Editor to fill the CountriesList with values.
	 */
	public void setCountries() {
		Editor editor = new Editor();
		CountriesList = editor.setCountries(CountriesList);
	}

	/**
	 * This is a setter for value of all of the countries inside CountriesList.
	 * 
	 * @param countryList ArrayList of all of the countries inside the map.
	 */
	public void setCountries(ArrayList<Country> countryList) {
		this.CountriesList = countryList;
	}

	/**
	 * This is a setter for value of all the players inside the map.
	 * 
	 * @param players Array of all the players.
	 */
	public void setPlayers(Player[] players) {
		this.Players = players;
	}

	/**
	 * @param listOfCountries List of countries which we want choose country ID from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the country ID.
	 */
	public int showCountryID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose country Name from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the country Name.
	 */
	public String showCountryName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryName();
	}

	/**
	 * @param listOfCountries List of countries which we want choose Army from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the Army.
	 */
	public int showArmy(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getArmy();
	}

	/**
	 * @param listOfCountries List of countries which we want choose continent ID from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the continent ID.
	 */
	public int showContinentID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getContinentID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose adjacent countries ID from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the adjacent countries ID.
	 */
	public int[] showAdjacentCountriesID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getAdjacentCountriesID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose player ID from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the player ID.
	 */
	public int showPlayerID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose player name from.
	 * @param counter which specific country from the ArrayList.
	 * @return It will return the player name.
	 */
	public String showPlayerName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerName();
	}

	/**
	 * This method will set a player to a country.
	 * 
	 * @param countryID A country ID that player has won during attack.
	 * @param playerID corresponding player ID.
	 * @param playerName corresponding player name.
	 */
	public void setPlayer(int countryID, int playerID, String playerName) {
		CountriesList.get(countryID).setPlayer(playerID, playerName);
	}

	/**
	 * This method will set an army to a country.
	 * 
	 * @param countryID A country ID that player has won during attack.
	 * @param army corresponding army value that is remaining after the attack.
	 */
	public void setArmy(int countryID, int army) {
		CountriesList.get(countryID).setArmy(army);
	}

	/**
	 * @return An ArrayList containing all of the countries inside map.
	 */
	public ArrayList<Country> getCountries() {
		return CountriesList;
	}

	/**
	 * This method is a getter for all player objects.
	 * 
	 * @return it will return an array of all players.
	 */
	public Player[] getPlayers() {
		return (this.Players);
	}

	/**
	 * @param countriesID Array of countries a specific player owns.
	 * @return ArrayList of corresponding countries objects 
	 */
	public ArrayList<Country> getPlayerCorrespondingCountries(int[] countriesID) {
		ArrayList<Country> playerCountries = new ArrayList<Country>();

		for(int i = 0; i < countriesID.length; i++)
			for(int j = 0; j < CountriesList.size(); j++)
				if(showCountryID(CountriesList, j) == countriesID[i])
					playerCountries.add(CountriesList.get(j));

		return playerCountries;
	}

	/**
	 * @param playerCountries Array of countries that a specific player owns.
	 * @param specificCountryID A specific country ID that player has chosen to attack with.
	 * @return ArrayList of all of the ajdacencies that the player is able to attack.
	 */
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

	/**
	 * @param playerCountries Array of countries that a specific player owns.
	 * @param specificCountryID A specific country ID that player has chosen to fortify.
	 * @return ArrayList of all of the ajdacencies that the player is able to fortify.
	 */
	public ArrayList<Country> getSpecificCountryAdjacentsForMove(int[] playerCountries, int specificCountryID) {

		ArrayList<Country> specificCountryAdjacentsForMove = new ArrayList<Country>();
		ArrayList<Country> myCountries = new ArrayList<Country>();
		
		for(int i = 0; i < CountriesList.size(); i++) 
			for(int j = 0; j < showAdjacentCountriesID(CountriesList, i).length; j++) 
				if(showAdjacentCountriesID(CountriesList, i)[j] == specificCountryID)
					specificCountryAdjacentsForMove.add(CountriesList.get(i));

		int adjIdsLength = specificCountryAdjacentsForMove.size();
		for(int i = 0; i < adjIdsLength; i++)
			for(int j = 0; j < playerCountries.length; j++)
				if(showCountryID(specificCountryAdjacentsForMove, i) == playerCountries[j])
					myCountries.add(specificCountryAdjacentsForMove.get(i));

		return myCountries;
	}

	/**
	 * rndRange method will generate a random number between first to last.
	 * @param first  get first number .
	 * @param last   get last number .
	 * @return RndNumber is  a random number between first number and last number and display it.
	 */
	public static int rndRange(int first, int last) {
		Random rnd = new Random();
		int RndNumber = rnd.nextInt(last + 1 - first) + first;
		return RndNumber;
	}

	/**
	 * Competition method will do the fighting between two players and show the result step by step.
	 * 
	 * @param NumAttacker number of armies the attacker has at his disposal for the fight.
	 * @param NumDefender number of armies the defender has at his disposal for the fight.
	 * @return StatusFight return the result of the game that who wins.
	 */
	public static int competition (int NumAttacker , int NumDefender){
		int Dice_Attacker;
		int Dice_Defender;
		// if attacker won is true = 1;
		int StatusFight = 1;
		//continue until one the players army become ZERO.
		while (true) {
			Dice_Attacker = rndRange(1, 6);
			Dice_Defender = rndRange(1, 6);
			System.out.println("Dice of the Games: ");
			System.out.println(Dice_Attacker);
			System.out.println(Dice_Defender);
			//compare dice numbers and reducing the number of armies.
			if(Dice_Defender >= Dice_Attacker) {
				NumAttacker--;
			}
			else {
				NumDefender--;
			}
			System.out.println("Number of Armies: ");
			System.out.println(NumAttacker);
			System.out.println(NumDefender);
			//if the armies of defender or attacker got ZERO, war finishes. 
			if(NumDefender == 0 || NumAttacker == 0) {
				break;
			}
		}
		//RESAULT of war.
		if(NumDefender == 0) {
			StatusFight = 1;
			System.out.println("\nAttacker WINS\n");
		}
		else {
			StatusFight = 0;
			System.out.println("\nDefender WINS\n");
		}
		// status of the fight.
		return StatusFight;
	}
}