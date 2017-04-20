package markusi.githubapp.ui.repositories;

import com.google.auto.value.AutoValue;

import android.os.Parcelable;

import markusi.githubapp.data.models.user.User;

@AutoValue
public abstract class RepositoryListItem implements Parcelable {

    public static RepositoryListItem create(
            int id,
            String name,
            User user,
            int watchersCount,
            int forksCount,
            int issuesCount) {
        return new AutoValue_RepositoryListItem(id, name, user, watchersCount, forksCount, issuesCount);
    }

    public abstract int id();

    public abstract String name();

    public abstract User user();

    public abstract int watchersCount();

    public abstract int forksCount();

    public abstract int issuesCount();
}
