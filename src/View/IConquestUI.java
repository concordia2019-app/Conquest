package View;

/**
 * This interface has the structure of the UI to show outputs
 * This interface has been implemented in ConquestUI class 
 * 
 * @author FarzadShamriz
 */
import java.io.FileNotFoundException;
import Model.Country;
import Model.LeftArmiesResponse;
import Model.Player;

/**
 * This is a class to show messages below
 * <ul>
 * <li>Showing the map with related adjacencies</li>
 * <li>showing the menu to select map, attach or move of player</li>
 * <li>showing the list of countries and adjacencies</li>
 * </ul>
 * 
 * @author FarzadShamriz
 *
 */
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
	 * 
	 */
	public void conquestUIGetNodes();

	/**
	 * This method just ask from player if player want to attack Y to agree that
	 * want to attack N to skip the attack
	 * 
	 * @param strQuestion String to show the message
	 * @return if player pass n , then return false, on the other hand, if pass y,
	 *         then return true
	 */
	public boolean conquestUiYesNoQuestion(String strQuestion);

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
	 * @param country source country
	 * @return an object which contains CountryId and Number of left armies in that
	 *         country
	 */
	public LeftArmiesResponse numberOfArmiesToleave(Country country);

	/**
	 * This method give N number of armies to the players and they are able to add
	 * them to their armies who are in each country.
	 * 
	 * @param armiesNumber number of armies
	 * @param player       current player to separate countries and assign armies
	 * @return rest of armies
	 */
	public int reinforcementOfPlayer(int armiesNumber, Player player);

}
