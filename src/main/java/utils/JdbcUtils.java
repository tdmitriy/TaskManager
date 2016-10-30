package utils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JdbcUtils {
    /**
     * Simple parser that's helps to parse from string to java.sql.Date object.
     * Default date format in MySQL is `yyyy-MM-dd`
     * @param date given date string
     * @return null or java.sql.Date if string matches `yyyy-MM-dd` pattern.
     */
    public static Date parseDate(final String date) {
        if (date == null || !date.matches("\\d{4}-[01]\\d-[0-3]\\d"))
            return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        try {
            return new Date(df.parse(date).getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
