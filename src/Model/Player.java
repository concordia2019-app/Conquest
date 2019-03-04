package Model;

import java.util.Random;

/**
 * This class is for the attack phase.
 * 
 * @author Amirhossein
 */
public class Player {

	private int playerID;
	private String playerName;
	private int[] countryID;

	public Player(int playerID, String playerName, int[] countryID) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.countryID = countryID;

	}

	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int[] getCountryID() {
		return countryID;
	}

	public void setCountryId(int[] countriesIds) {
		this.countryID = countriesIds;
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

	public boolean Attack(int numberOfArmiesOfAttacker, int numberOfArmiesOfDefender) {
		int diceValueAttacker;
		int diceValueDefender;
		boolean resultOfAttack;

		while (true) {
			diceValueAttacker = randomNumberBetweenTwoIntegers(1, 6);
			diceValueDefender = randomNumberBetweenTwoIntegers(1, 6);

			// compare dice numbers and reducing the number of armies.
			if (diceValueDefender >= diceValueAttacker)
				numberOfArmiesOfAttacker--;
			else
				numberOfArmiesOfDefender--;

			// if the armies of defender or attacker got ZERO, war finishes.
			if (numberOfArmiesOfDefender == 0 || numberOfArmiesOfAttacker == 0)
				break;
		}

		if (numberOfArmiesOfAttacker == 0)
			resultOfAttack = false;
		else
			resultOfAttack = true;

		return resultOfAttack;
	}

	public void ResultOfAttack(boolean resultOfAttack) {

		if (resultOfAttack == true)
			System.out.println("Attacker Won! The country is now yours :)");
		else
			System.out.println("Defender Won! You have lost your armies :(");

	}

	public void Move(int firstCountryID, int secondCountryID) {

	}
}