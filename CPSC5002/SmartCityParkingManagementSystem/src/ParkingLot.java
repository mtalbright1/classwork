import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ParkingLot {

	private int[][] adjacencyMatrix;
	private List<ParkingSpot> spots;
	private Map<Vehicle, ParkingSpot> reservations;
	private Queue<Vehicle> queue;
	
	public ParkingLot() {
		adjacencyMatrix = new int[25][25];
		spots = new ArrayList<>();
		reservations = new HashMap<>();
		queue = new LinkedList<>();
		
		for (int i = 0; i < 25; i++) {		// initialize adjacencyMatrix
	        if (i + 1 < 25 && (i / 5) == ((i + 1) / 5)) {
	            adjacencyMatrix[i][i + 1] = 1;		// Only connect to the next spot if they're in the same row
	            adjacencyMatrix[i + 1][i] = 1;		// Same row means they have the same (i / 5) value
	        }
	    }
		
		for (int i = 1; i <= 25; i++) {
			ParkingSpot spot = new ParkingSpot(i);		// fill with 25 parking spots
			spots.add(spot);
		}
	}
	
	public void addToQueue(Vehicle v) {
		queue.offer(v);		// puts vehicle at the back of the queue
	}
	
	public Vehicle getFromQueue() {
		return queue.poll(); 	// returns front vehicle in queue or null if empty
	}
	
	public void parkVehicle(Vehicle vehicle, ParkingSpot spot) {
		reservations.put(vehicle, spot);		// assign the vehicle to the spot
		spot.occupySpot(vehicle);		// set the spot as occupied
	}
	
	public void removeVehicle(Vehicle vehicle) {
		ParkingSpot spot = reservations.get(vehicle);		// get this vehicle's spot
		spot.emptySpot();		// empty this spot
		reservations.remove(vehicle);		// remove the vehicle from the map
	}
}
