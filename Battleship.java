/// Playable Battleship Game

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Battleship extends JPanel implements MouseListener,Observer{

	//TODO Create actual icons
	private final ImageIcon WATER_TILE = new ImageIcon(getClass().getClassLoader().getResource("water.png"));
	private final ImageIcon SHIP_HEAD = new ImageIcon(getClass().getClassLoader().getResource("shiphead.png"));
	private final ImageIcon SHIP_BODY = new ImageIcon(getClass().getClassLoader().getResource("shipbody.png"));
	private final ImageIcon HIT = new ImageIcon(getClass().getClassLoader().getResource("hit.png"));

	private Grid grid;
	private Grid opGrid;
	private JLabel[][] tile;
	private JLabel[][] opTile;
	private LogBox logbox;
	private boolean isGameActive;
	private Random random;

	private int[] lastTurn;
	private boolean lastTurnWasHit;

	private class LogBox extends JPanel{
		public JLabel[] moves;
		public int pointer;
		public LogBox(){
			moves = new JLabel[20];
			setLayout(new GridLayout(20,1)); //20 high, 1 wide
			pointer = 0;
		}
		public void add(String s) {
			if(pointer<20)
				moves[pointer++].setText(s);
			else
			{
				for(int i=5;i<20;i++)
					moves[i-5] = moves[i];
				for(int i=15;i<20;i++)
					moves[i].setText("");
				pointer = 15;
				add(s);
			}
			repaint();
		}
	}


	/*
	* Create viewed game board (FRONTEND)
	*/
	public Battleship()
	{

		// Player

		logbox = new LogBox();
		grid = new Grid();
		isGameActive = true;

		grid.addObserver(this); //Observable interface

		setLayout(new GridLayout(10,10)); //Layout management

		//declaration of tile && initialisation of JLabels
		tile = new JLabel[10][10];
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++) {
				tile[i][j] = new JLabel("??????",WATER_TILE,SwingConstants.CENTER);
				tile[i][j].setText("");
				tile[i][j].setIcon(null);
				tile[i][j].setName("cell:"+i+":"+j);
				tile[i][j].setMinimumSize(new Dimension(50,50));
				tile[i][j].setPreferredSize(new Dimension(50,50));
				tile[i][j].setMaximumSize(new Dimension(50,50));
				tile[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				tile[i][j].addMouseListener(this);
				this.add(tile[i][j]);
			}

		// Opponent
		opGrid = new Grid();
		opTile = new JLabel[10][10];
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++) {
				tile[i][j] = new JLabel("??????",WATER_TILE,SwingConstants.CENTER);
				tile[i][j].setText("");
				tile[i][j].setIcon(null);
				tile[i][j].setName("cell:"+i+":"+j);
				tile[i][j].setMinimumSize(new Dimension(50,50));
				tile[i][j].setPreferredSize(new Dimension(50,50));
				tile[i][j].setMaximumSize(new Dimension(50,50));
				tile[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				this.add(tile[i][j]);
			}
	}

	//TODO implement
	public void update(Observable o, Object arg)
	{

	}

	//TODO finish
	public void mouseClicked(MouseEvent click)
	{
		Point p = whereClicked(click);

		//gui checks / changes

		repaint();

		if(grid.getResult(p.x,p.y) == Grid.Result.NONE)
			return;
		else
		{
		isGameActive = false;
		if(grid.getResult(p.x,p.y).equals(Grid.Result.LOSE))
			JOptionPane.showMessageDialog(this,"You Lost! All of your ships sank!","Game Over!",JOptionPane.ERROR_MESSAGE);
		else if(grid.getResult(p.x,p.y) == Grid.Result.WIN)
			JOptionPane.showMessageDialog(this,"You won! You sank all of your opponent's ships!","Game Over!",JOptionPane.PLAIN_MESSAGE);
		}

		opponentTurn();
	}

	public void opponentTurn()
	{
		random = new Random();
		int row = random.nextInt(10);
		int col = random.nextInt(10);
		if(isGameActive && !grid[row][col].isFiredOn()) {
			opGrid.fireOn(row, col);
		}
	}

	public Point whereClicked(MouseEvent click)
	{
		try{
			JLabel j = (JLabel)(event.getSource());
			String[] args = j.getName().split(":");
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			grid.getLocation(x,y); //validity check
			return new Point(x,y);
		}
		catch(NullPointerException e) {throw new ArrayIndexOutOfBoundsException(x+","+y+" is not a valid index.");}
	}


	//DO NOT IMPLEMENT
	public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
}
