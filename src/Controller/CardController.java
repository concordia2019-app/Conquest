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
            
             int pairInfantry=(int)cardsCounter.getInfantrycounter()/3;
            int pairCavalry=(int)cardsCounter.getCavalrycounter()/3;             
            int pairArtillery=(int)cardsCounter.getArtillerycounter()/3;
            int armiesCount=( pairInfantry + pairCavalry + pairArtillery) * 5 ;
            
            
            
            int tempInfantry=pairInfantry*3; int tempCavalry=pairCavalry*3; int tempArtillery=pairArtillery*3;
            
            if(tempInfantry>0)
             for(int i=0 ;i<cards.size(); i++)
                   if(cards.get(i).checkCardType(CardType.INFANTRY) && tempInfantry>0)
                   {
                       tempInfantry--;
                   }
              
                 if(tempCavalry>0)
             for(int i=0 ;i<cards.size(); i++)
                   if(cards.get(i).checkCardType(CardType.CAVALRY) && tempCavalry>0)
                   {
                       tempCavalry--;
                   }
                      if(tempArtillery>0)
             for(int i=0 ;i<cards.size(); i++)
                   if(cards.get(i).checkCardType(CardType.ARTILLERY) && tempArtillery>0)
                   {
                       tempArtillery--;
                   } 
            return armiesCount;
	}
	
}
