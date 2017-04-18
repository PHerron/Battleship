/// Location on Grid

/**
 * Individual cell of Grid
 * Keep track of own place on grid (?)
 * Enum type
 * Boolean firedOn
 */
public class Location {
	// This enum is mostly just to tell which graphic will be used for each tile
	public enum Type {EMPTY, SHIPHEAD, SHIPBODY};
	public Type type;
	
	/**
	 * This is the type of ship, or lack thereof, that is within the location
	 * 0 is an empty location
	 * 1-5 correspond to the 0-4 indices of the SHIPSIZES array in Grid.java
	 * 1 is the Carrier
	 * 2 is the Battleship
	 * 3 is the Cruiser
	 * 4 is the Submarine
	 * 5 is the Destroyer
	 * 
	 * We included this to hopefully avoid adding another class for the ships,
	 * but if it limits functionality, we may need to include Ship.java
	 */
	private int id;
	
	// This indicates whether the location has been clicked, by either the player or the AI
	private boolean firedOn;
	
	public Location() {
		type = Type.EMPTY;
		id = 0;
		firedOn = false;
	}
	
	public void setId(int id) {
		// if 0-numShips
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setFiredOn(boolean firedOn) {
		this.firedOn = firedOn;
	}
	
	public boolean isFiredOn() {
		return firedOn;
	}
}