/// Location on Grid

/**
 * Individual cell of Grid
 * Keep track of own place on grid (?)
 * Enum type
 * Boolean firedOn
 */
public class Location {
	// This enum is mostly just to tell which graphic will be used for each tile
	public enum Type {EMPTY, SHIP};
	public Type type;
	
	/*
	* Differentiates ships from wach other to avoid collision on ship sinking detction
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