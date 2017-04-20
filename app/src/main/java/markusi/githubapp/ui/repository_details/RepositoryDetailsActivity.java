package markusi.githubapp.ui.repository_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import markusi.githubapp.R;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.di.AppComponent;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repositories.RepositoryListItem;
import markusi.githubapp.ui.repository_details.di.RepositoryDetailsModule;
import markusi.githubapp.ui.shared.BaseActivity;
import markusi.githubapp.ui.shared.LabelValueView;
import markusi.githubapp.ui.user_details.UserDetailsActivity;
import markusi.githubapp.utils.ImageUtils;
import markusi.githubapp.utils.IntentUtils;
import markusi.githubapp.utils.StringUtils;

public class RepositoryDetailsActivity extends BaseActivity implements RepositoryDetailsMvp.View {

    private static final String EXTRA_REPOSITORY_LIST_ITEM = "extra_repository_list_item";

    @Inject
    RepositoryDetailsMvp.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.userAvatar)
    ImageView userAvatar;

    @BindView(R.id.userLogin)
    TextView userLogin;

    @BindView(R.id.repositoryDescription)
    LabelValueView repositoryDescription;

    @BindView(R.id.repositoryLanguage)
    LabelValueView repositoryLanguage;

    @BindView(R.id.repositoryWatchersCount)
    LabelValueView repositoryWatchersCount;

    @BindView(R.id.repositoryForksCount)
    LabelValueView repositoryForksCount;

    @BindView(R.id.repositoryIssuesCount)
    LabelValueView repositoryIssuesCount;

    @BindView(R.id.repositoryCreatedAt)
    LabelValueView repositoryCreatedAt;

    @BindView(R.id.repositoryUpdatedAt)
    LabelValueView repositoryUpdatedAt;

    @BindView(R.id.defaultBranchName)
    LabelValueView defaultBranchName;

    public static Intent createIntent(Context context, RepositoryListItem repositoryListItem) {
        Intent intent = new Intent(context, RepositoryDetailsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY_LIST_ITEM, repositoryListItem);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);
        ButterKnife.bind(this);
        setupBackIcon(toolbar);

        if (getIntent() != null && getIntent().getParcelableExtra(EXTRA_REPOSITORY_LIST_ITEM) != null) {
            presenter.init(getIntent().getParcelableExtra(EXTRA_REPOSITORY_LIST_ITEM));
        } else {
            throw new IllegalArgumentException(
                    "Repository details screen MUST be provided with a Repository List Item as an extra in the Intent with "
                            + "\"repository_list_item\" key!");
        }
    }

    @Override
    public void showPreview(RepositoryPreviewUiModel model) {
        toolbar.setTitle(model.name());
        ImageUtils.displayCircleCroppedImage(userAvatar, model.userAvatarUrl(), R.drawable.ic_avatar_placeholder);
        userLogin.setText(model.userLogin());
        repositoryWatchersCount.setValue(model.watchersCount());
        repositoryForksCount.setValue(model.forksCount());
        repositoryIssuesCount.setValue(model.issuesCount());
    }

    @Override
    public void showDetails(RepositoryDetailsUiModel model) {
        toolbar.setTitle(model.name());
        userLogin.setText(model.userLogin());
        setupNullableDetailItem(repositoryDescription, model.description());
        setupNullableDetailItem(repositoryLanguage, model.language());
        repositoryWatchersCount.setValue(model.watchersCount());
        repositoryForksCount.setValue(model.forksCount());
        repositoryIssuesCount.setValue(model.issuesCount());
        setupNullableDetailItem(repositoryCreatedAt, model.createdAt());
        setupNullableDetailItem(repositoryUpdatedAt, model.updatedAt());
        setupNullableDetailItem(defaultBranchName, model.defaultBranchName());
    }

    @OnClick(R.id.userLayout)
    void onUserAvatarClicked() {
        presenter.onUserAvatarClicked();
    }

    @Override
    public void navigateToUserDetails(User user) {
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, userAvatar,
                        getString(R.string.transition_name_user_avatar));
        startActivity(UserDetailsActivity.createIntent(this, user), options.toBundle());
    }

    @OnClick(R.id.detailsButton)
    void onDetailsButtonClicked() {
        presenter.onDetailsButtonClicked();
    }

    @Override
    public void openRepositoryWebPage(String url) {
        IntentUtils.openWebPageInExternalBrowser(this, url);
    }

    @Override
    protected void injectDependencies(@NonNull AppComponent appComponent) {
        appComponent.plus(new RepositoryDetailsModule(this)).inject(this);
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        return presenter;
    }

    private void setupNullableDetailItem(LabelValueView view, String text) {
        view.setValue(text);
        view.setVisibility(StringUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
    }
}
