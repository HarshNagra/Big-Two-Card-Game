import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 * The BigTwoTable class implements the CardGameTable interface.
 * 
 * @author harsh
 */
public class BigTwoTable implements CardGameTable {
	
	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JMenu menu;
	private JTextArea msgArea;
	private JTextArea chatArea;
	private JTextField chatTypeArea;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image[] avatars;
	private boolean isClickOn;
	
	/**
	 * a constructor for creating a BigTwoTable. The parameter game is a reference to a card game associates with this table.
	 * @param game A Card Game of BigTwo type to play through this GUI
	 */
	
	BigTwoTable(CardGame game){
	
		
		this.game = (BigTwoClient) game;
		this.selected = new boolean[13];
		
		//Importing images 
		avatars= new Image[4];
		avatars[0]= new ImageIcon(getClass().getResource("Images/batman.jpg")).getImage();
		avatars[1]= new ImageIcon(getClass().getResource("Images/flash.png")).getImage();
		avatars[2]= new ImageIcon(getClass().getResource("Images/green.png")).getImage();
		avatars[3]= new ImageIcon(getClass().getResource("Images/superman.png")).getImage();
		
		
		cardImages = new Image[4][13];
		Image saveCard;	
		for (int i=0; i<4;i++) {
			for(int j=0; j<13;j++) {
				 saveCard=new ImageIcon(getClass().getResource("Images/cards/"+j+""+i+".gif")).getImage();
				 cardImages[i][j]=saveCard;
			}
		}
		
		cardBackImage= new ImageIcon(getClass().getResource("Images/back.gif")).getImage();
		
		//setting the frame
		frame = new JFrame("Big Two (Harsh)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 750);
		
		
		//left side
		bigTwoPanel = new BigTwoPanel();
		bigTwoPanel.setPreferredSize(new Dimension(700,800));
	    
		
	    //right side
	    JPanel messages = new JPanel();
	    messages.setLayout(new BoxLayout(messages, BoxLayout.PAGE_AXIS));
	    
	    msgArea = new JTextArea(20,24);
	    msgArea.setEnabled(false);
	    DefaultCaret caret = (DefaultCaret) msgArea.getCaret();
	    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setViewportView(msgArea);
	    messages.add(scrollPane);
	    
	    chatArea = new JTextArea(21,24);
	    chatArea.setEnabled(false);;
	    DefaultCaret caretChat = (DefaultCaret) chatArea.getCaret();
	    caretChat.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	    JScrollPane scrollPaneChat = new JScrollPane();
	    scrollPaneChat.setViewportView(chatArea);
	    messages.add(scrollPaneChat);
	    
	    JPanel chat = new JPanel();
	    chat.setLayout(new FlowLayout());
	    chat.add(new JLabel("Message:"));
	    chatTypeArea = new JTextField();
	    chatTypeArea.getDocument().putProperty("filterNewlines", Boolean.TRUE);
	    chatTypeArea.addActionListener(new EnterListener());
	    chatTypeArea.setPreferredSize( new Dimension( 420, 24 ) );
	    chat.add(chatTypeArea);
	    messages.add(chat);
	    
	    
	    //buttons
	    JPanel buttons = new JPanel();
	    playButton = new JButton("Play");
	    playButton.addActionListener(new PlayButtonListener());
	    passButton = new JButton("Pass");
	    passButton.addActionListener(new PassButtonListener());
	    buttons.add(playButton);
	    buttons.add(passButton);
	    
	  //menu
	    JMenuBar menuBar = new JMenuBar();
	    menu = new JMenu("Game");
	    menuBar.add(menu);
	    JMenuItem menuItem1 = new JMenuItem("Connect");
	    menuItem1.addActionListener(new ConnectMenuItemListener());
	    menu.add(menuItem1);
	    
	    JMenuItem menuItem2 = new JMenuItem("Quit");
	    menuItem2.addActionListener(new QuitMenuItemListener());
	    menu.add(menuItem2);
	    
	    JMenu Message = new JMenu("Message");
	    JMenuItem option1 = new JMenuItem("Option1");
		JMenuItem option2 = new JMenuItem("Option2");
		Message.add(option1);
		Message.add(option2);
		menuBar.add(Message);
	    
		//adding to frame
	    frame.add(bigTwoPanel,BorderLayout.WEST);
	    frame.add(menuBar, BorderLayout.NORTH);
	    frame.add(messages, BorderLayout.EAST);
	    frame.add(buttons, BorderLayout.SOUTH);
	    frame.setResizable(true);
	    frame.setName("Big Two (Harsh Nagra)");
	    frame.setVisible(true);
	    
	}
	
	
	
	
	
	
	/**
	 * Sets the index of the active player (i.e., the current player).
	 * 
	 * @param activePlayer
	 *            an int value representing the index of the active player
	 */
	public void setActivePlayer(int activePlayer) {
		this.activePlayer = activePlayer;
	}

	/**
	 * Returns an array of indices of the cards selected.
	 * 
	 * @return an array of indices of the cards selected
	 */
	public int[] getSelected() {
		
		ArrayList<Integer> save = new ArrayList<Integer>();
		
		for (int i = 0; i < selected.length; i++) {
			if (selected[i] == true) {
				save.add(i);
			}
		}
		if (save.size() > 0) {
			int[] sizeSave = new int[save.size()];
			for(int i=0, len = save.size(); i < len; i++)
			   sizeSave[i] = save.get(i);
			return sizeSave;
		} else {
			return null;
		}
		
	}

	/**
	 * Resets the list of selected cards to an empty list.
	 */
	
	public void resetSelected() {
		this.selected = new boolean[13];
		this.repaint();
	}
	
	/**
	 * Repaints the GUI.
	 */
	public void repaint() {
		frame.repaint();
	}
	/**
	 * Prints the specified string to the message area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the message area of the card game
	 *            table
	 */
	public void printMsg(String msg) {
		msgArea.append(msg);
	}
	
	/**
	 * Clears the message area of the card game table.
	 */
	public void clearChatMsgArea() {
		chatArea.setText("");
	}

	/**
	 * Clears the message area of the card game table.
	 */
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	

	
	
	/**
	 * Prints the specified string to the chat message area of the card game table.
	 * 
	 * @param msg
	 *            the string to be printed to the chat message area of the card game
	 *            table
	 */
	public void printChatMsg(String msg) {
		chatArea.append(msg+"\n");
	}
	
	

	/**
	 * Resets the GUI.
	 */
	public void reset() {
		resetSelected();
		clearMsgArea();
		enable();
	}

	/**
	 * Enables user interactions.
	 */
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		isClickOn = true;
	}

