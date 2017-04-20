package markusi.githubapp.utils;

import android.support.annotation.Nullable;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {
        throw new AssertionError();
    }

    public static boolean hasItems(@Nullable Collection collection) {
        return collection != null && !collection.isEmpty();
    }
}
