package calendar;

/**
 * TimeInterval class creates time interval for each event; checks for overlapping times of 
 * events when creating them
 * 
 * @author Angela Kim
 * @version 1.0
 * @date 6/18/21
 */

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeInterval implements Comparable<TimeInterval> {
	private LocalTime st;
	private LocalTime et;
	
	/**
	 * Constructs TimeInterval object
	 * 
	 * @param st start time
	 * @param et end time
	 */
	public TimeInterval(LocalTime st, LocalTime et)
	{
		this.st = st;
		this.et = et;
	}
	
	/**
	 * Returns the time interval 
	 * 
	 * @return String of time interval
	 */
	public String TI()
	{
		DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm a");
		String ti = f.format(st);
		String ti2 = f.format(et);
		return ti + " - " + ti2;
 	}
	
	/**
	 * Indicates whether the events overlaps
	 * 
	 * @param e Event to compare event to
	 * @return boolean indicating whether the events overlap
	 */
	public boolean overlap(Event e)
	{
		TimeInterval compare = e.getTI();
		if ((compare.st.getHour() <= this.st.getHour()) && (this.st.getHour() <= compare.et.getHour()))
		{
			return true;
		}
		else if ((this.st.getHour() <= compare.st.getHour()) && (compare.st.getHour() <= this.et.getHour()))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Overrides compareTo() method from the Comparable<> interface
	 * 
	 * @return int indicating comparison
	 */
	public int compareTo(TimeInterval ti)
	{
		if (this.st.isBefore(ti.st) && this.et.isBefore(ti.st))
		{
			return -1;
		}
		else if (this.st.isAfter(ti.et))
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
}
