package markusi.githubapp.data.network;

import io.reactivex.Single;
import markusi.githubapp.data.models.repository.RepositoriesApiResponse;
import markusi.githubapp.data.models.repository.Repository;
import markusi.githubapp.data.models.user.User;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {

    @GET("/repos/{user}/{repo}")
    Single<Repository> getRepositoryDetails(@Path("user") String owner, @Path("repo") String repo);

    @GET("/search/repositories")
    Single<Response<RepositoriesApiResponse>> getRepositories(@Query("q") String searchTerm, @Query("sort") String sortType);

    @GET
    Single<Response<RepositoriesApiResponse>> getRepositoriesNextPage(@Url String nextPageUrl);

    @GET("/users/{user}")
    Single<User> getUserDetails(@Path("user") String user);
}
