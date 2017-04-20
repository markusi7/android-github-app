package markusi.githubapp.ui.shared;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import markusi.githubapp.GitHubApp;
import markusi.githubapp.R;
import markusi.githubapp.di.AppComponent;
import markusi.githubapp.ui.BaseMvp;

public abstract class BaseActivity extends AppCompatActivity implements BaseMvp.View {

    private BaseMvp.ProgressView progressView;

    private BaseMvp.ErrorView errorView;

    private BaseMvp.Presenter presenter;

    protected abstract void injectDependencies(@NonNull AppComponent appComponent);

    protected abstract BaseMvp.Presenter providePresenter();

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressView = new ProgressDialogDelegate(this);
        errorView = new AlertDialogErrorView(this);

        injectDependencies(GitHubApp.getInstance().getAppComponent());

        presenter = providePresenter();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.cancel();
    }

    protected void setupBackIcon(Toolbar toolbar) {
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showError(String message) {
        errorView.showError(message);
    }

    @Override
    public void showError(String message, @Nullable BaseMvp.DismissListener dismissListener) {
        errorView.showError(message, dismissListener);
    }

    @Override
    public void showError(@StringRes int resourceId) {
        errorView.showError(resourceId);
    }

    @Override
    public void showError(@StringRes int resourceId, @Nullable BaseMvp.DismissListener dismissListener) {
        errorView.showError(resourceId, dismissListener);
    }

    @Override
    public void showProgress() {
        hideKeyboard();
        progressView.showProgress();
    }

    @Override
    public void hideProgress() {
        progressView.dismissProgress();
    }

    protected void hideKeyboard() {
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            focusedView.clearFocus();
            InputMethodManager inputMethodManager = (InputMethodManager) focusedView.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }
}
