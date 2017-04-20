package markusi.githubapp.ui.repository_details.di;

import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repository_details.RepositoryDetailsMvp;
import markusi.githubapp.ui.repository_details.RepositoryDetailsPresenter;
import markusi.githubapp.ui.shared.ErrorHandlerDelegate;

@Module
public class RepositoryDetailsModule {

    private final RepositoryDetailsMvp.View view;

    public RepositoryDetailsModule(RepositoryDetailsMvp.View view) {
        this.view = view;
    }

    @Provides
    RepositoryDetailsMvp.View provideView() {
        return view;
    }

    @Provides
    RepositoryDetailsMvp.Presenter providePresenter(RepositoryDetailsPresenter presenter) {
        return presenter;
    }

    @Provides
    BaseMvp.ErrorListener provideErrorListener(RepositoryDetailsMvp.View view, Resources resources) {
        return new ErrorHandlerDelegate(view, resources);
    }
}
