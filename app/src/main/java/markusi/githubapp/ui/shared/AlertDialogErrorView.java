package markusi.githubapp.ui.shared;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import markusi.githubapp.R;
import markusi.githubapp.ui.BaseMvp;

class AlertDialogErrorView implements BaseMvp.ErrorView {

    private final Activity activity;

    AlertDialogErrorView(@NonNull Activity activity) {
        this.activity = activity;
    }

    @Override
    public void showError(String message) {
        showError(message, null);
    }

    @Override
    public void showError(String message, @Nullable BaseMvp.DismissListener dismissListener) {
        if (!activity.isFinishing()) {
            new AlertDialog.Builder(activity)
                    .setTitle(R.string.oops)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setOnDismissListener(dialog -> {
                        if (dismissListener != null) {
                            dismissListener.onDismissed();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void showError(@StringRes int resourceId) {
        showError(resourceId, null);
    }

    @Override
    public void showError(@StringRes int resourceId, @Nullable BaseMvp.DismissListener dismissListener) {
        showError(activity.getString(resourceId), dismissListener);
    }
}
