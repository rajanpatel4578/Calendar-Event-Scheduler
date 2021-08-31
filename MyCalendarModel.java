package calendar;
/**
 * MyCalendar class allows manipulation of calendar events; stores events in TreeSets; reads 
 * events from pre-existing .txt file
 * 
 * @author Angela Kim, Rajan Patel
 * @version 1.1
 * @date 8/01/21
 */


import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.io.*;

public class MyCalendarModel {

	private TreeSet<Event> myEvents;
	private ArrayList<String> events;
	private ArrayList<String> recurringEvents;
	private ChangeListener listener;
	private LocalDate currentCalendarDate = null;						// initial Current Calendar shall show current month
	private LocalDate selectedCalendarDate = LocalDate.now();			//date responsible for showing correct selected calendar view
	private Boolean dateViewSelected = true;
	private Boolean weekViewSelected = false;
	private Boolean monthViewSelected = false;
	
	/**
	 * Constructs MyCalendareModel object
	 */
	public MyCalendarModel()
	{
		myEvents = new TreeSet<>();
		currentCalendarDate = LocalDate.now();
	}
	
	// Rajan's addition
	/**
	 * Checks if DayView is active
	 * 
	 * @return boolean true if current view is day view
	 */
	public Boolean isDateViewActive() {
		return dateViewSelected;
	}
	
	/**
	 * Checks if WeekView is active
	 * 
	 * @return boolean true if current view is week view
	 */
	public Boolean isWeekViewActive() {
		return weekViewSelected;
	}
	
	/**
	 * Checks if MonthView is active
	 * 
	 * @return boolean true if current view is month view
	 */
	public Boolean isMonthViewActive() {
		return monthViewSelected;
	}
	
	/**
	 * Activates DayView
	 */
	public void activateDateView() {
		dateViewSelected=true;
		weekViewSelected=false;
		monthViewSelected=false;
	}
	
	/**
	 * Activates WeekView
	 */
	public void activateWeekView() {
		dateViewSelected=false;
		weekViewSelected=true;
		monthViewSelected=false;
	}
	
	/**
	 * Activates MonthView
	 */
	public void activateMonthView() {
		dateViewSelected=false;
		weekViewSelected=false;
		monthViewSelected=true;
	}
	
	/**
	 * Sets currentCalendarDate
	 * 
	 * @param someDate sets currentCalendarDate to someDate
	 */
	public void setCurrentCalendarDate(LocalDate someDate) {
		currentCalendarDate = someDate;
	}
	
	/**
	 * Gets currentCalendarDate
	 * 
	 * @return LocalDate currentCalendarDate
	 */
	public LocalDate getCurrentCalendarDate() {
		return currentCalendarDate;
	}
	
	/**
	 * Gets previous month
	 * 
	 * @param someDate previous month from this date
	 * @return LocalDate one month before someDate
	 */
	public LocalDate getPrevMonth(LocalDate someDate) {
		currentCalendarDate = someDate.minusMonths(1);
		return currentCalendarDate;
	}
	
	/**
	 * Gets next month
	 * 
	 * @param someDate next month from this date
	 * @return LocalDate one month after someDate
	 */
	public LocalDate getNextMonth(LocalDate someDate) {
		currentCalendarDate =  someDate.plusMonths(1);
		return currentCalendarDate;
	}
	
	/**
	 * Sets selectedCalendarDate
	 * 
	 * @param someDate selectedCalendareDate = someDate
	 */
	public void setSelectedCalendarDate(LocalDate someDate) {
		selectedCalendarDate=someDate;
		System.out.println("SelectedDate: "+ selectedCalendarDate);
	}
	
	public void setSelectedCalendarWeek(LocalDate someDate) {
		selectedCalendarDate = someDate.minusDays(someDate.getDayOfWeek().getValue());
		System.out.println("firstDayOfWeek: "+ selectedCalendarDate);
	}
	public void setSelectedCalendarMonth(LocalDate someDate) {
		selectedCalendarDate = someDate.minusDays(someDate.getDayOfMonth()-1);
		System.out.println("SelectedMonth: "+ selectedCalendarDate);
	}
	
