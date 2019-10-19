/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards.
 * 
 * @author Harsh
 *
 */
public class Hand extends CardList implements Abstract_Interface{
	
	private static final long serialVersionUID = 1L;

	/**
	 * a constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player, cardGamePlayer object containing the list of players
	 * @param cards, cardList object containing list of cards played
	 */
	
	public Hand(CardGamePlayer player, CardList cards)
	{
		this.player = player;
		
		for(int i = 0; i < cards.size();i++)
		{
			this.addCard(cards.getCard(i));
		}
	}
	
	/**
	 * private instance variable of the the player who plays this hand.
	 */
	
	private CardGamePlayer player;
	
	/**
	 * a method for retrieving the player of this hand.
	 * @return player containing the player of the active hand 
	 */
	
	public CardGamePlayer getPlayer()
	{
		return this.player;
		
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * @return null as it is overridden 
	 */
	
	public Card getTopCard()
	{
		return null;
	}
	
	/**
	 * A method for checking if this hand beats a specified hand.
	 * 
	 * @param hand
	 * @return true if the current hand beats the previous hand else false
	 */
	
	public boolean beats(Hand hand){
		
		if(hand.size() == 5)
		{
			if(this instanceof Straight)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType())
							if(this.getTopCard().compareTo(hand.getTopCard()) == 1)
								return true;
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
								return false;
							
					else
						return false;
				}	
			}
			if(this instanceof Flush)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
							return true;
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
							return false;
					
					else
					{
						if(hand.getType() == "Straight")
							return true;
					}
				}	
			}
			
			if(this instanceof FullHouse)
			{
				if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
						return true;
				
				else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
						return false;
				
				else if(hand.getType() != "StraightFlush" || hand.getType() != "Quad")
						return true;
				
			}
			
			if(this instanceof Quad)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
							return true;
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) != 1)
							return false;
					
					else
						if(hand.getType() != "StraightFlush")
							return true;
				}
			}
			
			
			if(this instanceof StraightFlush)
			{
				if(this.size() == hand.size())
				{
					if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
							return true;
					
					else if(this.getType() == hand.getType() && this.getTopCard().compareTo(hand.getTopCard()) == -1)
							return false;
					
					else
						return true;
				}
			}
					
		}
		
		
		if(hand.size() == 1)//SINGLE
		{
			if(this.isValid()  && this.size() == hand.size() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
					return true;
		}
		
		if(hand.size() == 2)//PAIR
		{
			if(this.isValid() && this.size() == hand.size() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
				return true;
		}
		
		if(hand.size() == 3)//TRIPLE
		{
			
			
			if(this.isValid() && this.size() == hand.size() && this.getTopCard().compareTo(hand.getTopCard()) == 1)
				return true;
		}
		
		
		return false;
	}
	
	
	/**
	 * To check if the current hand is valid 
	 * 
	 * @return true or false if it matches with the hand you are searching for
	 */
	
	public boolean isValid()
	{
		return false;
	}
	
	/**
	 * A method to return the type of Hand 
	 * 
	 * @return a string stating type of hand. 
	 */
	
	public String getType()
	{
		return null;
	}

}

