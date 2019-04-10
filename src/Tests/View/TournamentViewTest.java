package Tests.View;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import Model.TournamentResult;
import View.TournamentView;

public class TournamentViewTest {

	@Test
	public void getWinnerPlayerByRoundAndGameIndexTest() {
		TournamentView tournamentView = new TournamentView();
		ArrayList<TournamentResult> tournamentResults = new ArrayList<TournamentResult>();

		TournamentResult tournamentResult1 = new TournamentResult();
		tournamentResult1.setGameIndex(1);
		tournamentResult1.setMapIndex(1);
		tournamentResult1.setTurnIndex(1);
		tournamentResult1.setWinnerName("Test");
		tournamentResults.add(tournamentResult1);

		TournamentResult tournamentResult2 = new TournamentResult();
		tournamentResult2.setGameIndex(2);
		tournamentResult2.setMapIndex(2);
		tournamentResult2.setTurnIndex(2);
		tournamentResult2.setWinnerName("Test");
		tournamentResults.add(tournamentResult2);
		String winnerName = tournamentView.getWinnerPlayerByRoundAndGameIndex(tournamentResults, 1, 1);
		assertTrue(winnerName != "-");
	}

}
