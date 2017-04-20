package markusi.githubapp.data.models.repository;

import markusi.githubapp.R;

/**
 * Contains both display name for the Sort Type, as well the API value used as a query parameter.
 */
public enum RepositorySortType {
    RELEVANCE(R.string.relevance, null),
    STARS(R.string.stars, "stars"),
    FORKS(R.string.forks, "ic_forks"),
    LAST_UPDATED(R.string.last_updated, "updated");

    private final int stringResourceId;

    private final String apiValue;

    RepositorySortType(int stringResourceId, String apiValue) {
        this.stringResourceId = stringResourceId;
        this.apiValue = apiValue;
    }

    public int getStringResourceId() {
        return stringResourceId;
    }

    public String getApiValue() {
        return apiValue;
    }
}
