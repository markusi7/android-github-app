package markusi.githubapp.ui.repository_details;

import android.support.annotation.NonNull;

import markusi.githubapp.data.models.user.User;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repositories.RepositoryListItem;

public interface RepositoryDetailsMvp {

    interface View extends BaseMvp.View {

        void showPreview(RepositoryPreviewUiModel model);

        void showDetails(RepositoryDetailsUiModel model);

        void openRepositoryWebPage(String url);

        void navigateToUserDetails(User user);
    }

    interface Presenter extends BaseMvp.Presenter {

        void init(@NonNull RepositoryListItem repositoryListItem);

        void onDetailsButtonClicked();

        void onUserAvatarClicked();
    }
}
