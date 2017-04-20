package markusi.githubapp.data.interactors.repositories.search;

import com.google.auto.value.AutoValue;

import markusi.githubapp.data.models.repository.RepositorySortType;

@AutoValue
public abstract class RepositoriesSearchRequest {

    public static RepositoriesSearchRequest create(String searchTerm, RepositorySortType sortType) {
        return new AutoValue_RepositoriesSearchRequest(searchTerm, sortType);
    }

    public abstract String searchTerm();

    public abstract RepositorySortType sortType();
}
