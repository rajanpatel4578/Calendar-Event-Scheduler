package calendar;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Current calendar view; includes both left and right side of screen with buttons and panels
 * version 1.2
 * @author Rajan Patel, Angela Kim
 */
public class CurrentCalendarView extends JFrame {  

	private static final int MONTH_ROWS = 5;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");	//format
	private static Color todayColor = new Color(110, 192, 250);
	private static Color selected = new Color(204, 234, 255);
	private static Font f = new Font("Product Sans", Font.PLAIN, 10);
	
	private static JFrame myFrame = new JFrame();
	private JPanel myPanel = new JPanel();					//main panel
	private JPanel dayView = new JPanel();					
	private JPanel weekView = new JPanel();
	private JPanel monthView = new JPanel();
	private JPanel agendaView = new JPanel();
	private JPanel leftPanel = new JPanel();				//left side of screen
	private JPanel rightPanel = new JPanel();				//right side of screen
	private JPanel rightViewPanel = new JPanel();			//right calendar view of screen
	private JPanel panel1 = new JPanel();					//Holds today, previous, next buttons
	private JPanel panel2 = new JPanel();					//Holds create button
	private JPanel panel3 = new JPanel();					//Holds Month Field, Prev & Next buttons
	private JPanel panel4 = new JPanel();					//Holds SMTWTFS Labels
	private JPanel panel5 = new JPanel();					//Holds Month Calendar dates
	
	private ConcreteView defaultView = new DayView();
	private ConcreteView dv = new DayView();
	private ConcreteView wv = new WeekView();
	private ConcreteView mv = new MonthView();
	private ConcreteView av = new AgendaView();
	
	private MyCalendarModel calModel;
	
	private Create c;
	
	private LocalDate currentCalendarDates, currentViewDate;
	private LocalDate selectedDate = null;
	
	private int x=0, y=0;
	private JTextField monthField = new JTextField();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	/**
	 * Constructs CurrentCalendarView object
	 * 
	 * @param someCal MyCalendarModel object that gets passed
	 */
	public CurrentCalendarView(MyCalendarModel someCal)
	{
		calModel = someCal;
		currentCalendarDates = someCal.getCurrentCalendarDate();
		currentViewDate = someCal.getSelectedCalendarDate();
		this.setCurrentCalendarView(currentCalendarDates);
		monthField.setEditable(false);

	}
	
