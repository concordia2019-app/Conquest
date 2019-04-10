package View;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import Controller.CardController;
import Controller.ConquestController;
import Controller.TournamentController;
import Helper.CountryHelper;
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
 * @author FarzadShamriz
 *
 */
public class ConquestUI implements IConquestUI {

	private String StartGameMenuMessage = "** Conquest Game **\r\n1.Start Game with Default Map. \r\n2.Start Game with Load Map \r\n3.Play Single Mode\r\n4.Run Tornoment\r\n5.Quit";
	private String FilePathForPlayers = (System.getProperty("user.dir")
			+ "\\src\\MapAndPlayerSavedFiles\\Players.json");
	private String ContinueReinforcementMessage = "Do you want to finish the reinforcement phase?(Y/N)";
	private String WrongInputString = "Your input is not acceptable.";
	private String InputNumberOfPlayers = "Enter number of players [2..5]:";
	private String InputNumberOfTournamentPlayers = "Enter number of players [2..4]:";
	private String ErrorEnteredValue = "Entered value is not acceptable.";
	private String ErrorInputNumberOfPlayers = "Your input number is not acceptable. Please enter a number between 2..5";
	private String SaveAndExitQuestion = "Do you want to save and exit the Game ?(Y/N)";
	private String UsePlayerCardQuestion = "Do you want to use your cards?(Y/N)";
	private String HandInCardsForceMessage = "Your cards will be converted to armies,\n because you have more than 4 cards.";
	private Scanner scanner;
	private String PlayerTypeTableTournament = "1.Aggressive \r\n2.Benevolent \r\n3.Random \r\n4.Cheater \r\n";
	private String PlayerTypeTable = "1.Aggressive \r\n2.Benevolent \r\n3.Random \r\n4.Cheater \r\n5.Human\r\n";
	private String FinishGame = " ||==================================================||\n"
			+ " ||**************************************************||\n"
			+ " ||                                                  ||\n"
			+ " ||    =======       /\\\\      |\\\\      /|  |*****    ||\n"
			+ " ||    ||  ___      /__\\\\     | \\\\    / |  |         ||\n"
			+ " ||    ||    ||    /    \\\\    |  \\\\  /  |  |*****    ||\n"
			+ " ||    ||____||   /      \\\\   |   \\\\/   |  |         ||\n"
			+ " ||                                         *****    ||\n"
			+ " ||     =====    \\\\       ////  |*****      ||****|  ||\n"
			+ " ||    ||   ||    \\\\     ////   |           ||    |  ||\n"
			+ " ||    ||   ||     \\\\   ////    |*****      ||*****  ||\n"
			+ " ||    ||   ||      \\\\ ////     |           ||  \\\\   ||\n"
			+ " ||     =====        ---        |*****      ||   \\\\  ||\n"
			+ " ||                                                  ||\n"
			+ " ||**************************************************||\n"
			+ " ||==================================================||\n";
	private String StartGame = " ||==================================================||\n"
			+ " ||**************************************************||\n"
			+ " ||                                                  ||\n"
			+ " ||    =======       /\\\\      |\\      /|  |*****     ||\n"
			+ " ||    ||  ___      /__\\\\     | \\    / |  |          ||\n"
			+ " ||    ||    ||    /    \\\\    |  \\  /  |  |*****     ||\n"
			+ " ||    ||____||   /      \\\\   |   \\/   |  |          ||\n"
			+ "                                           *****     ||\n"
			+ " ||                                                  ||\n"
			+ " ||     =====   =======    /\\\\      ||****| =======  ||\n"
			+ " ||    |           |      /__\\\\     ||    |    |     ||\n"
			+ " ||    |-----      |     /    \\\\    ||*****    |     ||\n"
			+ " ||          |     |    /      \\\\   ||  \\\\     |     ||\n"
			+ " ||     =====      |   /        \\\\  ||   \\\\    |     ||\n"
			+ " ||                                                  ||\n"
			+ " ||**************************************************||\n"
			+ " ||==================================================||\n";
	private MapView mapView = new MapView();
	private ConquestController conquestController = ConquestController.getInstance();
	private CountryHelper countryHelper;
	private Player[] Players;
	private ArrayList<Country> Countries;
	private Integer PlayerNumber;
	private ArrayList<String> PlayerNames = new ArrayList<String>();
	UIHelper uiHelper;
	private Map map;
	final int FirstArmiesNumberReinforcement = 3;
	private int calculatedArmiesForReinforcement = 0;
	private MapGenerator mapGenerator;
	private CardController cardController;

