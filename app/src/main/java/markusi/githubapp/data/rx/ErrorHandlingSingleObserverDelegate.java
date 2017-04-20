package markusi.githubapp.data.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import markusi.githubapp.data.network.RetrofitUtils;
import markusi.githubapp.ui.BaseMvp;

public abstract class ErrorHandlingSingleObserverDelegate<T> implements SingleObserver<T>, BaseMvp.ErrorListener {

    private final BaseMvp.ErrorListener errorListener;

    private final CompositeDisposable compositeDisposable;

    protected ErrorHandlingSingleObserverDelegate(@NonNull BaseMvp.ErrorListener errorListener,
            @Nullable CompositeDisposable compositeDisposable) {
        this.errorListener = errorListener;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (compositeDisposable != null) {
            compositeDisposable.add(d);
        }
    }

    /**
     * Override to handle error messages differently.
     */
    @Override
    public void onError(String errorMessage) {
        errorListener.onError(errorMessage);
    }

    /**
     * Override to handle unknown problems differently.
     */
    @Override
    public void onUnknownProblem() {
        errorListener.onUnknownProblem();
    }

    /**
     * Override to handle network problems differently.
     */
    @Override
    public void onNetworkProblem() {
        errorListener.onNetworkProblem();
    }

    /**
     * Override to handle errors differently.
     */
    @Override
    public void onError(Throwable e) {
        RetrofitUtils.handleException(e, this);
    }
}
