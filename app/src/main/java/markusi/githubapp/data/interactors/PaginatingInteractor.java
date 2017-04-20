package markusi.githubapp.data.interactors;

import android.support.annotation.WorkerThread;

public interface PaginatingInteractor<RequestType, ResponseType> extends Interactor<RequestType, ResponseType> {

    @WorkerThread
    ResponseType loadNextPage();
}
