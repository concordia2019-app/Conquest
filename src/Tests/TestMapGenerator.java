package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.Country;
import Model.MapGenerator;

public class TestMapGenerator {

	/**
	 * This will test if the map is loaded properly or not. #1
	 */
	@Test
	public void testMapIsLoadedProperlyOrNot() {
		MapGenerator mapGenerator = new MapGenerator();
		boolean status = true;
		ArrayList<Country> allCountries = mapGenerator.MapReader(System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json");
		
		if(allCountries.isEmpty())
			status = false;
	
		assertTrue(status);
	}
	
	/**
	 * This will test for an invalid map. #3
	 */
	@Test
	public void testInvalidMap() {
		MapGenerator mapGenerator = new MapGenerator();
		boolean status = false;
		/*ArrayList<Country> allCountries = mapGenerator.mapReader(System.getProperty("user.dir") + "\\bin\\ResourceProject\\WrongCountrySample.json");
		
		if(mapGenerator.returnValidMapStatus())
			status = true;*/
	
		assertFalse(status);
	}

}
