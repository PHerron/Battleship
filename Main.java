/// Battleship Main

import javax.swing.*;

public class Main{
	
	public static void main(String[] args) {
		Battleship board = new Battleship();
        JFrame win = new JFrame("Battleship");
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.add(board);
        win.pack();
        win.setVisible(true);
	}
}