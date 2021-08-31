package calendar;

import java.time.*;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * Month view for calendar(right month view)
 * 
 * @author Karan Gandhi, Angela Kim
 */
public class MonthView extends ConcreteView {

	private MyCalendarModel calendar;
	private TreeSet<Event> myEvents;
	private JPanel gridview;
//	private JPanel panel;
	private ArrayList<JPanel> days;
	private LocalDate date;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
	private static Font f = new Font("Product Sans", Font.PLAIN, 15);
	private static Color todayColor = new Color(110, 192, 250);
	
	/**
	 * Constructs MonthView object
	 */
	public MonthView() 
	{	
		myEvents = new TreeSet<>();
		
		panel = new JPanel();
	}
	
	/**
	 * Gets the days in the month as panels to be added to the month view
	 * 
	 * @param numWeeks number of weeks to be created 
	 * @return ArrayList<JPanel> panels for each day of the month to be displayed
	 */
	public ArrayList<JPanel> getDays(int numWeeks) 
	{	
		days = new ArrayList<JPanel>();
		
		LocalDate firstDayMonth = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
		int numDays = date.lengthOfMonth();
		int startValue = firstDayMonth.getDayOfWeek().getValue();
		if (startValue == 7) { startValue = 0; }
		
		//previous month days
		int previousDays = startValue;
		LocalDate prevMonthDate = firstDayMonth.minusDays(previousDays);
		for (int i = previousDays; i >= 1; i--) 
		{	
			JPanel prevPanel = new JPanel();
			prevPanel.setLayout(new BorderLayout());
			
			JButton prevDays = new JButton();
			prevDays.setText(""+prevMonthDate.getDayOfMonth());
			prevDays.addActionListener(new MonthActionListener(prevMonthDate));
			
			JTextArea prevTextArea = new JTextArea(5,10);
			prevTextArea.setForeground(Color.gray);
			
			String prevStr = " ";
			
			for (Event e : myEvents) 
			{
				if (e.getDate().isEqual(prevMonthDate)) 
				{
					prevStr += e.getName();
					prevStr += "\n\n ";
				}
			}
			prevTextArea.setText(prevStr);
			
			prevDays.setBorderPainted(false);
			prevDays.setForeground(Color.gray);
			prevDays.setFont(f);
			
			prevPanel.setBackground(Color.white);
			prevPanel.add(prevDays, BorderLayout.NORTH);
			prevPanel.add(prevTextArea, BorderLayout.SOUTH);
			
			days.add(prevPanel);
			
			prevMonthDate = prevMonthDate.plusDays(1);
		}
		
		//current month days
		LocalDate currentDate = firstDayMonth;
		for (int i=1; i <= numDays; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBackground(Color.white);
			
			JButton thisDays = new JButton();
			thisDays.setText("" + i);
			thisDays.addActionListener(new MonthActionListener(currentDate));
			thisDays.setBorderPainted(false);
			
			if (currentDate.isEqual(LocalDate.now()))
			{
				thisDays.setBackground(todayColor);
				thisDays.setOpaque(true);
			}
			
			JTextArea textArea = new JTextArea(5, 10);
			
			String r = " ";
			
			for (Event e : myEvents) 
			{
				if (e.getDate().isEqual(currentDate)) 
				{
					r += e.getName();
					r += "\n\n ";
				}
			}
			textArea.setText(r);
			
			panel.add(thisDays, BorderLayout.NORTH);
			panel.add(textArea, BorderLayout.SOUTH);
			
			days.add(panel);
			
			currentDate = currentDate.plusDays(1);
		}
		
		//after month days
		int afterMonthDays = (numWeeks * 7) - previousDays - numDays;
		for (int i=1; i <= afterMonthDays; i++) {
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			
			JButton nextDays = new JButton();
			nextDays.setText(""+i);
			nextDays.addActionListener(new MonthActionListener(currentDate));
			
			JTextArea textArea = new JTextArea(5, 10);
			textArea.setForeground(Color.gray);
			
			String r = " ";
			
			for (Event e : myEvents) 
			{
				if (e.getDate().isEqual(currentDate)) 
				{
					r += e.getName();
					r += "\n\n ";
				}
			}
			textArea.setText(r);
			
			panel.setBackground(Color.white);
			
			nextDays.setBorderPainted(false);
			nextDays.setForeground(Color.gray);
			nextDays.setFont(f);
			
			panel.add(nextDays, BorderLayout.NORTH);
			panel.add(textArea, BorderLayout.SOUTH);
			
			days.add(panel);
			
			currentDate = currentDate.plusDays(1);
		}
		return days;
	}
	
