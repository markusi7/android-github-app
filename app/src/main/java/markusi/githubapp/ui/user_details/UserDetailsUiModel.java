package markusi.githubapp.ui.user_details;

import com.google.auto.value.AutoValue;

import android.support.annotation.Nullable;

@AutoValue
abstract class UserDetailsUiModel {

    static UserDetailsUiModel create(
            String login,
            String avatarUrl,
            @Nullable String bio,
            @Nullable String name,
            @Nullable String company,
            @Nullable String email,
            @Nullable String location,
            @Nullable String createdAt,
            String publicReposCount,
            String numberOfFollowers) {
        return new AutoValue_UserDetailsUiModel(login, avatarUrl, bio, name, company, email, location, createdAt, publicReposCount,
                numberOfFollowers);
    }

    abstract String login();

    abstract String avatarUrl();

    @Nullable
    abstract String bio();

    @Nullable
    abstract String name();

    @Nullable
    abstract String company();

    @Nullable
    abstract String email();

    @Nullable
    abstract String location();

    @Nullable
    abstract String createdAt();

    abstract String publicReposCount();

    abstract String numberOfFollowers();
}
