package markusi.githubapp.data.interactors.users;

import io.reactivex.Single;
import markusi.githubapp.data.interactors.Interactor;
import markusi.githubapp.data.models.user.User;

public interface UserGetInteractor extends Interactor<String, Single<User>> {

}
