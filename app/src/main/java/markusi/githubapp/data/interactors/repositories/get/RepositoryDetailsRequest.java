package markusi.githubapp.data.interactors.repositories.get;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RepositoryDetailsRequest {

    public static RepositoryDetailsRequest create(String userLogin, String repo) {
        return new AutoValue_RepositoryDetailsRequest(userLogin, repo);
    }

    public abstract String userLogin();

    public abstract String repo();
}
