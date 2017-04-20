package markusi.githubapp.ui.shared;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import markusi.githubapp.ui.BaseMvp;

class ProgressDialogDelegate implements BaseMvp.ProgressView {

    private final Activity activity;

    private final ProgressDialog progressDialog;

    ProgressDialogDelegate(@NonNull Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
    }

    @Override
    public void showProgress() {
        if (!progressDialog.isShowing() && !activity.isFinishing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
