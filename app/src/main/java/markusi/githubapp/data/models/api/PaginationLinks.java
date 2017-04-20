package markusi.githubapp.data.models.api;

import com.google.auto.value.AutoValue;

import android.support.annotation.Nullable;

@AutoValue
public abstract class PaginationLinks {

    public static PaginationLinks create(
            @Nullable String nextPageUrl,
            @Nullable String lastPageUrl,
            @Nullable String firstPageUrl,
            @Nullable String previousPageUrl) {
        return new AutoValue_PaginationLinks(nextPageUrl, lastPageUrl, firstPageUrl, previousPageUrl);
    }

    @Nullable
    public abstract String nextPageUrl();

    @Nullable
    public abstract String lastPageUrl();

    @Nullable
    public abstract String firstPageUrl();

    @Nullable
    public abstract String previousPageUrl();
}
