package View;

import java.util.ArrayList;
import java.util.Arrays;

import Model.Country;
import Model.Player;

/**
 * This class is printing tables which is related to the MainMap, MoveMap, and
 * AttackMap
 * 
 * @author FarzadShamriz
 *
 */
public class MapView {

	/**
	 * MoveMap is printing adjacencies of the specific country that player wants to
	 * move with and excludes countries that are not his/her from the list.
	 * 
	 * @param player  The player who wants to move.
	 * @param country The country that the player has chosen to move with.
	 */
	public void printMoveMap(Player player, Country country, ArrayList<Country> countriesList) {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";

		System.out.println("You have selected " + country.getCountryName() + " country to move! There are "
				+ country.getArmy() + " army inside this country.");

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");

		ArrayList<Country> specificCountryAdjacentsForMove = getSpecificCountryAdjacentsForMove(player.getCountryID(),
				country.getCountryID(), countriesList);

		for (int i = 0; i < specificCountryAdjacentsForMove.size(); i++)
			System.out.format(table, showCountryID(specificCountryAdjacentsForMove, i),
					showCountryName(specificCountryAdjacentsForMove, i), showArmy(specificCountryAdjacentsForMove, i),
					showContinentID(specificCountryAdjacentsForMove, i),
					Arrays.toString(showAdjacentCountriesID(specificCountryAdjacentsForMove, i)),
					showPlayerName(specificCountryAdjacentsForMove, i));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	/**
	 * AttackMap is printing adjacencies of the specific country that player wants
	 * to attack with and excludes countries that are his/her from the list.
	 * 
	 * @param player  The player who wants to attack.
	 * @param country The country that the player has chosen to attack with.
	 */
	public void printAttackMap(Player player, Country country, ArrayList<Country> countriesList) {

		String table = "|%-24d|%-26s|%-41d|%-23d|%n";

		System.out.println("You have selected " + country.getCountryName() + " country to attack! There are "
				+ country.getArmy() + " army inside this country.");

		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n");
		System.out.format(
				"| Opponent's country' ID | Opponent's country' Name | No. of armies inside opponent's country | Opponent's player' ID |%n");
		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n");

		ArrayList<Country> specificCountryAdjacentsForAttack = getSpecificCountryAdjacentsForAttack(
				player.getCountryID(), country.getCountryID(), countriesList);

		for (int i = 0; i < specificCountryAdjacentsForAttack.size(); i++)
			System.out.format(table, showCountryID(specificCountryAdjacentsForAttack, i),
					showCountryName(specificCountryAdjacentsForAttack, i),
					showArmy(specificCountryAdjacentsForAttack, i), showPlayerID(specificCountryAdjacentsForAttack, i));

		System.out.format(
				"+------------------------+--------------------------+-----------------------------------------+-----------------------+%n");
	}

	/**
	 * @param playerCountries   Array of countries that a specific player owns.
	 * @param specificCountryID A specific country ID that player has chosen to
	 *                          attack with.
	 * @return ArrayList of all of the ajdacencies that the player is able to
	 *         attack.
	 */
	public ArrayList<Country> getSpecificCountryAdjacentsForAttack(int[] playerCountries, int specificCountryID,
			ArrayList<Country> countriesList) {

		ArrayList<Country> specificCountryAdjacentsForAttack = new ArrayList<Country>();

		for (int i = 0; i < countriesList.size(); i++)
			for (int j = 0; j < showAdjacentCountriesID(countriesList, i).length; j++)
				if (showAdjacentCountriesID(countriesList, i)[j] == specificCountryID)
					specificCountryAdjacentsForAttack.add(countriesList.get(i));

		for (int i = 0; i < specificCountryAdjacentsForAttack.size(); i++)
			for (int j = 0; j < playerCountries.length; j++)
				if (showCountryID(specificCountryAdjacentsForAttack, i) == playerCountries[j])
					specificCountryAdjacentsForAttack.remove(i);

		return specificCountryAdjacentsForAttack;
	}

	/**
	 * PlayerMap is printing only countries the player we send owns.
	 * 
	 * @param player The player who we want to show his/her countries.
	 */
	public void printPlayerMap(Player player, ArrayList<Country> countriesList) {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");

		ArrayList<Country> playerCountries = getPlayerCorrespondingCountries(player.getCountryID(), countriesList);

		for (int i = 0; i < playerCountries.size(); i++)
			System.out.format(table, showCountryID(playerCountries, i), showCountryName(playerCountries, i),
					showArmy(playerCountries, i), showContinentID(playerCountries, i),
					Arrays.toString(showAdjacentCountriesID(playerCountries, i)), showPlayerName(playerCountries, i));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	/**
	 * MainMap is printing every country with its details and player it belongs to
	 * in the console.
	 */
	public void printMainMap(ArrayList<Country> countriesList) {

		String table = "|%-14d|%-16s|%-15d|%-16d|%-28s|%-15s|%n";

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
		System.out.format(
				"| Country's ID | Country's name | No. of armies | Continent's ID |   Adjacent countries' ID   | Player's name |%n");
		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");

		for (int i = 0; i < countriesList.size(); i++)
			System.out.format(table, showCountryID(countriesList, i), showCountryName(countriesList, i),
					showArmy(countriesList, i), showContinentID(countriesList, i),
					Arrays.toString(showAdjacentCountriesID(countriesList, i)), showPlayerName(countriesList, i));

		System.out.format(
				"+--------------+----------------+---------------+----------------+----------------------------+---------------+%n");
	}

	/**
	 * @param playerCountries   Array of countries that a specific player owns.
	 * @param specificCountryID A specific country ID that player has chosen to
	 *                          fortify.
	 * @return ArrayList of all of the ajdacencies that the player is able to
	 *         fortify.
	 */
	public ArrayList<Country> getSpecificCountryAdjacentsForMove(int[] playerCountries, int specificCountryID,
			ArrayList<Country> countriesList) {

		ArrayList<Country> specificCountryAdjacentsForMove = new ArrayList<Country>();
		ArrayList<Country> myCountries = new ArrayList<Country>();

		for (int i = 0; i < countriesList.size(); i++)
			for (int j = 0; j < showAdjacentCountriesID(countriesList, i).length; j++)
				if (showAdjacentCountriesID(countriesList, i)[j] == specificCountryID)
					specificCountryAdjacentsForMove.add(countriesList.get(i));

		int adjIdsLength = specificCountryAdjacentsForMove.size();
		for (int i = 0; i < adjIdsLength; i++)
			for (int j = 0; j < playerCountries.length; j++)
				if (showCountryID(specificCountryAdjacentsForMove, i) == playerCountries[j])
					myCountries.add(specificCountryAdjacentsForMove.get(i));

		return myCountries;
	}

	/**
	 * @param countriesID   Array of countries a specific player owns.
	 * @param countriesList List of countries
	 * @return ArrayList of corresponding countries objects
	 */
	public ArrayList<Country> getPlayerCorrespondingCountries(int[] countriesID, ArrayList<Country> countriesList) {
		ArrayList<Country> playerCountries = new ArrayList<Country>();

		for (int i = 0; i < countriesID.length; i++)
			for (int j = 0; j < countriesList.size(); j++)
				if (showCountryID(countriesList, j) == countriesID[i])
					playerCountries.add(countriesList.get(j));

		return playerCountries;
	}

	/**
	 * @param listOfCountries List of countries which we want choose country ID
	 *                        from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the country ID.
	 */
	public int showCountryID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose country Name
	 *                        from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the country Name.
	 */
	public String showCountryName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getCountryName();
	}

	/**
	 * @param listOfCountries List of countries which we want choose Army from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the Army.
	 */
	public int showArmy(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getArmy();
	}

	/**
	 * @param listOfCountries List of countries which we want choose continent ID
	 *                        from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the continent ID.
	 */
	public int showContinentID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getContinentID();
	}

	public int[] showAdjacentCountriesID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getAdjacentCountriesID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose player ID from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the player ID.
	 */
	public int showPlayerID(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerID();
	}

	/**
	 * @param listOfCountries List of countries which we want choose player name
	 *                        from.
	 * @param counter         which specific country from the ArrayList.
	 * @return It will return the player name.
	 */
	public String showPlayerName(ArrayList<Country> listOfCountries, int counter) {
		return listOfCountries.get(counter).getPlayerName();
	}

}
