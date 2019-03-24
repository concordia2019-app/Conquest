package Model;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import Controller.ConquestController;
import Helper.CountryHelper;
import Helper.PlayerHelper;
import Helper.UIHelper;
import View.ConquestUI;
import View.MapView;

/**
 * This class is related to player, related attributes and behavior.
 * 
 * @author AHasheminezhad
 */
public class Player {
	private String MoveQuestion = "Do you want to Move?(Y/N)";
	private String AttackQuestion = "Do you want to Attack?(Y/N)";
	private String ErrorEnteredValue = "Entered value is not acceptable.";
	private Scanner scanner = new Scanner(System.in);
	private int playerID;
	private String playerName;
	private int[] countryID;
	private ArrayList<Card> cards;
	private CardsCounter cardsCounter;
	private ConquestController conquestController = ConquestController.getInstance();
	private boolean allowToGetCard;

	public Player(int playerID, String playerName, int[] countryID) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.countryID = countryID;
		allowToGetCard = false;
		cards = new ArrayList<Card>();
	}

	public int getPlayerID() {
		return playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public CardsCounter getCardCounts() {
		return cardsCounter;
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

	public void setCards(ArrayList<Card> cardList) {
		cards.clear();
		for(Card cardItem : cardList) {
			cards.add(cardItem);
		}
	}

	public void addCard(Card cardToAdd) {
		this.cards.add(cardToAdd);
	}

	public boolean isMoreThanFive() {

		if (cards.size() >= 5)
			return true;
		else
			return false;
	}

	public boolean getAllowingCardStatus() {
		return this.allowToGetCard;
	}

	public void setAllowingStatus(boolean status) {
		this.allowToGetCard = status;
	}

	public void resultOfAttack(boolean resultOfAttack) {

		if (resultOfAttack == true)
			System.out.println("Attacker Won! The country is now yours :)");
		else
			System.out.println("Defender Won! You have lost your armies :(");

	}

	/**
	 * This method manage the attack of players
	 * 
	 * @param playerList  list of players to play one by one
	 * @param countryList list of countries to retrieve player's countries and
	 *                    Adjacent
	 */
	public ArrayList<Country> attackPlayer(ArrayList<Country> countryList) {
		Map map = Map.getInstance();
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
		mapView.printMainMap(map.getCountries());
		boolean attackAnswer = conquestUI.conquestUiYesNoQuestion(AttackQuestion);
		if (attackAnswer) {
			String enteredPlayerCountryId = "";
			int convertedPlayerCId = -1;
			String enteredEnemyCountryId = "";
			int convertedEnemyCId = -1;
			int[] relatedCountryIds = playerItem.getCountryID();
			boolean attackIsFinished = true;
			mapView.printMainMap(map.getCountries());
			System.out.println("Attack is started for player => " + playerItem.getPlayerName());
			while (attackIsFinished) {
				while (true) {
					mapView.printPlayerMap(playerItem, countryList);
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

				Country chosenEnemyCountry = null;
				while (true) {
					mapView.printAttackMap(playerItem, chosenPlayerCountry, countryList);
					System.out.println("Choose your enemy with enter the country Id:");
					enteredEnemyCountryId = scanner.next();
					if (enteredEnemyCountryId != "" && enteredEnemyCountryId != null
							&& uiHelper.tryParseInt(enteredEnemyCountryId)) {
						convertedEnemyCId = Integer.parseInt(enteredEnemyCountryId);
						chosenEnemyCountry = uiHelper.getCountryById(countryList, convertedEnemyCId);
						break;
					}
					System.out.println(ErrorEnteredValue);
					break;
				}

				System.out.println("You choose to attack to No." + convertedEnemyCId + " with country No."
						+ convertedPlayerCId + "   It will be calculated.");
				int numberOfArmiesForAttack = getNumberOfArmiesToAttack(countryList,
						chosenPlayerCountry.getCountryID());
				AttackResponse attackResponse = conquestController.attackCalculation(numberOfArmiesForAttack,
						chosenEnemyCountry.getArmy());
				int leftArmiesForAttackerCountry = ((chosenPlayerCountry.getArmy()) - numberOfArmiesForAttack);
				ArrayList<Country> updatedCountriesList = updateCountriesAfterAttack(countryList, chosenPlayerCountry,
						chosenEnemyCountry, playerItem, attackResponse, leftArmiesForAttackerCountry);
				mapView.printMainMap(map.getCountries());
				attackAnswer = conquestUI.conquestUiYesNoQuestion(AttackQuestion);
				if (!attackAnswer) {
					break;
				}
			}
		}
		return countryList;
	}

	/**
	 * this method give number of armies to attack and check the validity
	 * 
	 * @param countriesList - list of countries
	 * @param countryId     - id of country which is waiting to attack
	 * @return number of player as an integer
	 */
	public int getNumberOfArmiesToAttack(ArrayList<Country> countriesList, int countryId) {
		int armiyNumbers = 0;
		int currentArmies = 0;
		UIHelper uiHelper = new UIHelper();
		for (Country countryItem : countriesList) {
			int countryItemID = countryItem.getCountryID();
			if (countryItemID == countryId) {
				currentArmies = countryItem.getArmy();
				break;
			}
		}
		while (true) {
			System.out.print("Enter number of armies to attack or enter all to attack with all armies: ");
			String armyNumbersString = scanner.next();
			if (armyNumbersString.equalsIgnoreCase("all"))
				armyNumbersString = Integer.toString(currentArmies - 1);
			if (uiHelper.tryParseInt(armyNumbersString)) {
				armiyNumbers = Integer.parseInt(armyNumbersString);
				if (currentArmies <= armiyNumbers) {
					System.out.print(ErrorEnteredValue);
				} else
					break;
			} else
				System.out.print(ErrorEnteredValue);
			System.out.println();
		}
		return armiyNumbers;
	}

	public ArrayList<Country> updateCountriesAfterAttack(ArrayList<Country> countryList, Country attackerCountry,
			Country defenderCountry, Player attackerPlayer, AttackResponse attackResponse,
			int leftArmiesForAttackerCountry) {

		PlayerHelper playerHelper = new PlayerHelper();
		Map map = Map.getInstance();
		Player[] players = map.getPlayers();
		CountryHelper countryHelper = new CountryHelper();
		int restOfArmies = attackResponse.getRestOfArmies();
		boolean attackState = attackResponse.getAttackStatus();

		for (Country country : countryList) {
			if (country.getCountryID() == attackerCountry.getCountryID()) {
				country.setArmy(leftArmiesForAttackerCountry);
				countryHelper.updateCountryArmiesByObject(country);
				break;
			}
		}
		if (attackState) {
			// it means that the attacker is win
			allowToGetCard = true;
			for (Country country : countryList) {
				if (country.getCountryID() == defenderCountry.getCountryID()) {
					country.setArmy(restOfArmies);
					country.setPlayer(attackerPlayer.getPlayerID(), attackerPlayer.getPlayerName());
					countryHelper.updateCountryArmiesByObject(country);
					break;
				}
			}
			Player[] newPlayerArray = playerHelper.addCountryIDToPlayer(players, defenderCountry.getCountryID(),
					this.playerID);
			Player[] playerForUpdate = playerHelper.removeCountryIDToPlayer(newPlayerArray,
					defenderCountry.getCountryID(), defenderCountry.getPlayerID());
			map.setPlayers(playerForUpdate);
		} else {
			// it means that the attacker is loose
			for (Country country : countryList) {
				if (country.getCountryID() == defenderCountry.getCountryID()) {
					country.setArmy(restOfArmies);
					break;
				}
			}
		}

		// UpdateCountries
		boolean syncCountriesStatus = false;
		while (!syncCountriesStatus) {
			syncCountriesStatus = countryHelper.updateSourceCountriesArmies(countryList);
		}

		return countryList;
	}

	/**
	 * this method get source and target of country and number of armies to move and
	 * then calculate all move operation
	 * 
	 * @param countryList
	 */
	public void movePlayer(ArrayList<Country> countryList) {
		UIHelper uiHelper = new UIHelper();
		MapView mapView = new MapView();
		PlayerHelper playerHelper = new PlayerHelper();
		Map map = Map.getInstance();
		ConquestUI conquestUI = new ConquestUI();
		Player playerItem = this;
		String attackInfoTitle = "Attack for player - " + playerItem.getPlayerName() + " - \r\n";
		int attackInfoTitleLength = ("Attack for player - " + playerItem.getPlayerName() + " - \r\n").length();
		for (int i = 0; i < attackInfoTitleLength; i++) {
			attackInfoTitle += "=";
		}

		mapView.printMainMap(countryList);
		boolean moveAnswer = conquestUI.conquestUiYesNoQuestion(MoveQuestion);
		if (moveAnswer) {
			String enteredPlayerCountryId = "";
			int convertedPlayerCId = -1;
			String enteredCountryIdForMove = "";
			int convertCIdForMove = -1;
			int[] relatedCountryIds = playerItem.getCountryID();
			boolean movementIsFinished = true;
			System.out.println("Mmove is started for player => " + playerItem.getPlayerName());
			while (movementIsFinished) {
				while (true) {
					mapView.printPlayerMap(playerItem, countryList);
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
						mapView.printMoveMap(playerItem, chosenPlayerCountry, countryList);
						System.out.println("Choose your country as a target country with enter the Id:");
						enteredCountryIdForMove = scanner.next();
						if (enteredCountryIdForMove != "" && enteredCountryIdForMove != null
								&& uiHelper.tryParseInt(enteredCountryIdForMove)) {
							convertCIdForMove = Integer.parseInt(enteredCountryIdForMove);
							break;
						}
						System.out.println(ErrorEnteredValue);
					} else {
						System.out.println("There is no country as a target country to move.");
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
					} else {
						ArrayList<Country> updatedCountryList = map.getCountries();
						mapView.printMainMap(updatedCountryList);
						break;
					}
				}

				// End move
				moveAnswer = conquestUI.conquestUiYesNoQuestion(MoveQuestion);
				if (!moveAnswer) {
					mapView.printMainMap(map.getCountries());
					break;
				}
			}
		}
	}
}