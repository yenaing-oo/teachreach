package comp3350.teachreach.presentation.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;

public class SearchTutorRecyclerAdapter
        extends RecyclerView.Adapter<SearchTutorRecyclerAdapter.ViewHolder>
{
    private static List<ITutor>          tutorList;
    private static IOnTutorClickListener listener;

    public SearchTutorRecyclerAdapter(List<ITutor> tutors,
                                      IOnTutorClickListener listener)
    {
        SearchTutorRecyclerAdapter.tutorList = tutors;
        SearchTutorRecyclerAdapter.listener  = listener;
    }

    @NonNull
    @Override
    public SearchTutorRecyclerAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_row_tutor, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SearchTutorRecyclerAdapter.ViewHolder holder, int position)
    {
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        ITutor              tutor = tutorList.get(position);
        TutorProfileHandler tph   = new TutorProfileHandler(tutor);

        CardView tutorCard = holder.getCardView();
        TextView tvName    = holder.getTvName();
        TextView tvMajor   = holder.getTvMajor();
        TextView tvRatings = holder.getTvRatings();
        TextView tvPrice   = holder.getTvHourlyRate();

        tutorCard.setOnClickListener(v -> listener.onTutorClick(tutor));
        tvName.setText(tph.getUserName());
        tvMajor.setText(tph.getUserMajor());
        tvRatings.setText(String.format(Locale.getDefault(),
                                        "%.2f",
                                        tph.getAvgReview()));
        tvPrice.setText(String.format(Locale.getDefault(),
                                      "%.2f",
                                      tph.getHourlyRate()));
    }

    @Override
    public int getItemCount()
    {
        return tutorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final CardView cardView;
        private final TextView tvName, tvMajor, tvRatings, tvHourlyRate;

        public ViewHolder(View view)
        {
            super(view);
            cardView     = view.findViewById(R.id.cardTutor);
            tvName       = view.findViewById(R.id.tvNameField);
            tvMajor      = view.findViewById(R.id.tvMajorField);
            tvRatings    = view.findViewById(R.id.tvRatingsField);
            tvHourlyRate = view.findViewById(R.id.tvHourlyRateField);
        }

        public CardView getCardView()
        {
            return cardView;
        }

        public TextView getTvName()
        {
            return tvName;
        }

        public TextView getTvMajor()
        {
            return tvMajor;
        }

        public TextView getTvRatings()
        {
            return tvRatings;
        }

        public TextView getTvHourlyRate()
        {
            return tvHourlyRate;
        }
    }
}
