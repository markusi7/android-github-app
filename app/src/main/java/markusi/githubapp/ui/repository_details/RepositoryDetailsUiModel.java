package markusi.githubapp.ui.repository_details;

import com.google.auto.value.AutoValue;

import android.support.annotation.Nullable;

@AutoValue
abstract class RepositoryDetailsUiModel {

    static RepositoryDetailsUiModel create(
            String name,
            String userAvatarUrl,
            String userLogin,
            @Nullable String description,
            @Nullable String language,
            String watchersCount,
            String forksCount,
            String issuesCount,
            @Nullable String createdAt,
            @Nullable String updatedAt,
            @Nullable String defaultBranchName) {
        return new AutoValue_RepositoryDetailsUiModel(name, userAvatarUrl, userLogin, description, language, watchersCount, forksCount,
                issuesCount, createdAt, updatedAt, defaultBranchName);
    }

    abstract String name();

    abstract String userAvatarUrl();

    abstract String userLogin();

    @Nullable
    abstract String description();

    @Nullable
    abstract String language();

    abstract String watchersCount();

    abstract String forksCount();

    abstract String issuesCount();

    @Nullable
    abstract String createdAt();

    @Nullable
    abstract String updatedAt();

    @Nullable
    abstract String defaultBranchName();

}
