
public class Appointment {
	
	private int year, month, day;
	private String description;
	
	public Appointment(int year, int month, int day, String description) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.description = description;
	}
	
	public int getYear() {
		return year;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getDay() {
		return day;
	}
	
	public String getDescription() {
		return description;
	}
	
	@Override
	public String toString() {
		return getClass().getName() + "[" + description + " Date: " + month + "/" + day + "/" + year + "]";
	}
	
	public boolean occursOn(int year, int month, int day) {
		return false;
	}
}