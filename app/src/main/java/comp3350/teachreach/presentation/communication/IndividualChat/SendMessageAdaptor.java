package comp3350.teachreach.presentation.communication.IndividualChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import comp3350.teachreach.databinding.CardChatUserBinding;
import comp3350.teachreach.databinding.FragmentIndividualChatBinding;
import comp3350.teachreach.databinding.SingleMessageBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.communication.Groups.UsersAdapter;


public class SendMessageAdaptor extends RecyclerView.Adapter<UsersAdapter.SentMessageHolder> {

    private NavigationBarView navigationMenu;
    private IMessageHandler messageHandler;
    private List<IAccount> users;

    private  SingleMessageBinding binding;
    private RecyclerView recyclerView;


    private int   accountID;
    private TRViewModel vm;

    private List<IMessage> messages;
    public SendMessageAdaptor(List<IMessage> messages){
        this.messages = messages;
    }




    @NonNull
    @Override
    public SendMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
         SingleMessageBinding singleMessageBinding = SingleMessageBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false);

        return new SendMessageViewHolder(singleMessagBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SendMessageAdaptorr.UserViewHolder userViewHolder, int position) {
        userViewHolder.setUserData(users.get(position));

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }



    class SendMessageViewHolder extends RecyclerView.ViewHolder{
        SendMessageViewHolder(SingleMessageBinding singleMessageBinding){
            super(singleMessageBinding.getRoot());
            binding = singleMessageBinding;
        }

        void setUserData(IMessage sentMessage){
            binding.myText.setText(sentMessage.getMessage());
            int Date = (int) messageHandler.timeStampConverter(sentMessage.getTime()).get("Date");
            binding.textGchatTimestampMe.setText(Date);
        }
    }

}
