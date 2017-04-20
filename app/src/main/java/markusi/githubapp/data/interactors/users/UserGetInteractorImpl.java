package markusi.githubapp.data.interactors.users;

import javax.inject.Inject;

import io.reactivex.Single;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.data.network.ApiService;

class UserGetInteractorImpl implements UserGetInteractor {

    private final ApiService apiService;

    @Inject
    UserGetInteractorImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<User> execute(String requestParam) {
        return apiService.getUserDetails(requestParam);
    }
}
