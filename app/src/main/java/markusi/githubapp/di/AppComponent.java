package markusi.githubapp.di;

import javax.inject.Singleton;

import dagger.Component;
import markusi.githubapp.ui.repositories.di.RepositoriesComponent;
import markusi.githubapp.ui.repositories.di.RepositoriesModule;
import markusi.githubapp.ui.repository_details.di.RepositoryDetailsComponent;
import markusi.githubapp.ui.repository_details.di.RepositoryDetailsModule;
import markusi.githubapp.ui.user_details.di.UserDetailsComponent;
import markusi.githubapp.ui.user_details.di.UserDetailsModule;
import retrofit2.Retrofit;

@Component(modules = {
        AppModule.class,
        ApiModule.class,
        GsonModule.class,
        SchedulersModule.class
})
@Singleton
public interface AppComponent {

    Retrofit retrofit();

    RepositoriesComponent plus(RepositoriesModule module);

    RepositoryDetailsComponent plus(RepositoryDetailsModule module);

    UserDetailsComponent plus(UserDetailsModule module);
}
