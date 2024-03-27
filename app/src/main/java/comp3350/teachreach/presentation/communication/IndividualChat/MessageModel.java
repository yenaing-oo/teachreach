package comp3350.teachreach.presentation.communication.IndividualChat;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;

public class MessageModel extends ViewModel {

    private final MutableLiveData<List<IMessage>> messageList = new MutableLiveData<>();

    public MutableLiveData<List<IMessage>> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<IMessage> messageList) {
        this.messageList.setValue(messageList);
    }

    private final MutableLiveData<Integer> groupID = new MutableLiveData<>();

    private final MutableLiveData<IAccount> otherUser = new MutableLiveData<>();

    public MutableLiveData<IAccount> getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(IAccount otherUser) {
        this.otherUser.setValue(otherUser);
    }

    public MutableLiveData<Integer> getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID.setValue(groupID);
    }


}
