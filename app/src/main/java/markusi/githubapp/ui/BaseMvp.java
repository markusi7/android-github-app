package markusi.githubapp.ui;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public interface BaseMvp {

    interface View extends ErrorView {

        void showProgress();

        void hideProgress();
    }

    interface Presenter {

        void cancel();
    }

    interface ProgressView {

        void showProgress();

        void dismissProgress();

    }

    interface ErrorView {

        void showError(String message);

        void showError(String message, @Nullable DismissListener dismissListener);

        void showError(@StringRes int resourceId);

        void showError(@StringRes int resourceId, @Nullable DismissListener dismissListener);
    }

    interface ErrorListener {

        void onError(String errorMessage);

        void onUnknownProblem();

        void onNetworkProblem();
    }

    interface DismissListener {

        void onDismissed();
    }
}
