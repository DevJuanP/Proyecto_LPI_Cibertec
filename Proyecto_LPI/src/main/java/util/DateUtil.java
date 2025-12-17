package util;

import java.time.LocalDate;

public class DateUtil {
    
    /**
     * Convierte un LocalDate a una representación de año en español.
     * - Años a.C.: "400 a.C." (solo año con sufijo "a.C.")
     * - Años d.C.: "1259" (solo año)
     * 
     * @param date la fecha a convertir
     * @return año formateado como String, o null si date es null
     */
    public static String formatYear(LocalDate date) {
        if (date == null) {
            return null;
        }
        
        int year = date.getYear();
        
        if (year <= 0) {
            int bcYear = -year + 1;
            return bcYear + " a.C.";
        } else {
            return String.valueOf(year);
        }
    }
}