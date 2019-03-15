package Model;

import java.util.Random;

/**
 * This class is for the attack phase.
 * 
 * @author Farzad Torkaman
 */
public class Player {

	private int playerID;
	private String playerName;
	private int[] countryID;
	private int attackerCountryArmy;
	private int defenderCountryArmy;
	private int[] status;
	
	public Player(int defenderArmy , int attackerArmy, int[] status) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.countryID = countryID;
		this.defenderCountryArmy = defenderArmy;
		this.attackerCountryArmy = attackerArmy;
		this.status = status;
		
	}
	public int getDefenderArmy() {
		return defenderCountryArmy;
	}
	
	public int getAttackerArmy() {
		return attackerCountryArmy;
	}
	
	public int[] status() {
		return status;
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
	 * This method will simulate the attack when player attacks an adjacent country.
	 * 
	 * @param numberOfArmiesOfAttacker Number of armies that are inside attacker's
	 *                                 country.
	 * @param numberOfArmiesOfDefender Number of armies that are inside defender's
	 *                                 country.
	 * 
	 * @return resultOfAttack Result of the process.
	 */

	public AttackResponse attackCalculation(int attackerCountryArmy, int defenderCountryArmy, boolean fightStatus) {
        int diceValueAttacker;
		int diceValueDefender;
		AttackResponse attackResponse = new AttackResponse();
		//boolean status;
		Random rnd = new Random();
		while (true) {
			diceValueAttacker = rnd.nextInt(5)+1;
			diceValueDefender = rnd.nextInt(5)+1;

			// compare dice numbers and reducing the number of armies.
			if (diceValueDefender >= diceValueAttacker)
				attackerCountryArmy--;
			else
				defenderCountryArmy--;

			// if the armies of defender or attacker got ZERO, war finishes.
			if (defenderCountryArmy == 0 || attackerCountryArmy == 0) {
				
				if(defenderCountryArmy == 0) {
				//status[1] shows remain attacker army 
					attackResponse.setAttackStatus(true);
					attackResponse.setRestOfArmies(attackerCountryArmy);
				}
				//status[1] shows remain defender army 
				else {
					attackResponse.setAttackStatus(false);
					attackResponse.setRestOfArmies(attackerCountryArmy);
				}
				break;
			}
		}

		return attackResponse;
	}

	public void resultOfAttack() {

		if (status[0] == 1)
			System.out.println("Attacker Won! The country is now yours, your army is:)"+ attackerCountryArmy);
		else
			System.out.println("Defender Won! You have lost your armies :(");
			
	}

	public void move(int firstCountryID, int secondCountryID) {

	}
}