	private static ConquestUI instance;

	public static ConquestUI getInstance() {
		if (instance == null) {
			instance = new ConquestUI();
			return instance;
		} else {
			return instance;
		}

	}

	private ConquestUI() {
		mapGenerator = new MapGenerator();
		map = Map.getInstance();
		scanner = new Scanner(System.in);
		uiHelper = new UIHelper();
		countryHelper = new CountryHelper();
		cardController = new CardController();
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
		System.out.println(StartGame);
		String startMenuInput;
		while (true) {
			System.out.println(StartGameMenuMessage);
			startMenuInput = scanner.nextLine();
			if ((uiHelper.tryParseInt(startMenuInput)
					&& (Integer.parseInt(startMenuInput) >= 1 && Integer.parseInt(startMenuInput) < 6))) {
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
					boolean syncCountriesDataStatus = countryHelper.updateSourceCountriesArmies(Countries);
					while (true && syncCountriesDataStatus) {
						for (Player playerItem : Players) {

							System.out.println("Playr " + playerItem.getPlayerName() + " has "
									+ conquestController.playerPercentageCalculation(playerItem, Countries)
									+ " percentage of the map");

							int restOfReinforcementArmies = 0;
							CardsCounter playerCardsCounter = new CardsCounter();
							Countries = map.getCountries();
							if (playerItem.getReinforcementPlayerArmies() > 0) {
								restOfReinforcementArmies = reinforcementOfPlayer(
										playerItem.getReinforcementPlayerArmies(), playerItem);
								playerItem.setReinforcementPlayerArmies(restOfReinforcementArmies);
								calculatedArmiesForReinforcement = 0;
							}

							playerItem.attackPlayer(Countries);
							syncCountriesDataStatus = countryHelper.updateSourceCountriesArmies(Countries);
							Countries = map.getCountries();
							mapView.printMainMap(map.getCountries());

							int playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
							playerReinforcementArmyCount += FirstArmiesNumberReinforcement;
							playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
							playerReinforcementArmyCount = 0;
							ArrayList<Card> playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
							mapView.printMainMap(map.getCountries());
							CardView cardView = new CardView();
							if (playerItem.getAllowingCardStatus()) {
								CardController cardController = new CardController();
								Card cardToAssign = cardController.cardAssigner();
								Player CurrentSourcePlayer = uiHelper.getPlayerById(playerItem.getPlayerID());
								ArrayList<Card> playerItemCards = new ArrayList<Card>();
								for (Card cardItem : CurrentSourcePlayer.getCards()) {
									playerItemCards.add(cardItem);
								}
								playerItemCards.add(cardToAssign);
								playerItem.addCard(cardToAssign);
								playerItem.setCardCounts(playerCardsCounter);
								map.setPlayers(Players);
								playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
								playerItem.setAllowingStatus(false);
								playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
							}

							playerItem.movePlayer(Countries);
							syncCountriesDataStatus = countryHelper.updateSourceCountriesArmies(Countries);
							Countries = map.getCountries();

							Player currentPlayerItem = uiHelper.getPlayerById(playerItem.getPlayerID());
							if (currentPlayerItem.getCards().size() > 0) {
								cardView.printCardsPlayer(currentPlayerItem);
							}

							playerCardsCounter = cardController.defineCardsType(playerItem.getCards());
							playerItem.setCardCounts(playerCardsCounter);

							if (playerCards.size() > 2) {
								boolean cardHandInAnswer = conquestUiYesNoQuestion(UsePlayerCardQuestion);
								if (cardHandInAnswer) {
									calculatedArmiesForReinforcement = cardController
											.calculateArmiesCount(playerCardsCounter, playerCards);
									playerCardsCounter = cardController.defineCardsType(playerCards);
									playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
									playerReinforcementArmyCount += calculatedArmiesForReinforcement;
									playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
									playerReinforcementArmyCount = 0;
								} else {
									if (playerItem.getCards().size() >= 5) {
										System.out.println(HandInCardsForceMessage);
										playerCardsCounter = cardController.defineCardsType(playerCards);
										calculatedArmiesForReinforcement = cardController
												.calculateArmiesCount(playerCardsCounter, playerCards);
										playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
										playerReinforcementArmyCount += calculatedArmiesForReinforcement;
										playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
										playerReinforcementArmyCount = 0;
									}

								}
							}
							if (playerItem.getPlayerID() == Players[Players.length - 1].getPlayerID()) {
								boolean saveAndExitGame = conquestUiYesNoQuestion(SaveAndExitQuestion);
								if (saveAndExitGame) {
									mapGenerator.writeMap(map.getCountries(), getFilePathForWritingMap());
									ArrayList<Player> playerArrayList = new ArrayList<Player>();
									Player[] playersForWrite = map.getPlayers();
									for (int playerCounter = 0; playerCounter < playersForWrite.length; playerCounter++) {
										playerArrayList.add(playersForWrite[playerCounter]);
									}
									String playerSaveResult = mapGenerator.savePlayers(playerArrayList,
											FilePathForPlayers);
									System.exit(0);
								}
							}
							boolean finishGameStatus = conquestController.isGameFinish();
							if (finishGameStatus) {
								printFinishGame();
								break;
							}

						}
						// attackPlayer(Players, Countries);
						// movePlayer(Players, Countries);
					}
					// break;
				case 2:
					System.out.println("Loading new map.");
					boolean readFileStatus = getFilePathForLoadingMap();
					if (readFileStatus) {
						System.out.println("**   Game is started   **");
						// PlayerNumber = getNumberOfPlayer();
						// ArrayList<String> playerNamesInLoadMap = getPlayernames(PlayerNumber);
						// map.assigningPlayerCountries(playerNamesInLoadMap, PlayerNumber);

						ArrayList<Player> importPlayerList = mapGenerator.assignePlayers(FilePathForPlayers);
						Player[] playerTempArrayList = new Player[(importPlayerList.size())];
						if (importPlayerList.size() > 0) {
							for (int playerTempCounter = 0; playerTempCounter < importPlayerList
									.size(); playerTempCounter++) {
								playerTempArrayList[playerTempCounter] = importPlayerList.get(playerTempCounter);
								System.out.println("Player " + importPlayerList.get(playerTempCounter).getPlayerName()
										+ " is loaded.");
							}
							Players = playerTempArrayList;
							Map.getInstance().setPlayers(playerTempArrayList);
						}

						Countries = map.getCountries();
						Players = map.getPlayers();
						for (Player playerItem : Players) {
							reinforcementOfPlayer(FirstArmiesNumberReinforcement, playerItem);
						}
						boolean syncCountriesDataStatusInLoadMap = countryHelper.updateSourceCountriesArmies(Countries);
						while (true && syncCountriesDataStatusInLoadMap) {
							for (Player playerItem : Players) {

								System.out.println("Playr " + playerItem.getPlayerName() + " has "
										+ conquestController.playerPercentageCalculation(playerItem, Countries)
										+ " percentage of the map");

								int restOfReinforcementArmies = 0;
								CardsCounter playerCardsCounter = new CardsCounter();
								Countries = map.getCountries();
								if (playerItem.getReinforcementPlayerArmies() > 0) {
									restOfReinforcementArmies = reinforcementOfPlayer(
											playerItem.getReinforcementPlayerArmies(), playerItem);
									playerItem.setReinforcementPlayerArmies(restOfReinforcementArmies);
									calculatedArmiesForReinforcement = 0;
								}
								playerItem.attackPlayer(Countries);
								syncCountriesDataStatusInLoadMap = countryHelper.updateSourceCountriesArmies(Countries);
								Countries = map.getCountries();
								mapView.printMainMap(map.getCountries());

								int playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
								playerReinforcementArmyCount += FirstArmiesNumberReinforcement;
								playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
								playerReinforcementArmyCount = 0;
								ArrayList<Card> playerCards = uiHelper.getPlayerById(playerItem.getPlayerID())
										.getCards();
								mapView.printMainMap(map.getCountries());
								CardView cardView = new CardView();
								if (playerItem.getAllowingCardStatus()) {
									CardController cardController = new CardController();
									Card cardToAssign = cardController.cardAssigner();
									Player CurrentSourcePlayer = uiHelper.getPlayerById(playerItem.getPlayerID());
									ArrayList<Card> playerItemCards = new ArrayList<Card>();
									for (Card cardItem : CurrentSourcePlayer.getCards()) {
										playerItemCards.add(cardItem);
									}
									playerItemCards.add(cardToAssign);
									playerItem.addCard(cardToAssign);
									playerItem.setCardCounts(playerCardsCounter);
									map.setPlayers(Players);
									playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
									playerItem.setAllowingStatus(false);

									playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
								}

								playerItem.movePlayer(Countries);
								syncCountriesDataStatusInLoadMap = countryHelper.updateSourceCountriesArmies(Countries);
								Countries = map.getCountries();

								Player currentPlayerItem = uiHelper.getPlayerById(playerItem.getPlayerID());
								if (currentPlayerItem.getCards().size() > 0) {
									cardView.printCardsPlayer(currentPlayerItem);
								}

								playerCardsCounter = cardController.defineCardsType(playerItem.getCards());
								playerItem.setCardCounts(playerCardsCounter);

								if (playerCards.size() > 2) {
									boolean cardHandInAnswer = conquestUiYesNoQuestion(UsePlayerCardQuestion);
									if (cardHandInAnswer) {
										calculatedArmiesForReinforcement = cardController
												.calculateArmiesCount(playerCardsCounter, playerCards);
										playerCardsCounter = cardController.defineCardsType(playerCards);

										playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
										playerReinforcementArmyCount += calculatedArmiesForReinforcement;
										playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
										playerReinforcementArmyCount = 0;
									} else {
										if (playerItem.getCards().size() >= 5) {
											System.out.println(HandInCardsForceMessage);
											playerCardsCounter = cardController.defineCardsType(playerCards);
											calculatedArmiesForReinforcement = cardController
													.calculateArmiesCount(playerCardsCounter, playerCards);

											playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
											playerReinforcementArmyCount += calculatedArmiesForReinforcement;
											playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
											playerReinforcementArmyCount = 0;
										}

									}
								}
								if (playerItem.getPlayerID() == Players[Players.length - 1].getPlayerID()) {
									boolean saveAndExitGame = conquestUiYesNoQuestion(SaveAndExitQuestion);
									if (saveAndExitGame) {
										mapGenerator.writeMap(map.getCountries(), getFilePathForWritingMap());
										ArrayList<Player> playerArrayList = new ArrayList<Player>();
										for (int playerCounter = 0; playerCounter < map.getPlayers().length; map
												.getPlayers()) {
											playerArrayList.add(map.getPlayers()[playerCounter]);
										}
										String playerSaveResult = mapGenerator.savePlayers(playerArrayList,
												FilePathForPlayers);
										System.exit(0);
									}
								}
								boolean finishGameStatus = conquestController.isGameFinish();
								if (finishGameStatus) {
									printFinishGame();
									break;
								}

							}
							// attackPlayer(Players, Countries);
							// movePlayer(Players, Countries);
						}
					}

					// break;
				case 3:
					System.out.println("**   Game is started   **");
					PlayerNumber = getNumberOfPlayer();
					ArrayList<PlayerNameAndType> playerNamesAndTypes = getPlayerNamesAndTypes(PlayerNumber);
					Map.getInstance().assigningPlayerCountries(playerNamesAndTypes);
					Countries = map.getCountries();
					Players = map.getPlayers();
					boolean finishingSingeGameStatus = false;
					while (!finishingSingeGameStatus) {
						for (Player playerItem : Players) {
							switch (playerItem.getPlayerType()) {
							case AGGRESSIVE:
								AggressivePlayer aggressivePlayer = new AggressivePlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								aggressivePlayer.aggressiveReinforcementPlayer(Map.getInstance().getCountries());
								aggressivePlayer.aggressiveAttackPlayer(Map.getInstance().getCountries());
								if (conquestController.isGameFinish()) {

								}
								aggressivePlayer.aggressiveMovePlayer(Map.getInstance().getCountries());
								break;
							case BENOVOLENT:
								BenovolentPlayer benovolentPlayer = new BenovolentPlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								benovolentPlayer.reinforcementPlayer(Map.getInstance().getCountries());
								benovolentPlayer.attackPlayer(Map.getInstance().getCountries());
								benovolentPlayer.movePlayer(Map.getInstance().getCountries());
								break;
							case CHEATER:
								CheaterPlayer cheaterPlayer = new CheaterPlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								cheaterPlayer.setReinforcementPlayerArmies(playerItem.getReinforcementPlayerArmies());
								cheaterPlayer.attackPlayer(Map.getInstance().getCountries());
								cheaterPlayer.movePlayer(Map.getInstance().getCountries());
								break;
							case RANDOM:
								RandomPlayer randomPlayer = new RandomPlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								randomPlayer.reinforcementPlayer(Map.getInstance().getCountries());
								randomPlayer.attackPlayer(Map.getInstance().getCountries());
								randomPlayer.movePlayer(Map.getInstance().getCountries());
								break;
							default:
								humanPlayerBehavioursInSingleMode(playerItem);
							}
						}
						boolean syncCountriesDataStatusForSingleMode = countryHelper
								.updateSourceCountriesArmies(Countries);
					}

					break;
				case 4:
					TournamentController tournamentController = TournamentController.getTournamentControllerInstance();
					tournamentController.tournamentStart();

					// TODO run Tournament
					break;
				case 5:
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

	public void humanPlayerBehavioursInSingleMode(Player playerItem) {
		System.out.println("Playr " + playerItem.getPlayerName() + " has "
				+ conquestController.playerPercentageCalculation(playerItem, Countries) + " percentage of the map");

		int restOfReinforcementArmies = 0;
		CardsCounter playerCardsCounter = new CardsCounter();
		Countries = map.getCountries();
		if (playerItem.getReinforcementPlayerArmies() > 0) {
			restOfReinforcementArmies = reinforcementOfPlayer(playerItem.getReinforcementPlayerArmies(), playerItem);
			playerItem.setReinforcementPlayerArmies(restOfReinforcementArmies);
			calculatedArmiesForReinforcement = 0;
		}
		playerItem.attackPlayer(Countries);
		boolean syncCountriesDataStatusInLoadMap = countryHelper.updateSourceCountriesArmies(Countries);
		Countries = map.getCountries();
		mapView.printMainMap(map.getCountries());

		int playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
		playerReinforcementArmyCount += FirstArmiesNumberReinforcement;
		playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
		playerReinforcementArmyCount = 0;
		ArrayList<Card> playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
		mapView.printMainMap(map.getCountries());
		CardView cardView = new CardView();
		if (playerItem.getAllowingCardStatus()) {
			CardController cardController = new CardController();
			Card cardToAssign = cardController.cardAssigner();
			Player CurrentSourcePlayer = uiHelper.getPlayerById(playerItem.getPlayerID());
			ArrayList<Card> playerItemCards = new ArrayList<Card>();
			for (Card cardItem : CurrentSourcePlayer.getCards()) {
				playerItemCards.add(cardItem);
			}
			playerItemCards.add(cardToAssign);
			playerItem.addCard(cardToAssign);
			playerItem.setCardCounts(playerCardsCounter);
			map.setPlayers(Players);
			playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
			playerItem.setAllowingStatus(false);

			playerCards = uiHelper.getPlayerById(playerItem.getPlayerID()).getCards();
		}

		playerItem.movePlayer(Countries);
		syncCountriesDataStatusInLoadMap = countryHelper.updateSourceCountriesArmies(Countries);
		Countries = map.getCountries();

		Player currentPlayerItem = uiHelper.getPlayerById(playerItem.getPlayerID());
		if (currentPlayerItem.getCards().size() > 0) {
			cardView.printCardsPlayer(currentPlayerItem);
		}

		playerCardsCounter = cardController.defineCardsType(playerItem.getCards());
		playerItem.setCardCounts(playerCardsCounter);

		if (playerCards.size() > 2) {
			boolean cardHandInAnswer = conquestUiYesNoQuestion(UsePlayerCardQuestion);
			if (cardHandInAnswer) {
				calculatedArmiesForReinforcement = cardController.calculateArmiesCount(playerCards);
				playerCardsCounter = cardController.defineCardsType(playerCards);
				if (calculatedArmiesForReinforcement > 0) {
					playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
					playerReinforcementArmyCount += calculatedArmiesForReinforcement;
					playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
					playerReinforcementArmyCount = 0;
				}
				playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
				playerReinforcementArmyCount += calculatedArmiesForReinforcement;
				playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
				playerReinforcementArmyCount = 0;
			} else {
				if (playerItem.getCards().size() >= 5) {
					System.out.println(HandInCardsForceMessage);
					playerCardsCounter = cardController.defineCardsType(playerCards);
					calculatedArmiesForReinforcement = cardController.calculateArmiesCount(playerCards);
					if (calculatedArmiesForReinforcement > 0) {
						playerReinforcementArmyCount = playerItem.getReinforcementPlayerArmies();
						playerReinforcementArmyCount += calculatedArmiesForReinforcement;
						playerItem.setReinforcementPlayerArmies(playerReinforcementArmyCount);
						playerReinforcementArmyCount = 0;
					}

				}

			}
		}
		if (playerItem.getPlayerID() == Players[Players.length - 1].getPlayerID()) {
			boolean saveAndExitGame = conquestUiYesNoQuestion(SaveAndExitQuestion);
			if (saveAndExitGame) {
				mapGenerator.writeMap(map.getCountries(), getFilePathForWritingMap());
				ArrayList<Player> playerArrayList = new ArrayList<Player>();
				for (int playerCounter = 0; playerCounter < map.getPlayers().length; map.getPlayers()) {
					playerArrayList.add(map.getPlayers()[playerCounter]);
				}
				String playerSaveResult = mapGenerator.savePlayers(playerArrayList, FilePathForPlayers);
				System.exit(0);
			}
		}
		boolean finishGameStatus = conquestController.isGameFinish();
		if (finishGameStatus) {
			printFinishGame();
			System.exit(0);
		}

	}

	/**
	 * Get name and type of player To make players
	 * 
	 * @param numberOfPlayers - number of players for creation
	 * @return return a class model which is a list of players. Each player has name
	 *         and type to help to create specific player with specific behaviour.
	 */
	public ArrayList<PlayerNameAndType> getPlayerNamesAndTypesTournament(int numberOfPlayers) {
		ArrayList<PlayerNameAndType> playerAttrList = new ArrayList<PlayerNameAndType>();
		scanner.nextLine();
		System.out.format("Enter %d names of your players: \n", numberOfPlayers);
		for (int i = 0; i < numberOfPlayers; i++) {
			String playerName = "";
			int playerTypeID = 0;
			PlayerType playerType = PlayerType.HUMAN;
			while (true) {
				System.out.format("Enter name of player %d: ", i + 1);
				playerName = scanner.nextLine();
				System.out.print(PlayerTypeTableTournament);
				System.out.format("Choose type of player %d: ", i + 1);
				playerTypeID = Integer.parseInt(scanner.nextLine());
				if ((playerName.isEmpty() || playerName.length() > 16) && (playerTypeID > 0 && playerTypeID < 6)) {
					System.out.println(ErrorEnteredValue);
				} else {
					switch (playerTypeID) {
					case 1:
						playerType = PlayerType.AGGRESSIVE;
						break;
					case 2:
						playerType = PlayerType.BENOVOLENT;
						break;
					case 3:
						playerType = PlayerType.RANDOM;
						break;
					case 4:
						playerType = PlayerType.CHEATER;
						break;
					case 5:
						playerType = PlayerType.HUMAN;
						break;
					}
					PlayerNameAndType playerAttr = new PlayerNameAndType(playerName, playerType);
					playerAttrList.add(playerAttr);
					break;
				}
			}
			PlayerNames.add(playerName);
		}
		return playerAttrList;
	}

	/**
	 * Get name and type of player To make players
	 * 
	 * @param numberOfPlayers - number of players for creation
	 * @return return a class model which is a list of players. Each player has name
	 *         and type to help to create specific player with specific behaviour.
	 */
	public ArrayList<PlayerNameAndType> getPlayerNamesAndTypes(int numberOfPlayers) {
		ArrayList<PlayerNameAndType> playerAttrList = new ArrayList<PlayerNameAndType>();
		scanner.nextLine();
		System.out.format("Enter %d names of your players: \n", numberOfPlayers);
		for (int i = 0; i < numberOfPlayers; i++) {
			String playerName = "";
			int playerTypeID = 0;
			PlayerType playerType = PlayerType.HUMAN;
			while (true) {
				System.out.format("Enter name of player %d: ", i + 1);
				playerName = scanner.nextLine();
				System.out.print(PlayerTypeTableTournament);
				System.out.format("Choose type of player %d: ", i + 1);
				playerTypeID = Integer.parseInt(scanner.nextLine());
				if ((playerName.isEmpty() || playerName.length() > 16) && (playerTypeID > 0 && playerTypeID < 6)) {
					System.out.println(ErrorEnteredValue);
				} else {
					switch (playerTypeID) {
					case 1:
						playerType = PlayerType.AGGRESSIVE;
						break;
					case 2:
						playerType = PlayerType.BENOVOLENT;
						break;
					case 3:
						playerType = PlayerType.RANDOM;
						break;
					case 4:
						playerType = PlayerType.CHEATER;
						break;
					case 5:
						playerType = PlayerType.HUMAN;
						break;
					}
					PlayerNameAndType playerAttr = new PlayerNameAndType(playerName, playerType);
					playerAttrList.add(playerAttr);
					break;
				}
			}
			PlayerNames.add(playerName);
		}
		return playerAttrList;
	}

	public Player[] addCardToPlayer(Player[] playersForUpdate, Player playerItem) {
		Player[] updatedPlayers = new Player[playersForUpdate.length];
		for (int j = 0; j < playersForUpdate.length; j++) {
			updatedPlayers[j] = playersForUpdate[j];
			ArrayList<Card> calculatedPlayerCards = playerItem.getCards();
			if (playersForUpdate[j].getPlayerID() == playerItem.getPlayerID()) {
				for (Card cardItem : calculatedPlayerCards) {
					updatedPlayers[j].addCard(cardItem);
				}
			}
		}
		return updatedPlayers;
	}

	public Player[] resetAllowingCardToPlayerStatus(Player[] playersFORupdate, Player playerItem) {
		for (int j = 0; j < playersFORupdate.length; j++) {
			if (playersFORupdate[j].getPlayerID() == playerItem.getPlayerID()) {
				playersFORupdate[j].setAllowingStatus(false);
			}
		}
		return playersFORupdate;
	}

	private boolean getFilePathForLoadingMap() {
		System.out.println("Your file should be in this path : " + System.getProperty("user.dir")
				+ "\\bin\\ResourceProject\\CountrySample.json");
		String filePath = System.getProperty("user.dir") + "\\src\\MapAndPlayerSavedFiles\\CountrySample.json";
		ArrayList<Country> loadingListCountries = mapGenerator.mapReader(filePath);
		map.setCountries(loadingListCountries);
		if (loadingListCountries.size() > 0) {
			return true;
		}
		return false;
	}

	private String getFilePathForWritingMap() {
		return (System.getProperty("user.dir") + "\\src\\MapAndPlayerSavedFiles\\CountrySample.json");
	}

	public void printGameOver() {
		System.out.print(FinishGame);
	}

	public int getNumberOfMaps() {
		int numberOfMaps = 0;
		while (true) {
			System.out.print("Select number of tournament maps. [1..5] :");
			numberOfMaps = scanner.nextInt();
			if (numberOfMaps > 0 && numberOfMaps <= 5)
				break;
			else
				System.out.println(ErrorEnteredValue);
		}
		return numberOfMaps;
	}

	public int getNumberOfGamesPerMap() {
		int numberOfGamesPerMap = 0;
		while (true) {
			System.out.print("Select number of Games per maps. [1..5] :");
			numberOfGamesPerMap = scanner.nextInt();
			if (numberOfGamesPerMap > 0 && numberOfGamesPerMap <= 5)
				break;
			else
				System.out.println(ErrorEnteredValue);
		}
		return numberOfGamesPerMap;
	}

	public int getNumberOfTurnsPerGame() {
		int numberOfGamesPerMap = 0;
		while (true) {
			System.out.print("Select number of Games per maps. [10..50] :");
			numberOfGamesPerMap = scanner.nextInt();
			if (numberOfGamesPerMap >= 10 && numberOfGamesPerMap <= 50)
				break;
			else
				System.out.println(ErrorEnteredValue);
		}
		return numberOfGamesPerMap;
	}

	/**
	 * <p>
	 * This method give the path of note file to to load them in the map, then read
	 * the graphs and information about map and countries.
	 * </p>
	 * 
	 * 
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
	 * This method just ask from player if player want to attack Y to agree that
	 * want to attack N to skip the attack
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
			if (!checkPlayerNumber(num)) {
				System.out.println(ErrorInputNumberOfPlayers);
			} else
				return num;
		}
	}

	/**
	 * numberOfPlayer method will ask the player to enter an integer for number of
	 * players and save the number for the next part to ask the player for their
	 * names. if the player enter a number more than 5 or less than 2 he will get an
	 * error for entering a wrong number.
	 * 
	 * @return num is number of players that player entered.
	 */
	public int getNumberOfTournamentPlayer() {
		while (true) {
			System.out.print(InputNumberOfTournamentPlayers);
			int num = scanner.nextInt();
			if (!checkTournamentPlayerNumber(num)) {
				System.out.println(ErrorInputNumberOfPlayers);
			} else
				return num;
		}
	}

	public boolean checkTournamentPlayerNumber(int number) {
		if (number > 1 && number < 5) {
			return true;
		} else
			return false;
	}

	public boolean checkPlayerNumber(int number) {
		if (number > 1 && number <= 5) {
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

	/**
	 * This method give N number of armies to the players and they are able to add
	 * them to their armies who are in each country.
	 * 
	 * @param armiesNumber number of armies
	 * 
	 * @return rest of armies
	 */
	@Override
	public int reinforcementOfPlayer(int armiesNumber, Player player) {

		String inputArmiesNumberReinforcementStr = "";
		int inputArmiesNumberReinforcement = -1;
		int armiesNumberReinforcement = armiesNumber;
		int[] playerCountriesIdList = player.getCountryID();
		String countryIdStr = "";
		int countryId = -1;
		boolean finishReinforcementAnswer = conquestUiYesNoQuestion(ContinueReinforcementMessage);
		if (finishReinforcementAnswer) {
			return armiesNumber;
		}
		while (true) {
			countryIdStr = "";
			// Show list of player's countries
			mapView.printPlayerMap(player, Countries);
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
								mapView.printMainMap(Countries);
								return armiesNumberReinforcement;
							} else {
								finishReinforcementAnswer = conquestUiYesNoQuestion(ContinueReinforcementMessage);
								if (finishReinforcementAnswer) {
									System.out.println(
											"Reinforcement for player " + player.getPlayerName() + " is finished.");
									mapView.printMainMap(Countries);
									return armiesNumberReinforcement;
								}
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

	public void printFinishGame() {
		System.out.print(FinishGame);
	}
}
