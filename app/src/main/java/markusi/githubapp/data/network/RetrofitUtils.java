package markusi.githubapp.data.network;

import java.io.IOException;
import java.lang.annotation.Annotation;

import markusi.githubapp.GitHubApp;
import markusi.githubapp.data.models.api.ErrorBody;
import markusi.githubapp.ui.BaseMvp;
import markusi.githubapp.utils.StringUtils;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import timber.log.Timber;

public class RetrofitUtils {

    private RetrofitUtils() {
        throw new AssertionError();
    }

    public static void handleException(Throwable throwable, BaseMvp.ErrorListener errorListener) {
        try {
            if (throwable instanceof HttpException) {
                Retrofit retrofit = GitHubApp.getInstance().getAppComponent().retrofit();
                String errorMessage = extractErrorMessage((HttpException) throwable, retrofit);
                if (!StringUtils.isEmpty(errorMessage)) {
                    errorListener.onError(errorMessage);
                } else {
                    errorListener.onUnknownProblem();
                }
            } else if (throwable instanceof IOException) {
                errorListener.onNetworkProblem();
            } else {
                Timber.e(throwable);
                errorListener.onUnknownProblem();
            }
        } catch (Throwable anotherThrowable) {
            Timber.e(anotherThrowable);
            errorListener.onUnknownProblem();
        }
    }

    private static String extractErrorMessage(HttpException httpException, Retrofit retrofit) throws IOException {
        return getErrorBodyAs(httpException.response().errorBody(), retrofit, ErrorBody.class).message();
    }

    private static <ErrorModel> ErrorModel getErrorBodyAs(ResponseBody errorBody, Retrofit retrofit, Class<ErrorModel> type)
            throws IOException {
        Converter<ResponseBody, ErrorModel> converter = retrofit.responseBodyConverter(type, new Annotation[]{});
        return converter.convert(errorBody);
    }
}
