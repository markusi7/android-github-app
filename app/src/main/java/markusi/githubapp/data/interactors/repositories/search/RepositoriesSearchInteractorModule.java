package markusi.githubapp.data.interactors.repositories.search;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesSearchInteractorModule {

    @Provides
    RepositoriesSearchInteractor provideInteractor(RepositoriesSearchInteractorImpl interactor) {
        return interactor;
    }
}
