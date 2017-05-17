/* 
* Save all events into json format file and Read all events from the json format file 
*/
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;  
import org.json.JSONObject;

public class JSON_write_read {  
	private JSONObject jsonObject; // jsonobject used to write and read json file
	private Map<String, String> savedEventMap;  
	private Map<String, String> ReadEventmap;
	private int len_readEvent, len_split_eventFirstPart, len_split_eventSecondPart;
	private String readEvent, correctFormatEvent, eventContent, eventDate;
	private String[] split_singleEvent, split_eventTwoParts;
	public JSON_write_read() {
		jsonObject = new JSONObject();
	}
	
	// function used to write file
	public static void writeFile(String filePath, String sets)
            throws IOException {  
        FileWriter fw = new FileWriter(filePath, false);  
        PrintWriter out = new PrintWriter(fw);  
        out.write(sets);  
        out.println();  
        fw.close();  
        out.close();  
    }  
	// function used to read file
    public static String ReadFile(String path) {  
    	File file = new File(path);  
        BufferedReader reader = null;  
        String laststr = "";  
        try {  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
                laststr = laststr + tempString;  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
        return laststr;  
    }  
    
    // traverse the event map, get data and pass it to the jsonobject, then recall writeFile function 
    // to write to the json file
    public void saveEventtoJSON(Map<String, String> eventmap, String path) throws Exception{
    	savedEventMap = eventmap;
    	Set<String> setEventDate = savedEventMap.keySet();
    	String[] eventDate = setEventDate.toArray(new String[setEventDate.size()]);
    	for (int i=0; i<eventDate.length; i++) {
    		jsonObject.put(eventDate[i], savedEventMap.get(eventDate[i]));
    	}
    	writeFile(path, jsonObject.toString());
    }
    
    // use readFile function to get data from json file. Split json format data to "date" and "name"
    // Then seperately pass them to the eventmap
    public Map<String, String> readJSONtoEvent(String path) { 
    	ReadEventmap = new HashMap<String, String>();
    	readEvent = ReadFile(path);    	
    	len_readEvent = readEvent.length();    	
    	correctFormatEvent = readEvent.substring(1, len_readEvent-1);
    	split_singleEvent = correctFormatEvent.split(",");
    	
    	for (int i=0; i<split_singleEvent.length; i++) {
    		split_eventTwoParts = split_singleEvent[i].split(":");
    		len_split_eventFirstPart = split_eventTwoParts[0].length();
    		len_split_eventSecondPart = split_eventTwoParts[1].length();
    		eventDate = split_eventTwoParts[0].substring(1,len_split_eventFirstPart - 1);
    		eventContent = split_eventTwoParts[1].substring(1, len_split_eventSecondPart - 1);
    		ReadEventmap.put(eventDate, eventContent);
    	}
    	return ReadEventmap;
    }
}  
