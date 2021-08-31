package calendar;


import java.awt.*;

import java.time.LocalDate;
import java.util.*;
import javax.swing.*;

/**
 * Week view for calendar
 * 
 * @author Angela Kim
 */

public class WeekView extends ConcreteView {
	
	private static Font f = new Font("Product Sans", Font.PLAIN, 10);
	private static Color todayColor = new Color(110, 192, 250);
	
//	private JPanel panel;
	private ArrayList<JPanel> weekdays;
	private ArrayList<JPanel> days;
	private ArrayList<LocalDate> thisWeek; 
	private ArrayList<String> scheduled;
	private TreeSet<Event> myEvents;
	private LocalDate date;
	
	/**
	 * Constructs WeekView object
	 */
	public WeekView()
	{
		thisWeek = new ArrayList<>();
		scheduled = new ArrayList<>();
		myEvents = new TreeSet<>();
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.setVisible(true);
		panel.setBackground(Color.white);
	}
	
	/**
	 * Creates 7 JPanels for each day in week view
	 * 
	 * @return ArrayList<JPanel> day panels
	 */
	public ArrayList<JPanel> createWeek()
	{
		String[] weekdays = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
		
		for (int i = 0; i < 7; i++)
		{
			JPanel day  = new JPanel();
			day.setLayout(new BoxLayout(day, BoxLayout.Y_AXIS));
			day.setVisible(true);
			day.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
			day.setBackground(Color.white);
			
			JLabel dayLabel = new JLabel(weekdays[i]);
			dayLabel.setFont(f);
			dayLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			day.add(dayLabel);
			days.add(day);
		}
		
		return days;
	}

	/**
	 * Gets WeekView panel to be added to CurrentCalendarView
	 * 
	 * @return JPanel week view
	 */
	@Override
	public JPanel getView() {
		return panel;
	}

	/**
	 * Load week view with MyCalendarModel events
	 * 
	 * @param c date that is referenced for calendar
	 * @param myCal calendar object to be passed to get events
	 */
	@Override
	public void loadView(LocalDate c, MyCalendarModel myCal) {
		myEvents = myCal.getAll();
		weekdays = new ArrayList<>();
		days = new ArrayList<>();
		weekdays = createWeek();
		Font bigger = new Font("Product Sans", Font.PLAIN, 15);
		
		date = c;
		int theDay = date.getDayOfWeek().getValue();
		int dayOfWeek = 0;
		LocalDate d = date;
		
		for (JPanel j : weekdays)
		{
			LocalDate dPlusMinus = LocalDate.now();
			JButton button = new JButton();
			button.setBorderPainted(false);
			button.setBackground(Color.white);
			button.setFont(bigger);
			button.setAlignmentX(Component.CENTER_ALIGNMENT);
			if (theDay == 7)
			{
				theDay = 0;
			}
			
			if (theDay > dayOfWeek)
			{
				int i = theDay - dayOfWeek;
				dPlusMinus = d.minusDays(i);
				button.setText(Integer.toString(dPlusMinus.getDayOfMonth()));
				thisWeek.add(d.minusDays(i));
			}
			else if (theDay == dayOfWeek)
			{
				dPlusMinus = d;
				button.setText(Integer.toString(dPlusMinus.getDayOfMonth()));
				thisWeek.add(d);
			}
			else if (theDay < dayOfWeek)
			{
				int i = dayOfWeek - theDay;
				dPlusMinus = d.plusDays(i);
				button.setText(Integer.toString(dPlusMinus.getDayOfMonth()));
				thisWeek.add(d.plusDays(i));
			}
			
			if (theDay == dayOfWeek && c.isEqual(LocalDate.now()))
			{
				button.setBackground(todayColor);
				button.setOpaque(true);
			}
			
			j.add(button);
			
			JTextArea events = new JTextArea(20, 12);
			events.setPreferredSize(events.getSize());
			events.setEditable(false);
			events.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
			
			String agenda = "";
			for (Event e : myEvents)
			{
				String addThis;
				if (e.getDate().isEqual(dPlusMinus))
				{
					addThis = e.getName() + "\n" + e.getTI().TI();
					scheduled.add(addThis);
				}
			}
			for (String s : scheduled)
			{
				agenda += s + "\n \n";
			}
			events.setText(agenda);
			agenda = "";
			scheduled.removeAll(scheduled);
			
			j.add(events);
			
			panel.add(j);
			dayOfWeek++;
			
		}
	}
}
