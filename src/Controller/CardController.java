package Controller;

import java.util.ArrayList;
import java.util.Random;

import Model.Card;

public class CardController {
	
	
	public Card randomCardAssigner() {
		Random random = new Random();
		int rnd = random.nextInt(3);
		Card card = new Card(rnd + 1);
		
		return card;
	}
	
	public void searchForCards(ArrayList<Card> cards) {
		for(int i = 0; i < cards.size(); i++) {
			int counterInfantry = 0;
			int counterCavalry = 0;
			int counterArtillery = 0;
			
			if(cards.get(i).checkCardType(1)) 
				counterInfantry++;
			else if(cards.get(i).checkCardType(2))
				counterCavalry++;
		    else  if(cards.get(i).checkCardType(3))	
				counterArtillery++;
		    
		}
	}
	
	  
	
}
