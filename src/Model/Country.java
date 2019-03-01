package Model;

/**
 * Date: Feb 15-2019
 * <p>
 * This class is to contain the attributes and behavior of the selected country.
 * 
 * @author AHasheminezhad
 */
public class Country {

	/**
	 * Gets the country's name as a string
	 */
	private String countryName;
	private int countryID;
	private int continentID;
	private int army;
	private int[] adjacentCountriesID;
	private int playerID;

	/**
	 * @param countryName         Country's name
	 * @param countryID           Country's ID
	 * @param continentID         Continent's ID
	 * @param army                Number of armies in the designated country
	 * @param adjacentCountriesID ArrayList of countries's ID which are adjacent to
	 *                            the selected country
	 * @param playerID            Player's ID
	 */

	public Country(String countryName, int countryID, int continentID, int army, int[] adjacentCountriesID,
			int playerID, String playerName) {

		this.countryName = countryName;
		this.countryID = countryID;
		this.continentID = continentID;
		this.army = army;
		this.adjacentCountriesID = adjacentCountriesID;
		this.playerID = playerID;
	}

	public int getCountryID() {

		return countryID;
	}

	public String getCountryName() {

		return countryName;
	}

	public int getContinentID() {

		return continentID;
	}

	public int getArmy() {

		return army;
	}

	public int[] getAdjacentCountriesID() {

		return adjacentCountriesID;
	}

	public int getPlayerID() {

		return playerID;
	}

	public void setPlayerId(int playerId) {
		this.playerID = playerId;
	}
}