package View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import Helper.UIHelper;
import Model.*;

/**
 * This is a class to show messages below
 * <ul>
 * <li>Showing the map with related adjacencies</li>
 * <li>showing the menu to select map, attach or move of player</li>
 * <li>showing the list of countries and adjacencies</li>
 * </ul>
 * 
 * @author F.S
 *
 */

public class ConquestUI implements IConquestUI {

	private String StartGameMenuMessage = "** Conquest Game **\r\n1.Start Game with Default Map. \r\n2.Start Game with Load Map \r\n3.Quit";
	private String AttackQuestion = "Do you want to Attack?(Y/N)";
	private String MoveQuestion = "Do you want to Move?(Y/N)";
	private String WrongInputString = "Your input is not acceptable.";
	private String AttackIsFinished = "The attack is finished.";
	private String SelectYourCountryID = "Please select your country ID.";
	private String InputNumberOfPlayers = "Enter bumber of players [2..5]:";
	private String ErrorEnteredValue = "Entered value is not acceptable.";
	private String ErrorInputNumberOfPlayers = "Your input number is not acceptable. Please enter a number between 2..5";
	private String AttackFinishQuestion = "Is attack finished ?(Y/N)";
	private Scanner scanner;

	private Player[] Players;
	private ArrayList<Country> Countries;
	private Integer PlayerNumber;
	private ArrayList<String> PlayerNames = new ArrayList<String>();
	UIHelper uiHelper;
	private Map map = new Map();
	final int FirstArmiesNumberReinforcement = 3;
	private MapGenerator mapGenerator = new MapGenerator();
	private String ErrorFileRead = "Your file not found or maybe is not in correct format. please check and try again.";

	public ConquestUI() {
		scanner = new Scanner(System.in);
		uiHelper = new UIHelper();
	}

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
	@Override
	public void conquestUIShowStartMenu() {
		String startMenuInput;
		while (true) {
			System.out.println(StartGameMenuMessage);
			startMenuInput = scanner.nextLine();
			if ((uiHelper.tryParseInt(startMenuInput)
					&& (Integer.parseInt(startMenuInput) >= 1 && Integer.parseInt(startMenuInput) < 4))) {
				Integer parsedInputValue = Integer.parseInt(startMenuInput);
				switch (parsedInputValue) {
				case 1:
					System.out.println("**   Game is started   **");
					PlayerNumber = getNumberOfPlayer();
					ArrayList<String> playerNames = getPlayernames(PlayerNumber);
					map.assigningPlayerCountries(playerNames, PlayerNumber);
					Countries = map.getCountries();
					Players = map.getPlayers();
					for (Player playerItem : Players) {
						reinforcementOfPlayer(FirstArmiesNumberReinforcement, playerItem);
					}
					while (true) {
						attackPlayer(Players, Countries);
						movePlayer(Players, Countries);
					}
					// break;
				case 2:
					System.out.println("Loading new map.");
					boolean readFileStatus = getFilePathForLoadingMap();
					while (true) {
						if (readFileStatus) {
							System.out.println("**   Game is started   **");
							PlayerNumber = getNumberOfPlayer();
							ArrayList<String> playerNamesInLoadMap = getPlayernames(PlayerNumber);
							map.assigningPlayerCountries(playerNamesInLoadMap, PlayerNumber);
							Countries = map.getCountries();
							Players = map.getPlayers();
							for (Player playerItem : Players) {
								reinforcementOfPlayer(FirstArmiesNumberReinforcement, playerItem);
							}

							while (true) {
								attackPlayer(Players, Countries);
								movePlayer(Players, Countries);
								//TODO check game is finished or not
								break;
							}
							break;
						} else {
							System.out.println(ErrorFileRead);
						}
					}

					// break;
				case 3:
					System.out.println("quit.");
					System.exit(0);
					break;

				default:
					System.out.println("Entered value is not acceptable.[0..2]");
					break;
				}
				break;
			} else
				System.out.println("Entered value is not acceptable.[0..2]");
		}

	}

