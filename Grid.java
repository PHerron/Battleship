/// Grid

import java.util.*;

public class Grid extends Observable {
	public enum Result {NONE, WIN, LOSE};
	
	// Grid of Locations
	private Location[][] location;
	
	// Battleship length = width, so this works for both dimensions
	private static final int GRIDSIZE = 10;
	
	// Array for the sizes of each individual ship; will have something to do with location.id
	private static final int[] SHIPSIZES = {5, 4, 3, 3, 2};
	
	// How many ships you or the opponent have left
	private int remainingShips = SHIPSIZES.length;
	
	private Random random;
	
	public Grid() {
		location = new Location[GRIDSIZE][GRIDSIZE];
		random = new Random();
		
		for(int i = 0; i < GRIDSIZE; i++){
			for(int j = 0; j < GRIDSIZE; j++){
				location[i][j] = new Location();
			}
        }
		
		deployFleet();
	}
	
	public void deployFleet() {
		// TODO
		// use isValidLocation()
	}
	
	public boolean isValidLocation(int row, int col, 
								   boolean vertical, int shipLength) {
		return false;
	}
	
	public Result getResult() {
		// TODO
		
		int count = 0;
		int mineCount = 0;
		for(int i = 0; i < getHeight(); i++) {
			for(int j = 0; j < getWidth(); j++) {
				if(location[i][j].hasMine() && location[i][j].getType() == Location.Type.UNCOVERED) {
					for(int k = 0; k < getHeight(); k++) {
						for(int l = 0; l < getWidth(); l++) {
							if(location[k][l].hasMine()) uncoverAt(k, l);
						}
					}
					return Result.LOSE;
				}
				else if(location[i][j].getType() == Location.Type.COVERED) {
					count++;
					if(location[i][j].hasMine()) mineCount++;
				}
			}
		}
		if(count == mineCount) return Result.WIN;
        return Result.NONE;
    }
	
	public Location getLocation(int row, int col) {
		return location[row][col];
    }
	
	public boolean isHit(int row, int col) {
		if(getLocation(row, col).isFiredOn() && 
		   (getLocation(row, col).getId() != 0)) {
			return true;
		}
		return false;
	}
	
	// for Observable
	public void fireOn(int row, int col) {
		// TODO
	}
	
	// for Observable
	public void checkSunkShip() {
		// TODO
	}
}