
/**
 * The BigTwoCard class is a subclass of the Card class, and is used to model a card used in a Big Two card game. 
 * 
 * @author Harsh
 *
 */
public class BigTwoCard extends Card {


	private static final long serialVersionUID = 1L;


	/**
	 * This constructor builds a card with a secific suit and rank
	 * 
	 * @param suit an int value between 0 and 3 representing the suit of a card:
	 *      
	 * @param rank an int value between 0 and 12 representing the rank of a card:
	 *           
	 */
	
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Compares this card with the specified card for order.
	 * 
	 * @param card
	 *            the card to be compared
	 * @return a negative integer, zero, or a positive integer as this card is
	 *         less than, equal to, or greater than the specified card
	 */
	
	public int compareTo(Card card)
	{
		
		int thisRank = this.rank;
		int cardRank = card.rank;
		
		if(cardRank == 0)
			cardRank = 13;
		
		
		if(cardRank == 1)
			cardRank = 14;	
		
		if(thisRank == 0)
			thisRank = 13;
		
		if(thisRank == 1)
			thisRank = 14;
		
		
		if (cardRank<thisRank) {
			return 1;
		} else if (cardRank>thisRank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
	
}


