
public class Daily extends Appointment {

	public Daily (int year, int month, int day, String description) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.description = description;
	}
	
	public boolean occursOn (int year, int month, int day) {
		if (this.year < year || (this.year == year && this.month < month) || (this.year == year && this.month == month && this.day <= day)) {
			return true;
		}
		return false;
	}
}
