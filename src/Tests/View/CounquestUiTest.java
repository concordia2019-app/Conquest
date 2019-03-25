package Tests.View;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import Model.Country;
import Model.LeftArmiesResponse;
import View.ConquestUI;

public class CounquestUiTest {
	ConquestUI conquestUI;
	Country countryTest;
	@Test
	@Before
	public void cntextMaker() {
		conquestUI = new ConquestUI();
		countryTest = new Country("TestCountry", 1, 1, 10, null, 1, "playerTest");
	}
	
	public void checkPlayerNumberTest() {
		boolean result = conquestUI.checkPlayerNumber(3);
		assertTrue(result);
	}
}
