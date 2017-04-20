package markusi.githubapp.ui.repository_details;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class RepositoryPreviewUiModel {

    static RepositoryPreviewUiModel create(
            String name,
            String userAvatarUrl,
            String userLogin,
            String watchersCount,
            String forksCount,
            String issuesCount) {
        return new AutoValue_RepositoryPreviewUiModel(name, userAvatarUrl, userLogin, watchersCount, forksCount, issuesCount);
    }

    abstract String name();

    abstract String userAvatarUrl();

    abstract String userLogin();

    abstract String watchersCount();

    abstract String forksCount();

    abstract String issuesCount();
}
