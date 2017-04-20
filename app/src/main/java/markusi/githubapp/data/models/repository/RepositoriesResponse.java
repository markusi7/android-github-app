package markusi.githubapp.data.models.repository;

import com.google.auto.value.AutoValue;

import java.util.List;

import markusi.githubapp.data.models.api.RateLimit;

@AutoValue
public abstract class RepositoriesResponse {

    public static RepositoriesResponse create(RateLimit rateLimit, List<Repository> repositories, boolean hasReachedEnd) {
        return new AutoValue_RepositoriesResponse(rateLimit, repositories, hasReachedEnd);
    }

    public abstract RateLimit rateLimit();

    public abstract List<Repository> repositories();

    public abstract boolean hasReachedEnd();
}
