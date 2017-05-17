/*
 * In this part, we realized adding event function. When users right click a specific date on the calendar, a menu will 
 * pop up on the label. Users can add event in the new open window to add events in detailed time and date. Also, when 
 * users click save button, all the events can save to a file we created in our system when started up. On the footer 
 * of the event window, users can fill out their email address and our system will send email to users address. 
 */
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;

public class EventWindow extends JFrame{
	private Container c = getContentPane();
	private JPanel p_header, p_content, p_control, p_show, p_footer;
	private JLabel lb_date, lb_hour, lb_minute, lb_event, mainImage, lb_mail, lb_empty;
	private JTextArea ta_show, tf_event;
	private JComboBox combo_hour, combo_minute;
	private JTextField tf_mail;
	private JButton btn_confirm, btn_add, btn_mail;
	private int wx, hy, lx, ly;
	private int clickedYear, clickedMonth, clickedDay;
	private String currentDate;
	private String showEventContent;
	private String keyDate;
	private String valueEvent;
	private Calendar cal;
	private Map<String, String> eventmap;
	private Set<String> setEvent;
	private String[] allEvents;
	private boolean flag;
	
	private Border border;
	
	/*
	 * EventWindow is the constructor of the EventWindow class, it will initialize a new event window when users clicked 
	 * "Add event" label. It contains three paramenters, 
	 */
	public EventWindow(int clicked_Year, int clicked_Month, int clicked_Day) {
		super("Add Event");
		flag = true;
		clickedYear = clicked_Year;
		clickedMonth = clicked_Month;
		clickedDay = clicked_Day;
		cal = Calendar.getInstance();
		currentDate = "Add events in Day : " + clickedYear + " / " + clickedMonth + " / " + clickedDay;
		showEventContent = "";
		keyDate = "";
		valueEvent = "";

		/*
		 * Create several Panels to 
		 */
		p_header = new JPanel();
		p_header.setOpaque(false);
		
		p_content = new JPanel();
		p_content.setOpaque(false);
		p_content.setLayout(new BorderLayout());
		p_control = new JPanel();
		p_control.setOpaque(false);
		
		p_show = new JPanel();
		border = BorderFactory.createLineBorder(Color.gray, 1);
		p_show.setBorder(border);
		p_show.setOpaque(false);
		p_show.setPreferredSize(new Dimension(200,200));
		p_footer = new JPanel();
		p_footer.setOpaque(false);
		
		mainImage = new JLabel(new ImageIcon("images/sea.jpg"));
		mainImage.setLayout(new BorderLayout());
		c.add(mainImage);
		
		mainImage.add(p_header, BorderLayout.NORTH);
		mainImage.add(p_content, BorderLayout.CENTER);
		mainImage.add(p_footer, BorderLayout.SOUTH);
		p_content.add(p_control, BorderLayout.CENTER);	
		p_content.add(p_show, BorderLayout.EAST);
	 
		lb_date = new JLabel(currentDate);
		lb_date.setFont(new Font("Dialog",1,20));
		p_header.add(lb_date);
		
		lb_hour = new JLabel(" Select Hour :");
		lb_hour.setPreferredSize(new Dimension(100, 30));
		lb_hour.setFont(new Font("Dialog",1,12));
		combo_hour = new JComboBox();
		combo_hour.setPreferredSize(new Dimension(100, 30));
		for (int i=0; i<24; i++) {
	    	combo_hour.addItem(new Integer(i));
	    }
		combo_hour.setSelectedIndex(-1);
		
		lb_minute = new JLabel(" Select Minute :");
		lb_minute.setPreferredSize(new Dimension(100, 30));
		lb_minute.setFont(new Font("Dialog",1,12));
		combo_minute = new JComboBox();
		combo_minute.setPreferredSize(new Dimension(100, 30));
		for (int i=0; i<60; i++) {
			combo_minute.addItem(new Integer(i));
		}
		combo_minute.setSelectedIndex(-1);
		
		lb_event = new JLabel(" Add Events :");
		lb_event.setPreferredSize(new Dimension(100, 30));
		lb_event.setFont(new Font("Dialog",1,12));
		
		tf_event = new JTextArea();
		tf_event.setLineWrap(true);
		tf_event.setWrapStyleWord(true);
		tf_event.setPreferredSize(new Dimension(100, 100));
		
		lb_mail = new JLabel("Email (Optinal):");
		lb_mail.setFont(new Font("Dialog",1,12));
		lb_mail.setPreferredSize(new Dimension(100, 30));
		tf_mail = new JTextField();
		tf_mail.setPreferredSize(new Dimension(180, 30));
		btn_mail = new JButton("Send to Me");
		btn_mail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sendText = "You just added an event " + ta_show.getText();
				new sendMail().sendEventMail(tf_mail.getText().trim(), sendText);
			}
		});
		btn_mail.setPreferredSize(new Dimension(100, 30));
		
		lb_empty = new JLabel();
		lb_empty.setPreferredSize(new Dimension(120, 7));
		
		btn_confirm = new JButton("Confirm");
		btn_confirm.setPreferredSize(new Dimension(120,27));
		/*
		 * Confirm button realized three functions. First, getting specific date which users clicked and show them in 
		 * left text area. Second, pop up Null Alert when users forgot to type text field. Third, Time conflict can remind 
		 * users there already exists some events in that time.  
		 */
		btn_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flag = true;
				/*
				 * To judge if users have filled out all the required text fields. 
				 */
				if (combo_hour.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(p_footer, "Please select the hour!");
				} else if (combo_minute.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(p_footer, "Please select the minute!");
				} else if (tf_event.getText().equals("")) {
					JOptionPane.showMessageDialog(p_footer, "Please input the event content!");
				} else {
							//Get existed events stored in files and realize time conflict function. 
							eventmap = MyCalendarWindow.getEvents();
							setEvent = eventmap.keySet();
							allEvents = setEvent.toArray(new String[setEvent.size()]);
							// User split method to get the specific year, month, day and hour to judge if the new event's time is same as before. 
							for(int i=0; i<allEvents.length; i++) {
								String[] splitEventDate = allEvents[i].split("/");
								if (splitEventDate[0].equals(clickedYear + "") && splitEventDate[1].equals(clickedMonth + "") && splitEventDate[2].equals(clickedDay + "") && splitEventDate[3].equals(combo_hour.getSelectedItem() + "")) {
									combo_hour.setSelectedIndex(-1);
									combo_minute.setSelectedIndex(-1);
									tf_event.setText("");
									// Pop up alert window to remind time conflict. 
									JOptionPane.showMessageDialog(p_footer, "Time Conflict! Please reselect the date!");
									flag = false;
								}
							}
							// If the events users added do not have time conflict, get the specifc date and show them in the right text area. 
							if (flag) {
							keyDate = clickedYear + "/" + clickedMonth + "/" + clickedDay + "/" + combo_hour.getSelectedItem() + "/" + combo_minute.getSelectedItem();
							valueEvent = tf_event.getText();
							MyCalendarWindow.getEvents().put(keyDate, valueEvent);
							
							showEventContent += clickedYear + " / " + clickedMonth + " / " + clickedDay + " / " + combo_hour.getSelectedItem() + " \' " + combo_minute.getSelectedItem() + " \" " + "\n" + "---> " + tf_event.getText() + "\n"; 
							ta_show.setText(showEventContent);
							
							combo_hour.setSelectedIndex(-1);
							combo_minute.setSelectedIndex(-1);
							tf_event.setText("");
							}
				}
			}
		});
		
		// Add several elements to the event panel. 
		p_control.add(lb_hour);
		p_control.add(combo_hour);
		p_control.add(lb_minute);
		p_control.add(combo_minute);
		p_control.add(lb_event);
		p_control.add(tf_event);
		p_control.add(lb_empty);
		p_control.add(btn_confirm);
		
		p_footer.add(lb_mail);
		p_footer.add(tf_mail);
		p_footer.add(btn_mail);
		
		ta_show = new JTextArea("");
		ta_show.setEditable(false);
		ta_show.setOpaque(false);
		p_show.add(ta_show);

		/*
		 * Add action listener to Save button in order to save events to file. 
		 */
		btn_add = new JButton("Save");
		btn_add.setPreferredSize(new Dimension(120,27));
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Print the length of Map which stores events users added before. 
				MyCalendarWindow.lenMap();
				ta_show.setText("");
				showEventContent = "";
				try {
					//Save events to file. 
						new JSON_write_read().saveEventtoJSON(MyCalendarWindow.getEvents(), "/Users/yimingli/Documents/workspace/Mashibing/Calendar/images/666.json");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		p_control.add(btn_add);
		
		//Set the size and location of the Event Window. 
		wx = MyCalendarWindow.getwidthx();
		lx = MyCalendarWindow.getlocationx();
		hy = MyCalendarWindow.getheighty();
		ly = MyCalendarWindow.getlocationy();
		setSize(wx - 305, hy - 250);
		setLocation(lx + wx, ly);	
		setVisible(true);
	    setResizable(false); 
	}
}