import java.util.ArrayList;

public class Calendar {
	
	private ArrayList<Appointment> appointments;
	
	public Calendar () {
		appointments = new ArrayList<>();
	}
	
	public void add (Appointment apt) {
		appointments.add(apt);
	}
	
	public void remove (int year, int month, int day) {
		for (int i = 0; i < appointments.size(); i++) {
			if (appointments.get(i).occursOn(year, month, day) == true) {
				appointments.remove(appointments.get(i));
				i--;
			}
		}
	}
	
	public String toString () {
		String ret = "";
		for (Appointment i : appointments) {
			ret += i.toString() + "\n";
		}
		return ret;
	}
}