	/**
	 * Gets selectedCalendarDate
	 * 
	 * @return LocalDate selectedCalendarDate
	 */
	public LocalDate getSelectedCalendarDate() {
		return selectedCalendarDate;
	}
	
	/**
	 * Gets previous day
	 * 
	 * @param someDate previous day from this date
	 * @return LocalDate day before someDate
	 */
	public LocalDate getPrevDay(LocalDate someDate) {
		return someDate.minusDays(1);
	}
	
	/**
	 * Gets next day
	 * 
	 * @param someDate next day from this date
	 * @return LocalDate day after someDate
	 */
	public LocalDate getNextDay(LocalDate someDate) {
		return someDate.plusDays(1);
	}
	
	/**
	 * Gets previous week
	 * 
	 * @param someDate previous week from this date
	 * @return LocalDate one week after someDate
	 */
	public LocalDate getPrevWeek(LocalDate someDate) {
		return someDate.minusDays(7);
	}
	
	/**
	 * Gets next week
	 * 
	 * @param someDate next week from this date
	 * @return LocalDate one week after someDate
	 */
	public LocalDate getNextWeek(LocalDate someDate) {
		return someDate.plusDays(7);
	}
	
	/**
	 * Update changes from Create  
	 */
	public void update(Event e) {
		myEvents.add(e);
		
		listener.stateChanged(new ChangeEvent(this));
	}
	
	/**
	 * Attach a listener to the Model
     * @param listener the listeners
	 */
	public void attach(ChangeListener listener) {
		this.listener = listener;
	}
	
