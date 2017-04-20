package markusi.githubapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
class SchedulersModule {

    @Provides
    @Singleton
    @IO
    Scheduler ioScheduler() {
        return Schedulers.io();
    }

    @Provides
    @Singleton
    @Callback
    Scheduler callbackScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
