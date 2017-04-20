package markusi.githubapp.di;

import android.content.Context;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import markusi.githubapp.GitHubApp;

@Module
class AppModule {

    @Provides
    @AppContext
    @Singleton
    Context appContext() {
        return GitHubApp.getInstance();
    }

    @Provides
    @Singleton
    public Resources resources(@AppContext Context context) {
        return context.getResources();
    }
}
