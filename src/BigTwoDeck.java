/**
 * The BigTwoDeck class is a subclass of the Deck class, and is used to model a deck of cards used in a Big Two card game.
 * 
 * @author Harsh
 *
 */

public class BigTwoDeck extends Deck {

	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * a method for initializing a deck of Big Two cards.
	 * 
	 */
	
	public void initialize()
	{
		removeAllCards();//clear old data
		
		int i=0;
		
		while(i<4) {
			
			int j=0;
			
			while(j<13) {
				
				BigTwoCard bigtwocard =new BigTwoCard(i,j);
				
				this.addCard(bigtwocard);
				
				j++;
			}
			
			i++;
		}
		
	}

}