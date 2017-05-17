/*
 * calculate some special holiday dates for each different year Eg.Sunday in first week 
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
 
public class SpecificHoliday {
    private static Calendar cal;
    private static DateFormat df;
    private int sundays, maxDate;
    private int currentYear;
    private String sdate_motherday, sdate_fatherday, sdate_laborday, sdate_martinday, sdate_memorialday, sdate_presidentday, sdate_thanksgivingday, sdate_easterday;
    
    public SpecificHoliday() {
    	cal = Calendar.getInstance();
    	sundays = 0;
    }
 
    // 2 sunday in 5 (May)
    public String getMotherDayByYear(int year) {    
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 4); // the first month is 0
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays ++;
                if(sundays == 2) {
                    break;
                }
            }
	    }
        sdate_motherday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
        return sdate_motherday;
    }
    
    //3 sunday in 6 (June)
    public String getFatherDayByYear(int year) {    
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 5); 
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays ++;
                if(sundays == 3) {
                    break;
                }
            }
	    }
        sdate_fatherday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
        return sdate_fatherday;
    }
    
    // 1 Monday in 9(September)
    public String getLaborDayByYear(int year) {
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 8); 
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                sundays ++;
                if(sundays == 1) {
                    break;
                }
            }
	    }
        sdate_laborday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
    	return sdate_laborday;
    }
    
    // 3 Monday in 1(jan)
    public String getMartinDayByYear(int year) {
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 0); 
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                sundays ++;
                if(sundays == 3) {
                    break;
                }
            }
	    }
        sdate_martinday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
    	return sdate_martinday;
    }
    
    // get memorialday. last monday of May.
    // First get first monday of June. then sub 1 
    public String getMemorialDayByYear(int year) {
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 5);
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                sundays ++;
                if(sundays == 1) {
                    break;
                }
            }
	    }
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        sdate_memorialday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
    	return sdate_memorialday;
    }
    
    // 3 Monday in 2
    public String getPresidentDayByYear(int year) {
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 1);
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                sundays ++;
                if(sundays == 3) {
                    break;
                }
            }
	    }
        sdate_presidentday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
    	return sdate_presidentday;
    }
    
    // 4 thursday in December
    public String getThanksgivingDayByYear(int year) {
    	currentYear = year;            
        cal.set(Calendar.YEAR, currentYear);
        cal.set(Calendar.MONTH, 10);
        maxDate = cal.getActualMaximum(Calendar.DATE);
        for(int i = 1; i <= maxDate; i++) {
            cal.set(Calendar.DATE, i);
            if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
                sundays ++;
                if(sundays == 4) {
                    break;
                }
            }
	    }
        sdate_thanksgivingday = (new SimpleDateFormat("M/d")).format(cal.getTime()); 
    	return sdate_thanksgivingday;
    }
    
    // easter day :first Sunday following the first full moon. March or April
    public String getEasterDayByYear(int year) {
	    int a = year % 19,
	        b = year / 100,
	        c = year % 100,
	        d = b / 4,
	        e = b % 4,
	        g = (8 * b + 13) / 25,
	        h = (19 * a + b - d - g + 15) % 30,
	        j = c / 4,
	        k = c % 4,
	        m = (a + 11 * h) / 319,
	        r = (2 * e + 2 * j - k - h + m + 32) % 7,
	        n = (h - m + r + 90) / 25,
	        p = (h - m + r + n + 19) % 32;

        int resultmonth = 0;
        switch(n)
        {
            case 1:
            	resultmonth = 1;
                break;
            case 2:
            	resultmonth = 2;
                break;
            case 3:
            	resultmonth = 3;
                break;
            case 4:
            	resultmonth = 4;
                break;
            case 5:
            	resultmonth = 5;
                break;
            case 6:
            	resultmonth = 6;
                break;
            case 7:
            	resultmonth = 7;
                break;
            case 8:
            	resultmonth = 8;
                break;
            case 9:
            	resultmonth = 9;
                break;
            case 10:
            	resultmonth = 10;
                break;
            case 11:
            	resultmonth = 11;
                break;
            case 12:
            	resultmonth = 12;
                break;
        }
        sdate_easterday = resultmonth + "/" + p;    	    
    	return sdate_easterday;
    }
}
