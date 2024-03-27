package comp3350.teachreach.presentation.session;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.common.util.concurrent.Callables;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comp3350.teachreach.logic.availability.TutorAvailabilityManager;
import comp3350.teachreach.logic.interfaces.ISessionHandler;
import comp3350.teachreach.logic.interfaces.ITutorAvailabilityManager;
import comp3350.teachreach.logic.session.SessionHandler;
import comp3350.teachreach.objects.interfaces.ISession;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;

public class SessionViewModel extends ViewModel {
    private final ExecutorService          threadPool = Executors.newCachedThreadPool();
    private final ListeningExecutorService service    = MoreExecutors.listeningDecorator(
            threadPool);

    private final ITutorAvailabilityManager availabilityManager = new TutorAvailabilityManager();
    private final ISessionHandler           sessionHandler      = new SessionHandler(
            availabilityManager);

    private final MutableLiveData<List<ISession>> sessionsBeingViewed = new MutableLiveData<>();

    private final MutableLiveData<String> err = new MutableLiveData<>(null);

    public void setSessionsStudent(@NonNull SessionType type, @NonNull IStudent s) {
        ListenableFuture<List<ISession>> futureList = Futures.submitAsync(
                Callables.asAsyncCallable(() -> {
                    switch (type) {
                        case pending -> {
                            return sessionHandler.getPendingSessions(s);
                        }
                        case accepted -> {
                            return sessionHandler.getAcceptedSessions(s);
                        }
                        case rejected -> {
                            return sessionHandler.getRejectedSessions(s);
                        }
                        default -> {
                            return sessionHandler.getSessions(s);
                        }
                    }
                }, service), service);
        waitForUpdate(futureList);
    }

    public void setSessionsTutor(@NonNull SessionType type, @NonNull ITutor t) {
        ListenableFuture<List<ISession>> futureList = Futures.submitAsync(
                Callables.asAsyncCallable(() -> {
                    switch (type) {
                        case pending -> {
                            return sessionHandler.getPendingSessions(t);
                        }
                        case accepted -> {
                            return sessionHandler.getAcceptedSessions(t);
                        }
                        case rejected -> {
                            return sessionHandler.getRejectedSessions(t);
                        }
                        default -> {
                            return sessionHandler.getSessions(t);
                        }
                    }
                }, service), service);
        waitForUpdate(futureList);
    }

    private void waitForUpdate(@NonNull ListenableFuture<List<ISession>> future) {
        Futures.addCallback(future, new FutureCallback<>() {
            @Override
            public void onSuccess(List<ISession> result) {
                sessionsBeingViewed.postValue(result);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                err.postValue(t.getMessage());
            }
        }, service);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        threadPool.shutdown();
        service.shutdown();
    }

    public enum SessionType {
        pending,
        accepted,
        rejected
    }
}
