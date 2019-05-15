package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

	public static String convertDateToWritten(Calendar date) {
		String writtenDay = new SimpleDateFormat("EEEE").format(date.getTime());
		writtenDay = writtenDay.substring(0, 1).toUpperCase() + writtenDay.substring(1);
		String writtenDate = writtenDay + ", " + date.get(Calendar.DATE);		
		
		switch (date.get(Calendar.MONTH)) {
			case 0 : writtenDate += " de Janeiro"; break;
			case 1 : writtenDate += " de Fevereiro"; break;
			case 2 : writtenDate += " de Março"; break;
			case 3 : writtenDate += " de Abril"; break;
			case 4 : writtenDate += " de Maio"; break;
			case 5 : writtenDate += " de Junho"; break;
			case 6 : writtenDate += " de Julho"; break;
			case 7 : writtenDate += " de Agosto"; break;
			case 8 : writtenDate += " de Setembro"; break;
			case 9 : writtenDate += " de Outubro"; break;
			case 10 : writtenDate += " de Novembro"; break;
			default : writtenDate += " de Dezembro";
		}		
		writtenDate += " de " + date.get(Calendar.YEAR);
		return writtenDate;
	}
	
	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}
	
	public static SimpleDateFormat getHourFormat() {
		return new SimpleDateFormat("HH:mm:ss");
	}
	
	public static SimpleDateFormat getHourFormatAMPM() {
		return new SimpleDateFormat("hh:mm:ss");
	}
	
	public static SimpleDateFormat getAMPM() {
		return new SimpleDateFormat("a");
	}
}
