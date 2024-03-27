package comp3350.teachreach.presentation.session;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.objects.interfaces.ISession;

public class SessionRecyclerAdapter<T> extends
        RecyclerView.Adapter<SessionRecyclerAdapter.ViewHolder> {
    private static List<ISession>         sessionList;
    private static View.OnClickListener   listener;
    private        IUserProfileHandler<T> profileHandler;
    private        ITutorProfileHandler   tutorProfileHandler;

    public SessionRecyclerAdapter(IUserProfileHandler<T> profileHandler,
                                  ITutorProfileHandler tutorProfileHandler,
                                  List<ISession> sessions, boolean isTutor,
                                  View.OnClickListener listener) {
        SessionRecyclerAdapter.sessionList = sessions;
        SessionRecyclerAdapter.listener    = listener;
        this.profileHandler                = profileHandler;
        this.tutorProfileHandler           = tutorProfileHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.card_row_tutor, parent, false);
        return new SessionRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public static
    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView withWhom, startTime, endTime, duration, price, accepted;
        private final Button acceptedButton;

        public ViewHolder(View view) {
            super(view);
            withWhom       = view.requireViewById(R.id.sessionWithWhom);
            startTime      = view.requireViewById(R.id.sessionStartDate);
            endTime        = view.requireViewById(R.id.sessionEndDate);
            duration       = view.requireViewById(R.id.sessionDuration);
            price          = view.requireViewById(R.id.sessionPrice);
            accepted       = view.requireViewById(R.id.sessionStatusString);
            acceptedButton = view.requireViewById(R.id.sessionStatusButton);
        }

        public TextView getWithWhom() {
            return withWhom;
        }

        public TextView getStartTime() {
            return startTime;
        }

        public TextView getEndTime() {
            return endTime;
        }

        public TextView getDuration() {
            return duration;
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getAccepted() {
            return accepted;
        }

        public Button getAcceptedButton() {
            return acceptedButton;
        }
    }
}
