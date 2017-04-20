package markusi.githubapp;

import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.StrictMode;

import markusi.githubapp.di.AppComponent;
import markusi.githubapp.di.DaggerAppComponent;
import timber.log.Timber;

public class GitHubApp extends Application {

    private static GitHubApp instance;

    private AppComponent appComponent;

    public static GitHubApp getInstance() {
        return instance;
    }

    private static void setInstance(GitHubApp instance) {
        GitHubApp.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);

        appComponent = DaggerAppComponent.create();

        registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        init();
    }

    private void init() {
        AndroidThreeTen.init(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            //TODO REPORT CRASHES
            Timber.plant(new Timber.DebugTree());
            initStrictMode();
        }
    }

    private void initStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyFlashScreen().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}
