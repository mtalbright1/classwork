import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;

public class ParkingDemo {

	static ParkingLot smartLot;
	
	public static void main(String[] args) {
		smartLot = new ParkingLot();
		boolean running = true;
		Scanner input = new Scanner(System.in);
		String licensePlate;
		
		while(running) {
			System.out.print("=== SMART CITY PARKING SYSTEM ===\n" +
							 "\n" +
							 "1.Enter Parking Lot\n" +
							 "2.Exit Parking Lot\n" +
							 "3.View Current Rates\n" +
							 "4.View Lot Occupancy\n" +
							 "5.View Statistics Report\n" +
							 "6.Admin: Update Graph\n" +
					 		 "7.Exit System\n");
			
			switch(input.nextInt()) {
			case(1):
				System.out.print("--- VEHICLE ENTRY ---\n" +
								 "Enter license plate: ");
				licensePlate = input.nextLine();
				System.out.print("Select vehicle type:\n" +
								 "1.Car\n" +
								 "2.Motorcycle\n" +
								 "3.Electric Vehicle\n" +
								 "Choice: ");
				int vehicleType = input.nextInt();
				vehicleEntry(licensePlate, vehicleType);
				break;
			case(2):
				System.out.print("--- VEHICLE EXIT ---\n" +
								 "Enter license plate: ");
				licensePlate = input.nextLine();
				vehicleExit(licensePlate);
				break;
			case(3):
				String currentDemand = smartLot.calculateDemand();
				System.out.println("--- CURRENT RATES ---");
				if (currentDemand.equals("HIGH")) {
					System.out.printf("Standard: %f.2\n", 5*1.5);
					System.out.printf("Electric: %f.2\n", 7*1.5*0.7);
					System.out.printf("Compact: %f.2\n", 5*1.5*0.5);
				}
				else if (currentDemand.equals("LOW")) {
					System.out.printf("Standard: %f.2\n", 5*0.7);
					System.out.printf("Electric: %f.2\n", 7*0.7*0.7);
					System.out.printf("Compact: %f.2\n", 5*0.7*0.5);
				}
				else {
					System.out.printf("Standard: %f.2\n", 5);
					System.out.printf("Electric: %f.2\n", 7*0.7);
					System.out.printf("Compact: %f.2\n", 5*0.5);
				}
				break;
			case(4):
				int[] occupancy = smartLot.getOccupancy();
				System.out.print("--- LOT OCCUPANCY ---\n" + 
								 "Standard: " + occupancy[0] + "/15\n" +
								 "Electric: " + occupancy[1] + "/5\n" +
								 "Compact: " + occupancy[2] + "/5\n");
				break;
			case(5):
				
			}
		}
		
		input.close();
	}
	
	private static void vehicleEntry(String licensePlate, int vehicleType) {
		switch(vehicleType) {
		case(1):		// Car
			
			break;
		case(2):		// Motorcycle
			
			break;
		case(3):		// Electric Vehicle
			
			break;
		default:
			System.out.println("Error: Invalid vehicle type");
			return;
		}
	}
	
	private static void vehicleExit(String licensePlate) {
		Vehicle exitingVehicle = smartLot.getVehicleByLicensePlate(licensePlate);
		ParkingSpot spot = smartLot.getSpotByVehicle(exitingVehicle);
		
		exitingVehicle.setExitTime();
		exitingVehicle.calculateFee(smartLot.calculateDemand());
		smartLot.removeVehicle(exitingVehicle);
		System.out.println("Spot " + spot.getNumber() + " is now available.");
	}
	
	
}
