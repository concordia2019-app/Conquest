package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Card;
import Model.CardType;
import Model.CardsCounter;

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
	
	public CardsCounter defineCardsType(ArrayList<Card> cards) {
            
            	CardsCounter cardCounter= new  CardsCounter();
                
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).checkCardType(CardType.INFANTRY)) 
				cardCounter.increaseInfantrycounter(); 
			else if(cards.get(i).checkCardType(CardType.CAVALRY))
				cardCounter.increaseCavalrycounter();
		        else if(cards.get(i).checkCardType(CardType.ARTILLERY))	
				cardCounter.increaseArtillerycounter();
		}
                return cardCounter;
	}
        public int calculateArmiesCount(CardsCounter cardsCounter , ArrayList<Card> cards) {
            int armiesCount=0;
            int pairInfantry=cardsCounter.getInfantrycounter()/3;
            int pairCavalry=cardsCounter.getCavalrycounter()/3;             
            int pairArtillery=cardsCounter.getArtillerycounter()/3;

		armiesCount+= pairInfantry *5;
                armiesCount+=pairCavalry*5;	                 
                armiesCount+=pairArtillery*5;
            return armiesCount;
	}
	
}
