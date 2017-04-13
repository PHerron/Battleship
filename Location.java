/// Location on Grid

/**
 * Individual cell of Grid
 * Keep track of own place on grid (?)
 * Enum type
 * Boolean firedOn
 */
public class Location {
	public enum Type {EMPTY, SHIPHEAD, SHIPBODY};
	public Type type;
	private int id;
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