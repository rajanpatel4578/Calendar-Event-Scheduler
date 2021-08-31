package calendar;

import javax.swing.JFrame;

/**
 * MyCalenderTester has main() method to execute program and deliver functions of program
 * such as viewing calendar events, creating events, removing events, etc.
 * 
 * @author Angela Kim, Rajan Patel, Karan Gandhi
 * @version 1.0
 * @date 8/4/21
 */

public class MyCalendarTester {
	static JFrame frame;
	public static void main(String[] args)
	{
		
		MyCalendarModel calendar = new MyCalendarModel();
		Create c = new Create(calendar);
		
		calendar.attach(c);
		
	}
	
}