package markusi.githubapp.data.models.repository;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.ZonedDateTime;

import android.support.annotation.Nullable;

import markusi.githubapp.data.models.user.User;

@AutoValue
public abstract class Repository {

    public static TypeAdapter<Repository> typeAdapter(Gson gson) {
        return new AutoValue_Repository.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract int id();

    @SerializedName("owner")
    public abstract User user();

    @SerializedName("name")
    public abstract String name();

    @SerializedName("html_url")
    public abstract String htmlUrl();

    @SerializedName("watchers_count")
    public abstract int watchersCount();

    @SerializedName("forks_count")
    public abstract int forksCount();

    @SerializedName("created_at")
    public abstract ZonedDateTime createdAt();

    @SerializedName("updated_at")
    public abstract ZonedDateTime updatedAt();

    @SerializedName("open_issues_count")
    public abstract int issuesCount();

    @Nullable
    @SerializedName("language")
    public abstract String language();

    @Nullable
    @SerializedName("description")
    public abstract String description();

    @SerializedName("default_branch")
    public abstract String defaultBranch();

}