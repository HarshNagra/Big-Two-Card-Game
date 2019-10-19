

/**
 * To check if this hand consists of two cards with the same rank
 * 
 * @author Harsh
 *
 */


public class Pair extends Hand implements Abstract_Interface {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a pair object, holding pair hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public Pair(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return getCard with the value of top card of current hand
	 */

	
	public Card getTopCard()
	{
		if(this.getCard(0).suit < this.getCard(1).suit)
			return this.getCard(1);
		else 
			return this.getCard(0);// has to be greater 
		
	}
	
	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	
	public boolean isValid()
	{
		if(this.size() != 2)// to check the size 
			return false;
		
		if(this.getCard(1).rank != this.getCard(0).rank)
			return false;
		
		else
			return true;	
	}
	/**
	 * A method to return the type of Hand 
	 * 
	 * @return a string stating type of hand. 
	 */
	
	
	public String getType()
	{
		return new String("Pair");
	}
}