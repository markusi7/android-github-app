package markusi.githubapp.ui.user_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import markusi.githubapp.ui.shared.BaseActivity;
import markusi.githubapp.ui.shared.LabelValueView;
import markusi.githubapp.ui.user_details.di.UserDetailsModule;
import markusi.githubapp.utils.ImageUtils;
import markusi.githubapp.utils.IntentUtils;
import markusi.githubapp.utils.StringUtils;

public class UserDetailsActivity extends BaseActivity implements UserDetailsMvp.View {

    private static final String EXTRA_USER = "extra_user";

    @Inject
    UserDetailsMvp.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.userAvatar)
    ImageView userAvatar;

    @BindView(R.id.userBio)
    TextView userBio;

    @BindView(R.id.userName)
    LabelValueView userName;

    @BindView(R.id.userCompany)
    LabelValueView userCompany;

    @BindView(R.id.userEmail)
    LabelValueView userEmail;

    @BindView(R.id.userLocation)
    LabelValueView userLocation;

    @BindView(R.id.userCreatedAt)
    LabelValueView userCreatedAt;

    @BindView(R.id.publicReposCount)
    LabelValueView publicReposCount;

    @BindView(R.id.numberOfFollowers)
    LabelValueView numberOfFollowers;

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, UserDetailsActivity.class);
        intent.putExtra(EXTRA_USER, user);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        setupBackIcon(toolbar);

        if (getIntent() != null && getIntent().getParcelableExtra(EXTRA_USER) != null) {
            presenter.init(getIntent().getParcelableExtra(EXTRA_USER));
        } else {
            throw new IllegalArgumentException(
                    "User details screen MUST be provided with User as an extra in the Intent with \"userLogin\" key!");
        }
    }

    @Override
    public void showPreview(UserPreviewUiModel model) {
        toolbar.setTitle(model.login());
        ImageUtils.displayCircleCroppedImage(userAvatar, model.avatarUrl());
    }

    @Override
    public void showDetails(UserDetailsUiModel model) {
        toolbar.setTitle(model.login());
        userBio.setText(model.bio());
        userBio.setVisibility(model.bio() != null ? View.VISIBLE : View.GONE);
        setupNullableDetailItem(userName, model.name());
        setupNullableDetailItem(userCompany, model.company());
        setupNullableDetailItem(userEmail, model.email());
        setupNullableDetailItem(userLocation, model.location());
        setupNullableDetailItem(userCreatedAt, model.createdAt());
        setupNullableDetailItem(publicReposCount, model.publicReposCount());
        setupNullableDetailItem(numberOfFollowers, model.numberOfFollowers());
    }

    @OnClick(R.id.detailsButton)
    void onDetailsButtonClicked() {
        presenter.onDetailsButtonClicked();
    }

    @Override
    public void openUserWebPage(String htmlUrl) {
        IntentUtils.openWebPageInExternalBrowser(this, htmlUrl);
    }

    @Override
    protected void injectDependencies(@NonNull AppComponent appComponent) {
        appComponent.plus(new UserDetailsModule(this)).inject(this);
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
