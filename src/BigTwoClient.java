import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.net.*;
import java.io.*;
/**
 * The BigTwoClient class implements the CardGame interface 
 * and NetworkGame interface. It is used to model a Big Two card 
 * game that supports 4 players playing over the internet.
 * 
 * @author Harsh
 */
public class BigTwoClient implements CardGame, NetworkGame{
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private int currentIdx;
	private BigTwoTable table;
	
	//new variables 
	private int previous;
	private boolean initial;
	private ObjectInputStream ois;

	
	BigTwoClient(){
		
		this.playerList = new ArrayList<CardGamePlayer>();
		
		CardGamePlayer p1= new CardGamePlayer();
		CardGamePlayer p2= new CardGamePlayer();
		CardGamePlayer p3= new CardGamePlayer();
		CardGamePlayer p4= new CardGamePlayer();
		this.playerList.add(p1);
		this.playerList.add(p2);
		this.playerList.add(p3);
		this.playerList.add(p4);
		
		this.numOfPlayers = this.playerList.size();
		this.handsOnTable = new ArrayList<Hand>();
		this.table = new BigTwoTable(this);
		
		//networking
		
		String name = JOptionPane.showInputDialog("Enter Your Name: ");
		if (name == null) {
			this.table.printMsg("Enter your name.\n");
			this.table.disable();
		} else {
			this.setPlayerName(name);
			this.setServerIP("127.0.0.1");
			this.setServerPort(2396);
			this.makeConnection();
			table.disable();
		}
	}
	
	/**
	 * Returns the number of players in this card game.
	 * 
	 * @return the number of players in this card game
	 */
	public int getNumOfPlayers() {
		return this.numOfPlayers;
	}

	/**
	 * Returns the deck of cards being used in this card game.
	 * 
	 * @return the deck of cards being used in this card game
	 */
	public Deck getDeck() {
		return this.deck;
	}

	/**
	 * Returns the list of players in this card game.
	 * 
	 * @return the list of players in this card game
	 */
	public ArrayList<CardGamePlayer> getPlayerList() {
		return this.playerList;
	}

	/**
	 * Returns the list of hands played on the table.
	 * 
	 * @return the list of hands played on the table
	 */
	public ArrayList<Hand> getHandsOnTable() {
		return this.handsOnTable;
	}

	/**
	 * Returns the index of the current player.
	 * 
	 * @return the index of the current player
	 */
	public int getCurrentIdx() {
		return this.currentIdx;
	}

	/**
	 * Starts the card game.
	 * 
	 * @param deck
	 *            the deck of (shuffled) cards to be used in this game
	 */
	public void start(Deck deck) {
		
		//removing old cards
		for(int i = 0; i < this.getNumOfPlayers(); i++) {
			this.getPlayerList().get(i).removeAllCards();
		}
		this.getHandsOnTable().clear();
		
		//finding first player
		for (int i = 0; i < deck.size(); i++) {
			this.getPlayerList().get(i%getNumOfPlayers()).addCard(deck.getCard(i));
			if (deck.getCard(i).getRank() == 2 && deck.getCard(i).getSuit() == 0) {
				currentIdx = i % getNumOfPlayers();
				table.setActivePlayer(this.getCurrentIdx());
			}
		}
		//sort for easier display
		for (int i=0;i<4;i++) {
			playerList.get(i).getCardsInHand().sort();
		}
		table.reset();
		initial = true;
		table.printMsg("All players are ready.\n Game starts!!\n");
		table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:\n");
		table.repaint();
	}