	/**
	 * Disables user interactions.
	 */
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		isClickOn = false;
		
	}
	
	
	/**
	 * an inner class that extends the JPanel class and implements the MouseListener interface. Overrides the paintComponent() method inherited from the JPanel class to draw the card game table. Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
	 * @author harsh
	 */
	class BigTwoPanel extends JPanel implements MouseListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * BigTwoPanel default constructor which adds the Mouse Listener and sets background of the card table.
		 */
		BigTwoPanel(){
	        setBackground(new Color(50,150,50)); 
	        this.addMouseListener(this);
		}

		/**
		 * Draws the avatars, text and cards on card table
		 * @param g Provided by system to allow drawing
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			Font font1=new Font("Arial", Font.BOLD, 10);
			Font font2=new Font("Arial", Font.BOLD, 10);
			for (int i = 0; i < game.getNumOfPlayers(); i++) {
				if (i == game.getPlayerID()) {
					if (i == game.getCurrentIdx()) {
						g.setColor(Color.BLUE);//current player 
						g.setFont(font1);
					} else {
						g.setColor(Color.WHITE);
						g.setFont(font2);
					}
					g.drawString(game.getPlayerList().get(i).getName()+" (YOU)",15,i*130+15);
					g.setColor(Color.BLACK);
				} else if (i == game.getCurrentIdx()) {
					g.setColor(Color.BLUE);
					g.setFont(font2);
					g.drawString(game.getPlayerList().get(i).getName(),15,i*130+15);
					g.setColor(Color.BLACK);
				} else {
					g.setFont(font2);
					g.drawString(game.getPlayerList().get(i).getName(),15,i*130+15);
				}
				g.drawImage(avatars[i],5,30+i*130,this);
				for(int k=0;k<4;k++) {
					g.drawLine(0, 110+130*k, 1600, 110+130*k);
				}
				for (int j = 0; j < game.getPlayerList().get(i).getNumOfCards(); j++) {
					if (i == game.getPlayerID()) {
						if (selected[j] == true) 
							g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()]
									[game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()],80+j*cardImages[0][0].getWidth(this)/2,10+i*130-10,this);
						
						else 
							g.drawImage(cardImages[game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit()]
									[game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank()],80+j*cardImages[0][0].getWidth(this)/2,10+i*130,this);
					} 
					else 
						g.drawImage(cardBackImage,80+j*cardImages[0][0].getWidth(this)/2,10+i*130,this);
				}
			}
			
			if (game.getHandsOnTable().size()-1 > -1) {
				Hand previousHand  = game.getHandsOnTable().get(game.getHandsOnTable().size()-1);
				g.drawString("Played by "+previousHand .getPlayer().getName(), 5, 60+4*130 - 10);
				for (int i = 0; i < previousHand .size();i++) {
					g.drawImage(cardImages[previousHand .getCard(i).getSuit()]
							[previousHand .getCard(i).getRank()],150+i*20,10+4*130,this);
				}
			}
		}

		/**
		 * Defines what happens when mouse is clicked on the card table. Only allows clicks on cards of active player. Once cards are selected, the JPanel is repainted to reflect changes.
		 * @param e Mouse event created when Mouse Clicked
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (isClickOn && activePlayer == ((BigTwoClient) game).getPlayerID()) {
				Image sizeSave= cardImages[0][0];
				int width = sizeSave.getWidth(this);
				int height = sizeSave.getHeight(this);
				int num = game.getPlayerList().get(activePlayer).getNumOfCards();
				
				
				if (e.getX() >= 80 && e.getX() <= 80+(width/2)*num+width && e.getY() >= activePlayer*130 && e.getY() <= 10+activePlayer*130+height) {	
					int cardNumber = (int)Math.ceil((e.getX()-80)/(width/2));
					if(cardNumber/num>0) {
						cardNumber=num-1;
					}
					else {
					}
					if (selected[cardNumber]) {
						if (e.getY() > (10+activePlayer*130+height - 10) && e.getX() < (80+(width/2)*cardNumber + width/2) && selected[cardNumber-1] == false) {
							if (cardNumber != 0) 
								cardNumber = cardNumber - 1;
							selected[cardNumber] = true;
						} 
						else if (e.getY() < (10+activePlayer*130+height - 10))
							selected[cardNumber] = false;
						
					} else if (e.getY() > (activePlayer*130 + 10))
						selected[cardNumber] = true;
					 else if (selected[cardNumber - 1] && e.getX() < (80+(width/2)*cardNumber + width/2)) 
						selected[cardNumber-1] = false;
					
					this.repaint();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {	
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {	
		}
	}
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Play” button. When the “Play” button is clicked, it calls the makeMove() method of CardGame object to make a move.
	 * @author harsh
	 */
	class PlayButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (game.getPlayerID() == game.getCurrentIdx()) {
				if (getSelected() == null) {
					printMsg("No Cards Selected\n");
					
				} else {
					game.makeMove(activePlayer, getSelected());
				}
			}
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Pass” button. When the “Pass” button is clicked, it calls the makeMove() method of CardGame object to make a move.
	 * @author harsh
	 */
	class PassButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (game.getPlayerID() == game.getCurrentIdx()) {
				game.makeMove(activePlayer, null);
			}
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle button-click events for the “Send” button.
	 * @author harsh
	 */
	class EnterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			CardGameMessage message = new CardGameMessage(CardGameMessage.MSG,-1,chatTypeArea.getText());
			chatTypeArea.setText("");
			game.sendMessage(message);
		}
	}
	
	/**
	 * an inner class that implements the ActionListener interface. 
	 * Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Connect” menu item.
	 * @author harsh
	 */
	class ConnectMenuItemListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		
			if (!game.isConnected()) {
				game.setServerIP("127.0.0.1");
				game.setServerPort(2396);
				game.makeConnection();
				reset();
			}
			else
				System.out.println("elsee");
		}
		
	}
	
	/**
	 * an inner class that implements the ActionListener interface. Implements the actionPerformed() method from the ActionListener interface to handle menu-item-click events for the “Quit” menu item. When the “Quit” menu item is selected, it terminates application.
	 * @author harsh
	 */
	class QuitMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
}