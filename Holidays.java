/* 
* defining the specific holiday information 
*/
import java.util.*;
//Holidays class
public class Holidays {
	//member variable storing all the legal holidays dates and names in pairs in the HashMap
	private static Map<String, String> holidaymap;
	//set static method in order to be recalled by other class without instantiating the class
	//define each holiday information including date and name previously
	//especially, some holiday dates are not fixed, they need to recall the corresponding methods defined in the MyCalendarWindow to get exact date for each year
    public static Map<String, String> getHolidays() {
    	holidaymap = new HashMap<String, String>();
    	holidaymap.put("1/1", "New Year's Day");
    	holidaymap.put(MyCalendarWindow.getMartinDay(), "Martin Luther King Jr.'s Birthday");
    	holidaymap.put("2/12", "Lincoln's Birthday");
    	holidaymap.put("2/14", "Valentine's Day");
    	holidaymap.put(MyCalendarWindow.getPresidentDay(), "President's Day");
    	holidaymap.put("3/8", "International Women's Day");
    	holidaymap.put("3/17", "St.Patrick's Day");
    	holidaymap.put("4/1", "April Fool's Day");
    	holidaymap.put(MyCalendarWindow.getEasterDay(), "Easter");
    	holidaymap.put("5/1", "MAY Day");
    	holidaymap.put("5/6", "Nurses' Day");
    	holidaymap.put(MyCalendarWindow.getMotherDay(), "Mother's Day");
    	holidaymap.put(MyCalendarWindow.getMemorialDay(), "Memorial Day");
    	holidaymap.put(MyCalendarWindow.getFatherDay(), "Father's Day");
    	holidaymap.put("7/4", "Independence Day");
    	holidaymap.put(MyCalendarWindow.getLaborDay(), "Labor Day");
    	holidaymap.put("10/12", "Columbus Day");
    	holidaymap.put("10/31", "Halloween");
    	holidaymap.put("11/11", "Veterans Day");
    	holidaymap.put(MyCalendarWindow.getThanksgivingDay(), "Thanksgiving");
    	holidaymap.put("12/25", "Christmas");
    	
    	return holidaymap;
    }
}
