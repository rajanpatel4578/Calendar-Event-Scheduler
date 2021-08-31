package calendar;

/**
 * Event class allows construction of Event objects with name, day, and time
 * 
 * @author Angela Kim
 * @version 1.0
 * @date 6/18/21
 */


import java.util.*;
import java.text.Format;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;

public class Event implements Comparable<Event>{
	
	private String name;
	private TimeInterval ti;
	private String day;
	private String type;
	private LocalDate date;
	private String[] d;
	private TreeSet<Event> recurring;
	private Integer mo, dy, yr;
	public String startDay;
	public String endDay;
	
	/**
	 * Constructs Event object
	 * 
	 * @param name name of event
	 * @param ti time interval for event
	 * @param day day that event occurs
	 */
	public Event(String name, TimeInterval ti, String day)
	{
		this.name = name;
		this.ti = ti;
		this.day = day;
		char c = day.charAt(0);
		if (Character.isDigit(c))
		{
			d = day.split("/");
			mo = Integer.parseInt(d[0]);
			dy = Integer.parseInt(d[1]);
			yr = Integer.parseInt(d[2]);
			yr += 2000;
			date = LocalDate.of(yr, mo, dy);
		}
	}
	
	/**
	 * Return name of the event
	 * 
	 * @return String name of event
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the time interval of the event
	 * 
	 * @return TimeInterval of the event
	 */
	public TimeInterval getTI()
	{
		return ti;
	}
	
	/**
	 * Returns the date of the event in String
	 * 
	 * @return String of date of event
	 */
	public String getDay()
	{
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yy");
		return f.format(date);
	}
	
	/**
	 * Returns the date of the event in LocalDate
	 * 
	 * @return LocalDate date of event
	 */
	public LocalDate getDate()
	{
		return date;
	}

	/**
	 * Sets the type of the event; "o" for one time or "r" for recurring
	 * 
	 * @param s String of type
	 */
	public void setType(String s)
	{
		this.type = s;
	}
	
	/**
	 * Returns the type of the event
	 * 
	 * @return String of type of event
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Returns a TreeSet of recurring events; used to create recurring events given an event read from file
	 * 
	 * @return TreeSet of recurring events
	 */
	public TreeSet<Event> recurring()
	{
		recurring = new TreeSet<>();
		d = day.split("");
		day = "";
		for (int i = 0; i < d.length; i++)
		{
			if (d[i].equals("S"))
			{
				day += "Sun ";
			}
			else if (d[i].equals("M"))
			{
				day += "Mon ";
			}
			else if (d[i].equals("T"))
			{
				day += "Tue ";
			}
			else if (d[i].equals("W"))
			{
				day += "Wed ";
			}
			else if (d[i].equals("R"))
			{
				day += "Thu ";
			}
			else if (d[i].equals("F"))
			{
				day += "Fri ";
			}
			else if (d[i].equals("A"))
			{
				day += "Sat ";
			}
		}
			
		String[] s = startDay.split("/");
		mo = Integer.parseInt(s[0]);
		dy = Integer.parseInt(s[1]);
		yr = Integer.parseInt(s[2]);
		LocalDate start = LocalDate.of(yr, mo, dy);
		String[] e = endDay.split("/");
		mo = Integer.parseInt(e[0]);
		dy = Integer.parseInt(e[1]);
		yr = Integer.parseInt(e[2]);
		LocalDate end = LocalDate.of(yr, mo, dy); 
		
		for (int i = start.getDayOfYear(); i <= end.getDayOfYear(); i++)
		{
			String thisDay = start.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
			String thisDate = "";
			if (day.contains(thisDay))
			{
				thisDate = String.valueOf(start.getMonthValue()) + "/" + String.valueOf(start.getDayOfMonth()) + "/" + String.valueOf(start.getYear());
				Event re = new Event(this.name, this.ti, thisDate);
				recurring.add(re);
			}
			start = start.plusDays(1);
		}
		
		return recurring;
	}
	
	/**
	 * Returns the start day of a recurring event
	 * 
	 * @return LocalDate of start day of the recurring event
	 */
	public LocalDate getStartDay()
	{
		if (startDay != null)
		{
			String[] d = startDay.split("/");
			if (d[0].length() == 1)
			{
				d[0] = "0" + d[0];
			}
			if (d[1].length() == 1)
			{
				d[1] = "0" + d[1];
			}
			int yr = Integer.parseInt(d[2]);
			int mo = Integer.parseInt(d[0]);
			int dy = Integer.parseInt(d[1]);
			LocalDate start = LocalDate.of(yr, mo, dy);
			return start;
		}
		return null;
	}
	
	/**
	 * Overrides the compareTo() method from the Comparable<> interface
	 * 
	 * @return int indicating comparison
	 */
	public int compareTo(Event e) 
	{
		if (this.date.getYear() < e.date.getYear())
		{
			return -1;
		}
		else if (this.date.getYear() > e.date.getYear())
		{
			return 1;
		}
		else 
		{
			if (this.date.getMonthValue() < e.date.getMonthValue())
			{
				return -1;
			}
			else if (this.date.getMonthValue() > e.date.getMonthValue())
			{
				return 1;
			}
			else
			{
				if (this.date.getDayOfMonth() < e.date.getDayOfMonth())
				{
					return -1;
				}
				else if (this.date.getDayOfMonth() > e.date.getDayOfMonth())
				{
					return 1;
				}
				else 
				{
					if (this.ti.compareTo(e.ti) < 0)
					{
						return -1;
					}
					else if(this.ti.compareTo(e.ti) > 0)
					{
						return 1;
					}
					else
					{
						return 0;
					}
				}
			}
		}
	}
	
}
