package markusi.githubapp.data.interactors.repositories.get;

import javax.inject.Inject;

import io.reactivex.Single;
import markusi.githubapp.data.models.repository.Repository;
import markusi.githubapp.data.network.ApiService;

public class RepositoryGetDetailsInteractorImpl implements RepositoryGetDetailsInteractor {

    private final ApiService apiService;

    @Inject
    RepositoryGetDetailsInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<Repository> execute(RepositoryDetailsRequest requestParam) {
        return apiService.getRepositoryDetails(requestParam.userLogin(), requestParam.repo());
    }
}
