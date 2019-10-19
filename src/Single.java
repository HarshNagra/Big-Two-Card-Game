/**
 * This hand consists of one single card 
 * 
 * @author Harsh
 *
 */

public class Single extends Hand implements Abstract_Interface{

	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a single object, holding single hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public Single(CardGamePlayer player, CardList card)
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
		return this.getCard(0);//the only card
	}
	
	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	
	public boolean isValid()
	{
		if(this.size() != 1)
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
		return new String("Single");
	}
	
	

}
