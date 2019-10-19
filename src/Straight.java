import java.util.Arrays;

/**
 * This hand consists of five cards with consecutive ranks.
 * 
 * @author Harsh
 *
 */

public class Straight extends Hand implements Abstract_Interface{

	
	private static final long serialVersionUID = 1L;

	/**
	 * The following constructor creates a Straight object, holding Straight hand of the player
	 *  
	 * @param player, CardGamePlayer specifying to whom the current hand belongs
	 * @param card, CardList containing list of cards played by the active player
	 */
	
	public Straight(CardGamePlayer player, CardList card)
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
		int [] numval = {this.getCard(0).rank, this.getCard(1).rank, 
						this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		int temp = 0;
		
		int i=0;
		while(i<5) {
			if(numval[i]==0)
				numval[i]=13;
			if(numval[i]==1)
				numval[i]=14;
			i++;
		}
		
		Arrays.sort(numval);//sorting the hand numerically.
		
		int j=0;
		while(j<5) {
			if(this.getCard(i).rank==numval[4])//comparing with the highest possible value tp find the position in actual getCard
				temp=i;
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
		
		
		int [] numval = {this.getCard(0).rank, this.getCard(1).rank, 
						this.getCard(2).rank, this.getCard(3).rank, this.getCard(4).rank};
		int i=0;
		while(i<5) {
			if(numval[i]==0)
				numval[i]=13;
			if(numval[i]==1)
				numval[i]=14;
			i++;
		}
		
		
		Arrays.sort(numval);
		
		int j=0, flag=0;
		while(j<4) {
			if(numval[j]+1!=numval[j+1]) {
				flag=1;
			}
			j++;
		}
		
		if (flag==0)
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
		return "Straight";
	}
}