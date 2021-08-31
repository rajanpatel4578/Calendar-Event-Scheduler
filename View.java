package calendar;

import java.time.LocalDate;
import java.util.*;
import javax.swing.JPanel;
/**
 * Loads and gets views; implementable on all right-hand side views
 * 
 * @author Angela Kim
 */
public interface View {

	/**
	 * Returns the JPanel of the selected view
	 * 
	 * @return JPanel panel to add to main frame
	 */
	JPanel getView();
	
	/**
	 * Loads the current view with MyCalendarModel events
	 */
	void loadView(LocalDate c, MyCalendarModel myCal);
}
