
/**
 * To check if this hand consists of five cards, with four having the same rank.
 * 
 * @author Harsh
 *
 */


public class Quad extends Hand implements Abstract_Interface {
	
	
	private static final long serialVersionUID = 1L;


	/**
	 * The following constructor creates a Quad object, holding Quad hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public Quad(CardGamePlayer player, CardList card)
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
		this.sort();
		
		if(this.getCard(3).rank == this.getCard(4).rank)
			return this.getCard(4);
		
		else
			return this.getCard(3);
	}
	
	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	
	public boolean isValid()
	{
		if(this.size() != 5)
			return false;
		
		this.sort();
		if(this.getCard(0).rank == this.getCard(1).rank && 
				this.getCard(1).rank == this.getCard(2).rank && 
				this.getCard(2).rank == this.getCard(3).rank)
				{return true;}
		
		else if(this.getCard(1).rank == this.getCard(2).rank && 
				this.getCard(2).rank == this.getCard(3).rank && 
				this.getCard(3).rank == this.getCard(4).rank)
				{return true;}
		
		return false;
	}
	
	
	/**
	 * A method to return the type of Hand 
	 * 
	 * @return a string stating type of hand. 
	 */
	
	public String getType()
	{
		return "Quad";
	}
}
//DONE
//DOne

	