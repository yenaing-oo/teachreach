package comp3350.teachreach.presentation;

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
import comp3350.teachreach.presentation.models.TutorModel;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.MyViewHolder> {
    Context context;
    ArrayList<TutorModel> tutorModels;

    public SearchRecyclerViewAdapter(Context context, ArrayList<TutorModel> tutorModels) {
        this.context = context;
        this.tutorModels = tutorModels;
    }

    @NonNull
    @Override
    public SearchRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_result_recycler_view_row, parent, false);

        return new SearchRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(R.drawable.user_icon);
        holder.tvUserName.setText(tutorModels.get(position).getName());
        holder.tvRating.setText(tutorModels.get(position).getRating());
        holder.tvHourlyRate.setText(tutorModels.get(position).getHourlyRate());
    }

    @Override
    public int getItemCount() {
        return tutorModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tvUserName, tvRating, tvHourlyRate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.tutorImage);
            tvUserName = itemView.findViewById(R.id.tutorName);
            tvRating = itemView.findViewById(R.id.tutorRating);
            tvHourlyRate = itemView.findViewById(R.id.tutorHourlyRate);
        }
    }
}
