package util;

public class DateUtil {
    
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