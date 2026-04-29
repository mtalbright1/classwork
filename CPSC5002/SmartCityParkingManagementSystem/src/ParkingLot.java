import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ParkingLot {

	private final int CAPACITY = 25;
	private int[][] adjacencyMatrix;
	private List<ParkingSpot> spots;
	private Map<Vehicle, ParkingSpot> reservations;
	private Queue<Vehicle> queue;
	
	public ParkingLot() {
		adjacencyMatrix = new int[CAPACITY][CAPACITY];
		spots = new ArrayList<>();
		reservations = new HashMap<>();
		queue = new LinkedList<>();
		
		for (int i = 0; i < CAPACITY; i++) {		// initialize adjacencyMatrix
	        if (i + 1 < CAPACITY && (i / 5) == ((i + 1) / 5)) {
	            adjacencyMatrix[i][i + 1] = 1;		// Only connect to the next spot if they're in the same row
	            adjacencyMatrix[i + 1][i] = 1;		// Same row means they have the same (i / 5) value
	        }
	    }
		
		for (int i = 1; i <= CAPACITY; i++) {
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
		reservations.put(vehicle, spot);		// assign the spot value to the vehicle key
		spot.occupySpot(vehicle);		// set the spot's vehicle
		vehicle.setAssignedSpot(spot);		// set the vehicle's spot
		vehicle.setDemandUponArrival(calculateDemand());		// sets the demandUponArrival
		vehicle.setEntryTime(); 		// set the entryTime
	}
	
	public void removeVehicle(Vehicle vehicle) {
		ParkingSpot spot = reservations.get(vehicle);		// get this vehicle's spot
		spot.emptySpot();		// empty this spot
		reservations.remove(vehicle);		// remove the vehicle from the map
	}
	
	public Vehicle getVehicleByLicensePlate(String licensePlate) {
		for (Vehicle i : reservations.keySet()) {
			if (i.getLicensePlate().equals(licensePlate)) {		// if license plates match..
				return i;
			}
		}
		System.out.println("Error: Couldn't find vehicle trying to exit");
		return null;
	}
	
	public ParkingSpot getSpotByVehicle(Vehicle vehicle) {
		for (Map.Entry<Vehicle, ParkingSpot> i : reservations.entrySet()) {
			if (i.getKey() == vehicle) {
				return i.getValue();
			}
		}
		System.out.println("Error: Could not find vehicle's spot");
		return null;
	}
	
	public String calculateDemand() {
		int count = 0;
		
		for (Map.Entry<Vehicle, ParkingSpot> i : reservations.entrySet()) {
			if (i.getKey() != null && i.getValue() != null) {
				count++;
			}
		}
		
		double ratio = count / CAPACITY;
		
		if (ratio > 0.8) { return "HIGH"; }
		else if (ratio < 0.3) { return "LOW"; }
		else { return "STANDARD"; }
	}
	
	public int[] getOccupancy() {
		int[] occupancy = new int[3];
		
		for (ParkingSpot i : reservations.values()) {
			if (i.getIsOccupied()) {
				if (i.getType().equals("STANDARD")) { occupancy[0]++; }
				else if (i.getType().equals("ELECTRIC")) { occupancy[1]++; }
				else if (i.getType().equals("COMPACT")) { occupancy[2]++; }
			}
		}
		
		return occupancy;
	}
}
