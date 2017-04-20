package markusi.githubapp.ui.shared;

import android.content.res.Resources;

import javax.inject.Inject;

import markusi.githubapp.R;
import markusi.githubapp.ui.BaseMvp;

public class ErrorHandlerDelegate implements BaseMvp.ErrorListener {

    private final Resources resources;

    private final BaseMvp.View view;

    @Inject
    public ErrorHandlerDelegate(BaseMvp.View view, Resources resources) {
        this.resources = resources;
        this.view = view;
    }

    @Override
    public void onError(String errorMessage) {
        view.hideProgress();
        view.showError(errorMessage);
    }

    @Override
    public void onUnknownProblem() {
        onError(resources.getString(R.string.error_unknown_problem));
    }

    @Override
    public void onNetworkProblem() {
        onError(resources.getString(R.string.error_network_problem));
    }
}
