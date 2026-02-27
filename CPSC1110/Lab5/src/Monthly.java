
public class Monthly extends Appointment {

	public Monthly(int year, int month, int day, String description) {
		super(year, month, day, description);
	}
	
	@Override
	public boolean occursOn(int year, int month, int day) {
		if((getYear() < year && getDay() == day) || (getYear() == year && getMonth() <= month && getDay() == day)) {
			return true;
		}
		return false;
	}
}

