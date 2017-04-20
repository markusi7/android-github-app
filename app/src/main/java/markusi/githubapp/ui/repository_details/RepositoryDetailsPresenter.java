package markusi.githubapp.ui.repository_details;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import markusi.githubapp.data.interactors.repositories.get.RepositoryDetailsRequest;
import markusi.githubapp.data.interactors.repositories.get.RepositoryGetDetailsInteractor;
import markusi.githubapp.data.models.repository.Repository;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.data.rx.ErrorHandlingSingleObserverDelegate;
import markusi.githubapp.di.Callback;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repositories.RepositoryListItem;
import markusi.githubapp.utils.DateTimeFormatUtils;

public class RepositoryDetailsPresenter implements RepositoryDetailsMvp.Presenter {

    private final RepositoryDetailsMvp.View view;

    private final RepositoryGetDetailsInteractor interactor;

    private final Scheduler callbackScheduler;

    private final BaseMvp.ErrorListener errorListener;

    private final CompositeDisposable compositeDisposable;

    private String htmlUrl;

    private User user;

    @Inject
    RepositoryDetailsPresenter(RepositoryDetailsMvp.View view, RepositoryGetDetailsInteractor interactor,
            @Callback Scheduler callbackScheduler, BaseMvp.ErrorListener errorListener) {
        this.view = view;
        this.interactor = interactor;
        this.callbackScheduler = callbackScheduler;
        this.errorListener = errorListener;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void init(@NonNull RepositoryListItem item) {
        view.showPreview(RepositoryPreviewUiModel.create(
                item.name(),
                item.user().avatarUrl(),
                item.user().login(),
                String.valueOf(item.watchersCount()),
                String.valueOf(item.forksCount()),
                String.valueOf(item.issuesCount())
        ));
        interactor.execute(RepositoryDetailsRequest.create(item.user().login(), item.name()))
                .observeOn(callbackScheduler)
                .subscribe(new ErrorHandlingSingleObserverDelegate<Repository>(errorListener, compositeDisposable) {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Repository repository) {
                        RepositoryDetailsPresenter.this.htmlUrl = repository.htmlUrl();
                        RepositoryDetailsPresenter.this.user = repository.user();
                        view.showDetails(RepositoryDetailsUiModel.create(
                                repository.name(),
                                repository.user().avatarUrl(),
                                repository.user().login(),
                                repository.description(),
                                repository.language(),
                                String.valueOf(repository.watchersCount()),
                                String.valueOf(repository.forksCount()),
                                String.valueOf(repository.issuesCount()),
                                DateTimeFormatUtils.formatToDateTime(repository.createdAt()),
                                DateTimeFormatUtils.formatToDateTime(repository.updatedAt()),
                                repository.defaultBranch()));
                    }
                });
    }

    @Override
    public void onDetailsButtonClicked() {
        if (htmlUrl != null) {
            view.openRepositoryWebPage(htmlUrl);
        }
    }

    @Override
    public void onUserAvatarClicked() {
        view.navigateToUserDetails(user);
    }

    @Override
    public void cancel() {
        compositeDisposable.clear();
    }
}
