package markusi.githubapp.ui.user_details.di;

import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.shared.ErrorHandlerDelegate;
import markusi.githubapp.ui.user_details.UserDetailsMvp;
import markusi.githubapp.ui.user_details.UserDetailsPresenter;

@Module
public class UserDetailsModule {

    private final UserDetailsMvp.View view;

    public UserDetailsModule(UserDetailsMvp.View view) {
        this.view = view;
    }

    @Provides
    UserDetailsMvp.View provideView() {
        return view;
    }

    @Provides
    public UserDetailsMvp.Presenter providePresenter(UserDetailsPresenter presenter) {
        return presenter;
    }

    @Provides
    BaseMvp.ErrorListener provideErrorListener(UserDetailsMvp.View view, Resources resources) {
        return new ErrorHandlerDelegate(view, resources);
    }
}