	/**
	 * Action Listeners for the date buttons in month view
	 * 
	 * @author angelakim
	 */
	public class MonthActionListener implements ActionListener {

		private LocalDate buttonDate;
		
		public MonthActionListener(LocalDate buttonDate) {
			this.buttonDate = buttonDate;
		}
		
		public void actionPerformed(ActionEvent event) {
			
			calendar.setSelectedCalendarDate(buttonDate);
			calendar.activateDateView();
		}
		
	}

	/**
	 * Gets MonthView panel to be added to CurrentCalendarView
	 * 
	 * @return JPanel month view
	 */
	@Override
	public JPanel getView() 
	{
		return panel;
	}

	/**
	 * Load month view with MyCalendarModel events
	 * 
	 * @param c date that is referenced for calendar
	 * @param myCal calendar object to be passed to get events
	 */
	@Override
	public void loadView(LocalDate c, MyCalendarModel myCal) 
	{
		date = c;
		calendar = myCal;
		myEvents = calendar.getAll();
		gridview = new JPanel();
		
		panel.setLayout(new BorderLayout());
		JPanel topPanel = new JPanel();
		
		Font f = new Font("Product Sans", Font.PLAIN, 12);
		
		//days of the week
		JPanel daysOfWeekPanel = new JPanel();
		
		daysOfWeekPanel.setLayout(new GridLayout(1, 7));
		
		ArrayList<JLabel> daysOfWeek = new ArrayList<JLabel>();
		
		String str = "SUNMONTUEWEDTHUFRISAT";
		
		for (int i = 0; i < 21; i+=3) {
			JTextArea textArea = new JTextArea(2,5);
			String dayWeek = str.substring(i, i+3);
			JLabel label = new JLabel(dayWeek, SwingConstants.CENTER);
			label.setFont(f);
			daysOfWeek.add(label);
		}
		for (JLabel l : daysOfWeek) {
			daysOfWeekPanel.add(l);
		}
		
		f = new Font("Product Sans", Font.PLAIN, 15);
		
		JLabel monthYearLabel = new JLabel(formatter.format(date), SwingConstants.CENTER);
		monthYearLabel.setFont(f);
		monthYearLabel.setBackground(Color.white);
		
		daysOfWeekPanel.setBackground(Color.white);
		
		JPanel northBorderPanel = new JPanel();
		northBorderPanel.setBackground(Color.white);
		northBorderPanel.setLayout(new BorderLayout());
		northBorderPanel.add(monthYearLabel, BorderLayout.NORTH);
		northBorderPanel.add(daysOfWeekPanel, BorderLayout.SOUTH);
		
		int numDays = date.lengthOfMonth();
		
		LocalDate firstGC = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
		numDays = firstGC.lengthOfMonth();
		LocalDate lastGC = LocalDate.of(date.getYear(), date.getMonthValue(), numDays);
		
		int firstWeekofMonth = firstGC.get(WeekFields.of(Locale.US).weekOfYear());
		int lastWeekofMonth = lastGC.get(WeekFields.of(Locale.US).weekOfYear());
		int numWeeks = lastWeekofMonth - firstWeekofMonth + 1;
		
		gridview.setLayout(new GridLayout(numWeeks,7));
		
		days = getDays(numWeeks);
		for (JPanel d : days) {
			d.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 1, Color.gray));
			gridview.add(d);
		}
		
		panel.setBackground(Color.white);
		panel.add(northBorderPanel, BorderLayout.NORTH);
		panel.add(gridview, BorderLayout.SOUTH);
		
		days.removeAll(days);
		
	}
	
	
}
