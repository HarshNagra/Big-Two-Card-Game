import java.util.Arrays;

/**
 * Subclass of Hand, used to check if the hand is a flush,
 * return Flush if true
 * 
 * @author Harsh
 *
 */

public class Flush extends Hand implements Abstract_Interface {

	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a flush object, holding flush hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public Flush(CardGamePlayer player, CardList card)
	{
		super(player,card);
	}
	
	
	/**
	 * A method for retrieving the top card of this hand.
	 * @return getCard with the value of top card of current hand
	 */
	
	
	public Card getTopCard()
	{
		int [] numbers = {this.getCard(0).rank, 
							this.getCard(1).rank, 
							this.getCard(2).rank, 
							this.getCard(3).rank, 
							this.getCard(4).rank}; // store the ranks 
		
		
		for(int i = 0; i < 5;i++)
		{
			if(numbers[i] == 1)
				numbers[i] = 14;
			if(numbers[i] == 0)
				numbers[i] = 13;
		}
		
		Arrays.sort(numbers);// ascending order. numbers[4] is the greatest
		
		int temp = 0;
		int i=0;
		while(i<numbers.length) {
			if(numbers[4]==this.getCard(i).rank )//compare with the highest rank
				temp = i;
			i++;
		}
		
		return this.getCard(temp);
	}
	
	
	/**
	 * To check if the current hand is valid 
	 * @return true or false if it matches with the hand you are searching for
	 */
	public boolean isValid()
	{
		if(this.size()!= 5)
			return false;
		
		if(this.getCard(0).suit == this.getCard(1).suit && 
				this.getCard(1).suit == this.getCard(2).suit && 
				this.getCard(2).suit == this.getCard(3).suit && 
				this.getCard(3).suit == this.getCard(4).suit)
		{
			return true;// all the suits are the same, therefore, flush!
		}
		else
			return false;
	}
	
	/**
	 * A method to return the type of Hand 
	 * @return a string stating type of hand. 
	 */

	
	public String getType()
	{
		return "Flush";
	}
	

}

