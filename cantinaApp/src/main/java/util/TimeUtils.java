package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class TimeUtils {
	
	private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	public static java.util.Date fromStringToDate(String day) throws ParseException {
		return formatter.parse(day);
	}
	
	public static java.util.Date fromInstantToDate() throws ParseException {
		return formatter.parse(Instant.now().toString());
	}
	
	public static java.util.Date fromSqlDateToUtilDate(java.sql.Date date) throws ParseException{
		String sdate=date.toString();
		return formatter.parse(sdate);
	}
	
	public static java.sql.Date fromUtilDateToSqlDate(java.util.Date date){
		return new java.sql.Date(date.getTime());
	}
}
