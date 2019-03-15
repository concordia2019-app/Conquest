package Controller;

import java.util.Random;

import Model.Card;

public class CardController {
	
	
	public Card randomCardAssigner() {
		Random random = new Random();
		int rnd = random.nextInt(3);
		Card card = new Card(rnd + 1);
		
		return card;
	}
}
