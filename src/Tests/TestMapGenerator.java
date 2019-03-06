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
		ArrayList<Country> allCountries = mapGenerator.MapReader();
		
		if(allCountries.isEmpty())
			status = false;
	
		assertTrue(status);
	}

}