	// Angela's original work
	/**
	 * loads the calendar with the events from the 'events.txt' pre-existing file
	 */
	public void loadCalendar()
	{
		events = new ArrayList<>();
		recurringEvents = new ArrayList<>();
		String name;
		String days;
		ArrayList<DayOfWeek> daysOfWeek = new ArrayList<>();
		String year;
		String moStart;
		String moEnd;
		String timeS;
		String timeE;
		
		File file = new File("/Users/angelakim/Desktop/eventsDraft.txt");
		FileReader fr;
		try 
		{
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			try
			{
				String line = br.readLine();
				while (line != null)
				{
					events.add(line);
					line = br.readLine();
				}
				
				for (int j = 0; j < events.size(); j ++)
				{
					String[] addEvent = events.get(j).split(";");
		
					name = addEvent[0];
					year = addEvent[1];
					moStart = addEvent[2];
					moEnd = addEvent[3];
					days = addEvent[4];
					timeS = addEvent[5];
					timeS = timeS.concat(":00");
					timeE = addEvent[6];
					timeE = timeE.concat(":00");
					
					for (int i = 0; i < days.length(); i++)
					{
						String day = String.valueOf(days.charAt(i));
						if (day.equals("M"))
						{
							daysOfWeek.add(DayOfWeek.MONDAY);
						}
						else if (day.equals("T"))
						{
							daysOfWeek.add(DayOfWeek.TUESDAY);
						}
						else if (day.equals("W"))
						{
							daysOfWeek.add(DayOfWeek.WEDNESDAY);
						}
						else if (day.equals("R"))
						{
							daysOfWeek.add(DayOfWeek.THURSDAY);
						}
						else if (day.equals("F"))
						{
							daysOfWeek.add(DayOfWeek.FRIDAY);
						}
						else if (day.equals("A"))
						{
							daysOfWeek.add(DayOfWeek.SATURDAY);
						}
						else if (day.equals("S"))
						{
							daysOfWeek.add(DayOfWeek.SUNDAY);
						}
					}
					
					LocalTime st = LocalTime.parse(timeS, DateTimeFormatter.ofPattern("HH:mm"));
					LocalTime et = LocalTime.parse(timeE, DateTimeFormatter.ofPattern("HH:mm"));
					TimeInterval ti = new TimeInterval(st, et);
					
					LocalDate firstDayOfEvent = LocalDate.of(Integer.parseInt(year), Integer.parseInt(moStart), 30);
					for (DayOfWeek d : daysOfWeek)
					{
						LocalDate temp = firstDayOfEvent.with(TemporalAdjusters.firstInMonth(d));
						if (temp.isBefore(firstDayOfEvent))
						{
							firstDayOfEvent = temp;
						}
					}
					
					LocalDate lastDayOfEvent = LocalDate.of(Integer.parseInt(year), Integer.parseInt(moEnd), 1);
					for (DayOfWeek d : daysOfWeek)
					{
						LocalDate temp = lastDayOfEvent.with(TemporalAdjusters.lastInMonth(d));
						if (temp.isAfter(lastDayOfEvent))
						{
							lastDayOfEvent = temp;
						}
					}
					
					Event e = new Event(name, ti, days);
					e.startDay = firstDayOfEvent.format(DateTimeFormatter.ofPattern("MM/dd/yy"));
					e.endDay = lastDayOfEvent.format(DateTimeFormatter.ofPattern("MM/dd/yy"));
					String nameAndDays = days + " " + timeS + " " + timeE + " " + e.startDay + " " + e.endDay + " " + name; 
					recurringEvents.add(nameAndDays);
					
					TreeSet<Event> e2 = e.recurring();
					for (Event ev : e2)
					{
						ev.startDay = e.startDay;
						ev.endDay = e.endDay;
						ev.setType("r");
						myEvents.add(ev);
					}
				}
				
				br.close();
				fr.close();
			}
		catch (IOException e)
		{
				e.getMessage();
		}
		}
		catch (FileNotFoundException e1) {
				e1.getMessage();
		} 
	}
	/**
	 * add an event to the calendar
	 * 
	 * @param e is the event to be added to the calendar
	 */
	public void add (Event e)
	{
		boolean exists = false;
		myEvents.add(e);
		for (int i = 0; i < events.size(); i += 2)
		{
			String ev = events.get(i);
			if (ev.equals(e.getName()))
			{
				exists = true;
			}
		}
		if (!exists)
		{
			String name = e.getName();
			String[] time = e.getTI().TI().split(" ");
			String dAndT = e.getDay() + " " + time[0] + " " + time[2];
			events.add(name);
			events.add(dAndT);
			e.setType("o");
		}
		
	}
	/**
	 * removes an event from the calendar
	 * 
	 * @param name is the String reference of the event name to be removed
	 */
	public void remove (String name)
	{
		TreeSet<Event> remove = new TreeSet<>();
		for (Event event : myEvents)
		{
			if (event.getName().equals(name))
			{
				remove.add(event);
			}
		}
		myEvents.removeAll(remove);
		ArrayList<String> re = new ArrayList<>();
		for (Event e : remove)
		{
			for (String s : events)
			{
				if (s.contains(e.getName()))
				{
					re.add(s);
					re.add(events.get(events.indexOf(s) + 1));
				}
			}
		}
		events.removeAll(re);
	}
	
	/**
	 * retrieves events scheduled for certain dates
	 * 
	 * @param l date of reference to retrieve events scheduled on the date
	 * @return String of events on specified date
	 */
	public String get (LocalDate l)
	{
		System.out.println("LocalDate inside String get:" + l);
		DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yy");
		String d = f.format(l);
		TreeSet<String> event = new TreeSet<>();
		String list = "";
		for (Event e : myEvents)
		{
			if (e.getDay().equals(d))
			{
				event.add(e.getName() + " : " + e.getTI().TI());
			}
		}
		for (String s : event)
		{
			list += s + "\n\n";
		}
		return list;
	}
	
	/**
	 * returns all events in the calendar
	 * 
	 * @return TreeSet of all events
	 */
	public TreeSet<Event> getAll()
	{
		return myEvents;
	}
	
}