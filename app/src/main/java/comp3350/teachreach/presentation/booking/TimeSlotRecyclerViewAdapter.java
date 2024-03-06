package comp3350.teachreach.presentation.booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import comp3350.teachreach.R;

public
class TimeSlotRecyclerViewAdapter
        extends RecyclerView.Adapter<TimeSlotRecyclerViewAdapter.MyViewHolder>
{
    private final ITimeSlotRecyclerView recyclerViewInterface;
    private final Context               context;
    private final List<String>          timeSlotList;
    private       int                   selectedPosition
            = RecyclerView.NO_POSITION;

    public
    TimeSlotRecyclerViewAdapter(Context context,
                                List<String> timeSlotList,
                                ITimeSlotRecyclerView recyclerViewInterface)
    {
        this.context               = context;
        this.timeSlotList          = timeSlotList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public
    TimeSlotRecyclerViewAdapter.MyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.time_slot_recycler_view_row,
                                     parent,
                                     false);

        return new TimeSlotRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public
    void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.bind(position);
    }

    @Override
    public
    int getItemCount()
    {
        return timeSlotList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {

        TextView timeSlotTextView;
        CardView timeSlotCardView;

        MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            timeSlotTextView = itemView.findViewById(R.id.timeSlotText);
            timeSlotCardView = itemView.findViewById(R.id.timeSlotCard);
            itemView.setOnClickListener(this);
        }

        void bind(int position)
        {
            timeSlotTextView.setText(timeSlotList.get(position));
            // Update background color based on selection
            if (position == selectedPosition) {
                timeSlotCardView.setCardBackgroundColor(ContextCompat.getColor(
                        context,
                        R.color.TIME_CLOT_CARD_BACKGROUND_PRESSED));
            } else {
                timeSlotCardView.setCardBackgroundColor(ContextCompat.getColor(
                        context,
                        R.color.TIME_CLOT_CARD_BACKGROUND_DEFAULT));
            }
        }

        @Override
        public
        void onClick(View v)
        {
            // Update selected position
            int previousSelectedPosition = selectedPosition;
            selectedPosition = getAdapterPosition();
            // Update views
            notifyItemChanged(previousSelectedPosition);
            notifyItemChanged(selectedPosition);
            // Notify interface
            if (recyclerViewInterface != null &&
                selectedPosition != RecyclerView.NO_POSITION) {
                recyclerViewInterface.onTimeSlotItemClick(selectedPosition);
            }
        }
    }
}
