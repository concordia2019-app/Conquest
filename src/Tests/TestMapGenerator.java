package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import Model.Country;
import Model.MapGenerator;

public class TestMapGenerator {

	@Test
	public void testMapIsLoadedProperlyOrNot() {
		MapGenerator mapGenerator = new MapGenerator();
		boolean status = true;
		ArrayList<Country> allCountries = mapGenerator.mapReader(System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json");
		
		if(allCountries.isEmpty())
			status = false;
	
		assertTrue(status);
	}
	
	@Test
	public void testInvalidMap() {
		MapGenerator mapGenerator = new MapGenerator();
		boolean status = false;
		ArrayList<Country> allCountries = mapGenerator.mapReader(System.getProperty("user.dir") + "\\bin\\ResourceProject\\WrongCountrySample.json");
		
		if(mapGenerator.returnValidMapStatus())
			status = true;
	
		assertTrue(status);
	}

}
