/* 
* Displaying the main calendar framework interface 
*/
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument.Iterator;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;/* 

 */
import java.util.Set;
// MyCalendarWindow class
public class MyCalendarWindow extends JFrame {
	//member variable fields
	private Container c = getContentPane();
	private JPanel p_header, p_current, p_control, p_display, p_footer;
	private JLabel lb_current, lb_time, lb_DayOfWeek, lb_yr, lb_mon, lb_changeTheme, mainImage;
	private JLabel[] display;
	private JComboBox combo_yr, combo_mon, combo_changeTheme;
	private JButton btn_before, btn_current, btn_after, btn_export, btn_reminder, btn_import;
	private JPopupMenu pmenu;
	private JMenuItem menu_add;
    private Border border;
	private Calendar cal;
	private int currentYear, currentMonth, currentDay, maxDayOfMonth, firstDayIndex, currentDayOfWeek, clickedDay, flag;
	private static int widthx = 720, heighty = 600;
	private static int locationx = 80, locationy = 50;
    private Map<String, String> holidays;
    private static Map<String,String> events;
    private Set<String> setEvent;
    private String[] allEvents, clickedSplit;
    private String[] headers = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    private String[] themePaper = {"Flower", "Sea", "Rainbow"};
    private String[] themePaperSource = {"images/flower.jpg", "images/sea.jpg", "images/rainbow.jpg"};
	private String status, messege, check_holiday, check_event;
    private static String motherDate, fatherDate, laborDate, MartinDate, MemorialDate, PresidentDate, ThanksgivingDate, EasterDate;
    private static boolean openReminderWindow, openEventWindow;
    //constructor(instantiating all the demand variable and JFrame components including JLabel, JCombobox, JButton)
	public MyCalendarWindow() {
		super("EE810 JAVA Project: Calendar");
		//reading the event json file from the default address automatically  
		events = new JSON_write_read().readJSONtoEvent("/Users/sumomomubezen/Desktop/666.json");
		openReminderWindow = true;
		openEventWindow = true;
		//instantiating the Calendar object
		cal = Calendar.getInstance();
		//set the first day in a week is Sunday
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		//get the current date
		currentYear = cal.get(Calendar.YEAR);
		currentMonth = cal.get(Calendar.MONTH) + 1;
		currentDay = cal.get(Calendar.DAY_OF_MONTH);
		currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		//define which day in the current week is Sunday, Monday,..., Saturday 
		switch (currentDayOfWeek) {
			case 1:
				status = "Sunday!";
				break;
			case 2:
				status = "Monday!";
				break;
			case 3:
				status = "Tuesday!";
				break;
			case 4:
				status = "Wednesday!";
				break;
			case 5:
				status = "Thursday!";
				break;
			case 6:
				status = "Friday!";
				break;
			case 7:
				status = "Saturday!";
				break;
		}
		
		p_header = new JPanel();
		p_header.setLayout(new BorderLayout());
		//set background transparents as the following components above the container in order to display the background image
		p_header.setOpaque(false);
		
		p_current = new JPanel();
		p_current.setOpaque(false);
		
		p_control = new JPanel();
		p_control.setOpaque(false);
		
	    p_display = new JPanel();
	    p_display.setLayout(new GridLayout(7,7));
	    p_display.setOpaque(false);
	    
	    p_footer = new JPanel();
	    p_footer.setOpaque(false);
	    p_footer.setLayout(new BorderLayout());
	    //set the background image JLabel
	    mainImage = new JLabel(new ImageIcon("images/sea.jpg"));
	    //the first layer is the container, and then add the mainImage above the container, finally add all other components on the mainImage layer
	    c.add(mainImage);
	    mainImage.setLayout(new BorderLayout());
	    mainImage.add(p_header, BorderLayout.NORTH);
	    p_header.add(p_current, BorderLayout.NORTH);
	    p_header.add(p_control, BorderLayout.SOUTH);	    
	    mainImage.add(p_display, BorderLayout.CENTER);
	    mainImage.add(p_footer, BorderLayout.SOUTH);
	    
	    lb_current = new JLabel("Today: ");
	    lb_yr = new JLabel("Year:");
	    combo_yr = new JComboBox();
	    for (int i=1980; i<=2030; i++) {
	    	combo_yr.addItem(new Integer(i));
	    }
	    //set the listener for changing the year
	    combo_yr.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//every time change the year, firstly updating the current year to the selected year by user in the combobox
	    		currentYear = Integer.parseInt(((JComboBox)e.getSource()).getSelectedItem().toString());  		
	    		clear();
	    		init();
	    	}
	    });
	   //set the listener for changing the month
	    lb_mon = new JLabel("Month:");
	    combo_mon = new JComboBox();
	    for (int i=1; i<=12; i++) {
	    	combo_mon.addItem(new Integer(i));
	    }
	    combo_mon.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentMonth = Integer.parseInt(((JComboBox)e.getSource()).getSelectedItem().toString());
	    		clear();
	    		init();
	    	}
	    });
	    //former month function part
	    btn_before = new JButton("<");
	    btn_before.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//1980 is the first year defined in the combobox, the user can not choose the year earlier than this year
	    		if (currentYear == 1980 && currentMonth == 1) {
	    			JOptionPane.showMessageDialog(p_footer, "Exceed calendar extent!");//alert
	    		} else {
		    		if (currentMonth == 1) {//every time the current month is January, the next month will be the December in the former year
		    			currentMonth = 12;
		    			currentYear -= 1;
		    		}
		    		else {
		    			currentMonth -= 1;
		    		}	    		
		    		clear();
		    		init();
	    		}
	    	}
	    });
	    //return to the current date function part
	    btn_current = new JButton("Current");
	    btn_current.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		currentYear = cal.get(Calendar.YEAR);
	    		currentMonth = cal.get(Calendar.MONTH) + 1;
	    		clear();
	    		init();
	    	}
	    });
	    //next month function part
	    btn_after = new JButton(">");
	    btn_after.addActionListener(new ActionListener() {
	    	//the logic idea is as same as the former month function implementation
	    	public void actionPerformed(ActionEvent e) {
	    		if (currentYear == 2030 && currentMonth == 12) {
	    			JOptionPane.showMessageDialog(p_footer, "Exceed calendar extent!");
	    		}
	    		else {
	    			if (currentMonth == 12) {
		    			currentMonth = 1;
		    			currentYear += 1;
		    		}
		    		else {
		    			currentMonth += 1;
		    		}	    		
		    		clear();
		    		init();
	    		}
	    	}
	    });
	    //let the user choose their favorite background image
	    //Sorry, this is the only function we have not achieved, but we will continue doing this in the summer
	    lb_changeTheme = new JLabel("Theme: ");
	    combo_changeTheme = new JComboBox();
	    for (int i=0; i<themePaper.length; i++) {
	    	combo_changeTheme.addItem(themePaper[i]);	    	
	    }
	    combo_changeTheme.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
