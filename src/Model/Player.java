package Model;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Helper.PlayerHelper;
import Helper.UIHelper;
import View.ConquestUI;
import View.MapView;

/**
 * This class is for the attack phase.	
 * 
 * @author AHasheminezhad
 */
public class Player {
	private String MoveQuestion = "Do you want to Move?(Y/N)";
	private String AttackQuestion = "Do you want to Attack?(Y/N)";
	private String ErrorEnteredValue = "Entered value is not acceptable.";
	private Scanner scanner= new Scanner(System.in);
	private int playerID;
	private String playerName;
	private int[] countryID;
	private ArrayList<Card> cards;

	
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
	
	public ArrayList<Card> getCards() {
		return cards;
	}
	
	public void setCards(int cardTypeIndex) {
		//cards.add(CardController)
	}
	
	public boolean isMoreThanFive() {
		
		if(cards.size() >= 5)
			return true;
		else
			return false;
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

	public boolean attack(int numberOfArmiesOfAttacker, int numberOfArmiesOfDefender) {
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

	public void resultOfAttack(boolean resultOfAttack) {

		if (resultOfAttack == true)
			System.out.println("Attacker Won! The country is now yours :)");
		else
			System.out.println("Defender Won! You have lost your armies :(");

	}

	public void move(int firstCountryID, int secondCountryID) {

	}

	/**
	 * This method manage the attack of players
	 * 
	 * @param playerList  list of players to play one by one
	 * @param countryList list of countries to retrieve player's countries and
	 *                    adjacencies
	 */
	public void attackPlayer(ArrayList<Country> countryList) {
		Map map = new Map();
		MapView mapView = new MapView();
		ConquestUI conquestUI = new ConquestUI();
		UIHelper uiHelper = new UIHelper();
		Player playerItem = this;
		String attackInfoTitle = "Attack for player - " + playerItem.getPlayerName() + " - \r\n";
		int attackInfoTitleLength = ("Attack for player - " + playerItem.getPlayerName() + " - \r\n").length();
		for (int i = 0; i < attackInfoTitleLength; i++) {
			attackInfoTitle += "=";
		}
		System.out.println(attackInfoTitle);

		boolean attackAnswer = conquestUI.conquestUiYesNoQuestion(AttackQuestion);
		if (attackAnswer) {
			String enteredPlayerCountryId = "";
			int convertedPlayerCId = -1;
			String enteredEnemyCountryId = "";
			int convertedEnemyCId = -1;
			int[] relatedCountryIds = playerItem.getCountryID();
			boolean attackIsFinished = true;
			System.out.println("Attack is started for player => " + playerItem.getPlayerName());
			while (attackIsFinished) {
				while (true) {
					mapView.printPlayerMap(playerItem,countryList);
					System.out.println("Choose your country Id to attack:");
					enteredPlayerCountryId = scanner.next();
					if (enteredPlayerCountryId != "" && enteredPlayerCountryId != null
							&& uiHelper.tryParseInt(enteredPlayerCountryId)) {
						convertedPlayerCId = Integer.parseInt(enteredPlayerCountryId);
						break;
					}
					System.out.println(ErrorEnteredValue);
					break;
				}

				Country chosenPlayerCountry = uiHelper.getCountryById(countryList, convertedPlayerCId);
				int[] adjacaniesIds = chosenPlayerCountry.getAdjacentCountriesID();

				while (true) {
					mapView.printAttackMap(playerItem, chosenPlayerCountry,countryList);
					System.out.println("Choose your enemy with enter the country Id:");
					enteredEnemyCountryId = scanner.next();
					if (enteredEnemyCountryId != "" && enteredEnemyCountryId != null
							&& uiHelper.tryParseInt(enteredEnemyCountryId)) {
						convertedEnemyCId = Integer.parseInt(enteredEnemyCountryId);
						break;
					}
					System.out.println(ErrorEnteredValue);
					break;
				}

				System.out.println("You chose to attack to No." + convertedEnemyCId + " with country No."
						+ convertedPlayerCId + "   It will be calculated.");

				attackAnswer = conquestUI.conquestUiYesNoQuestion(AttackQuestion);
				if (!attackAnswer) {
					break;
				}
			}
		}

	}

	public void movePlayer(ArrayList<Country> countryList) {
		UIHelper uiHelper = new UIHelper();
		MapView mapView = new MapView();
		PlayerHelper playerHelper = new PlayerHelper();
		Map map = new Map();
		ConquestUI conquestUI = new ConquestUI();
		Player playerItem = this;
		String attackInfoTitle = "Attack for player - " + playerItem.getPlayerName() + " - \r\n";
		int attackInfoTitleLength = ("Attack for player - " + playerItem.getPlayerName() + " - \r\n").length();
		for (int i = 0; i < attackInfoTitleLength; i++) {
			attackInfoTitle += "=";
		}
		System.out.println(attackInfoTitle);

		boolean attackAnswer = conquestUI.conquestUiYesNoQuestion(MoveQuestion);
		if (attackAnswer) {
			String enteredPlayerCountryId = "";
			int convertedPlayerCId = -1;
			String enteredCountryIdForMove = "";
			int convertCIdForMove = -1;
			int[] relatedCountryIds = playerItem.getCountryID();
			boolean movementIsFinished = true;
			System.out.println("Mmove is started for player => " + playerItem.getPlayerName());
			while (movementIsFinished) {
				while (true) {
					mapView.printPlayerMap(playerItem,countryList);
					System.out.println("Choose your country Id to move:");
					enteredPlayerCountryId = scanner.next();
					if (enteredPlayerCountryId != "" && enteredPlayerCountryId != null
							&& uiHelper.tryParseInt(enteredPlayerCountryId)) {
						convertedPlayerCId = Integer.parseInt(enteredPlayerCountryId);
						break;
					}
					System.out.println(ErrorEnteredValue);
					break;
				}

				Country chosenPlayerCountry = uiHelper.getCountryById(countryList, convertedPlayerCId);
				ArrayList<Country> adjacaniesIds = map.getSpecificCountryAdjacentsForMove(playerItem.getCountryID(),
						chosenPlayerCountry.getCountryID());

				while (true) {
					if (adjacaniesIds.size() > 0) {
						mapView.printMoveMap(playerItem, chosenPlayerCountry,countryList);
						System.out.println("Choose your country as a target country with enter the Id:");
						enteredCountryIdForMove = scanner.next();
						if (enteredCountryIdForMove != "" && enteredCountryIdForMove != null
								&& uiHelper.tryParseInt(enteredCountryIdForMove)) {
							convertCIdForMove = Integer.parseInt(enteredCountryIdForMove);
							break;
						}
						System.out.println(ErrorEnteredValue);
						break;
					} else {
						System.out.println("There is no country as a target country to move.");
						break;
					}

				}
				// Move calculation
				boolean isMoveSuccessful = false;
				while (!isMoveSuccessful) {
					System.out.println("Enter number of armies to move.");
					int armiesForMovement = scanner.nextInt();
					isMoveSuccessful = playerHelper.calculationOfMovement(convertedPlayerCId, convertCIdForMove,
							armiesForMovement);
					if (!isMoveSuccessful) {
						System.out.println(ErrorEnteredValue);
					}
				}

				// End move
				attackAnswer = conquestUI.conquestUiYesNoQuestion(MoveQuestion);
				if (!attackAnswer) {
					break;
				}
			}
		}
	}
}