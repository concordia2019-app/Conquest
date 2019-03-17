package Model;

/**
 * This class is the model for Card.
 * 
 * @author AHasheminezhad
 */
public class Card {
	
	
	private CardType cardType; 
		
	/**
 * This function is is a constructor for initializing cardType.
 * 
 * @author Amirhossein
 */
	public Card(CardType card) {
		switch (card) {
		case INFANTRY:
			this.cardType = CardType.INFANTRY;
			break;
		case  CAVALRY:
			this.cardType = CardType.CAVALRY;
			break;
		case  ARTILLERY:
			this.cardType = CardType.ARTILLERY;
			break; 
		}
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public boolean checkCardType(CardType card)
	{
		if(this.cardType == card)
			return true;
		else {
			return false;
		}
	}
}
