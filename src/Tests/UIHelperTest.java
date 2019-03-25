/**
 * 
 */
package Tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Helper.UIHelper;

/**
 * @author Amirhossein
 *
 */
public class UIHelperTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void testTryParseInt() {
     UIHelper helper=new UIHelper();
     assertEquals(  helper.tryParseInt("player"),true);
	}

	/**
	 * Test method for {@link Helper.UIHelper#getCountryById(java.util.ArrayList, java.lang.Integer)}.
	 */
	@Test
	public void testGetCountryById() {
		 UIHelper helper=new UIHelper();
	     assertEquals(  helper.tryParseInt("player"),true);	}

	/**
	 * Test method for {@link Helper.UIHelper#isIdExistInList(int[], int)}.
	 */
	@Test
	public void testIsIdExistInList() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link Helper.UIHelper#addArmiesToCountryById(int, java.util.ArrayList, int)}.
	 */
	@Test
	public void testAddArmiesToCountryById() {
		fail("Not yet implemented");
	}

}
