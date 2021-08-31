package calendar;

import java.time.LocalDate;

import javax.swing.JPanel;
/**
 * Concrete strategy for the interface View
 * version 1.0
 * @author Rajan Patel
 */
public class ConcreteView implements View {

	protected JPanel panel;
	
	/**
	 * Returns the JPanel of the selected view
	 * 
	 * @return JPanel panel to add to main frame
	 */
	@Override
	public JPanel getView() {
		return panel;
	}

	/**
	 * Loads the current view with MyCalendarModel events
	 * 
	 * @param c date that is referenced for calendar view
	 * @param myCal model object to be passed to get events
	 */
	@Override
	public void loadView(LocalDate c, MyCalendarModel myCal) { }

}
