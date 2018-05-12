package fr.endyron.leuceusseu.lcs;

import java.util.Date;

/**
 * Convert dates from the LCS format to Java Date
 */
public abstract class DateConverter {

	public static Date stringToDate(String toFormat){
		int year = Integer.parseInt(toFormat.substring(0, 4)) - 1900;
		int month = Integer.parseInt(toFormat.substring(5, 7)) - 1;
		int day = Integer.parseInt(toFormat.substring(8, 10));
		int hour = Integer.parseInt(toFormat.substring(11, 13)) + 2; //Not sure about that, may need to be changed sometimes
		int min = Integer.parseInt(toFormat.substring(14, 16));
		return new Date(year, month, day, hour, min);
	}
}
