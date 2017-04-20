package markusi.githubapp.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.threeten.bp.ZonedDateTime;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import markusi.githubapp.data.adapters.ZonedDateTimeAdapter;

@Module
class GsonModule {

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(AutoGsonAdapterFactory.create())
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                .create();
    }
}
