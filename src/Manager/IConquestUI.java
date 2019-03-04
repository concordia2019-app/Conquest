package Manager;

/**
 * This interface has the structure of the UI to show outputs
 * This interface has been implemented in ConquestUI class 
 * 
 * @author F.S
 */
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Model.Country;
import Model.LeftArmiesResponse;
import Model.Player;

public interface IConquestUI {
	/**
	 * <p>
	 * This method is printing the start menu of the game
	 * </p>
	 * <p>
	 * The content of the menu is in the private field
	 * </p>
	 * <p>
	 * Private field name is <strong>StartGameMenuMessage</strong>.
	 * </p>
	 */
	public void conquestUIShowStartMenu();

	/**
	 * <p>
	 * This method give the path of note file to to load them in the map, then read
	 * the graphs and information about map and countries.
	 * </p>
	 * 
	 * @throws FileNotFoundException
	 */
	public void conquestUIGetNodes();

	/**
	 * This method just ask from player if player want to attack Y => to agree that
	 * want to attack N => to skip the attack
	 */
	public void conquestUIAttackQuestion();

	/**
	 * This method just ask from player if player want to move Y => to agree that
	 * want to move N => to skip the move
	 */
	public void conquestUIMove();

	/**
	 * This method shows that the attack is finished and call the move method
	 */
	public void conquestUIFinish();

	/**
	 * This method give the country Id from player and check the validity of it.
	 * Then call the attack method.
	 * 
	 * @param playerCountries this is a list of countries
	 */
	public void conquestUIGetCountryIdForAttack(List<Country> playerCountries);

	/**
	 * Show the whole map in console. Content map converted to a table which can be
	 * shown in the console
	 */
	public void showMap();

	/**
	 * numberOfPlayer method will ask the player to enter an integer for number of
	 * players and save the number for the next part to ask the player for their
	 * names. if the player enter a number more than 5 or less than 2 he will get an
	 * error for entering a wrong number.
	 * 
	 * @return num is number of players that player entered.
	 */
	public int getNumberOfPlayer();

	/**
	 * This method check the count of armies which player want to leave in moving
	 * 
	 * @param country
	 * @return an object which contains CountryId and Number of left armies in that
	 *         country
	 */
	public LeftArmiesResponse numberOfArmiesToleave(Country country);

	/**
	 * This method manage the attack of players
	 * 
	 * @param playerList list of players to play one by one 
	 * @param countryList list of countries to retrieve player's countries and adjacencies
	 */
	public void attackPlayer(Player[] playerList , ArrayList<Country> countryList);
	
	/**
	 * This method give N number of armies to the players and they are able to add
	 * them to their armies who are in each country.
	 * 
	 * @param armiesNumber number of armies
	 * @player current player to separate countries and assign armies
	 */
	public void reinforcementOfPlayer(int armiesNumber,Player player);

}
