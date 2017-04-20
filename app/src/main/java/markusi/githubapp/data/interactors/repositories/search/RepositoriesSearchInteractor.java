package markusi.githubapp.data.interactors.repositories.search;

import io.reactivex.Single;
import markusi.githubapp.data.interactors.PaginatingInteractor;
import markusi.githubapp.data.models.repository.RepositoriesResponse;

public interface RepositoriesSearchInteractor
        extends PaginatingInteractor<RepositoriesSearchRequest, Single<RepositoriesResponse>> {

}
