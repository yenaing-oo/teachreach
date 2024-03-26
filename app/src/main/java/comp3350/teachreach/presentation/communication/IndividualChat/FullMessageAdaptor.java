package comp3350.teachreach.presentation.communication.IndividualChat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentOtherGuyMessageBinding;
import comp3350.teachreach.databinding.SingleMessageBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.presentation.profile.tutor.TutorProfileViewModel;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class FullMessageAdaptor
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int VIEW_TYPE_SENT_MESSAGE     = 1;
    private static final int VIEW_TYPE_RECEIVED_MESSAGE = 2;

    private Context context;

    private Map<Integer, Map<Timestamp, String>> messages;

    //private int userID;

    private TRViewModel vm;
    private MessageModel mm;

    //private Map<Timestamp, String> sentMessage;

    //private Map<Timestamp, String> receivedMessage;

    private List<IMessage> messageList;

    public FullMessageAdaptor(Context context,
                                List<IMessage> messageList,
                              //Map<Integer, Map<Timestamp, String>> messages,
                              TRViewModel vm, MessageModel mm)
    {
        this.context  = context;
        this.messageList = messageList;
        this.vm = vm;
        this.mm = mm;
        //userID = vm.getAccount().getValue().getAccountID();
        //this.receivedMessage = new TreeMap<>();
        //this.sentMessage     = new TreeMap<>();
        extractMessages();
    }

    private void extractMessages()
    {
        messageList = mm.getMessageList().getValue();
        //find out current account ID
//        int currentUserID = getCurrentUserID();
//
//        //extract messages
//        for (Map.Entry<Integer, Map<Timestamp, String>> entry :
//                messages.entrySet()) {
//            int                    senderID    = entry.getKey();
//            Map<Timestamp, String> messageData = entry.getValue();
//
//            for (Map.Entry<Timestamp, String> messageEntry :
//                    messageData.entrySet()) {
//                //int receivedID = (senderID == currentUserID) ?
//                // getOtherUserID(senderID): currentUserID;
//                if (senderID == currentUserID) {
//                    sentMessage.put(messageEntry.getKey(),
//                                    messageEntry.getValue());
//                } else {
//                    receivedMessage.put(messageEntry.getKey(),
//                                        messageEntry.getValue());
//                }
//            }
//        }
    }

    public void setMessages()//Map<Integer, Map<Timestamp, String>> messages)
    {
        //this.messages = messages;
        extractMessages();
        notifyDataSetChanged();
    }

    private int getCurrentUserID() {
        return vm.getAccount().getValue().getAccountID();
    }

//        if (Boolean.TRUE.equals(vm.getIsTutor().getValue())) {
//            return vm.getAccount().getValue().getTutorID();
//        } else {
//            return vm.getStudent().getValue().getStudentID();
//        }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        if(viewType == VIEW_TYPE_SENT_MESSAGE){
            return new SendMessageViewHolder(SingleMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false));
        }else{
            return new ReceivedMessageViewHolder(FragmentOtherGuyMessageBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent,
                    false
            ));
        }
    }
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View           view;
//
//        switch (viewType) {
//            case VIEW_TYPE_SENT_MESSAGE:
//                view = inflater.inflate(R.layout.single_message,
//                        parent,
//                        false);
//                return new SendMessageViewHolder(
//                        SingleMessageBinding.bind(view));
//            case VIEW_TYPE_RECEIVED_MESSAGE:
//                view = inflater.inflate(R.layout.fragment_other_guy_message,
//                                        parent,
//                                        false);
//                return new ReceivedMessageViewHolder(
//                        FragmentOtherGuyMessageBinding.bind(view));
           // default:
               // throw new IllegalArgumentException("Invalid view type");
       // }
    //}

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position)
    {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_SENT_MESSAGE:
                ((SendMessageViewHolder) holder).setMessageData(
                        messageList.get(position));
                break;
            case VIEW_TYPE_RECEIVED_MESSAGE:
                ((ReceivedMessageViewHolder) holder).setMessageData(
                        messageList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        IMessageHandler messageHandler = new MessageHandler();

        return messageHandler.retrieveAllMessageByGroupID(mm.getGroupID().getValue()).size();
                //messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).getSenderID() == mm.getOtherUser().getValue().getAccountID()){//vm.getAccount().getValue().getAccountID()) {
            return VIEW_TYPE_RECEIVED_MESSAGE;
        } else {
            return VIEW_TYPE_SENT_MESSAGE;
        }
    }


        //Map<Integer,Map<Timestamp,String>> message = messages.get(position);
//        if (position < sentMessage.size()) {
//            return VIEW_TYPE_SENT_MESSAGE;
//        } else {
//            return VIEW_TYPE_RECEIVED_MESSAGE;
//        }
   // }

    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder
    {

        private FragmentOtherGuyMessageBinding binding;
        private IMessageHandler                messageHandler;

        ReceivedMessageViewHolder(FragmentOtherGuyMessageBinding otherGuyMessageBinding)
        {
            super(otherGuyMessageBinding.getRoot());
            binding        = otherGuyMessageBinding;
            messageHandler = new MessageHandler();
        }

        void setMessageData(IMessage receivedMessage //Map<Timestamp, String> receivedMessage,
                            )
        {
            binding.otherGuyNameView.setText(mm.getOtherUser().getValue().getUserName());
            binding.otherGuyRealMessageView.setText(receivedMessage.getMessage());
            binding.otherGuyTimeView.setText(
                    messageHandler.timeStampConverter(receivedMessage.getTime())
            );
            //binding.otherGuyTimeHour.setText(messageHandler.timeStampConverter(receivedMessage.getTime()));


            // Retrieve message data at the given position
//            Timestamp time    = (Timestamp) receivedMessage
//                    .keySet()
//                    .toArray()[position];
//            String    message = receivedMessage.get(time);
//
//            // Bind message data to views
//            binding.otherGuyRealMessage.setText(message);
//            int hour = (int) messageHandler
//                    .timeStampConverter(time)
//                    .get("Hour");
//            binding.otherGuyTimeHour.setText(String.valueOf(hour));
        }
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

        void setMessageData(IMessage sentMessage)//Map<Timestamp, String> sentMessage)//, int position)
        {
            binding.messageView.setText(sentMessage.getMessage());
            binding.timeTextView.setText
                    (messageHandler.timeStampConverter(sentMessage.getTime()));
//            // Retrieve message data at the given position
//            Timestamp time    = (Timestamp) sentMessage
//                    .keySet()
//                    .toArray()[position];
//            String    message = sentMessage.get(time);
//
//            // Bind message data to views
//            binding.myText.setText(message);
//            int date = (int) messageHandler
//                    .timeStampConverter(time)
//                    .get("Date");
//            binding.textGchatTimestampMe.setText(String.valueOf(date));
        }
    }
}
