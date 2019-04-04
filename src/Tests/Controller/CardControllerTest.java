
/**
 * 
 */
package Tests.Controller;
import Model.CardType;
import Model.Card;
import Model.CardsCounter;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import Controller.CardController;

/**
 * @author Amirhossein
 *
 */
public class CardControllerTest {

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
	 * Test method for {@link Controller.CardController#cardAssigner()}.
	 */
//	@Test
	//public void testCardAssigner() {
	//	fail("Not yet implemented");
	//}

	/**
	 * Test method for {@link Controller.CardController#defineCardsType(java.util.ArrayList)}.
	 */
//	@Test
	//public void testDefineCardsType() {
		//fail("Not yet implemented");
//	}

	/**
	 * Test method for {@link Controller.CardController#calculateArmiesCount(Model.CardsCounter, java.util.ArrayList)}.
	 */
	@Test
	public void testCalculateArmiesCount() {
		
		CardController cardcontroller=new CardController();
		CardsCounter cardsCounter=new CardsCounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseCavalrycounter();
		cardsCounter.increaseCavalrycounter();
		cardsCounter.increaseInfantrycounter();
        ArrayList<Card> cards= new ArrayList<Card>();
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.CAVALRY));
        cards.add(new Card(CardType.CAVALRY));
        cards.add(new Card(CardType.INFANTRY));


		assertEquals(cardcontroller.calculateArmiesCount(cardsCounter,cards), 5);

	}


	/**
	 * Test method for {@link Controller.CardController#calculateArmiesCount(Model.CardsCounter, java.util.ArrayList)}.
	 */
	@Test
	public void testUpdateListCards() {
		
		CardController cardcontroller=new CardController();
		CardsCounter cardsCounter=new CardsCounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseArtillerycounter();
		cardsCounter.increaseCavalrycounter();
		cardsCounter.increaseCavalrycounter();
		cardsCounter.increaseInfantrycounter();
        ArrayList<Card> cards= new ArrayList<Card>();
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.ARTILLERY));
        cards.add(new Card(CardType.CAVALRY));
        cards.add(new Card(CardType.CAVALRY));
        cards.add(new Card(CardType.INFANTRY));


		assertEquals(cardcontroller.updateListCards(cardsCounter, cards).size(),3);

	}
}
