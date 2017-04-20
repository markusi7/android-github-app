package markusi.githubapp.data.models.api;

import com.google.auto.value.AutoValue;

import org.threeten.bp.ZonedDateTime;

@AutoValue
public abstract class RateLimit {

    public static RateLimit create(int limit, int remaining, ZonedDateTime resetDateTime) {
        return new AutoValue_RateLimit(limit, remaining, resetDateTime);
    }

    public abstract int limit();

    public abstract int remaining();

    public abstract ZonedDateTime resetDateTime();
}
