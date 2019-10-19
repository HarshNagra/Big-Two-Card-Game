import java.util.Arrays;

/**
 * This hand consists of five cards with consecutive ranks and the same suit.
 * 
 * @author harsh
 *
 */

public class StraightFlush extends Hand implements Abstract_Interface {

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * The following constructor creates a Straight flush object, holding Straight Flush hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */	
	public StraightFlush(CardGamePlayer player, CardList card)
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
		int [] numval = {this.getCard(0).rank, 
				this.getCard(1).rank, this.getCard(2).rank, 
				this.getCard(3).rank, this.getCard(4).rank};
		
	
		int i=0;
		while (i<5) {

			if(numval[i] == 1)
				numval[i] = 14;
			
			if(numval[i] == 0)
				numval[i] = 13;
			
			i++;
		}
		
		Arrays.sort(numval);
		
		int temp = 0, j=0;
		while(j<5) {
			if(this.getCard(j).rank == numval[4])//find the position of top carc
				temp = j;
			j++;
		}
		
		return this.getCard(temp);
		
	}
	
	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	public boolean isValid()
	{
		if(this.size() != 5)
		{
			return false;
		}
		
		
		int [] numval = {this.getCard(0).rank, 
				this.getCard(1).rank, this.getCard(2).rank, 
				this.getCard(3).rank, this.getCard(4).rank};
		

		int i=0;
		while (i<5) {

			if(numval[i] == 1)
				numval[i] = 14;
			
			if(numval[i] == 0)
				numval[i] = 13;
			
			i++;
		}
		
		
		Arrays.sort(numval);
		boolean ifSF = false;
		
		for (i = 0; i < numval.length-1; i++) 
		{
			if(numval[i]+1 == numval[i+1] && this.getCard(i).suit==this.getCard(i+1).suit) 
					ifSF=true;
			else 
					ifSF=false;
			
		}
		
		return ifSF;
	}
	
	/**
	 * A method to return the type of Hand 
	 * 
	 * @return a string stating type of hand. 
	 */
	
	public String getType()
	{
		return "StraightFlush";
	}
	

}