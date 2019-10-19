/**
 * Subclass of Hand, used to check if the hand is a FulHouse,
 * return FullHouse if true
 * 
 * @author Harsh
 *
 */

public class FullHouse extends Hand implements Abstract_Interface {

	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a fullhouse object, holding fullhouse hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public FullHouse(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * @return getCard with the value of top card of current hand
	 */
	
	public Card getTopCard()
	{
		this.sort();//sorting the cards 
		
		if(this.getCard(0).rank == this.getCard(2).rank) 
			return this.getCard(2);
		
		else 
				return this.getCard(4);// 3 similar have to be later
	
	}
	
	/**
	 * To check if the current hand is valid 
	 * @return true or false if it matches with the hand you are searching for
	 * 
	 */

	public boolean isValid()
	{
		if(this.size()!= 5)
		{
			return false;
		}
		
		this.sort(); // sort to compare ranks
		if(this.getCard(2).rank == this.getCard(4).rank)// 3 similar is later
		{
			if(this.getCard(3).rank == this.getCard(4).rank 
					&& this.getCard(0).rank == this.getCard(1).rank)
				return true;
			
		}
		
		else if(this.getCard(0).rank == this.getCard(2).rank)// 3 similar is first 
		{
			if(this.getCard(1).rank == this.getCard(2).rank 
					&& this.getCard(3).rank == this.getCard(4).rank)
				return true; 
	
		}
		
		return false;
		
		
	}
	
	
	/**
	 * A method to return the type of Hand 
	 * @return a string stating type of hand. 
	 */
	
	public String getType()
	{
		return "FullHouse";// it is a full house 
	}
	

	
}
