
public class Appointment {
	
	int year, month, day;
	String description;
	
	public int getYear () {
		return this.year;
	}
	
	public int getMonth () {
		return this.month;
	}
	
	public int getDay () {
		return this.day;
	}
	
	public String getDescription () {
		return this.description;
	}
	
	public String toString () {
		return this.getClass().getName() + "[" + this.description + " Date: " + this.month + "/" + this.day + "/" + this.year + "]";
	}
	
	public boolean occursOn (int year, int month, int day) {
		return false;
	}
}