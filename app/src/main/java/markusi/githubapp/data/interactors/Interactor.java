package markusi.githubapp.data.interactors;

import android.support.annotation.WorkerThread;

public interface Interactor<RequestType, ResponseType> {

    @WorkerThread
    ResponseType execute(RequestType requestParam);
}
