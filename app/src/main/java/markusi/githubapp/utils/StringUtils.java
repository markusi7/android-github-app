package markusi.githubapp.utils;

import android.support.annotation.Nullable;
import android.text.TextUtils;

public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    public static boolean isEmpty(@Nullable CharSequence text) {
        return TextUtils.isEmpty(text);
    }

}
