/**
 * This hand consists of three cards with the same rank.
 * 
 * @author Harsh
 *
 */
public class Triple extends Hand implements Abstract_Interface {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a triple object, holding triple hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */	
	public Triple(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return getCard with the value of top card of current hand
	 */
	
	public Card getTopCard(){
		
		if(this.getCard(0).suit > this.getCard(1).suit && 
				this.getCard(0).suit > this.getCard(2).suit)
			return this.getCard(0);
		
		if(this.getCard(1).suit > this.getCard(0).suit && 
				this.getCard(1).suit > this.getCard(2).suit)
			return this.getCard(1);
		
		//if(this.getCard(2).suit > this.getCard(1).suit && 
				//this.getCard(2).suit > this.getCard(0).suit)
		else 
			return this.getCard(2);
		

		
	}
	

	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	
	public boolean isValid()
	{
		if(this.size()  != 3)
			return false;
		
		if(this.getCard(0).rank == this.getCard(1).rank && 
				this.getCard(1).rank == this.getCard(2).rank)
			return true;
		else
			return false;
	}
	
	/**
	 * A method to return the type of Hand 
	 * 
	 * @return a string stating type of hand. 
	 */
	
	public String getType()
	{
		return new String("Triple");
	}

}