package comp3350.teachreach.presentation.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.utils.TutorProfileFormatter;

public class SearchRecyclerViewAdapter
        extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder>
{

    private final ITutorRecyclerView recyclerViewInterface;
    Context      context;
    List<ITutor> tutorList;

    public SearchRecyclerViewAdapter(Context context,
                                     List<ITutor> tutorList,
                                     ITutorRecyclerView recyclerViewInterface)
    {
        this.context               = context;
        this.tutorList             = tutorList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_result_recycler_view_row,
                                     parent,
                                     false);

        return new SearchRecyclerViewAdapter.MyViewHolder(view,
                                                          recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(
            @NonNull SearchRecyclerViewAdapter.MyViewHolder holder,
            int position)
    {
        TutorProfileFormatter tutorProfileFormatter = new TutorProfileFormatter(
                new TutorProfileHandler(tutorList.get(position)));
        holder.imageView.setImageResource(R.drawable.user_icon);
        holder.tvUserName.setText(tutorProfileFormatter.getName());
        holder.tvRating.setText(tutorProfileFormatter.getRating());
        holder.tvHourlyRate.setText(tutorProfileFormatter.getHourlyRate());
    }

    @Override
    public int getItemCount()
    {
        return tutorList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView  tvUserName, tvRating, tvHourlyRate;

        public MyViewHolder(@NonNull View itemView,
                            ITutorRecyclerView recyclerViewInterface)
        {
            super(itemView);

            imageView    = itemView.findViewById(R.id.tutorImage);
            tvUserName   = itemView.findViewById(R.id.timeSlotText);
            tvRating     = itemView.findViewById(R.id.tutorRating);
            tvHourlyRate = itemView.findViewById(R.id.tutorHourlyRate);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onTutorItemClick(pos);
                    }
                }
            });
        }
    }
}