	/**
	 * Makes a move by the player.
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE,-1,cardIdx);
		sendMessage(move);
	}
	

	/**
	 * Checks the move made by the player.
	 * 
	 * @param playerID
	 *            the playerID of the player who makes the move
	 * @param cardIdx
	 *            the list of the indices of the cards selected by the player
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		Hand attempt=null;
		if(cardIdx != null) {
			attempt=composeHand(this.getPlayerList().get(playerID),this.getPlayerList().get(playerID).play(cardIdx));
		}
		
		if ((initial || playerID == previous ) && cardIdx == null ) {//here
			table.printMsg("{pass} \n <= Not a legal move!! \n ");
		} else if (cardIdx == null){//pass 
			currentIdx=playerID+1;
			currentIdx= currentIdx%getNumOfPlayers();
			table.printMsg("{pass}\n");
			table.setActivePlayer(this.getCurrentIdx());
			initial = false;//TILL HERE CHI WAH
			table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:-\n");
		} else if (initial && !attempt.contains(new BigTwoCard(0,2))) {
			table.printMsg(" <= Not a legal move!!\n");// if not 3 of diamonds 
		}
		else if (attempt == null) {
			table.printMsg(" <= Not a legal move!!\n");
		}  else if ((!this.getHandsOnTable().isEmpty() && attempt!=null && playerID != previous) ? 
				(this.getHandsOnTable().get(this.getHandsOnTable().size() - 1).size()!=attempt.size() 
				|| !(attempt.beats(this.getHandsOnTable().get(this.getHandsOnTable().size() - 1)))) : false) {
			table.printMsg(" <= Not a legal move!!\n");
		} else {
			previous = playerID;
			this.getPlayerList().get(playerID).removeCards(this.getPlayerList().get(playerID).play(cardIdx));
			attempt.sort();
			this.getHandsOnTable().add(attempt);
			table.printMsg("{"+attempt.getType()+"} \n");
			currentIdx = (playerID + 1);
			currentIdx=currentIdx% getNumOfPlayers();
			table.setActivePlayer(this.getCurrentIdx());
			table.printMsg(this.getPlayerList().get(this.getCurrentIdx()).getName()+"'s turn:\n");
			initial = false;
		}
		table.resetSelected();
		table.repaint();
		
		if (endOfGame()) {
			table.repaint();
			handsOnTable.clear();
			String message = "Game ends\n";
			for (int i = 0; i < this.getNumOfPlayers(); i++) {
				if (this.getPlayerList().get(i).getNumOfCards() != 0) {//lose
					message += this.getPlayerList().get(i).getName() + " has " + this.getPlayerList().get(i).getNumOfCards() + " cards in hand.\n";
					
				} else {//win
					message += this.getPlayerList().get(i).getName() + " wins the game!!!\n";
				}
			}
			table.disable();
			CardGameMessage ready = new CardGameMessage(CardGameMessage.READY,-1,null);
			JOptionPane.showMessageDialog(null, message);
			sendMessage(ready);
			
		}
	}

	/**
	 * Checks for end of game.
	 * 
	 * @return true if the game ends; false otherwise
	 */
	public boolean endOfGame(){
		
		for (int i=0;i<playerList.size();i++) {
			if(playerList.get(i).getCardsInHand().size()==0)
				return true;
		
		}
		return false;
	}
	
	class ServerHandler implements Runnable {
		@Override
		public void run() {	
			try {
				ois = new ObjectInputStream(sock.getInputStream());
				CardGameMessage message;
				while (!sock.isClosed()) 
					if ((message = (CardGameMessage) ois.readObject()) != null) 
						parseMessage(message);
				
				ois.close();
			} catch (Exception ex) {
				ex.printStackTrace();//track exception
			}
			
		}
		
	}
	
	public static void main(String[] args) {
		BigTwoClient run = new BigTwoClient();
	}
	public Hand composeHand(CardGamePlayer player, CardList cards)
	{
		Single single = new Single(player,cards);
		Pair pair = new Pair(player,cards);
		Triple triple = new Triple(player,cards);
		Straight straight = new Straight(player,cards);
		FullHouse fullhouse = new FullHouse(player,cards);
		Flush flush = new Flush(player,cards);
		Quad quad = new Quad(player,cards); 
		StraightFlush straightflush = new StraightFlush(player,cards); 
		 
		if(single.isValid())
			return single; // returns single
		
		if(pair.isValid())
			return pair; // returns pair 
		
		if(triple.isValid())
			return triple; // returns triple  
		
		if(flush.isValid())
			return flush; // returns flush 
		
		if(fullhouse.isValid())
			return fullhouse; // returns fullhouse
		
		if(quad.isValid())
			return quad; // returns quad
		
		if(straightflush.isValid())
			return straightflush; // returns straightflush
		
		if(straight.isValid())
			return straight; // returns straight 
		
		
		return new Hand(player,cards); 
	}
	
	/**
	 * Returns the playerID (index) of the local player.
	 * 
	 * @return the playerID (index) of the local player
	 */
	public int getPlayerID() {
		return this.playerID;
	}

