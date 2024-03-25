package comp3350.teachreach.presentation.communication.Groups;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class GroupModel extends ViewModel {
    private final MutableLiveData<List<Integer>> groupIDs
            = new MutableLiveData<>();

    private final MutableLiveData<Integer> groupID = new MutableLiveData<>();

    private final  MutableLiveData<Integer> otherAccountID = new MutableLiveData<>();

    public MutableLiveData<Integer> getOtherAccountID() {
        return otherAccountID;
    }

    public void setOtherAccountID(MutableLiveData<Integer> otherAccountID ) {
        this.otherAccountID.setValue(otherAccountID.getValue());
    }

    public MutableLiveData<Integer> getGroupID() {
        return groupID;
    }

    public void setGroupIDs(List<Integer> groups){
        this.groupIDs.setValue(groups);
    }


    public MutableLiveData<List<Integer>> getGroupIDs() {
        return groupIDs;
    }


    public void setGroupID(Integer group){
        this.groupID.setValue(group);
    }

}
