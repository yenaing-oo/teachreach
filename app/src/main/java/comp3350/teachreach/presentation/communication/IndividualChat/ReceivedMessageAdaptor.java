//package comp3350.teachreach.presentation.communication.IndividualChat;
//
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.sql.Timestamp;
//import java.util.Map;
//
//import comp3350.teachreach.databinding.FragmentOtherGuyMessageBinding;
//import comp3350.teachreach.databinding.SingleMessageBinding;
//import comp3350.teachreach.logic.communication.MessageHandler;
//import comp3350.teachreach.logic.interfaces.IMessageHandler;
//
//public class ReceivedMessageAdaptor extends RecyclerView.Adapter<ReceivedMessageAdaptor.ReceivedMessageViewHolder> {
//
//    //Map<Timestamp,String> messages
//
//
//    private Map<Timestamp,String>  sentmessages;
//
//    public ReceivedMessageAdaptor(Map<Timestamp,String> messages){
//        this.sentmessages = messages;
//    }
//
//    @NonNull
//    @Override
//    public ReceivedMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
//        FragmentOtherGuyMessageBinding otherGuyMessageBinding = FragmentOtherGuyMessageBinding.inflate(
//                LayoutInflater.from(parent.getContext()),parent,false);
//
//        return new ReceivedMessageAdaptor.ReceivedMessageViewHolder(otherGuyMessageBinding);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ReceivedMessageViewHolder holder, int position) {
//    Map.Entry<Timestamp, String> messageEntry = getMessageEntry(position);
//        holder.setMessageData(messageEntry.getKey(), messageEntry.getValue());
//    }
//
//
//
//    private Map.Entry<Timestamp, String> getMessageEntry(int position) {
//        int index = 0;
//        for (Map.Entry<Timestamp, String> entry : sentmessages.entrySet()) {
//            if (index == position) {
//                return entry;
//            }
//            index++;
//        }
//        throw new IndexOutOfBoundsException("Invalid position: " + position);
//    }
//    @Override
//    public int getItemCount() {
//        return sentmessages.size();
//    }
//
//
//    class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
//
//        private FragmentOtherGuyMessageBinding binding;
//        private IMessageHandler messageHandler;
//        ReceivedMessageViewHolder(FragmentOtherGuyMessageBinding otherGuyMessageBinding){
//            super(otherGuyMessageBinding.getRoot());
//            binding = otherGuyMessageBinding;
//            messageHandler = new MessageHandler();
//        }
//
//        void setMessageData(Timestamp time,String receivedMessage){
//            binding.otherGuyRealMessage.setText(receivedMessage);
//            int hour = (int) messageHandler.timeStampConverter(time).get("Hour");
//            binding.otherGuyTimeHour.setText(hour);
//        }
//    }
//
//
//}
