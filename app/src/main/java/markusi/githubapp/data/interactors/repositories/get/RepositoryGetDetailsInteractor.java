package markusi.githubapp.data.interactors.repositories.get;

import io.reactivex.Single;
import markusi.githubapp.data.interactors.Interactor;
import markusi.githubapp.data.models.repository.Repository;

public interface RepositoryGetDetailsInteractor extends Interactor<RepositoryDetailsRequest, Single<Repository>> {

}
