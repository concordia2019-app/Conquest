package Controller;

import java.util.Random;

import Model.AttackResponse;
import Model.Card;
import Model.Country;
import Model.Map;
import Model.Player;

import java.util.ArrayList;

public class ConquestController {

	private static ConquestController instance;
 
	private ConquestController() {

	}

	public static ConquestController getInstance() {
		if (instance == null) {
			instance = new ConquestController();
			return instance;
		} else {
			return instance;
		}

	}

        public float playerPercentageCalculation(Player player, ArrayList<Country> countries ){
         return   ((float)player.getCountryID().length/countries.size())*100;            
        }
	/**
	 * This method get player and set random card to player object
	 * 
	 * @param currentPlayer
	 */
	public Player setCardToPlayer(Player currentPlayer) {
		CardController cardController = new CardController();
		Card cardToAssign = cardController.cardAssigner();
		currentPlayer.addCard(cardToAssign);
		return currentPlayer;
	}

	/**
	 * check Game is finished or not
	 * 
	 * @param players - List of players
	 * @return If game is finished then return true, if it's not finished, then
	 *         return false
	 */
	public boolean isGameFinish() {
		Player[] players = Map.getInstance().getPlayers();
		int looserCounter = 0;
		for (Player playerItem : players) {
			int playerCountriesCount = playerItem.getCountryID().length;
			if (playerCountriesCount < 1)
				looserCounter++;
		}
		if ((players.length - 1) == looserCounter)
			return true;
		return false;
	}

	/**
	 * this method is returning winner player in case the match in finished
	 * 
	 * @param players - list of player is passed to the method
	 * @return - output is an object of player which is win the game
	 */
	public Player getWinnerPlayer(Player[] players) {
		int looserCounter = 0;
		Player winner = null;
		for (Player playerItem : players) {
			int playerCountriesCount = playerItem.getCountryID().length;
			if (playerCountriesCount < 1)
				looserCounter++;
			else
				winner = playerItem;
		}
		if ((players.length - 1) == looserCounter)
			return winner;
		else
			return null;
	}

	/**
	 * This method will generate a random number between two integers.
	 * 
	 * @param least    Gets the least number that can be generated.
	 * @param greatest Gets the greatest number that can be generated.
	 * 
	 * @return randomNumber It is a random number between the least and greatest
	 *         number possible.
	 */
	public int randomNumberBetweenTwoIntegers(int least, int greatest) {

		Random random = new Random();
		int randomNumber = random.nextInt(greatest + 1 - least) + least;
		return randomNumber;
	}
        public ArrayList<Country> getPlayerCountries(int playerId)
        {
            ArrayList<Country> playerCountries= new  ArrayList<Country> ();
            for(int i=0; i< Map.getInstance().getCountries().size() ;i++)
            {
                  if( Map.getInstance().getCountries().get(i).getPlayerID()==playerId)
                    {
                        playerCountries.add(Map.getInstance().getCountries().get(i));
                    }
            }
            return playerCountries;
        }

	/**
	 * This method will simulate the attack when player attacks an adjacent country.
	 * 
	 * @param numberOfArmiesOfAttacker Number of armies that are inside attacker's
	 *                                 country.
	 * @param numberOfArmiesOfDefender Number of armies that are inside defender's
	 *                                 country.
	 * 
	 * @return resultOfAttack Result of the process.
	 */
	public AttackResponse attackCalculation(int numberOfArmiesOfAttacker, int numberOfArmiesOfDefender) {
		int diceValueAttacker;
		int diceValueDefender;
		AttackResponse attackResponse = new AttackResponse();

		while (true) {
			diceValueAttacker = randomNumberBetweenTwoIntegers(1, 6);
			diceValueDefender = randomNumberBetweenTwoIntegers(1, 6);

			// compare dice numbers and reducing the number of armies.
			if (diceValueDefender >= diceValueAttacker)
				numberOfArmiesOfAttacker--;
			else
				numberOfArmiesOfDefender--;

			// if the armies of defender or attacker got ZERO, war finishes.
			if (numberOfArmiesOfDefender < 1 || numberOfArmiesOfAttacker < 1)
				break;
		}

		if (numberOfArmiesOfAttacker == 0) {
			attackResponse.setAttackStatus(false);
			attackResponse.setRestOfArmies(numberOfArmiesOfDefender);
		} else {
			attackResponse.setAttackStatus(true);
			attackResponse.setRestOfArmies(numberOfArmiesOfAttacker);
		}

		return attackResponse;
	}

}
