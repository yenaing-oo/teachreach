package comp3350.teachreach.presentation.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.teachreach.R;

public class TimeSlotRecyclerViewAdapter extends RecyclerView.Adapter<TimeSlotRecyclerViewAdapter.MyViewHolder> {
    private final ITimeSlotRecyclerView recyclerViewInterface;
    Context context;
    List<String> timeSlotList;

    public TimeSlotRecyclerViewAdapter(Context context,
                                       List<String> timeSlotList,
                                       ITimeSlotRecyclerView recyclerViewInterface) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public TimeSlotRecyclerViewAdapter(ITimeSlotRecyclerView recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public TimeSlotRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.time_slot_recycler_view_row, parent, false);

        return new TimeSlotRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.timeSlot.setText(timeSlotList.get(position));
    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView timeSlot;

        public MyViewHolder(@NonNull View itemView, ITimeSlotRecyclerView recyclerViewInterface) {
            super(itemView);

            timeSlot = itemView.findViewById(R.id.timeSlot);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onTimeSlotItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
