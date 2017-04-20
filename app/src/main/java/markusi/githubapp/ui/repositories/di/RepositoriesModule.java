package markusi.githubapp.ui.repositories.di;

import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repositories.RepositoriesMvp;
import markusi.githubapp.ui.repositories.RepositoriesPresenter;
import markusi.githubapp.ui.shared.ErrorHandlerDelegate;

@Module
public class RepositoriesModule {

    private final RepositoriesMvp.View view;

    public RepositoriesModule(RepositoriesMvp.View view) {
        this.view = view;
    }

    @Provides
    RepositoriesMvp.View provideView() {
        return view;
    }

    @Provides
    RepositoriesMvp.Presenter providePresenter(RepositoriesPresenter presenter) {
        return presenter;
    }

    @Provides
    BaseMvp.ErrorListener provideErrorListener(RepositoriesMvp.View view, Resources resources) {
        return new ErrorHandlerDelegate(view, resources);
    }
}
