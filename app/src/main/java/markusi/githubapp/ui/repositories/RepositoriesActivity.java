package markusi.githubapp.ui.repositories;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;
import com.paginate.Paginate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import markusi.githubapp.R;
import markusi.githubapp.data.models.repository.RepositorySortType;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.di.AppComponent;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.ui.repositories.di.RepositoriesModule;
import markusi.githubapp.ui.repository_details.RepositoryDetailsActivity;
import markusi.githubapp.ui.shared.BaseActivity;
import markusi.githubapp.ui.shared.EmptyPlaceholderView;
import markusi.githubapp.ui.user_details.UserDetailsActivity;
import markusi.githubapp.utils.StringUtils;
import timber.log.Timber;

public class RepositoriesActivity extends BaseActivity implements RepositoriesMvp.View, RepositoriesAdapter.OnRepositoryClickListener,
        Paginate.Callbacks {

    private static final int LOADING_TRIGGER_THRESHOLD = 5;

    @Inject
    RepositoriesMvp.Presenter presenter;

    @BindView(R.id.editText)
    EditText editText;

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptyPlaceholderView)
    EmptyPlaceholderView emptyPlaceholderView;

    @BindInt(R.integer.debounce_time_ms)
    int debounceTimeMs;

    private RepositoriesAdapter repositoriesAdapter;

    private Disposable disposable;

    private boolean isLoadingNextPage;

    private boolean hasLoadedAllItems;

    private Paginate paginate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUi();
    }

    private void initUi() {
        setContentView(R.layout.activity_repositories);
        ButterKnife.bind(this);

        // setup spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, R.layout.item_spinner);
        for (RepositorySortType repositorySortType : RepositorySortType.values()) {
            adapter.add(getString(repositorySortType.getStringResourceId()));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!StringUtils.isEmpty(editText.getText())) {
                    onSearchTermChanged(editText.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //ignore this
            }
        });

        // setup empty placeholder view
        emptyPlaceholderView.setState(EmptyPlaceholderView.State.INITIAL);

        // setup the list and adapter
        repositoriesAdapter = new RepositoriesAdapter();
        repositoriesAdapter.setOnRepositoryClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(repositoriesAdapter);

        // setup debounce filter on user input
        disposable = RxTextView.textChangeEvents(editText)
                .debounce(debounceTimeMs, TimeUnit.MILLISECONDS)
                .filter(changes -> !StringUtils.isEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<TextViewTextChangeEvent>() {
                    @Override
                    public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                        onSearchTermChanged(textViewTextChangeEvent.text());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // setup click on search icon (action defined in activity's content view)
        editText.setOnEditorActionListener((textView, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                onSearchTermChanged(editText.getText());
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    public void showItems(List<RepositoryListItem> items) {
        if (paginate != null) {
            paginate.unbind();
        }
        hasLoadedAllItems = false;
        isLoadingNextPage = false;
        repositoriesAdapter.setItems(items);
        recyclerView.setVisibility(View.VISIBLE);
        emptyPlaceholderView.setVisibility(View.INVISIBLE);
        paginate = Paginate.with(recyclerView, this)
                .setLoadingTriggerThreshold(LOADING_TRIGGER_THRESHOLD)
                .addLoadingListItem(false)
                .build();
    }

    @Override
    public void showMoreItems(List<RepositoryListItem> items) {
        isLoadingNextPage = false;
        repositoriesAdapter.appendItems(items);
    }

    @Override
    public void onNoSearchResults() {
        isLoadingNextPage = false;
        repositoriesAdapter.clearItems();
        recyclerView.setVisibility(View.INVISIBLE);
        emptyPlaceholderView.setVisibility(View.VISIBLE);
        emptyPlaceholderView.setState(EmptyPlaceholderView.State.NO_RESULTS);
    }

    @Override
    public void onAllItemsLoaded() {
        isLoadingNextPage = false;
        hasLoadedAllItems = true;
    }

    @Override
    public void onRepositoryClicked(View view, RepositoryListItem item) {
        ActivityOptionsCompat options = createUserAvatarTransition(view);
        startActivity(RepositoryDetailsActivity.createIntent(this, item), options.toBundle());
    }

    @Override
    public void onUserAvatarClicked(View view, User user) {
        ActivityOptionsCompat options = createUserAvatarTransition(view);
        startActivity(UserDetailsActivity.createIntent(this, user), options.toBundle());
    }

    @Override
    public void showLimitWarning(String textToDisplay) {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(R.string.app_name)
                .setMessage(textToDisplay)
                .setPositiveButton(android.R.string.ok, null)
                .setCancelable(false)
                .show();
    }

    @Override
    protected void injectDependencies(@NonNull AppComponent appComponent) {
        appComponent.plus(new RepositoriesModule(this)).inject(this);
    }

    @Override
    protected BaseMvp.Presenter providePresenter() {
        return presenter;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoadMore() {
        presenter.loadNext();
        isLoadingNextPage = true;
    }

    @Override
    public boolean isLoading() {
        return isLoadingNextPage;
    }

    @Override
    public boolean hasLoadedAllItems() {
        return hasLoadedAllItems;
    }

    @NonNull
    private ActivityOptionsCompat createUserAvatarTransition(View view) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, ButterKnife.findById(view, R.id.userAvatar),
                getString(R.string.transition_name_user_avatar));
    }

    private void onSearchTermChanged(CharSequence charSequence) {
        presenter.onSearchTermChanged(
                charSequence.toString().trim(),
                RepositorySortType.values()[spinner.getSelectedItemPosition()]);
        isLoadingNextPage = false;
    }
}
