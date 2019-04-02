package Tests.Helper;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import Helper.CountryHelper;
import Model.Country;
import View.ConquestUI;

public class CountryHelperTest {
	CountryHelper countryHelper;
	Country countryTest;
	ArrayList<Country> countryList = new ArrayList<Country>();

	@Test
	@Before
	public void cntextMaker() {
		countryHelper = new CountryHelper();
		countryTest = new Country("TestCountry", 1, 1, 10, null, 1, "playerTest");
		countryList.add(countryTest);
		boolean result = countryHelper.updateSourceCountriesArmies(countryList);
		assertTrue(result);
	}
}
