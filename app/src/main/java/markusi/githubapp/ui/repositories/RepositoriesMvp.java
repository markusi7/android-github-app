package markusi.githubapp.ui.repositories;

import java.util.List;

import markusi.githubapp.data.models.repository.RepositorySortType;
import markusi.githubapp.ui.BaseMvp;

public interface RepositoriesMvp {

    interface View extends BaseMvp.View {

        void showItems(List<RepositoryListItem> items);

        void showMoreItems(List<RepositoryListItem> items);

        void onAllItemsLoaded();

        void onNoSearchResults();

        void showLimitWarning(String textToDisplay);
    }

    interface Presenter extends BaseMvp.Presenter {

        void onSearchTermChanged(String searchTerm, RepositorySortType repositorySortType);

        void loadNext();
    }
}
