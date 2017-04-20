package markusi.githubapp.ui.repositories;

import org.threeten.bp.format.DateTimeFormatter;

import android.content.res.Resources;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import markusi.githubapp.R;
import markusi.githubapp.data.interactors.repositories.search.RepositoriesSearchInteractor;
import markusi.githubapp.data.interactors.repositories.search.RepositoriesSearchRequest;
import markusi.githubapp.data.models.api.RateLimit;
import markusi.githubapp.data.models.repository.RepositoriesResponse;
import markusi.githubapp.data.models.repository.Repository;
import markusi.githubapp.data.models.repository.RepositorySortType;
import markusi.githubapp.data.rx.ErrorHandlingSingleObserverDelegate;
import markusi.githubapp.di.Callback;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.utils.CollectionUtils;
import timber.log.Timber;

public class RepositoriesPresenter implements RepositoriesMvp.Presenter {

    // user should be alerted if it used more then (RATE_LIMIT_THRESHOLD * API limit) number of requests
    private static final double RATE_LIMIT_THRESHOLD = 0.7;

    private final RepositoriesMvp.View view;

    private final RepositoriesSearchInteractor interactor;

    private final Scheduler callbackScheduler;

    private final BaseMvp.ErrorListener errorListener;

    private final Resources resources;

    private final CompositeDisposable compositeDisposable;

    @Inject
    RepositoriesPresenter(RepositoriesMvp.View view, RepositoriesSearchInteractor interactor,
            @Callback Scheduler callbackScheduler, BaseMvp.ErrorListener errorListener,
            Resources resources) {
        this.view = view;
        this.interactor = interactor;
        this.callbackScheduler = callbackScheduler;
        this.errorListener = errorListener;
        this.resources = resources;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onSearchTermChanged(String searchTerm, RepositorySortType repositorySortType) {
        view.showProgress();
        interactor.execute(RepositoriesSearchRequest.create(searchTerm, repositorySortType))
                .observeOn(callbackScheduler)
                .subscribe(new ErrorHandlingSingleObserverDelegate<RepositoriesResponse>(errorListener, compositeDisposable) {

                    @Override
                    public void onSuccess(@NonNull RepositoriesResponse repositoriesResponse) {
                        onSuccessfulResponse(repositoriesResponse,
                                new ErrorHandlingSingleObserverDelegate<List<RepositoryListItem>>(errorListener,
                                        compositeDisposable) {
                                    @Override
                                    public void onSuccess(@NonNull List<RepositoryListItem> repositoryListItems) {
                                        view.hideProgress();
                                        view.showItems(repositoryListItems);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        view.hideProgress();
                                        Timber.e(e);
                                    }
                                });
                    }
                });
    }

    @Override
    public void loadNext() {
        view.showProgress();
        interactor.loadNextPage().observeOn(callbackScheduler).subscribe(
                new ErrorHandlingSingleObserverDelegate<RepositoriesResponse>(errorListener, compositeDisposable) {
                    @Override
                    public void onSuccess(@NonNull RepositoriesResponse repositoriesResponse) {
                        onSuccessfulResponse(repositoriesResponse,
                                new ErrorHandlingSingleObserverDelegate<List<RepositoryListItem>>(errorListener,
                                        compositeDisposable) {
                                    @Override
                                    public void onSuccess(@NonNull List<RepositoryListItem> repositoryListItems) {
                                        view.hideProgress();
                                        view.showMoreItems(repositoryListItems);
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        view.hideProgress();
                                        Timber.e(e);
                                    }
                                });
                    }
                });
    }

    private boolean isCloseToRateLimit(int remaining, int limit) {
        return 1 - (1.0f * remaining / limit) > RATE_LIMIT_THRESHOLD;
    }

    // if the response is successful, convert model items to list items, set the empty state on the View
    // also, inform the View if all items have been loaded and if the user is close to the API limit
    private void onSuccessfulResponse(RepositoriesResponse repositoriesResponse,
            ErrorHandlingSingleObserverDelegate<List<RepositoryListItem>> delegate) {
        List<Repository> repositories = repositoriesResponse.repositories();
        if (CollectionUtils.hasItems(repositories)) {
            Observable.fromIterable(repositories).map(repository ->
                    RepositoryListItem.create(
                            repository.id(),
                            repository.name(),
                            repository.user(),
                            repository.watchersCount(),
                            repository.forksCount(),
                            repository.issuesCount()))
                    .toList()
                    .subscribe(delegate);
        } else {
            view.onNoSearchResults();
        }
        if (repositoriesResponse.hasReachedEnd()) {
            view.onAllItemsLoaded();
        }
        final RateLimit rateLimit = repositoriesResponse.rateLimit();
        if (isCloseToRateLimit(rateLimit.remaining(), rateLimit.limit())) {
            prepareLimitWarning(rateLimit);
        }
        view.hideProgress();
    }

    @Override
    public void cancel() {
        compositeDisposable.clear();
    }

    // prepare a user friendly message
    private void prepareLimitWarning(RateLimit rateLimit) {
        String formattedDateTime = null;
        try {
            formattedDateTime = DateTimeFormatter.ISO_LOCAL_TIME.format(rateLimit.resetDateTime());
        } catch (Exception exception) {
            Timber.e(exception);
        } finally {
            if (formattedDateTime == null) {
                formattedDateTime = resources.getString(R.string.unknown);
            }
        }
        view.showLimitWarning(resources.getString(
                R.string.rate_limit_placeholder, rateLimit.limit() - rateLimit.remaining(),
                rateLimit.limit(),
                formattedDateTime));
    }
}
