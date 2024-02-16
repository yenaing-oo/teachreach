package comp3350.teachreach.presentation.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.objects.Tutor;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Tutor> tutorList;
    private TutorFormatter tutorFormatter;

    public SearchRecyclerViewAdapter(Context context, ArrayList<Tutor> tutorList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.tutorList = tutorList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_result_recycler_view_row, parent, false);

        return new SearchRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.MyViewHolder holder, int position) {
        tutorFormatter = new TutorFormatter(tutorList.get(position));
        holder.imageView.setImageResource(R.drawable.user_icon);
        holder.tvUserName.setText(tutorFormatter.getName());
        holder.tvRating.setText(tutorFormatter.getRating());
        holder.tvHourlyRate.setText(tutorFormatter.getHourlyRate());
    }

    @Override
    public int getItemCount() {
        return tutorList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvUserName, tvRating, tvHourlyRate;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.tutorImage);
            tvUserName = itemView.findViewById(R.id.tutorName);
            tvRating = itemView.findViewById(R.id.tutorRating);
            tvHourlyRate = itemView.findViewById(R.id.tutorHourlyRate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onTutorItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
