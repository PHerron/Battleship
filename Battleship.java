/// Playable Battleship Game

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Battleship extends JPanel implements MouseListener,Observer{

	//TODO Create actual icons
	private final ImageIcon WATER_TILE = new ImageIcon(getClass().getClassLoader().getResource("water.png"));
	private final ImageIcon SHIP_TILE = new ImageIcon(getClass().getClassLoader().getResource("ship.png"));
	private final ImageIcon HIT = new ImageIcon(getClass().getClassLoader().getResource("hitship.png"));
	private final ImageIcon MISS = new ImageIcon(getClass().getClassLoader().getResource("misswater.png"));

	private Grid grid;
	private Grid opGrid;
	private JLabel[][] tile;
	private JLabel[][] opTile;
	private LogBox logbox;
	private boolean isGameActive;
	private Random random;

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

		setLayout(new GridLayout(21,10)); //Layout management

		//declaration of tile && initialisation of JLabels
		tile = new JLabel[10][10];
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++) {
				tile[i][j] = new JLabel("??????",WATER_TILE,SwingConstants.CENTER);
				tile[i][j].setIcon(WATER_TILE);
				tile[i][j].setText("");
				tile[i][j].setName("cell:"+i+":"+j);
				tile[i][j].setMinimumSize(new Dimension(50,50));
				tile[i][j].setPreferredSize(new Dimension(50,50));
				tile[i][j].setMaximumSize(new Dimension(50,50));
				tile[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				tile[i][j].addMouseListener(this);
				this.add(tile[i][j]);
			}
		
		JLabel playerLabel = new JLabel("^ Player");
		playerLabel.setMinimumSize(new Dimension(50,50));
		playerLabel.setPreferredSize(new Dimension(50,50));
		playerLabel.setMaximumSize(new Dimension(50,50));
		add(playerLabel);
		JLabel[] blank = new JLabel[8];
		for(int i = 0; i < 8; i++) {
			blank[i] = new JLabel();
			blank[i].setMinimumSize(new Dimension(50,50));
			blank[i].setPreferredSize(new Dimension(50,50));
			blank[i].setMaximumSize(new Dimension(50,50));
			add(blank[i]);
		}
		JLabel opLabel = new JLabel("CPU v");
		opLabel.setMinimumSize(new Dimension(50,50));
		opLabel.setPreferredSize(new Dimension(50,50));
		opLabel.setMaximumSize(new Dimension(50,50));
		add(opLabel);
		
		// Opponent
		opGrid = new Grid();
		opTile = new JLabel[10][10];
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++) {
				opTile[i][j] = new JLabel("??????",WATER_TILE,SwingConstants.CENTER);
				opTile[i][j].setIcon(WATER_TILE);
				if(opGrid.getLocation(i, j).getId() != 0) 
					opTile[i][j].setIcon(SHIP_TILE);
				opTile[i][j].setText("");
				opTile[i][j].setName("cell:"+i+":"+j);
				opTile[i][j].setMinimumSize(new Dimension(50,50));
				opTile[i][j].setPreferredSize(new Dimension(50,50));
				opTile[i][j].setMaximumSize(new Dimension(50,50));
				opTile[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
				this.add(opTile[i][j]);
			}
	}

	//TODO implement
	public void update(Observable o, Object arg)
	{
		String s = (String)arg;
		String[] args = s.split(":");
		if(args[0].equals("firedOnPlayer"))
		{
			if(grid.getLocation(Integer.parseInt(args[1]),Integer.parseInt(args[2])).getId() != 0)
				tile[Integer.parseInt(args[1])][Integer.parseInt(args[2])].setIcon(HIT);
			else
				tile[Integer.parseInt(args[1])][Integer.parseInt(args[2])].setIcon(MISS);
		}
		else if(args[0].equals("firedOnCPU"))
		{
			if(opGrid.getLocation(Integer.parseInt(args[1]),Integer.parseInt(args[2])).getId() != 0)
				opTile[Integer.parseInt(args[1])][Integer.parseInt(args[2])].setIcon(HIT);
			else
				opTile[Integer.parseInt(args[1])][Integer.parseInt(args[2])].setIcon(MISS);
		}
		else if(args[0].equals("shipSunk"))
		{
			logbox.add("Ship of size "+Integer.parseInt(args[1])+" sank.");
		}
	}

	//TODO finish
	public void mouseClicked(MouseEvent click)
	{
		Point p = whereClicked(click);

		if(click.getButton() == MouseEvent.BUTTON1)
		{
			grid.fireOn(true, p.y,p.x);
			if(grid.getResult().equals(Grid.Result.LOSE)) {
				isGameActive = false;
				JOptionPane.showMessageDialog(this,"You won! You sank all of your opponent's ships!","Game Over!",JOptionPane.PLAIN_MESSAGE);
			}
			
			opponentTurn();
		}
		
		repaint();
	}

	public void opponentTurn()
	{
		random = new Random();
		int row = random.nextInt(10);
		int col = random.nextInt(10);
		System.out.println("Firing On");
		System.out.println(isGameActive + " " + opGrid.getLocation(row,col).isFiredOn());
		if(isGameActive && !opGrid.getLocation(row,col).isFiredOn()) {
			opGrid.fireOn(false, row, col);
			System.out.println("Fired On");
		}
		System.out.println("Fire Complete");

		if(opGrid.getResult() == Grid.Result.LOSE) {
			isGameActive = false;
			JOptionPane.showMessageDialog(this,"You Lost! All of your ships sank!","Game Over!",JOptionPane.ERROR_MESSAGE);
		}
	}

	public Point whereClicked(MouseEvent click)
	{
		int x = -1;
		int y = -1;
		try{
			JLabel j = (JLabel)(click.getSource());
			String[] args = j.getName().split(":");
			x = Integer.parseInt(args[1]);
			y = Integer.parseInt(args[2]);
			grid.getLocation(x,y); //validity check
			return new Point(y,x);
		}
		catch(NullPointerException e) {throw new ArrayIndexOutOfBoundsException(x+","+y+" is not a valid index.");}
	}


	//DO NOT IMPLEMENT
	public void mousePressed(MouseEvent event) {}
    public void mouseReleased(MouseEvent event) {}
    public void mouseEntered(MouseEvent event) {}
    public void mouseExited(MouseEvent event) {}
}
