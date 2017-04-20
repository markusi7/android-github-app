package markusi.githubapp.di;

import com.google.common.net.HttpHeaders;
import com.google.gson.Gson;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import markusi.githubapp.BuildConfig;
import markusi.githubapp.data.network.ApiService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

@Module
class ApiModule {

    @Provides
    @Singleton
    OkHttpClient okHttpClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(acceptHeaderInterceptor());
        okHttpBuilder.addInterceptor(userAgentInterceptor());

        if (BuildConfig.DEBUG) {
            okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> Timber.d(message));
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            okHttpBuilder.addInterceptor(loggingInterceptor);
        }

        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    Retrofit retrofit(OkHttpClient okHttpClient, Gson gson, @IO Scheduler ioScheduler) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(ScalarsConverterFactory.create()) // if we don't add this, strings get serialized with extra quotes!
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(ioScheduler))
                .client(okHttpClient);
        return retrofitBuilder.build();
    }

    /**
     * Adds the Accept header as described in GitHub API specification.
     *
     * @see <a href="https://developer.github.com/v3/#current-version">GitHub official documentation</a>
     */
    private Interceptor acceptHeaderInterceptor() {
        return chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader(HttpHeaders.ACCEPT, "application/vnd.github.v3+json")
                    .build();
            return chain.proceed(request);
        };
    }


    /**
     * Adds the Accept header as described in GitHub API specification.
     *
     * @see <a href="https://developer.github.com/v3/#current-version">GitHub official documentation</a>
     */
    private Interceptor userAgentInterceptor() {
        return chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader(HttpHeaders.USER_AGENT, "GitHubApp")
                    .build();
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
