package markusi.githubapp.ui.repository_details.di;

import dagger.Subcomponent;
import markusi.githubapp.data.interactors.repositories.get.RepositoryGetDetailsInteractorModule;
import markusi.githubapp.ui.repository_details.RepositoryDetailsActivity;

@Subcomponent(modules = {
        RepositoryDetailsModule.class,
        RepositoryGetDetailsInteractorModule.class
})
public interface RepositoryDetailsComponent {

    void inject(RepositoryDetailsActivity activity);
}
