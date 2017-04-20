package markusi.githubapp.data.models.api;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class ErrorBody {

    public static TypeAdapter<ErrorBody> typeAdapter(Gson gson) {
        return new AutoValue_ErrorBody.GsonTypeAdapter(gson);
    }

    @SerializedName("message")
    public abstract String message();
}