	private boolean getFilePathForLoadingMap() {
		System.out.println("Your file should be in this path : " + System.getProperty("user.dir")
				+ "\\bin\\ResourceProject\\CountrySample.json");
		String filePath = System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json";
		ArrayList<Country> loadingListCountries = mapGenerator.MapReader(filePath);
		map.setCountries(loadingListCountries);
		if (loadingListCountries.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * <p>
	 * This method give the path of note file to to load them in the map, then read
	 * the graphs and information about map and countries.
	 * </p>
	 * 
	 * @throws FileNotFoundException
	 */
	@Override
	public void conquestUIGetNodes() {
		System.out.println("Enter the path of your map: ");
		String nodePath = scanner.nextLine();
		String nodeFileContent;
		if (!nodePath.toLowerCase().contains(".txt")) {
			nodePath += ".txt";
		}
		try {
			FileReader fileReader = new FileReader(nodePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((nodeFileContent = bufferedReader.readLine()) != null) {
				System.out.println(nodeFileContent);
				System.out.println("I can detect that there is another line of text.");
			}
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + nodePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + nodePath + "'");
		}
	}

	/**
	 * This method just ask from player if player want to attack Y => to agree that
	 * want to attack N => to skip the attack
	 * 
	 * @return if player pass n , then return false, on the other hand, if pass y,
	 *         then return true
	 */
	@Override
	public boolean conquestUiYesNoQuestion(String strQuestion) {
		while (true) {
			System.out.println(strQuestion);
			String attackDecision = scanner.next();
			if (attackDecision.equalsIgnoreCase("n") || attackDecision == "N") {
				System.out.println("Attack is finieshed.");
				return false;
			} else if (attackDecision.equalsIgnoreCase("y") || attackDecision == "Y") {
				System.out.println("Attack is started.");
				// attackPlayer(Players, Countries);
				return true;
			} else {
				System.out.println(WrongInputString);
			}
		}
	}

	/**
	 * This method just ask from player if player want to move Y => to agree that
	 * want to move N => to skip the move
	 */
	@Override
	public void conquestUIMove() {
		while (true) {
			System.out.println(MoveQuestion);
			String attackDecision = scanner.nextLine();
			if (attackDecision.equalsIgnoreCase("n") || attackDecision == "N") {
				// no for move
				System.out.println("Move is finished. The Game should turn.");
				break;
			} else if (attackDecision.equalsIgnoreCase("y") || attackDecision == "Y") {
				movePlayer(Players, Countries);
				break;
			} else {
				System.out.println(WrongInputString);
			}
		}
	}

	/**
	 * This method shows that the attack is finished and call the move method
	 */
	@Override
	public void conquestUIFinish() {
		System.out.println(AttackIsFinished);
		conquestUIMove();
	}

	/**
	 * This method give the country Id from player and check the validity of it.
	 * Then call the attack method.
	 * 
	 * @param playerCountries this is a list of countries
	 */
	@Override
	public void conquestUIGetCountryIdForAttack(List<Country> playerCountries) {
		for (int i = 1; i <= playerCountries.size(); i++) {
			System.out.println(i + playerCountries.get(i).getCountryName());
		}
		System.out.println(SelectYourCountryID);
		String countryId = scanner.nextLine();
		// TODO Implement Attack and call it
	}

	/**
	 * numberOfPlayer method will ask the player to enter an integer for number of
	 * players and save the number for the next part to ask the player for their
	 * names. if the player enter a number more than 5 or less than 2 he will get an
	 * error for entering a wrong number.
	 * 
	 * @return num is number of players that player entered.
	 */
	@Override
	public int getNumberOfPlayer() {
		while (true) {
			System.out.print(InputNumberOfPlayers);
			int num = scanner.nextInt();
			if (checkPlayerNumber(num)) {
				System.out.println(ErrorInputNumberOfPlayers);
			} else
				return num;
		}
	}

	public boolean checkPlayerNumber(int number) {
		if (number < 1 || number >= 5) {
			return true;
		} else
			return false;
	}

	/**
	 * This method check the count of armies which player want to leave in moving
	 * 
	 * @return an object which contains CountryId and Number of left armies in that
	 *         country
	 */
	@Override
	public LeftArmiesResponse numberOfArmiesToleave(Country country) {
		while (true) {
			System.out.println("Enter number of armies which you want to leave: 0");
			String leftArmies = scanner.nextLine();
			Integer currentArmiyNumbers = country.getArmy();
			if (uiHelper.tryParseInt(leftArmies)) {
				Integer inputArmyNumbers = Integer.parseInt(leftArmies);
				if (inputArmyNumbers < currentArmiyNumbers) {
					return new LeftArmiesResponse(country.getCountryID(), inputArmyNumbers);
				}
			}
			System.out.println(ErrorEnteredValue);
		}
	}

	/**
	 * nameOfPlayer method will get the players name and show the in console.
	 * 
	 * @param n is a number that player must enter the names of the players.
	 * @return Names that player enter names for each player.
	 */
	public ArrayList<String> getPlayernames(int n) {
		scanner.nextLine();
		System.out.format("Enter %d names of your players: \n", n);
		for (int i = 0; i < n; i++) {
			String playerName = "";
			while (true) {
				System.out.format("Enter name of player %d: ", i + 1);
				playerName = scanner.nextLine();
				if (playerName.isEmpty() || playerName.length() > 16) {
					System.out.println(ErrorEnteredValue);
				} else
					break;
			}
			PlayerNames.add(playerName);
		}
		return PlayerNames;
	}

	public void movePlayer(Player[] playerList, ArrayList<Country> countryList) {
		for (Player playerItem : Players) {
			String attackInfoTitle = "Attack for player - " + playerItem.getPlayerName() + " - \r\n";
			int attackInfoTitleLength = ("Attack for player - " + playerItem.getPlayerName() + " - \r\n").length();
			for (int i = 0; i < attackInfoTitleLength; i++) {
				attackInfoTitle += "=";
			}
			System.out.println(attackInfoTitle);

			boolean attackAnswer = conquestUiYesNoQuestion(MoveQuestion);
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
						map.printPlayerMap(playerItem);
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
							map.printMoveMap(playerItem, chosenPlayerCountry);
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
						isMoveSuccessful = calculationOfMovement(convertedPlayerCId, convertCIdForMove,
								armiesForMovement);
						if (!isMoveSuccessful) {
							System.out.println(ErrorEnteredValue);
						}
					}

					// End move
					attackAnswer = conquestUiYesNoQuestion(MoveQuestion);
					if (!attackAnswer) {
						break;
					}
				}
			}
		}
	}

	public boolean calculationOfMovement(int fromCountryId, int toCountryId, int armyNumbToMove) {
		Country sourceCountry = uiHelper.getCountryById(Countries, fromCountryId);
		int sourceCountryArmies = sourceCountry.getArmy();
		Country targegCountry = uiHelper.getCountryById(Countries, toCountryId);
		int targetCountryArmies = targegCountry.getArmy();

		if ((sourceCountryArmies - 1) < armyNumbToMove) {
			return false;
		} else {
			decreaseCountryArmies(fromCountryId, armyNumbToMove);
			increaseCountryArmies(toCountryId, armyNumbToMove);
			return true;
		}

	}

	public void increaseCountryArmies(int Cid, int armiesNumber) {
		for (Country country : Countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies - armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	public void decreaseCountryArmies(int Cid, int armiesNumber) {
		for (Country country : Countries) {
			int currentCountryId = country.getCountryID();
			int currentCountryArmies = country.getArmy();
			if (currentCountryId == Cid) {
				int calculatedArmies = currentCountryArmies + armiesNumber;
				country.setArmy(calculatedArmies);
			}
		}
	}

	/**
	 * This method manage the attack of players
	 * 
	 * @param playerList  list of players to play one by one
	 * @param countryList list of countries to retrieve player's countries and
	 *                    adjacencies
	 */
	@Override
	public void attackPlayer(Player[] playerList, ArrayList<Country> countryList) {
		for (Player playerItem : Players) {
			String attackInfoTitle = "Attack for player - " + playerItem.getPlayerName() + " - \r\n";
			int attackInfoTitleLength = ("Attack for player - " + playerItem.getPlayerName() + " - \r\n").length();
			for (int i = 0; i < attackInfoTitleLength; i++) {
				attackInfoTitle += "=";
			}
			System.out.println(attackInfoTitle);

			boolean attackAnswer = conquestUiYesNoQuestion(AttackQuestion);
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
						map.printPlayerMap(playerItem);
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
						map.printAttackMap(playerItem, chosenPlayerCountry);
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

					attackAnswer = conquestUiYesNoQuestion(AttackQuestion);
					if (!attackAnswer) {
						break;
					}
				}
			}

		}
	}

	/**
	 * This method give N number of armies to the players and they are able to add
	 * them to their armies who are in each country.
	 * 
	 * @param armiesNumber number of armies
	 * @player current player to separate countries and assign armies
	 */
	@Override
	public void reinforcementOfPlayer(int armiesNumber, Player player) {
		String inputArmiesNumberReinforcementStr = "";
		int inputArmiesNumberReinforcement = -1;
		int armiesNumberReinforcement = armiesNumber;
		int[] playerCountriesIdList = player.getCountryID();
		String countryIdStr = "";
		int countryId = -1;

		while (true) {
			countryIdStr = "";
			// Show list of player's countries
			map.printPlayerMap(player);
			// Show list of player's countries

			System.out.println("-- Reinforcement for player " + player.getPlayerName() + " is started.");
			System.out.println("There are -" + armiesNumberReinforcement + "- armies to assign to your countries.");
			System.out.println("Choose your country that you want to add armies: ");

			countryIdStr = scanner.next();
			if (uiHelper.tryParseInt(countryIdStr)) {
				countryId = Integer.parseInt(countryIdStr);
				if (uiHelper.isIdExistInList(playerCountriesIdList, countryId)) {
					System.out.println("Enter number of armies to add to selected country: ");
					inputArmiesNumberReinforcementStr = scanner.next();
					if (uiHelper.tryParseInt(inputArmiesNumberReinforcementStr)) {
						inputArmiesNumberReinforcement = Integer.parseInt(inputArmiesNumberReinforcementStr);
						if (inputArmiesNumberReinforcement > 0
								&& inputArmiesNumberReinforcement <= armiesNumberReinforcement) {
							uiHelper.addArmiesToCountryById(countryId, Countries, inputArmiesNumberReinforcement);
							armiesNumberReinforcement -= inputArmiesNumberReinforcement;
							if (armiesNumberReinforcement < 1) {
								System.out.println(
										"Reinforcement for player " + player.getPlayerName() + " is finished.");
								map.printMainMap();
								break;
							}
						} else
							System.out.println(ErrorEnteredValue);
					} else
						System.out.println(ErrorEnteredValue);
				} else
					System.out.println(ErrorEnteredValue);
			} else
				System.out.println(ErrorEnteredValue);
		}

	}

}