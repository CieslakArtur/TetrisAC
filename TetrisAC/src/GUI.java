import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;
/**
 * The class is responsible for displaying the Tetris graphical interface.
 * @author Artur Cieslak
 * @version 1.0
 * @since 26.04.2017
 */
public class GUI extends JFrame implements ActionListener{
	/**
	 * The Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	//Objects of the 'Shape' class
	private Shape brick,nextBrick;
	private Timer time;
	private final int gridSize=30;
	//Two-dimensional array containing brick coordinates
	private int[][] drawShape;
	private boolean isPaused=true;
	/**Creates a new GUI instance. Sets up the window's properties and adds a controller listener. */
	public GUI(){
		/*
		 * Set the properties of the window
		 */
		super("TETRIS");
		createGUI();
		//Create an array to store coordinates
		drawShape=new int[4][2];
		//Create a Shape object
		brick=new Shape(5,gridSize);
		//Create a Shape object
		nextBrick=new Shape(5,gridSize);
		//Updating view
		repaint();
		//Adds listener
		addKeyListener(new ButtonAdapter());
		//Create a Timer object
		time=new Timer(300,this);
	}		
	/**Creates a graphical user interface */
	public void createGUI(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar mb=new JMenuBar();
		setJMenuBar(mb);
		JPanel panel=new JPanel();
		mb.add(panel);
		
		JButton newGame=new JButton("New Game");
		//Set focusable flag 
		newGame.setFocusable(false);
		//Button action in anonymous class
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DisplayTable.resetTable();
				brick=new Shape(5,gridSize);
				nextBrick=new Shape(5,gridSize);
				time.stop();
				isPaused=true;
				repaint();
			}
		});
		panel.add(newGame);
		
		JButton start=new JButton("Start");
		//Set focusable flag 
		start.setFocusable(false);
		//Button action in anonymous class
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				time.start();
				isPaused=false;
			}
		});
		panel.add(start);
		
		JButton pause=new JButton("Pause");
		//Set focusable flag 
		pause.setFocusable(false);
		//Button action in anonymous class
		pause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					time.stop();
					isPaused=true;
			}
		});
		panel.add(pause);
		
		JButton help=new JButton("Help");
		/*Set focusable flag */
		help.setFocusable(false);
		//Button action in anonymous class
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				//Shows control buttons
				JOptionPane.showMessageDialog(mb, "Move by arrows\n"
						+ "'A' - Rotate left\n"
						+ "'D' - Rotate right\n"
						+ "Press 'P' to Pause(Unpause)\n",
						"Help",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		panel.add(help);
		
		pack();
		//Set the window size
		setSize(10*gridSize+30,20*gridSize+100);
		setResizable(false);
		setVisible(true);
	}
	/**
	 * Draws the current location of items 
	 */
	public void paint(Graphics g){
		super.paint(g);
		//An array of colors
		Color colors[] = {new Color(204, 74, 102), 
	            new Color(102, 204, 102), new Color(102, 102, 204), 
	            new Color(204, 204, 102), new Color(204, 102, 204), 
	            new Color(102, 204, 204), new Color(218, 170, 0)};
		//Origin of coordinate system
		g.translate(15,80);
		//Drawing background
		g.setColor(new Color(79,156,156));
		g.drawRect(0, 0, gridSize*10, gridSize*20);
		g.fillRect(0, 0, gridSize*10, gridSize*20);
		
		//Get the brick's coordinates and display them on the screen
		brick.setDrawShape(drawShape);
		for(int i=0;i<4;i++){
			g.setColor(colors[brick.getRand()]);
			g.fillRect(drawShape[i][0], drawShape[i][1], gridSize, gridSize);
			g.setColor(Color.WHITE);
			g.drawRect(drawShape[i][0], drawShape[i][1], gridSize, gridSize);
		}
		//Get the next brick's coordinates and display them on the screen
		nextBrick.setDrawShape(drawShape);
		for(int i=0;i<4;i++){
			g.setColor(colors[nextBrick.getRand()]);
			g.fillRect(drawShape[i][0]/2-30, drawShape[i][1]/2+30, gridSize/2, gridSize/2);
			g.setColor(Color.WHITE);
			g.drawRect(drawShape[i][0]/2-30, drawShape[i][1]/2+30, gridSize/2, gridSize/2);
		}
		//Display all 'gridTab' elements
		for(int i=0;i<20;i++){
			for(int j=0;j<10;j++){
				//If the cell of the array is not empty 
				if(DisplayTable.checkGridTab(j,i)==true){
					g.setColor(new Color(207,128,0));
					g.fillRect((j)*gridSize,(i)*gridSize,gridSize,gridSize);
					g.setColor(Color.WHITE);
					g.drawRect((j)*gridSize,(i)*gridSize,gridSize,gridSize);
				}
			}
		}
		//Display the player score
		g.setColor(Color.BLACK);
		g.drawString("SCORE "+DisplayTable.getScore(),15, 20);
		//Displaying the message 'GAME OVER' when the game ends
		if(DisplayTable.gameOver()){
			g.setFont(new Font("name",Font.BOLD,20));
			g.drawString("GAME OVER", 100, 300);
		}
	}
	/**
	 * Method is executed when the state of the object 'time' changes 
	 */
	public void actionPerformed(ActionEvent e) {
		//If the brick is not falling and the game is not finished
		if(!DisplayTable.isFalling() && !DisplayTable.gameOver()){
			brick=nextBrick;
			//Create a new object that will appear next
			nextBrick=new Shape(5,gridSize);
			//Removing filled lines
			DisplayTable.eraseLine();
			repaint();
		//If the game is over	
		}else if(DisplayTable.gameOver()){
			time.stop();
		}else{
			//Increase the Y coordinate
			brick.incY();
			//Updating brick coordinates
			brick.setDrawShape(drawShape);
			//Updating view
			repaint();
		}
	}
	
	/**
	 * Inner class responsible for handling keyboard events.
	 * @author Artur Cieœlak
	 * @version 1.0
 	 * @since 28.03.2017r.
	 *
	 */
	class ButtonAdapter extends KeyAdapter{
		//KeyListener class method
		public void keyPressed(KeyEvent e) {
			//If the game is not paused, it is not finished and the brick is falling
			if(!isPaused && !DisplayTable.isGameOver() && DisplayTable.isFalling()){
				//If the right arrow was pressed
				if(e.getKeyCode()==KeyEvent.VK_RIGHT){
					//Increase the X coordinate
					brick.incX();
				//If the left arrow was pressed
				}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
					//Decrease the X coordinate
					brick.decX();
				//If the 'A' button was pressed
				}else if(e.getKeyCode()==KeyEvent.VK_A){
					//Rotate left
					brick.rotateLeft();
				//If the 'D' button was pressed
				}else if(e.getKeyCode()==KeyEvent.VK_D){
					//Rotate right
					brick.rotateRight();
				//If the down arrow was pressed
				}else if(e.getKeyCode()==KeyEvent.VK_DOWN){
					//Increase the Y coordinate
					brick.incY();
				}
				//Updating brick coordinates
				brick.setDrawShape(drawShape);
				//Updating view
				repaint();
			}
			//If the 'P' button was pressed
			if(e.getKeyCode()==KeyEvent.VK_P){
				//If the game is not paused and it is not finished
				if(!isPaused && !DisplayTable.isGameOver()){
					time.stop();
					isPaused=true;
				//If the game is paused
				}else if(isPaused){
						time.start();
						isPaused=false;
				}
			}
		}
	}
}












