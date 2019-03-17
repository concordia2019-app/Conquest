package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Card;
import Model.CardType;

/**
 * 
 * @author AHasheminezhad
 */
public class CardController {
	
	/**
 *     Randomly return a new card and assign it
 */
	public Card cardAssigner() {
		Random random = new Random();
		int number = random.nextInt(3);
                 switch(number)
                        {
                            case 0:
                            	  return new Card(CardType.INFANTRY );
                            case 1:
                                  return  new Card(CardType.CAVALRY );
                            case 2:
                                  return new Card(CardType.ARTILLERY ); 
                        } 
            return new Card(CardType.ARTILLERY ); 
 	}
	
	public int searchForCards(ArrayList<Card> cards) {
		for(int i = 0; i < cards.size(); i++) {
	
			
			if(cards.get(i).checkCardType(CardType.INFANTRY)) {
				counterInfantry++;
                                cards.remove(i);
                        }
			else if(cards.get(i).checkCardType(CardType.CAVALRY))
				counterCavalry++;
		        else if(cards.get(i).checkCardType(CardType.ARTILLERY))	
				counterArtillery++;
		    
		}
	}
	
}
