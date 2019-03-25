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

import Model.Card;
import Model.CardType;

/**
 * @author Amirhossein
 *
 */
public class CardTest {

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
	 * Test method for {@link Model.Card#card()}.
	 */
	@Test
	public void testCard() {
		Card card=new Card(CardType.ARTILLERY);
                assertEquals(card.getCardType(),CardType.ARTILLERY);
 	}

	/**
	 * Test method for {@link Model.Card#getCardType()}.
	 */
	@Test
	public void testGetCardType() {
		Card card=new Card(CardType.INFANTRY);
        assertEquals(card.getCardType(),CardType.INFANTRY);
    }

	/**
	 * Test method for {@link Model.Card#checkCardType(CardType)}.
	 */
	@Test
	public void testCheckCardType() {
		Card card=new Card(CardType.CAVALRY);
        assertEquals(card.checkCardType(CardType.CAVALRY),true);
 	}

}
