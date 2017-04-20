package markusi.githubapp.data.models.repository;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class RepositoriesApiResponse {

    public static TypeAdapter<RepositoriesApiResponse> typeAdapter(Gson gson) {
        return new AutoValue_RepositoriesApiResponse.GsonTypeAdapter(gson);
    }

    @SerializedName("items")
    public abstract List<Repository> repositories();
}
