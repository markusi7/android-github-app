package markusi.githubapp.ui.user_details;

import android.support.annotation.NonNull;

import markusi.githubapp.data.models.user.User;
import markusi.githubapp.ui.BaseMvp;

public interface UserDetailsMvp {

    interface View extends BaseMvp.View {

        void showPreview(UserPreviewUiModel model);

        void showDetails(UserDetailsUiModel model);

        void openUserWebPage(String htmlUrl);
    }

    interface Presenter extends BaseMvp.Presenter {

        void init(@NonNull User user);

        void onDetailsButtonClicked();
    }
}
