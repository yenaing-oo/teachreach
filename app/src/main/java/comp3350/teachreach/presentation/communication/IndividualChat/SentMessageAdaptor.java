package comp3350.teachreach.presentation.communication.IndividualChat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.databinding.SingleMessageBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class SentMessageAdaptor
        extends RecyclerView.Adapter<SentMessageAdaptor.SendMessageViewHolder>
{

    private NavigationBarView navigationMenu;
    private IMessageHandler   messageHandler;
    private List<IAccount>    users;

    private SingleMessageBinding binding;
    private RecyclerView         recyclerView;

    private int         accountID;
    private TRViewModel vm;

    private Map<Timestamp, String> sentmessages;

    public SentMessageAdaptor(Map<Timestamp, String> messages)
    {
        this.sentmessages = messages;
    }

    @NonNull
    @Override
    public SendMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                    int i)
    {
        SingleMessageBinding singleMessageBinding
                =
                SingleMessageBinding.inflate(LayoutInflater.from(parent.getContext()),
                                               parent,
                                               false);

        return new SendMessageViewHolder(singleMessageBinding);
    }

    //    @Override
    //    public void onBindViewHolder(@NonNull SendMessageViewHolder
    //    sendMessageViewHolder, int position) {
    //
    //       // Map.Entry<Timestamp, String> messageEntry = sentmessages.get
    //       (position);
    //        //sendMessageViewHolder.setMessageData(messageEntry.getKey(),
    //        messageEntry.getValue());
    //        Map.Entry<Timestamp, String> messageEntry = getMessageEntry
    //        (position);
    //        SendMessageViewHolder.setMessageData(messageEntry.getKey(),
    //        messageEntry.getValue();
    //    }
    @Override
    public void onBindViewHolder(@NonNull SendMessageViewHolder holder,
                                 int position)
    {
        Map.Entry<Timestamp, String> messageEntry = getMessageEntry(position);
        holder.setMessageData(messageEntry.getKey(), messageEntry.getValue());
    }

    @Override
    public int getItemCount()
    {
        return sentmessages.size();
    }

    private Map.Entry<Timestamp, String> getMessageEntry(int position)
    {
        int index = 0;
        for (Map.Entry<Timestamp, String> entry : sentmessages.entrySet()) {
            if (index == position) {
                return entry;
            }
            index++;
        }
        throw new IndexOutOfBoundsException("Invalid position: " + position);
    }

    class SendMessageViewHolder extends RecyclerView.ViewHolder
    {
        private SingleMessageBinding binding;
        private IMessageHandler      messageHandler;

        SendMessageViewHolder(SingleMessageBinding singleMessageBinding)
        {
            super(singleMessageBinding.getRoot());
            binding        = singleMessageBinding;
            messageHandler = new MessageHandler();
        }

        void setMessageData(Timestamp time, String sentMessage)
        {
            binding.myText.setText(sentMessage);
            int Date = (int) messageHandler
                    .timeStampConverter(time)
                    .get("Date");
            binding.textGchatTimestampMe.setText(Date);
        }
    }
}
