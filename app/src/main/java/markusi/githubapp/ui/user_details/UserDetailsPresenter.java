package markusi.githubapp.ui.user_details;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import markusi.githubapp.data.interactors.users.UserGetInteractor;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.data.rx.ErrorHandlingSingleObserverDelegate;
import markusi.githubapp.di.Callback;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.utils.DateTimeFormatUtils;

public class UserDetailsPresenter implements UserDetailsMvp.Presenter {

    private final UserDetailsMvp.View view;

    private final UserGetInteractor interactor;

    private final Scheduler callbackScheduler;

    private final BaseMvp.ErrorListener errorListener;

    private final CompositeDisposable compositeDisposable;

    private String htmlUrl;

    @Inject
    UserDetailsPresenter(UserDetailsMvp.View view, UserGetInteractor interactor,
            @Callback Scheduler callbackScheduler, BaseMvp.ErrorListener errorListener) {
        this.view = view;
        this.interactor = interactor;
        this.callbackScheduler = callbackScheduler;
        this.errorListener = errorListener;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void init(@NonNull User user) {
        view.showPreview(UserPreviewUiModel.create(user.login(), user.avatarUrl()));
        interactor.execute(user.login())
                .observeOn(callbackScheduler)
                .subscribe(new ErrorHandlingSingleObserverDelegate<User>(errorListener, compositeDisposable) {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                        UserDetailsPresenter.this.htmlUrl = user.htmlUrl();
                        view.showDetails(UserDetailsUiModel.create(
                                user.login(),
                                user.avatarUrl(),
                                user.bio(),
                                user.name(),
                                user.company(),
                                user.email(),
                                user.location(),
                                DateTimeFormatUtils.formatToDateTime(user.createdAt()),
                                String.valueOf(user.publicReposCount()),
                                String.valueOf(user.followersCount())
                        ));
                    }
                });
    }

    @Override
    public void onDetailsButtonClicked() {
        if (htmlUrl != null) {
            view.openUserWebPage(htmlUrl);
        }
    }

    @Override
    public void cancel() {
        compositeDisposable.clear();
    }
}