	/**
	 * Sets the playerID (index) of the local player.
	 * 
	 * @param playerID
	 *            the playerID (index) of the local player.
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * checks whether the player is connected 
	 * 
	 * @return boolean value, true if connected 
	 */
	public boolean isConnected() {
		if (sock == null) {
			return false;
		} else 
			return true;
		
	}

	/**
	 * Returns the name of the local player.
	 * 
	 * @return the name of the local player
	 */
	public String getPlayerName() {
		return this.playerName;
	}

	/**
	 * Sets the name of the local player.
	 * 
	 * @param playerName
	 *            the name of the local player
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Returns the IP address of the server.
	 * 
	 * @return the IP address of the server
	 */
	public String getServerIP() {
		return this.serverIP;
	}

	/**
	 * Sets the IP address of the server.
	 * 
	 * @param serverIP
	 *            the IP address of the server
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	/**
	 * Returns the TCP port of the server.
	 * 
	 * @return the TCP port of the server
	 */
	public int getServerPort() {
		return this.serverPort;
	}

	/**
	 * Sets the TCP port of the server
	 * 
	 * @param serverPort
	 *            the TCP port of the server
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * Makes a network connection to the server.
	 */
	public void makeConnection() {
		
		if(sock==null) {
			try {
				sock = new Socket(this.getServerIP(),this.getServerPort());
				oos = new ObjectOutputStream(sock.getOutputStream());
				
				Thread thread = new Thread(new ServerHandler());
				thread.start();//starting the thread
				
				CardGameMessage enterGame = new CardGameMessage(CardGameMessage.JOIN,-1,this.getPlayerName());
				sendMessage(enterGame);
				
				CardGameMessage getReady = new CardGameMessage(CardGameMessage.READY,-1,null);
				sendMessage(getReady);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (sock!=null && !sock.isConnected()) {
			try {
				sock = new Socket(this.getServerIP(),this.getServerPort());
				oos = new ObjectOutputStream(sock.getOutputStream());
				
				Thread thread = new Thread(new ServerHandler());
				thread.start();//starting the thread
				
				CardGameMessage enterGame = new CardGameMessage(CardGameMessage.JOIN,-1,this.getPlayerName());
				sendMessage(enterGame);
				
				CardGameMessage getReady = new CardGameMessage(CardGameMessage.READY,-1,null);
				sendMessage(getReady);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

	/**
	 * Parses the specified message received from the server.
	 * 
	 * @param message
	 *            the specified message received from the server
	 */
	public void parseMessage(GameMessage message) {
		//1 MAKE MOVE
		if (CardGameMessage.MOVE==message.getType()) {
			checkMove(message.getPlayerID(),(int[]) message.getData());
		} 
		//2 PLYAER LIST
		else if (CardGameMessage.PLAYER_LIST==message.getType()) {
			
			this.setPlayerID(message.getPlayerID());
			
			for (int i = 0; i < this.getNumOfPlayers(); i++) {
				if (((String[]) message.getData())[i] == null) {
				}
				else
					this.getPlayerList().get(i).setName(((String[]) message.getData())[i]);
			}
			this.table.repaint();
		}
		//3 QUIT GAME 
		else if (CardGameMessage.QUIT==message.getType() ) {
				this.getPlayerList().get(message.getPlayerID()).setName("");//NO NNAME
				if (!this.endOfGame()) {
					CardGameMessage ready = new CardGameMessage(CardGameMessage.READY,-1,null);
					this.table.disable();
					sendMessage(ready);
				}
			} 
		//4 4 PLAYER ALREADY FULL!
		else if (CardGameMessage.FULL==message.getType()) {
			this.table.printMsg("The server is full! Can't join the game!\n");
			try {
				this.sock.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		//5 READY!
		else 
			if (CardGameMessage.READY ==message.getType()) {
				this.table.printMsg("Player " + message.getPlayerID() + " is ready!\n");
			} 
		
		//6 JOIN GAME
		else if (CardGameMessage.JOIN==message.getType()) {
			this.getPlayerList().get(message.getPlayerID()).setName((String)message.getData());
			this.table.repaint();
		}
		//7 START GAME
		else if (CardGameMessage.START==message.getType()) {
			deck = (Deck) message.getData();
			this.start(deck);
		} 
		//8 SEND MESSAGE
		else if (CardGameMessage.MSG==message.getType()) {
			this.table.printChatMsg((String) message.getData());
		}
	}

	/**
	 * Sends the specified message to the server.
	 * 
	 * @param message
	 *            the specified message to be sent the server
	 */
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}