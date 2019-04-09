package Controller;

import java.util.ArrayList;

import javax.activation.MailcapCommandMap;

import Model.AggressivePlayer;
import Model.BenovolentPlayer;
import Model.CheaterPlayer;
import Model.Country;
import Model.Map;
import Model.MapGenerator;
import Model.Player;
import Model.PlayerNameAndType;
import Model.RandomPlayer;
import View.ConquestUI;

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

	public void tournamentStart() {
		ConquestUI conquestUI = ConquestUI.getInstance();
		int playerNumber = conquestUI.getNumberOfPlayer();
		ArrayList<PlayerNameAndType> playerNamesAndTypes = conquestUI.getPlayerNamesAndTypes(playerNumber);
		int numberOfMaps = conquestUI.getNumberOfMaps();
		int numberOfGamesPerMap = conquestUI.getNumberOfGamesPerMap();
		int numberOfTurnsPerGame = conquestUI.getNumberOfTurnsPerGame();

		// Map Counter loop
		for (int mapCounter = 0; mapCounter < numberOfMaps; mapCounter++) {
			boolean readFileStatusMap = getFilePathForLoadingMap(System.getProperty("user.dir")
					+ "\\bin\\ResourceProject\\CountryTournament" + (mapCounter + 1) + ".json");
			Map.getInstance().assigningPlayerCountries(playerNamesAndTypes);
			Player[] players = Map.getInstance().getPlayers();

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
								aggressivePlayer.aggressiveAttackPlayer();
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
								break;
							}
						}
					}

				}

			}

		}

		Map map = Map.getInstance();
		map.assigningPlayerCountries(playerNamesAndTypes);

	}

	private void createAndAssignMapAndPlayers() {

	}

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
