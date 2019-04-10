package Tests.Controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import Controller.TournamentController;
import Model.Country;

public class TournamentControllerTest {

	@Test
	public void getWinnerNameTest() {
		ArrayList<Country> countryList = new ArrayList<Country>();
		Country country1 = new Country("CountryTest1", 1, 1, 5, null, 1, "TestPlayerWinner");
		Country country2 = new Country("CountryTest1", 2, 2, 15, null, 1, "TestPlayerWinner");
		Country country3 = new Country("CountryTest1", 3, 3, 5, null, 2, "TestPlayerLoser");
		countryList.add(country1);
		countryList.add(country2);
		countryList.add(country3);
		TournamentController tournamentController = TournamentController.getTournamentControllerInstance();
		String winnerName = tournamentController.getWinnerName(countryList);
		assertEquals("TestPlayerWinner", winnerName);
	}

}
