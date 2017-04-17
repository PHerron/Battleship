I created this file for I did not know how y"all are going to work with the ships.
This class saves the ship type, and array of location that correspond with the ship, anbd the health of the ship.
Travis and I thought that this would be the best thing to do; There wasnt much on here to go off of so we tried
to create something that could help in the creation of the ships.



publc class Ship(){
	
	private enum ShipType {CARRIER, BATTLESHIP, CRUISER, SUBMARINE, DESTROYER};
	
	public int life;
	public Location[] location;
	public ShipType type;
	
	public Ship(ShipType t){
		shipType = t;
		this.setlife();
	}
	
	public void setLocation(Location[] location){
		this.location = location;
	}
	
	public void setLife(){
		if(shipType == ShipType.CARRIER){
			life = 5;
		}
		else if(shipType == ShipType.BATTLESHIP){
			life = 4;
		}
		else if(shipType == ShipType.CRUISER){
			life = 3;
		}
		else if(shipType == ShipType.SUBMARINE){
			life = 3;
		}
		else{
			life = 2;
		}
	}
	
	public int getLife(){
		return this.Life;
	}
	
	public void takesHit(){
		this.Life--;
	}
	
	public void setShipType(ShipType t){
		this.shipType = t;
	}
	
	public ShipType(){
		return shipType;
	}
	
	

	

}
