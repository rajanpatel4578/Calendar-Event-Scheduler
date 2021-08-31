package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Agenda View for calendar
 * 
 * @author Angela Kim
 */
public class AgendaView extends ConcreteView {

	private JPanel panel;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel label;
	private TreeSet<Event> myEvents;
	private ArrayList<String> scheduled;
	private String input = "";
	
	/**
	 * Constructs AgendaView object
	 */
	public AgendaView ()
	{	
		myEvents = new TreeSet<>();
		scheduled = new ArrayList<>();
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		label = new JLabel("Enter dates (MM/dd/yy : MM/dd/yy):");
		textField = new JTextField(20);
		textArea = new JTextArea(20, 30);
		textArea.setEditable(false);
		
		panel.add(label, BorderLayout.NORTH);
		panel.add(textField, BorderLayout.CENTER);
		panel.add(textArea, BorderLayout.SOUTH);
		
		panel.setVisible(true);
	}
	
	/**
	 * Inquiry on MyCalendarModel events to check for events within input time frame
	 * 
	 */
	public void inquire()
	{
		textField.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				String agenda = "";
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					input = textField.getText();
					textField.setText("");
					String[] dates = input.split(":");
					dates[0] = dates[0].replace(" ", "");
					dates[1] = dates[1].replace(" ", "");
					
					LocalDate start = LocalDate.parse(dates[0], DateTimeFormatter.ofPattern("MM/dd/yy"));
					LocalDate end = LocalDate.parse(dates[1], DateTimeFormatter.ofPattern("MM/dd/yy"));
					
					for (Event ev : myEvents)
					{
						String addThis;
						if (ev.getDate().isEqual(start)|| (ev.getDate().isAfter(start) && ev.getDate().isBefore(end)) || ev.getDate().isEqual(end))
						{
							addThis = ev.getName() + " " + ev.getDay() + " " + ev.getTI().TI();
							scheduled.add(addThis);
						}
					}
					
					for (String s : scheduled)
					{
						agenda += s + "\n";
					}
					textArea.setText(agenda);
					agenda = "";
					scheduled.removeAll(scheduled);
				}
			}
		});
	}

	/**
	 * Gets AgendaView panel to be added to CurrentCalendarView
	 * 
	 * @return JPanel agenda view
	 */
	@Override
	public JPanel getView() {
		return panel;
	}

	/**
	 * Load agenda with MyCalendarModel events
	 * 
	 * @param c date that is referenced for calendar
	 * @param myCal calendar object to be passed to get events
	 */
	@Override
	public void loadView(LocalDate c, MyCalendarModel myCal) {
		myEvents = myCal.getAll();
		inquire();
	}
	
}
