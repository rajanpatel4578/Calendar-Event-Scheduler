package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.*;
import java.util.*;
import javax.swing.*;
/**
 * Day View for calendar
 * version 1.0
 * @author Rajan Patel
 */
public class DayView extends ConcreteView {
	
//	private JPanel panel;
	private JTextArea dayLabel;
	private JTextArea dayViewArea;
	private LocalDate date;
	
	private static Color todayColor = new Color(110, 192, 250);
	
	/**
	 * Constructs DayView object
	 */
	public DayView()
	{	
		panel = new JPanel();
		panel.setVisible(true);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		dayLabel = new JTextArea(1, 35);
		dayViewArea = new JTextArea(20, 35);
	}

	/**
	 * Gets DayView panel to be added to CurrentCalendarView
	 * 
	 * @return JPanel day view
	 */
	@Override
	public JPanel getView() 
	{
		return panel;
	}

	/**
	 * Loads day view with MyCalendarModel events
	 * 
	 * @param c date that is referenced for calendar view
	 * @param myCal model object to be passed to get events
	 */
	@Override
	public void loadView(LocalDate c, MyCalendarModel myCal) 
	{
		date = c;
		dayLabel.setText(String.valueOf(date.getDayOfWeek()) + "\n" + String.valueOf(date.getDayOfMonth()));
		dayViewArea.setText("\n \n" + myCal.get(date));
		String[] check = dayLabel.getText().split("\n");
		
		if (date.isEqual(LocalDate.now()) && Integer.parseInt(check[1]) == LocalDate.now().getDayOfMonth())
		{
			dayLabel.setBackground(todayColor);
		}
		else 
		{
			dayLabel.setBackground(Color.white);
		}
		
		panel.add(dayLabel, BorderLayout.NORTH);
		panel.add(dayViewArea, BorderLayout.PAGE_END);
	}
}
