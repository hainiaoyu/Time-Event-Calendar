/* 
* Remind the user all the remaining days for holidays after the current day and the current month events 
*/
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.swing.*;
import javax.swing.border.Border;
//ReminderWindow class
public class ReminderWindow extends JFrame{
	private Container c = getContentPane();
	private JPanel p_holiday, p_event, p_holidayheader, p_eventheader, p_showholiday, p_showevent;
	private JLabel lb_holiday, lb_event, mainImage;
	private JTextArea ta_holiday, ta_event;
	private Border border;
	private Calendar cal;
	private Date currentDate, holidayDate, eventDate;
	private SimpleDateFormat sdf;
	private int wx, hy, lx, ly;
	private int currentYear, currentMonth, currentDay;
	private Map<String, String> holidaymap;
	private Map<String, String> eventmap;
	private Set<String> setHoliday, setEvent;	
	private String holidayContent = "";
	private String eventContent = "";	
	private String[] allHolidays, allEvents;	
	
	//constructor
	public ReminderWindow() {
		super("Reminder");
		border = BorderFactory.createLineBorder(Color.gray, 1);
		p_holiday = new JPanel();
		p_holiday.setOpaque(false);
		p_holiday.setLayout(new BorderLayout());
		
		p_event = new JPanel();
		p_event.setOpaque(false);
		p_event.setLayout(new BorderLayout());
		
		p_holidayheader = new JPanel();
		p_holidayheader.setOpaque(false);
		
		p_showholiday = new JPanel();
		p_showholiday.setPreferredSize(new Dimension(207, 200));
		p_showholiday.setBorder(border);
		p_showholiday.setOpaque(false);
		
		p_eventheader = new JPanel();
		p_eventheader.setOpaque(false);
		
		p_showevent = new JPanel();
		p_showevent.setBorder(border);
		p_showevent.setOpaque(false);
		
		lb_holiday = new JLabel(" Legal National Holidays ");
		lb_holiday.setFont(new Font("Dialog", 1, 14));
		
		lb_event = new JLabel(" Defined Upcoming Events ");		
		lb_event.setFont(new Font("Dialog", 1, 14));
		
		ta_holiday = new JTextArea();
		ta_holiday.setOpaque(false);
		ta_holiday.setFont(new Font("Dialog", 1, 12));
		ta_holiday.setEditable(false);
		
		ta_event = new JTextArea();
		ta_event.setOpaque(false);
		ta_event.setFont(new Font("Dialog", 1, 12));
		ta_event.setEditable(false);	
		//the first layer is the container, and then add the mainImage above the container, finally add all other components on the mainImage layer
		mainImage = new JLabel(new ImageIcon("images/sea.jpg"));
		c.add(mainImage);
		mainImage.setLayout(new BorderLayout());
		
		mainImage.add(p_holiday, BorderLayout.WEST);
		mainImage.add(p_event, BorderLayout.CENTER);
		p_holiday.add(p_holidayheader, BorderLayout.NORTH);
		p_holiday.add(p_showholiday, BorderLayout.CENTER);
		p_event.add(p_eventheader, BorderLayout.NORTH);
		p_event.add(p_showevent, BorderLayout.CENTER);
		p_holidayheader.add(lb_holiday);
		p_eventheader.add(lb_event);
		p_showholiday.add(ta_holiday);
		p_showevent.add(ta_event);
		//instantiating the Calendar object
		cal = Calendar.getInstance();
		//define the Date format
		sdf = new SimpleDateFormat("yyyy/MM/dd");
		//get the size and location of the MyCalendarWindow
		wx = MyCalendarWindow.getwidthx();
		lx = MyCalendarWindow.getlocationx();
		hy = MyCalendarWindow.getheighty();
		ly = MyCalendarWindow.getlocationy();		
		//set the ReminderWindow JFrame properties
		setSize(wx - 305, hy - 350);
		setLocation(lx + wx, ly + hy - 250);	
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//add listener to the window closing event
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//combined with control logic with the method defined in the MyCalendarWindow in order to check if the user can open a new ReminderWindow
				MyCalendarWindow.setOpenReminderWindow(true);
			}
		});     
	}
	//set the holiday reminder part content
	public void setHolidayContent(int currentYear, int currentMonth, int currentDay, Map<String, String> holidaymap) throws ParseException {
		this.currentYear = currentYear;
		this.currentMonth = currentMonth;
		this.currentDay = currentDay;
		this.holidaymap = holidaymap;
		//get the current date
		currentDate = sdf.parse(currentYear + "/" + currentMonth + "/" + currentDay);	
		
		setHoliday = holidaymap.keySet();
		allHolidays = setHoliday.toArray(new String[setHoliday.size()]); 
		//traversing each holiday in the holiday map
		for (int i=0; i<holidaymap.size(); i++) {
			//get the corresponding holiday date
			holidayDate= sdf.parse(currentYear + "/" + allHolidays[i]);
			//calculate the countdown days between current day and upcoming holidays
			cal.setTime(currentDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(holidayDate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000*3600*24);
			//check if the current day is ahead of the holiday, if yes, do the calculation
			if (between_days > 0) {
				holidayContent += holidaymap.get(allHolidays[i]) + "   " + between_days + "Days" + "\n";
			}
		}
		ta_holiday.setText(holidayContent);
	}
	//set the event reminder part content
	public void setEventContent(int currentYear, int currentMonth, int currentDay, Map<String, String> eventmap) throws ParseException {
		this.currentYear = currentYear;
		this.currentMonth = currentMonth;
		this.currentDay = currentDay;
		this.eventmap = eventmap;
		//define the Date format
		sdf = new SimpleDateFormat("yyyy/MM/dd"); 
		//get the current date
		currentDate = sdf.parse(currentYear + "/" + currentMonth + "/" + currentDay);
		
		setEvent = eventmap.keySet();
		//convert from event Set type to the event String[]
		allEvents = setEvent.toArray(new String[setEvent.size()]);
		//traversing each event in the event String[]
		for (int i=0; i<allEvents.length; i++) {
			String[] splitEventDate = allEvents[i].split("/");
			//get the event date
			eventDate = sdf.parse(splitEventDate[0] + "/" + splitEventDate[1] + "/" + splitEventDate[2]);
			//calculate the countdown days between current day and upcoming events
			cal.setTime(currentDate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(eventDate);
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000*3600*24);
			//check if the current day is ahead of the event, if yes, do the calculation
			if (between_days > 0) {
				eventContent += eventmap.get(allEvents[i]) + "   " + between_days + "Days" + "\n";
			}
		}
		ta_event.setText(eventContent);
	}
}
