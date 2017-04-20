package markusi.githubapp.data.models.user;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.ZonedDateTime;

import android.os.Parcelable;
import android.support.annotation.Nullable;

@AutoValue
public abstract class User implements Parcelable {

    public static TypeAdapter<User> typeAdapter(Gson gson) {
        return new $AutoValue_User.GsonTypeAdapter(gson);
    }

    @SerializedName("id")
    public abstract int id();

    @SerializedName("login")
    public abstract String login();

    @SerializedName("avatar_url")
    public abstract String avatarUrl();

    @SerializedName("html_url")
    public abstract String htmlUrl();

    @Nullable
    @SerializedName("bio")
    public abstract String bio();

    @Nullable
    @SerializedName("name")
    public abstract String name();

    @Nullable
    @SerializedName("company")
    public abstract String company();

    @Nullable
    @SerializedName("email")
    public abstract String email();

    @Nullable
    @SerializedName("location")
    public abstract String location();

    @Nullable
    @SerializedName("created_at")
    public abstract ZonedDateTime createdAt();

    @SerializedName("public_repos")
    public abstract int publicReposCount();

    @SerializedName("followersCount")
    public abstract int followersCount();
}
