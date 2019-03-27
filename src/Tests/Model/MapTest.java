package Tests.Model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import Model.Country;
import Model.Map;

public class MapTest {

	/**
	 * This test is to check whether the player country ids are corresponding to the actual country.
	 */
	@Test
	public void testGetPlayerCorrespondingCountries() {
		Map map = Map.getInstance();
		map.setCountries();
		
		int[] countriesID = {1};
		ArrayList<Country> Actual = map.getPlayerCorrespondingCountries(countriesID);

		assertEquals(map.CountriesList.get(0), Actual.get(0));
	}
	
	/**
	 * This test is to check whether the country id we send and the array of countries the player own 
	 * is returning country objects that are attackable.
	 */
	@Test
	public void testGetSpecificCountryAdjacentsForAttack() {
		Map map = Map.getInstance();
		map.setCountries();
		
		int[] playerCountriesID = {1, 2, 3};
		int countryID = 2;
		ArrayList<Country> Actual = map.getSpecificCountryAdjacentsForAttack(playerCountriesID, countryID);
		
		ArrayList<Country> Expected = new ArrayList<Country>();
		Expected.add(map.CountriesList.get(3));
		
		assertEquals(Expected.get(0), Actual.get(0));
	}

}




