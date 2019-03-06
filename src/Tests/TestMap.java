package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import Model.Country;
import Model.Map;

public class TestMap {

	@Test
	public void testGetPlayerCorrespondingCountries() {
		Map map = new Map();
		map.setCountries();
		
		int[] countriesID = {1};
		ArrayList<Country> Actual = map.getPlayerCorrespondingCountries(countriesID);

		assertEquals(map.CountriesList.get(0), Actual.get(0));
	}
	
	@Test
	public void testGetSpecificCountryAdjacentsForAttack() {
		Map map = new Map();
		map.setCountries();
		
		int[] playerCountriesID = {1, 2, 3};
		int countryID = 2;
		ArrayList<Country> Actual = map.getSpecificCountryAdjacentsForAttack(playerCountriesID, countryID);
		
		ArrayList<Country> Expected = new ArrayList<Country>();
		Expected.add(map.CountriesList.get(3));
		
		assertEquals(Expected.get(0), Actual.get(0));
	}

}




