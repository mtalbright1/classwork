
public class ParkingSpot {
	
	private int spotID;
	private String number;
	private String type;
	private boolean isOccupied;
	private Vehicle occupant;
	
	public ParkingSpot(int spotID) {
		this.spotID = spotID;
		
		int row = (spotID - 1) / 5;		// initializes 0 for 1-5, 1 for 6-10, etc
		int positionInRow = ((spotID - 1) % 5) + 1;		// initializes as 1-5 for 1-5 or for 6-10, etc 
		switch(row) {
		case(0): number = "A" + positionInRow; break;		// A1 - A5
		case(1): number = "B" + positionInRow; break;
		case(2): number = "C" + positionInRow; break;
		case(3): number = "D" + positionInRow; break;
		case(4): number = "M" + positionInRow; break;		// M1 - M5 is motorcycle/compact parking
		default: System.out.println("Error: Invalid parking number");
		}
		
		if (row == 4) { type = "COMPACT"; }
		else { type = "STANDARD"; }
		
		isOccupied = false;
		occupant = null;
	}
	
	public void occupySpot(Vehicle occupant) {
		if (isOccupied == false) {
			this.occupant = occupant;
			isOccupied = true;
		}
		else {
			System.out.println("Error: Tried to occupy an occupied spot");
		}
	}
	
	public void emptySpot() {
		if (isOccupied == true) {
			this.occupant = null;
			isOccupied = false;
		}
		else {
			System.out.println("Error: Tried to empty an empty spot");
		}
	}
	
	// accessors
	public int getSpotID() { return spotID; }
	
	public String getNumber() { return number; }
	
	public String getType() { return type; }
	
	public boolean getIsOccupied() { return isOccupied; }
	
	public Vehicle getOccupant() { return occupant; }
}
