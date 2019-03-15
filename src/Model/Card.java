package Model;

/**
 * This class is the model for Card.
 * 
 * @author AHasheminezhad
 */
public class Card {
	
	private enum cardTypes { INFANTRY , CAVALRY , ARTILLERY  };
	
	private cardTypes cardType;
	private int cardTypeNumber;
		
	
	public Card(int cardTypeIndex) {
		switch (cardTypeIndex) {
		case 1:
			this.cardType = cardTypes.INFANTRY;
			break;
		case 2:
			this.cardType = cardTypes.CAVALRY;
			break;
		case 3:
			this.cardType = cardTypes.ARTILLERY;
			break;

		default:
			System.out.println("Input index for typeCard is wrong.");
		}
		cardTypeNumber= cardTypeIndex;
	}
	
	public cardTypes getCardType() {
		return cardType;
	}
	
	public boolean checkCardType(int cardTypeIndex)
	{
		if(cardTypeNumber == cardTypeIndex)
			return true;
		else {
			return false;
		}
	}
}
