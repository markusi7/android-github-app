package markusi.githubapp.ui.user_details;

import com.google.auto.value.AutoValue;

@AutoValue
abstract class UserPreviewUiModel {

    static UserPreviewUiModel create(String login, String avatarUrl) {
        return new AutoValue_UserPreviewUiModel(login, avatarUrl);
    }

    abstract String login();

    abstract String avatarUrl();
}
