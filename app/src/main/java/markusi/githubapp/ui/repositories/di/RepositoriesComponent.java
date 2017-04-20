package markusi.githubapp.ui.repositories.di;

import dagger.Subcomponent;
import markusi.githubapp.data.interactors.repositories.search.RepositoriesSearchInteractorModule;
import markusi.githubapp.ui.repositories.RepositoriesActivity;

@Subcomponent(modules = {
        RepositoriesModule.class,
        RepositoriesSearchInteractorModule.class
})
public interface RepositoriesComponent {

    void inject(RepositoriesActivity activity);
}
