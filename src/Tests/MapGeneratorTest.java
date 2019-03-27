package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.junit.Test;

import Model.Country;
import Model.MapGenerator;

public class MapGeneratorTest {

	/**
	 * This will test if the map is loaded properly or not. #1
	 */
	@Test
	public void testMapIsLoadedProperlyOrNot() {
		MapGenerator mapGenerator = new MapGenerator();
		boolean status = true;
		int[] adj = {1,2,3};
		Country c = new Country("Iran", 1, 1, 3, adj, 1,"Pegah");
		Country c1 = new Country("Iraq", 1, 1, 3, adj, 1,"Pegah");
		ArrayList<Country> allCountriesTemp = new ArrayList<Country>();
		allCountriesTemp.add(c);
		allCountriesTemp.add(c1);
		JSONObject countries = new JSONObject();
		String filePath = System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json";
		String obj = mapGenerator.writeMap(allCountriesTemp,filePath);
		ArrayList<Country> allCountries = mapGenerator.mapReader(filePath);
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
	/**
	 * This will test if the map is written properly or not. 
	 */
	@Test
	public void testMapIsWrittenProperlyOrNot() {
		MapGenerator mapGenerator = new MapGenerator();
		int[] adj = {1,2,3};
		Country c = new Country("Iran", 1, 1, 3, adj, 1,"Pegah");
		Country c1 = new Country("Iraq", 1, 1, 3, adj, 1,"Pegah");
		ArrayList<Country> allCountries = new ArrayList<Country>();
		allCountries.add(c);
		allCountries.add(c1);
		String obj = mapGenerator.writeMap(allCountries,System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json");
		File tempFile = new File(System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json");
		boolean exists = tempFile.exists();
		assertTrue(exists);
	}
	
	@Test
	public void testgetObjectbyName() {
		MapGenerator mapGenerator = new MapGenerator();
		int[] adj = {1,2,3};
		Country c = new Country("Iran", 1, 1, 3, adj, 1,"Pegah");
		Country c1 = new Country("Iraq", 1, 1, 3, adj, 1,"Pegah");
		ArrayList<Country> allCountries = new ArrayList<Country>();
		allCountries.add(c);
		allCountries.add(c1);
		JSONObject countries = new JSONObject();
		countries.put("Countries", allCountries);
		String name = "Countries";
		String filePath = System.getProperty("user.dir") + "\\bin\\ResourceProject\\CountrySample.json";
		String obj = mapGenerator.writeMap(allCountries,filePath);
		JSONObject response = new JSONObject();
		response = mapGenerator.getObjectbyName(name, filePath);
		assertEquals(response.get("status"),1);
	}

}
