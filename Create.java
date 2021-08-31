package calendar;

import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Create frame to add events to MyCalendarModel; extends CurrentCalendarView to put change listeners into effect
 * 
 * @author Angela Kim
 */
public class Create extends CurrentCalendarView implements ChangeListener {

		private static Font f = new Font("Product Sans", Font.PLAIN, 15);
	
		private JFrame frame;
		private JPanel panel;
		private JTextField nameField = new JTextField(10);
		private JTextField dateField = new JTextField(10);
		private JTextField sTimeField = new JTextField(10);
		private JTextField eTimeField = new JTextField(10);
		
		private TreeSet<Event> myEvents;
		private MyCalendarModel cal;
		
		/**
		 * Constructs Create object
		 * 
		 * @param m MyCalendarModel to be used for retrieving events
		 */
		public Create(MyCalendarModel m)
		{
			super(m);
			cal = m;
			myEvents = new TreeSet<>();
			super.setCreate(this);
		}
		
		/**
		 * Creates a JPanel to indicate whether event has been successfully added to calendar or not
		 * 
		 * @return JPanel message panel
		 */
		public JPanel createEvent()
		{
			TreeSet<Event> overlaps = new TreeSet<>();
			
			String name = nameField.getText();
			String date = dateField.getText();
			String st = sTimeField.getText();
			String et = eTimeField.getText();
			
			LocalTime newSt = LocalTime.parse(st, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime newEt = LocalTime.parse(et, DateTimeFormatter.ofPattern("HH:mm"));
			TimeInterval ti = new TimeInterval(newSt, newEt);
			Event e = new Event(name, ti, date);
			
			boolean create = true;
			String message = "";
			for (Event ev : myEvents)
			{
				if ((ev.getDay().equals(e.getDay())) && (ev.getTI().overlap(e)))
				{
					create = false;
					overlaps.add(ev);
				}
			}
			if (!create)
			{
				message += "Cannot create event " + name + "; event overlaps with \n";
				for (Event event : overlaps)
				{
					message += event.getName() + " on " + event.getDay() + "\n";
				}
			}
			else
			{
				cal.update(e);
				message += "Event added to calendar successfully!";
			}
			
			JPanel p = new JPanel();
			p.setVisible(true);
			p.setBackground(Color.white);
			p.setOpaque(true);
			
			JTextArea ta = new JTextArea(5, 7);
			ta.setEditable(false);
			ta.setText(message);
			ta.setFont(f);
			
			p.add(ta);
			
			return p;
		}

		/**
		 * Creates frame for user input to add an event
		 */
		public void getCreateFrame()
		{
			myEvents = cal.getAll();
			frame = new JFrame("Create New Event");
			panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.white);
			panel.setOpaque(true);
			panel.setVisible(true);
			panel.setPreferredSize(new Dimension(400, 350));
		
			JPanel p1 = new JPanel();
			p1.setVisible(true);
			p1.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel nameLabel = new JLabel("Name : ");
			nameLabel.setFont(f);
			p1.add(nameLabel);
			p1.add(nameField);
			p1.setAlignmentX(Component.LEFT_ALIGNMENT);
			p1.setBackground(Color.white);
			p1.setOpaque(true);
			p1.setPreferredSize(new Dimension(400, 40));
			
			JPanel p2 = new JPanel();
			p2.setVisible(true);
			p2.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel dateLabel = new JLabel("Date (mm/dd/yy): ");
			dateLabel.setFont(f);
			p2.add(dateLabel);
			p2.add(dateField);
			p2.setAlignmentX(Component.LEFT_ALIGNMENT);
			p2.setBackground(Color.white);
			p2.setOpaque(true);
			p2.setPreferredSize(new Dimension(400, 40));
			
			JPanel p3 = new JPanel();
			p3.setVisible(true);
			p3.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel sTimeLabel = new JLabel("Start Time (00:00 - 24:00): ");
			sTimeLabel.setFont(f);
			p3.add(sTimeLabel);
			p3.add(sTimeField);
			p3.setAlignmentX(Component.LEFT_ALIGNMENT);
			p3.setBackground(Color.white);
			p3.setOpaque(true);
			p3.setPreferredSize(new Dimension(400, 40));
			
			JPanel p4 = new JPanel();
			p4.setVisible(true);
			p4.setLayout(new FlowLayout(FlowLayout.LEFT));
			JLabel eTimeLabel = new JLabel("End Time (00:00 - 24:00): ");
			eTimeLabel.setFont(f);
			p4.add(eTimeLabel);
			p4.add(eTimeField);
			p4.setAlignmentX(Component.LEFT_ALIGNMENT);
			p4.setBackground(Color.white);
			p4.setOpaque(true);
			p4.setPreferredSize(new Dimension(400, 40));
			
			JPanel p5 = new JPanel();
			p5.setVisible(false);
			p5.setAlignmentX(Component.LEFT_ALIGNMENT);
			p5.setBackground(Color.white);
			p5.setOpaque(true);
			p5.setPreferredSize(new Dimension(390, 60));
			
			JButton close = new JButton("Close");
			close.setFont(f);
			close.setAlignmentX(Component.CENTER_ALIGNMENT);
			close.addActionListener(event -> {
				nameField.setText("");
				dateField.setText("");
				sTimeField.setText("");
				eTimeField.setText("");
				frame.dispose();
			});
			
			JPanel p6 = new JPanel();
			p6.setVisible(false);
			p6.setAlignmentX(Component.LEFT_ALIGNMENT);
			p6.setBackground(Color.white);
			p6.setOpaque(true);
			p6.setPreferredSize(new Dimension(400, 40));
			p6.add(close);
			
			JPanel buttons = new JPanel();
			buttons.setLayout(new FlowLayout());
			buttons.setVisible(true);
			buttons.setBackground(Color.white);
			buttons.setOpaque(true);
			buttons.setPreferredSize(new Dimension(400, 20));
			buttons.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			JButton create = new JButton("Create");
			create.setFont(f);
			create.addActionListener(event -> {
				p5.setVisible(true);
				p5.removeAll();
				p5.add(createEvent());
				p5.revalidate();
				p5.repaint();
				p6.setVisible(true);
				
			});
			buttons.add(create);
			
			JButton cancel = new JButton("Cancel");
			cancel.setFont(f);
			cancel.addActionListener(event -> {
				frame.dispose();
			});
			buttons.add(cancel);
			
			panel.add(p1);
			panel.add(p2);
			panel.add(p3);
			panel.add(p4);
			panel.add(buttons);
			panel.add(p5);
			panel.add(p6);
			
			frame.add(panel);
			
			frame.pack();
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		/**
		 * Executes when the state of target listener is changed
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			myEvents = cal.getAll();
			super.repaint();
		}
		
}
