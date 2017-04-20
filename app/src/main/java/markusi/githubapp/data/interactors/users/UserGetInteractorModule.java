package markusi.githubapp.data.interactors.users;

import dagger.Module;
import dagger.Provides;

@Module
public class UserGetInteractorModule {

    @Provides
    UserGetInteractor provideInteractor(UserGetInteractorImpl interactor) {
        return interactor;
    }
}