	/**
	 * Sets the view of this calendar app
	 * 
	 * @param someDate reference date for calendar view
	 */
	public void setCurrentCalendarView(LocalDate someDate) 
	{	
		myFrame.setSize(300, 300);
		myFrame.setLayout(new BorderLayout());
		myFrame.setBackground(Color.white);
		
		/* Left side panel */
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		leftPanel.setBackground(Color.white);
		leftPanel.setOpaque(true);
		
		/* Top Panel of left side PANEL */
		Font f = new Font("Product Sans", Font.PLAIN, 20);
		panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		// Today Button
		JButton todayButton = new JButton("Today");
		todayButton.setBackground(Color.gray);
		todayButton.setFont(f);
		panel1.add(todayButton);
		todayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				if(calModel.isDateViewActive()) 
				{
					currentViewDate = LocalDate.now();
					setDayView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isWeekViewActive())
				{
					currentViewDate= LocalDate.now();
					setWeekView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isMonthViewActive())
				{
					currentViewDate = LocalDate.now();
					System.out.println(currentViewDate.toString());
					setMonthView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
			}
		});
		
		//Previous Button
		JButton previousMonthButton = new JButton("<");
		previousMonthButton.setBackground(Color.gray);
		previousMonthButton.setFont(f);
		previousMonthButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				int count = 0;
				if(calModel.isDateViewActive()) 
				{
					currentViewDate = calModel.getPrevDay(currentViewDate);
					setDayView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isWeekViewActive())
				{
					currentViewDate=calModel.getPrevWeek(currentViewDate);
					setWeekView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isMonthViewActive())
				{
					currentViewDate = calModel.getPrevMonth(currentViewDate);
					setMonthView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				
			}
		});
		panel1.add(previousMonthButton);
				
		//Next Button
		JButton nextMonthButton = new JButton(">");
		nextMonthButton.setBackground(Color.gray);
		nextMonthButton.setFont(f);
		nextMonthButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				if(calModel.isDateViewActive()) 
				{
					currentViewDate = calModel.getNextDay(currentViewDate);
					setDayView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isWeekViewActive())
				{
					currentViewDate=calModel.getNextWeek(currentViewDate);
					setWeekView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
				else if(calModel.isMonthViewActive())
				{
					currentViewDate=calModel.getNextMonth(currentViewDate);
					setMonthView();
					currentCalendarDates = currentViewDate;
					selectedDate = currentCalendarDates;
					monthField.setText(formatter.format(currentCalendarDates));
					createDayButton();
				}
			}
		});
		panel1.add(nextMonthButton);
		
		leftPanel.add(panel1);
		
		/* CENTER Panel of left side PANEL  */
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton createButton = new JButton("CREATE");
		createButton.setFont(f);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				c.getCreateFrame();
			}
		});
		
		panel2.add(createButton);
	      
	    leftPanel.add(panel2,BorderLayout.CENTER);
		
		/**SOUTH Panel of left side PANEL
		 * 
		 */
		
	    f = new Font("Product Sans", Font.PLAIN, 17);
	    
	    this.currentCalendarDates = someDate;		
		panel3.setBackground(Color.white);
		panel3.setOpaque(true);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		panel3.setPreferredSize(new Dimension(380, 30));

		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=5;
		
		//Current Month text field
		monthField.setText(formatter.format(currentCalendarDates));
		monthField.setFont(f);
		monthField.setBackground(Color.white);
		monthField.setOpaque(true);
		monthField.setHorizontalAlignment(JTextField.CENTER);
		monthField.setPreferredSize(new Dimension(130, 30));
		panel3.add(monthField);
		
		f = new Font("Product Sans", Font.PLAIN, 10);
		gbc.gridwidth=1;
		gbc.gridx=6;
		
		//Previous Button
		JButton previousButton = new JButton("<");
		previousButton.setBackground(Color.white);
		previousButton.setBorder(BorderFactory.createEmptyBorder( 3, 25, 3, 25));
		previousButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event1)
			{
				currentCalendarDates = currentCalendarDates.minusMonths(1);
				monthField.setText(formatter.format(currentCalendarDates));
				createDayButton();
				myFrame.repaint();
			}
		});
		previousButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel3.add(previousButton);
		
		//Next Button
		JButton nextButton = new JButton(">");
		nextButton.setBackground(Color.white);
		nextButton.setBorder(BorderFactory.createEmptyBorder(3, 25, 3, 25));
		gbc.gridx=7;
		nextButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event1)
			{
				currentCalendarDates = currentCalendarDates.plusMonths(1);
				monthField.setText(formatter.format(currentCalendarDates));
				createDayButton();
				myFrame.repaint();
			}
		});
		nextButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel3.add(nextButton);
		leftPanel.add(panel3);
		
		//Weekday Labels
		panel4.setLayout(new GridLayout(0,7));
		panel4.setPreferredSize(new Dimension(380, 20));
		
		String weekday = "SMTWTFS";
		gbc.gridx=x=0;
		gbc.gridy=1;
		ArrayList<JLabel> weekdayLabels = new ArrayList<>();
		
		for (int i = 0; i < weekday.length(); i++)
        {
			JLabel labels = new JLabel(String.valueOf(weekday.charAt(i)));
			labels.setHorizontalAlignment(JLabel.CENTER);
			weekdayLabels.add(labels);
			weekdayLabels.get(i).setBackground(Color.white);
			weekdayLabels.get(i).setOpaque(true);
			panel4.add(weekdayLabels.get(i));
			gbc.gridx=++x;
        }
		
		leftPanel.add(panel4);
		
		// Insert date buttons	
		panel5.setLayout(new GridLayout(0,7));
		panel5.setBackground(Color.white);
		panel5.setOpaque(true);
		panel5.setPreferredSize(new Dimension(380, 180));
		createDayButton();
		
		leftPanel.add(panel5);
		myPanel.add(leftPanel);
		myFrame.add(myPanel,BorderLayout.WEST);
		
		/* Right panel */
		rightPanel.setLayout(new BorderLayout());
		
		/* Top Panel of Right Panel */
		JPanel rightTopPanel = new JPanel();
		f = new Font("Product Sans", Font.PLAIN, 20);
		
		// Day Button
		JButton dayButton = new JButton("Day");
		dayButton.setBackground(Color.gray);
		dayButton.setFont(f);
		rightTopPanel.add(dayButton);
		dayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent E)
			{
				calModel.activateDateView();
				setDayView();
			}
		});
		
		//Week Button
		JButton weekButton = new JButton("Week");
		weekButton.setBackground(Color.gray);
		weekButton.setFont(f);
		rightTopPanel.add(weekButton);
		weekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				calModel.activateWeekView();
				setWeekView();
			}
			
		});
				
		//Month Button
		JButton monthButton = new JButton("Month");
		monthButton.setBackground(Color.gray);
		monthButton.setFont(f);
		rightTopPanel.add(monthButton);
		monthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				calModel.activateMonthView();
				setMonthView();
			}
		});
		
		//Agenda Button
		JButton agendaButton = new JButton("Agenda");
		agendaButton.setBackground(Color.gray);
		agendaButton.setFont(f);
		rightTopPanel.add(agendaButton);
		agendaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				av.loadView(currentViewDate, calModel);
				agendaView= av.getView();
				rightViewPanel.removeAll();
				rightViewPanel.add(agendaView);
				rightViewPanel.revalidate();
				rightViewPanel.repaint();
				myFrame.pack();
			}
		});
		rightPanel.add(rightTopPanel, BorderLayout.WEST);
		
		defaultView.loadView(currentViewDate, calModel);
		dayView = defaultView.getView();
		rightViewPanel.add(dayView);
		
		rightPanel.add(rightViewPanel, BorderLayout.SOUTH);
		
		myFrame.add(rightPanel,BorderLayout.CENTER);
		
		//From File Button
		JPanel filePanel = new JPanel();
		filePanel.setVisible(true);
		
		JButton fileButton = new JButton("From File");
		fileButton.setBackground(Color.gray);
		fileButton.setFont(f);
		filePanel.add(fileButton);
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				calModel.loadCalendar();
				if (calModel.isDateViewActive())
				{
					setDayView();
				}
				else if (calModel.isWeekViewActive())
				{
					setWeekView();
				}
				else if (calModel.isMonthViewActive())
				{
					setMonthView();
				}
				
			}
		});
		
		myFrame.add(filePanel, BorderLayout.EAST);
		
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
		myFrame.pack();
	}
	
	/**
	 * Creates calendar on left-hand side of application; dates are clickable
	 */
	private void createDayButton() 
	{	
		panel5.removeAll();
		ArrayList<JButton> monthDateButtons = new ArrayList<>();
		
		// Insert date buttons		
		LocalDate currentMonthStartDate = LocalDate.of(currentCalendarDates.getYear(), currentCalendarDates.getMonth(), 1);
		int monthDays = currentMonthStartDate.lengthOfMonth();
		int prevSpace = currentMonthStartDate.getDayOfWeek().getValue();
		LocalDate viewStartDate = currentMonthStartDate.minusDays(prevSpace);
		LocalDate today = LocalDate.now();

		f = new Font("Arial", Font.PLAIN, 14);
		
		for (int i = 0; i < 42; i++)
		{	
		    LocalDate tempDate = viewStartDate;
		    String label = String.valueOf(tempDate.getDayOfMonth());
		    JButton days = new JButton();
		    days.setPreferredSize(new Dimension(80, 28));
		    days.setFont(f);
		    days.setMargin(new Insets(1, 1, 1, 1));
		    days.setBorder(BorderFactory.createEmptyBorder( 3, 15, 3, 15));
		    monthDateButtons.add(days);
			monthDateButtons.get(i).setLabel(label);
			
			if(viewStartDate.compareTo(today)==0)
			{
				monthDateButtons.get(i).setBackground(todayColor);
				monthDateButtons.get(i).setOpaque(true);
				monthDateButtons.get(i).setForeground(Color.white);
			}
			else if (selectedDate != null && viewStartDate.compareTo(selectedDate) == 0)
			{
				monthDateButtons.get(i).setBackground(selected);
				monthDateButtons.get(i).setOpaque(true);
			}
			else
			{
				monthDateButtons.get(i).setBackground(Color.WHITE);
				monthDateButtons.get(i).setForeground(Color.gray);
			}
			if(String.valueOf(viewStartDate.getMonth())==String.valueOf(currentCalendarDates.getMonth()) 
					&& !viewStartDate.isEqual(today)) 
			{
				monthDateButtons.get(i).setForeground(Color.black);
				monthDateButtons.get(i).addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						selectedDate = tempDate;
						calModel.setSelectedCalendarDate(selectedDate);
						currentCalendarDates = calModel.getSelectedCalendarDate();
						createDayButton();
						
						if(calModel.isDateViewActive()) 
						{
							currentViewDate = calModel.getSelectedCalendarDate();
							setDayView();
							
						}
						else if(calModel.isWeekViewActive())
						{
							currentViewDate = calModel.getSelectedCalendarDate();
							setWeekView();
						}
						else if(calModel.isMonthViewActive())
						{
							currentViewDate = calModel.getSelectedCalendarDate();
							setMonthView();
						}
						
						myFrame.pack();
					}
				});
			}
			
			panel5.add(monthDateButtons.get(i));
			viewStartDate=viewStartDate.plusDays(1);
		}
		panel5.revalidate();
	}
	
	/**
	 * Sets the view on the right-hand side to DayView
	 */
	public void setDayView()
	{
		dv.loadView(currentViewDate, calModel);
		dayView = dv.getView();
		rightViewPanel.removeAll();
		rightViewPanel.add(dayView);
		rightViewPanel.revalidate();
		rightViewPanel.repaint();
		myFrame.pack();
	}
	
	/**
	 * Sets the view on the right-hand side to WeekView
	 */
	public void setWeekView()
	{
		weekView.removeAll();
		wv.loadView(currentViewDate, calModel);
		weekView = wv.getView();
		weekView.revalidate();
		weekView.repaint();
		rightViewPanel.removeAll();
		rightViewPanel.add(weekView);
		rightViewPanel.revalidate();
		rightViewPanel.repaint();
		myFrame.pack();
	}

	/**
	 * Sets the view on the right-hand side to MonthView
	 */
	public void setMonthView()
	{
		monthView.removeAll();
		mv.loadView(currentViewDate, calModel);
		monthView = mv.getView();
		monthView.revalidate();
		monthView.repaint();
		rightViewPanel.removeAll();
		rightViewPanel.add(monthView);
		rightViewPanel.revalidate();
		rightViewPanel.repaint();
		myFrame.pack();
	}
	
	/**
	 * Repaints the right-hand side of the app
	 */
	public void repaint()
	{
		if (calModel.isDateViewActive())
		{
			setDayView();
		}
		if (calModel.isWeekViewActive())
		{
			setWeekView();
		}
		if (calModel.isMonthViewActive())
		{
			setMonthView();
		}
	}
	
	/**
	 * Gets the current view date of the right-hand side calendar
	 * 
	 * @return LocalDate date of reference of right-hand side
	 */
	public LocalDate getCurrentViewDate()
	{
		return currentViewDate;
	}
	
	/**
	 * Sets the Create object for this class
	 * 
	 * @param c create object to add to this class
	 */
	public void setCreate(Create c)
	{
		this.c = c;
	}
}
