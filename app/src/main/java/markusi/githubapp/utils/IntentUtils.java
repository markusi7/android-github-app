package markusi.githubapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import timber.log.Timber;

public class IntentUtils {

    private IntentUtils() {
        throw new AssertionError();
    }

    public static void openWebPageInExternalBrowser(@NonNull Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (context.getPackageManager().resolveActivity(intent, 0) != null) {
            context.startActivity(intent);
        } else {
            Timber.d("Browser not found on the device!");
        }
    }
}