//	    		mainImage = new JLabel(new ImageIcon(themePaperSource[combo_changeTheme.getSelectedIndex()]));
//	    		mainImage.setIcon(themePaperSource[combo_changeTheme.getSelectedIndex()]);
//	    		c.add(mainImage);
//    		    mainImage.setLayout(new BorderLayout());
//	    		repaint();
	    	}
	    });
	    //users can read the events in the calendar json file
	    btn_import = new JButton("import");
	    btn_import.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {	
	    			    //users can choose their own address to read the specific file
		    			JFileChooser fc = new JFileChooser();
		    			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		    			fc.setDialogType(JFileChooser.FILES_ONLY);
		    			fc.setDialogTitle("Read the file:");
		    			fc.setMultiSelectionEnabled(false);
		    			fc.showOpenDialog(fc);
		    			//get the events map content(including the dates and events) from the selected json file
		    			events = new JSON_write_read().readJSONtoEvent(fc.getSelectedFile().getPath());
	    		} catch(Exception e2) {
	    			e2.printStackTrace();
	    		}
	    	}
	    });
	    //Reminding users all the remaining days for each holiday from now on and all the events defined in the current month 
	    btn_reminder = new JButton("Reminder");
	    btn_reminder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		//every time once a user opens a reminder window, he/she can not open another one reminder windown until this one closes
	    		if (openReminderWindow == true) {
		    		try {	    		
		    			    //control can open or can not open
		    				openReminderWindow = false;
			    			ReminderWindow reminder = new ReminderWindow();// open a reminder window
			    			//set the holiday remaining days and events reminder content
			    			reminder.setHolidayContent(currentYear, currentMonth, currentDay, holidays);
			    			reminder.setEventContent(currentYear, currentMonth, currentDay, events);
		    		} catch (ParseException e2){
		    			e2.printStackTrace();
		    		}
	    		}
	    	}
	    });
	    //save all the current events in the calendar to a json file  
	    btn_export = new JButton("export");
	    btn_export.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		try {	    		
	    			    //users can choose their own address to save to a file
		    			JFileChooser fc = new JFileChooser();
		    			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    			fc.setDialogType(JFileChooser.FILES_ONLY);
		    			fc.setDialogTitle("save the file:");
		    			fc.setMultiSelectionEnabled(false);
		    			fc.showSaveDialog(fc);
		    			//get the events map including all the event information and convert these String text to store in the json format file
		    			new JSON_write_read().saveEventtoJSON(events,fc.getSelectedFile().getPath());
	    		} catch(Exception e2) {
	    			e2.printStackTrace();
	    		}
	    	}
	    });
	    //correspondingly add all the components
	    p_current.add(lb_current);
	    p_control.add(lb_yr);
	    p_control.add(combo_yr);
	    p_control.add(lb_mon);
	    p_control.add(combo_mon);
	    p_control.add(btn_before);
	    p_control.add(btn_current);
	    p_control.add(btn_after);
	    p_control.add(lb_changeTheme);
	    p_control.add(combo_changeTheme);
	    p_footer.add(btn_import, BorderLayout.EAST);
	    p_footer.add(btn_reminder, BorderLayout.CENTER);
	    p_footer.add(btn_export, BorderLayout.WEST);
	    //instantiating the display String array to store the Sunday to Saturday and the all the days in one month
	    display = new JLabel[42];
	    //fix and display the Sunday, Monday,...., Saturday in the first line
	    for (int i=0; i<7; i++) {
	    	//marking the weekends with pink border
	    	if(i == 0 || i == 6) {
	    		lb_DayOfWeek = new JLabel(headers[i], JLabel.CENTER);
		    	lb_DayOfWeek.setOpaque(false);
		    	lb_DayOfWeek.setFont(new Font("Dialog", Font.PLAIN, 18));
	    	}
	    	else {
	    		lb_DayOfWeek = new JLabel(headers[i], JLabel.CENTER);
		    	lb_DayOfWeek.setOpaque(false);
		    	lb_DayOfWeek.setFont(new Font("Dialog", Font.PLAIN, 18));
	    	}
	    	p_display.add(lb_DayOfWeek);
	    }
	    //instantiating the JPopupmenu, when a user would like to add an event, he/she can click the right mouse to trigger the pmenu
	    pmenu = new JPopupMenu();
	    menu_add = new JMenuItem("Add Event");
	    pmenu.add(menu_add);
	    //get all the current event Dates 
	    setEvent = events.keySet();
	    //Convert from Set type to the String[] type
 		allEvents = setEvent.toArray(new String[setEvent.size()]);
		//set all the specific day displayment content and style
	    for (int i=0; i<display.length; i++) {
	    	display[i] = new JLabel("", JLabel.CENTER);
	    	display[i].setFont(new Font("Dialog", Font.PLAIN, 15));
	    	//setting the border and marking the weekends as same as above
	    	if(i%7 == 0 || i%7 == 6) {
	    		display[i].setOpaque(false);
	    		border = BorderFactory.createLineBorder(new Color(255, 170, 150), 2);
	    		display[i].setBorder(border);
	    	}
	    	else {
	    		border = BorderFactory.createLineBorder(Color.gray, 1);
	    		display[i].setBorder(border);
	    	}	    	
	    	p_display.add(display[i]);
	    	//set the mouse listener for each day
	    	display[i].addMouseListener(new MouseAdapter() {
	    		public void mouseClicked(MouseEvent e) {
	    			//check only if the clicked JLable content is not null
	    			if (!((JLabel)e.getSource()).getText().equals("")) {
	    				//split the JLabel text by whitespace
	    				clickedSplit = ((JLabel)e.getSource()).getText().split(" ");
	    				//get the first element i.e. the exact day
		    			clickedDay = Integer.parseInt(clickedSplit[0]);
		    			//check if the user double click the JLabel
		    			if (e.getClickCount() == 2) {
		    					//maybe some JLabels can not display all the content, so when a user double click a JLabel
		    				    //the system will pop up a dialogbox to show the detail information for each day including exact day, holiday name and event marks 
			    				if (((JLabel)e.getSource()).getText().length() > 2) {
			    					messege = "The Day is : " + currentYear + " / " + currentMonth + " / " + ((JLabel)e.getSource()).getText();
			    					JOptionPane.showMessageDialog(p_footer, messege);
			    				}
		    			}
		    			//check if the user click the JLabel by right mouse
		    			if (e.getButton() == MouseEvent.BUTTON3) {
		    				//show the pmenu i.e add the event
		    				pmenu.show(e.getComponent(), e.getX(), e.getY());
		    			}
	    			}
	    		}
	    	});
	    }
	    //when a user clicks the add the event, the system will turn to the event window to add the new event
	    menu_add.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		new EventWindow(currentYear, currentMonth, clickedDay);
	    	}
	    });
	    //set the JFrame properties
		setSize(widthx, heighty);
		setLocation(locationx, locationy);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	    setResizable(false); 
	}
	//define the private member variable get/set methods
	public static Map<String, String> getEvents() {
		return events;
	}
	
	public static void setEvents(Map<String, String> map) {
		events = map;
	}
	
	public static void lenMap() {
		System.out.println(events.size());
	}
	
	public static int getwidthx() {
		return widthx;
	}
	
	public static int getheighty() {
		return heighty;
	}
	
	public static int getlocationx() {
		return locationx;
	}
	
	public static int getlocationy() {
		return locationy;
	}
	
	public static String getMotherDay() {
		return motherDate;
	}
	
	public static String getFatherDay() {
		return fatherDate;
	}
	
	public static String getLaborDay() {
		return laborDate;
	}
	
	public static String getMartinDay() {
		return MartinDate;
	}
	
	public static String getMemorialDay() {
		return MemorialDate;
	}
	
	public static String getPresidentDay() {
		return PresidentDate;
	}
	
	public static String getThanksgivingDay() {
		return ThanksgivingDate;
	}
	
	public static String getEasterDay() {
		return EasterDate;
	}
	
	public static void setOpenReminderWindow(boolean reminderstatus) {
		openReminderWindow = reminderstatus;
	}
	
	public static void setOpenEventWindow(boolean eventstatus) {
		openEventWindow = eventstatus;
	}
	//before init(), the clear() method should clear the content and style
	//every time the updating operation such as opening the application, clicking different components will action listener to reset the window content especially the exact calendar day information
	public void init() {
		//represent the day(1-28/29/30/31)
		int temp = 1;
		//the following holiday exact date will be determined by the week num for each year
		//recall the methods defined in the SpecificHoliday class to get the exact holiday date for the exact year
		motherDate = new SpecificHoliday().getMotherDayByYear(currentYear);
		fatherDate = new SpecificHoliday().getFatherDayByYear(currentYear);
		laborDate = new SpecificHoliday().getLaborDayByYear(currentYear);
		MartinDate = new SpecificHoliday().getMartinDayByYear(currentYear);
		MemorialDate = new SpecificHoliday().getMemorialDayByYear(currentYear);
		PresidentDate = new SpecificHoliday().getPresidentDayByYear(currentYear);
		ThanksgivingDate = new SpecificHoliday().getThanksgivingDayByYear(currentYear);
		EasterDate = new SpecificHoliday().getEasterDayByYear(currentYear);
		//get all the event Dates
		setEvent = events.keySet();
    	allEvents = setEvent.toArray(new String[setEvent.size()]);
    	//get the day numbers for a month
		maxDayOfMonth = getDays(currentYear, currentMonth);
		//get the first day index for each month Eg. Monday is 1, Tuesday is 2, ... Sunday is 7
		firstDayIndex = getFirstDayOfWeek(currentYear, currentMonth);
		//when the current year and month update, the JComboBox selectedItem content will update either
		combo_yr.setSelectedItem(new Integer(currentYear));
		combo_mon.setSelectedItem(new Integer(currentMonth));
		lb_current.setText("Today is : " + cal.get(Calendar.YEAR) + " / " + (cal.get(Calendar.MONTH) + 1) + " / " + currentDay + "       Happy " + status);      
		lb_current.setFont(new Font("Dialog", Font.PLAIN, 20));
		//get the holiday map content from the Holidays class
		holidays = Holidays.getHolidays();
        //Converting from the firstDayIndex, that is to say that converting from the chinese algorithm idea to the display[] element index idea, get the final JLabel[] index for the displayment. Eg. display[0] means Sunday, display[1] means Monday..., display[6] means Saturday
		flag = firstDayIndex % 7;
		//traversing all the days in one month to set the content for each JLabel
		for (int i=flag; i<flag+maxDayOfMonth; i++) {
			//check if the current includes the current day, if yes, marking the current day with blue border
			if ((i+1) == cal.get(Calendar.DAY_OF_MONTH) && currentMonth == (cal.get(Calendar.MONTH) + 1) && currentYear == cal.get(Calendar.YEAR)) {
				border = BorderFactory.createLineBorder(Color.blue, 2);
				display[i].setBorder(border);
			}
			//check if each day JLabel content include holiday or event information, if yes, marking that day JLabel text with red foreground color
			if (display[i].getText().length() > 2) {
				display[i].setForeground(Color.red);
			}
			//get the holiday and event date
			check_holiday = currentMonth + "/" + temp;
			check_event = currentYear + "/" + currentMonth + "/" + temp;
			//check if each day has the corresponding holiday information, if yes, display it behind the day number
			if(holidays.containsKey(check_holiday)) {
        		display[i].setText(temp + " " + holidays.get(check_holiday));
        	}
			else {
				display[i].setText(temp + "");
			}
        	temp++;
        	//check if each day has the corresponding event information, if yes, display it behind the day number or holiday content
        	for (int j=0; j<allEvents.length; j++) {
        		String[] splitEventDate = allEvents[j].split("/");
        		String getDate = splitEventDate[0] + "/" + splitEventDate[1] + "/" + splitEventDate[2];
        		if (getDate.equals(check_event)) {
        			display[i].setText(display[i].getText() + " " + "*");
        		}
        	}
        }
		//forcingly update the content displaying on the window
        repaint();
	}
	//check if the current year is the leap year
	public boolean isLeap(int year)  
	{  
		if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))  
			return true;  
	    else  
	    	return false;  
	} 
	//get the days number for one exact month
	public int getDays(int year, int month)  
    {  
        int days = 0;  
        int FebDay = 28;  
        if (isLeap(year))  
            FebDay = 29;  
        switch (month)  
        {  
            case 1:  
            case 3:  
            case 5:  
            case 7:  
            case 8:  
            case 10:  
            case 12:  
                days = 31;  
                break;  
            case 4:  
            case 6:  
            case 9:  
            case 11:  
                days = 30;  
                break;  
            case 2:  
                days = FebDay;  
                break;  
        }  
        return days;  
    }  
	//***The "YiFu Liu" Algorithm used to decide the day of the week for every first day given by the exact year and month 
	public int getFirstDayOfWeek(int year, int month) {
		int cen = 0;
		int mon = 0;
		if (year < 2000)
			cen = 0;
		if (year >= 2000)
			cen = 6;
		switch (month) {
		case 1:
			if(isLeap(year)) {
				mon = 0;
			}
			else {
				mon = 1;
			}			
			break;
		case 2:
			if(isLeap(year)) {
				mon = 3;
			}
			else {
				mon = 4;
			}	
			break;
		case 3:
			mon = 4;
			break;
		case 4:
			mon = 0;
			break;
		case 5:
			mon = 2;
			break;
		case 6:
			mon = 5;
			break;
		case 7:
			mon = 0;
			break;
		case 8:
			mon = 3;
			break;
		case 9:
			mon = 6;
			break;
		case 10:
			mon = 1;
			break;
		case 11:
			mon = 4;
			break;
		case 12:
			mon = 6;
			break;
		}
		
		String str_year = String.valueOf(year);
		String str_last2 = str_year.charAt(2) + "" + str_year.charAt(3);
		int twoDigit = Integer.parseInt(str_last2);
		int dayIndexResult = (((((twoDigit % 7) + (twoDigit / 4) + cen) % 7) - 1) + mon) + 1; 
        if (dayIndexResult > 7)
        	dayIndexResult %= 7;
		return dayIndexResult;
	}
	//clear the each day number text and day JLabel words and border style
	public void clear() {
		for (int i=0; i<display.length; i++) {
			display[i].setText("");
			display[i].setForeground(Color.black);
			if(i%7 == 0 || i%7 == 6) {
	    		display[i].setOpaque(false);
	    		border = BorderFactory.createLineBorder(new Color(255, 170, 150), 2);
	    		display[i].setBorder(border);
	    	}
	    	else {
	    		border = BorderFactory.createLineBorder(Color.gray, 1);
	    		display[i].setBorder(border);
	    	}
		}
	}
	//Main method
	public static void main(String[] args) {
		MyCalendarWindow myCalendar = new MyCalendarWindow();
		myCalendar.init();
		//the opening window background music once
		new Music();
	}
}



