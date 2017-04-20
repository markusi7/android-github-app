package markusi.githubapp.utils;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;

import android.support.annotation.Nullable;

public class DateTimeFormatUtils {

    private static final DateTimeFormatter APP_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private DateTimeFormatUtils() {
        throw new AssertionError();
    }

    @Nullable
    public static String formatToDateTime(TemporalAccessor temporalAccessor) {
        String output;
        try {
            output = temporalAccessor != null ? APP_DATE_TIME_FORMATTER.format(temporalAccessor) : null;
        } catch (Exception e) {
            output = null;
        }
        return output;
    }
}
