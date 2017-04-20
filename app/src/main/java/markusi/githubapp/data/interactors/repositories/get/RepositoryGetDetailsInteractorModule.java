package markusi.githubapp.data.interactors.repositories.get;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryGetDetailsInteractorModule {

    @Provides
    RepositoryGetDetailsInteractor provideInteractor(RepositoryGetDetailsInteractorImpl interactor) {
        return interactor;
    }
}
