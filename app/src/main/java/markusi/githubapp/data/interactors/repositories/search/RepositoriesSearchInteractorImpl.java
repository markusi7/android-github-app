package markusi.githubapp.data.interactors.repositories.search;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import markusi.githubapp.data.models.api.PaginationLinks;
import markusi.githubapp.data.models.repository.RepositoriesApiResponse;
import markusi.githubapp.data.models.repository.RepositoriesResponse;
import markusi.githubapp.data.network.ApiService;
import markusi.githubapp.utils.ApiUtils;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * This Interactor isn't stateless, but I've found it great for 2 things.
 *
 * 1. exposing appropriate data models to the Presenter; this Interactor fetched data (the whole parametrized {@link Response} object,
 * extracts data from {@link retrofit2.http.Header}s and using {@link Single#flatMap(Function)} creates an appropriate model for the
 * Presenter. In my opinion, this logic seems to fit in the Interactor.
 *
 * 2. Pagination: this Interactor fetches next link from the response headers, stores it and navigates to the next page without the
 * Presenter storing the number of next page/ next URL to navigate to.
 * This also seems appropriate to be in the Interactor, because if we change the Interactor, we don't have to handle all these
 * implementation details about pagination: it's the Interactors task to do that.
 */
class RepositoriesSearchInteractorImpl implements RepositoriesSearchInteractor {

    private final ApiService apiService;

    private String nextPageUrl;

    // great example of flatMap rx operator: we get the whole response from ApiService, fetch data we want
    // and the return appropriate model for the Presenter.
    private final Function<Response<RepositoriesApiResponse>, SingleSource<RepositoriesResponse>> mapper =
            new Function<Response<RepositoriesApiResponse>, SingleSource<RepositoriesResponse>>() {
                @Override
                public SingleSource<RepositoriesResponse> apply(@NonNull Response<RepositoriesApiResponse> response) throws Exception {
                    if (response.isSuccessful()) {
                        PaginationLinks paginationLinks = ApiUtils.createPaginationLinks(response.headers());
                        nextPageUrl = paginationLinks != null ? paginationLinks.nextPageUrl() : null;
                        return Single.just(
                                RepositoriesResponse.create(
                                        ApiUtils.createRateLimit(response.headers()),
                                        response.body().repositories(),
                                        nextPageUrl == null
                                ));
                    } else {
                        throw new HttpException(response);
                    }
                }
            };

    @Inject
    RepositoriesSearchInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<RepositoriesResponse> execute(RepositoriesSearchRequest requestParam) {
        return apiService.getRepositories(requestParam.searchTerm(), requestParam.sortType().getApiValue()).flatMap(mapper);
    }

    @Override
    public Single<RepositoriesResponse> loadNextPage() {
        return apiService.getRepositoriesNextPage(nextPageUrl).flatMap(mapper);
    }
}
