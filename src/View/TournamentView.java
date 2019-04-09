package View;

import java.util.ArrayList;
import java.util.Arrays;

import Model.Country;
import Model.TournamentResult;

public class TournamentView {

	public void tournamentResult(ArrayList<Country> countriesList, int numberOfMaps, int numberOfPlayers,
			int numberOfGames, ArrayList<TournamentResult> tournamentResults) {
		String table = "|%-14s";
		String topAndDownTable = "+--------------";
		String headerTitles = "|++++++++++++++";
		for (int i = 1; i <= numberOfGames; i++) {
			table += "|%-14s";
			topAndDownTable += "+--------------";
			headerTitles += "|     Game " + i + "   ";
		}
		table += "|%n";
		topAndDownTable += "%n";
		headerTitles += "|%n";

		System.out.format(topAndDownTable);
		System.out.format(headerTitles);
		System.out.format(topAndDownTable);

		for (int i = 0; i < numberOfMaps; i++)
			System.out.format(table, "Map " + (i + 1), getWinnerPlayerByRoundAndGameIndex(tournamentResults, i, 0),
					getWinnerPlayerByRoundAndGameIndex(tournamentResults, i, 1),
					getWinnerPlayerByRoundAndGameIndex(tournamentResults, i, 2),
					getWinnerPlayerByRoundAndGameIndex(tournamentResults, i, 3),
					getWinnerPlayerByRoundAndGameIndex(tournamentResults, i, 4));

		System.out.format(topAndDownTable);
	}

	public String getWinnerPlayerByRoundAndGameIndex(ArrayList<TournamentResult> tournamentResults, int mapIndex,
			int gameIndex) {
		for (TournamentResult tournamentResult : tournamentResults) {
			int tournamentMapIndex = tournamentResult.getMapIndex();
			int tournamentGameIndex = tournamentResult.getGameIndex();
			String tournamentWinnerName = tournamentResult.getWinnerName();
			if (tournamentMapIndex == mapIndex && tournamentGameIndex == gameIndex) {
				return tournamentWinnerName;
			}
		}
		return "-";
	}

}
