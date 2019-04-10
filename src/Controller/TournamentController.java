package Controller;

import java.util.ArrayList;
import Model.AggressivePlayer;
import Model.BenovolentPlayer;
import Model.CheaterPlayer;
import Model.Country;
import Model.Map;
import Model.MapGenerator;
import Model.Player;
import Model.PlayerNameAndType;
import Model.RandomPlayer;
import Model.TournamentResult;
import View.ConquestUI;
import View.TournamentView;

/**
 * This class is for control of tournament mode
 * 
 * @author FarzadShamriz
 *
 */
public class TournamentController {

	private static TournamentController instance;

	/*
	 * creating in instance from the object singleton
	 */
	public static TournamentController getTournamentControllerInstance() {
		if (instance == null)
			instance = new TournamentController();
		return instance;
	}

	/**
	 * Main controller of tournament. It's responsible for handle Players list and
	 * run reinforcement, attack, and move of each one
	 */
	public void tournamentStart() {
		ConquestUI conquestUI = ConquestUI.getInstance();
		int playerNumber = conquestUI.getNumberOfTournamentPlayer();
		ArrayList<PlayerNameAndType> playerNamesAndTypes = conquestUI.getPlayerNamesAndTypes(playerNumber);
		int numberOfMaps = conquestUI.getNumberOfMaps();
		int numberOfGamesPerMap = conquestUI.getNumberOfGamesPerMap();
		int numberOfTurnsPerGame = conquestUI.getNumberOfTurnsPerGame();
		ArrayList<TournamentResult> tournamentResults = new ArrayList<TournamentResult>();

		// Map Counter loop
		for (int mapCounter = 0; mapCounter < numberOfMaps; mapCounter++) {
			boolean readFileStatusMap = getFilePathForLoadingMap(System.getProperty("user.dir")
					+ "\\src\\TournamentMaps\\CountryTournament" + (mapCounter + 1) + ".json");
			Map.getInstance().assigningPlayerCountries(playerNamesAndTypes);
			Player[] players = Map.getInstance().getPlayers();
			ConquestController conquestController = ConquestController.getInstance();

			// If map loaded completely
			if (readFileStatusMap) {

				// Game counter loop
				for (int gameCounter = 0; gameCounter < numberOfGamesPerMap; gameCounter++) {

					// Turn counter loop
					for (int turnCounter = 0; turnCounter < numberOfTurnsPerGame; turnCounter++) {
						players = Map.getInstance().getPlayers();
						for (Player playerItem : players) {
							switch (playerItem.getPlayerType()) {
							case AGGRESSIVE:
								AggressivePlayer aggressivePlayer = new AggressivePlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								aggressivePlayer.aggressiveReinforcementPlayer(Map.getInstance().getCountries());
								aggressivePlayer.aggressiveAttackPlayer(Map.getInstance().getCountries());
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
								cheaterPlayer.cheaterReinforcementPlayer(Map.getInstance().getCountries());
								cheaterPlayer.cheaterAttackPlayer(Map.getInstance().getCountries());
								cheaterPlayer.cheaterMovePlayer(Map.getInstance().getCountries());
								break;
							case RANDOM:
								RandomPlayer randomPlayer = new RandomPlayer(playerItem.getPlayerID(),
										playerItem.getPlayerName(), playerItem.getCountryID());
								randomPlayer.reinforcementPlayer(Map.getInstance().getCountries());
								randomPlayer.attackPlayer(Map.getInstance().getCountries());
								randomPlayer.movePlayer(Map.getInstance().getCountries());
								break;
							default:
								break;
							}
						}
					}
					TournamentResult tournamentResult = new TournamentResult();
					tournamentResult.setGameIndex(gameCounter);
					tournamentResult.setMapIndex(mapCounter);
					tournamentResult.setWinnerName(getWinnerName(Map.getInstance().getCountries()));
					tournamentResults.add(tournamentResult);
				}

			}

		}
		TournamentView tournamentView = new TournamentView();
		tournamentView.tournamentResult(Map.getInstance().getCountries(), numberOfMaps, playerNumber,
				numberOfGamesPerMap, tournamentResults);
		// Map map = Map.getInstance();
		// map.assigningPlayerCountries(playerNamesAndTypes);
		conquestUI.printGameOver();

	}

	/**
	 * This method get a list of countries and select the winner Player. Then return
	 * name of mentioned player.
	 * 
	 * @param countryList - list of countries
	 * @return Name of player as a string
	 */
	public String getWinnerName(ArrayList<Country> countryList) {
		ArrayList<Integer> playerScores = new ArrayList<Integer>();
		int p1 = 0;
		int p2 = 0;
		int p3 = 0;
		int p4 = 0;
		int p5 = 0;
		int pScoreTemp = 0;

		for (Country country : countryList) {
			switch (country.getPlayerID()) {
			case 1:
				pScoreTemp = p1;
				p1 = (pScoreTemp + 1);
				break;
			case 2:
				pScoreTemp = p2;
				p2 = (pScoreTemp + 1);
				break;
			case 3:
				pScoreTemp = p3;
				p3 = (pScoreTemp + 1);
				break;
			case 4:
				pScoreTemp = p4;
				p4 = (pScoreTemp + 1);
				break;
			case 5:
				pScoreTemp = p5;
				p5 = (pScoreTemp + 1);
				break;
			}
		}
		playerScores.add(p1);
		playerScores.add(p2);
		playerScores.add(p3);
		playerScores.add(p4);
		playerScores.add(p5);
		int winnerId = 0;
		for (int j = 1; j < 5; j++) {
			if (playerScores.get(winnerId) < playerScores.get(j)) {
				winnerId = j;
			}
		}
		for (Country country : countryList) {
			if (country.getPlayerID() == (winnerId + 1))
				return (country.getPlayerName());
		}
		return "-";
	}

	/**
	 * Getting file path for reading tournament map
	 * 
	 * @param filePath - path of file as an string
	 * @return result of reading file and creating countries as boolean parameter
	 */
	private boolean getFilePathForLoadingMap(String filePath) {
		MapGenerator mapGenerator = new MapGenerator();
		ArrayList<Country> loadingListCountries = mapGenerator.mapReader(filePath);
		Map map = Map.getInstance();
		map.setCountries(loadingListCountries);
		if (loadingListCountries.size() > 0) {
			return true;
		}
		return false;
	}
}
