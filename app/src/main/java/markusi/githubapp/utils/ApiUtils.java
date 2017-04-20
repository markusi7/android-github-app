package markusi.githubapp.utils;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import markusi.githubapp.data.models.api.PaginationLinkType;
import markusi.githubapp.data.models.api.PaginationLinks;
import markusi.githubapp.data.models.api.RateLimit;
import okhttp3.Headers;

public class ApiUtils {

    private static final String RATE_LIMIT_LIMIT = "X-RateLimit-Limit";

    private static final String RATE_LIMIT_REMAINING = "X-RateLimit-Remaining";

    private static final String RATE_LIMIT_RESET = "X-RateLimit-Reset";

    private static final String LINK = "Link";

    // very proud of this
    private static final String LINK_REGEX = "(?<=<)(.*?)(?>)>; rel=\"(first|last|prev|next)\"";

    private static final Pattern PATTERN = Pattern.compile(LINK_REGEX);

    private static final long MILLIS_IN_SECONDS = 1000;

    private ApiUtils() {
        throw new AssertionError();
    }

    @Nullable
    public static PaginationLinks createPaginationLinks(@NonNull Headers headers) {
        String linkHeader = headers.get(LINK);
        if (!StringUtils.isEmpty(linkHeader)) {
            Matcher matcher = PATTERN.matcher(linkHeader);
            String nextLinkUrl = null;
            String lastLinkUrl = null;
            String firstLinkUrl = null;
            String previousLinkUrl = null;
            while (matcher.find() && matcher.groupCount() == 2) {
                String url = matcher.group(1);
                @PaginationLinkType String ref = matcher.group(2);
                switch (ref) {
                    case PaginationLinkType.NEXT:
                        nextLinkUrl = url;
                        break;
                    case PaginationLinkType.LAST:
                        lastLinkUrl = url;
                        break;
                    case PaginationLinkType.FIRST:
                        firstLinkUrl = url;
                        break;
                    case PaginationLinkType.PREVIOUS:
                        previousLinkUrl = url;
                        break;
                    default:
                        // shouldn't happen as the regex has only this values
                        break;
                }
            }
            return PaginationLinks.create(nextLinkUrl, lastLinkUrl, firstLinkUrl, previousLinkUrl);
        } else {
            return null;
        }
    }

    public static RateLimit createRateLimit(@NonNull Headers headers) {
        return RateLimit.create(
                Integer.parseInt(headers.get(RATE_LIMIT_LIMIT)),
                Integer.parseInt(headers.get(RATE_LIMIT_REMAINING)),
                ZonedDateTime.ofInstant(Instant.ofEpochMilli(
                        Long.parseLong(headers.get(RATE_LIMIT_RESET)) * MILLIS_IN_SECONDS), ZoneId.systemDefault())
        );
    }
}
