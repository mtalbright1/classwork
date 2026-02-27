
public class Daily extends Appointment {

	public Daily(int year, int month, int day, String description) {
		super(year, month, day, description);
	}
	
	@Override
	public boolean occursOn(int year, int month, int day) {
		if(getYear() < year || (getYear() == year && getMonth() < month) || (getYear() == year && getMonth() == month && getDay() <= day)) {
			return true;
		}
		return false;
	}
}
