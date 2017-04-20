package markusi.githubapp.utils;

import android.content.Context;
import android.support.annotation.AnyRes;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;

public class ViewUtils {

    private ViewUtils() {
        throw new AssertionError();
    }

    @AnyRes
    public static int getResourceId(@NonNull Context context, @AttrRes int attributeId) {
        final AppCompatActivity activity = getAppCompatActivity(context);
        if (activity != null) {
            final TypedValue typedValue = new TypedValue();
            activity.getTheme().resolveAttribute(attributeId, typedValue, true);
            return typedValue.resourceId;
        }
        return 0;
    }

    @Nullable
    private static AppCompatActivity getAppCompatActivity(@NonNull Context context) {
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompatActivity(((ContextThemeWrapper) context).getBaseContext());
        } else if (context instanceof android.support.v7.view.ContextThemeWrapper) {
            return getAppCompatActivity(((android.support.v7.view.ContextThemeWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
