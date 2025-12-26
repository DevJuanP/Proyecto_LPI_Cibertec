package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Formatea un LocalDateTime a formato de fecha dd/MM/yyyy.
     * 
     * @param dateTime la fecha a formatear
     * @return fecha formateada como String, o "-" si dateTime es null
     */
    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }
        return dateTime.format(DATE_FORMATTER);
    }
    
    /**
     * Formatea un LocalDateTime a formato de fecha y hora dd/MM/yyyy HH:mm.
     * 
     * @param dateTime la fecha y hora a formatear
     * @return fecha y hora formateada como String, o "-" si dateTime es null
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "-";
        }
        return dateTime.format(DATETIME_FORMATTER);
    }
    
    /**
     * Convierte un Integer a una representación de año en español.
     * - Años a.C.: "400 a.C." (solo año con sufijo "a.C.")
     * - Años d.C.: "1259" (solo año)
     * 
     * @param year la fecha a convertir
     * @return año formateado como String, o null si date es null
     */
    public static String formatYear(Integer year) {
        if (year == null) {
            return "-";
        }
        
        if (year <= 0) {
            int bcYear = -year + 1;
            return bcYear + " a.C.";
        } else {
            return String.valueOf(year);
        }
    }
}