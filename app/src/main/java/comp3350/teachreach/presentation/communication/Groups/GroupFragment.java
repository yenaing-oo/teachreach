package comp3350.teachreach.presentation.communication.Groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentGroupBinding;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.DAOs.AccessStudents;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.objects.interfaces.IStudent;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.communication.IndividualChat.MessageModel;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class GroupFragment extends Fragment implements ISelectAccountListener
{

    //LiveData<IAccount> accountLiveData;
    List<IAccount>     contactAccounts;
    private IMessageHandler      messageHandler;
    private TRViewModel          vm;

    private MessageModel mm;
    private FragmentGroupBinding binding;
    private List<IAccount>       users;
    private Fragment             chatGroupView;

    private int          accountID;
    private RecyclerView recyclerView;
    private GroupModel gm;

    public GroupFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        messageHandler  = new MessageHandler();
        vm              = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
        mm              = new ViewModelProvider(requireActivity()).get(
                MessageModel.class);
        gm              = new ViewModelProvider(requireActivity()).get(
            GroupModel.class);
        contactAccounts = messageHandler.retrieveAllChatAccountsByAccountID(
                Objects
                        .requireNonNull(vm.getAccount().getValue())
                        .getAccountID());


//                LiveData<IAccount> accountLiveData = vm.getAccount();
//                accountLiveData.observe(this, account -> {
//                    // Extract the int value from the IAccount object
//                    accountID = account.getAccountID();});
//                users = messageHandler.retrieveAllChatAccountsByAccountID
//                (accountID);
//        users = gm.getContactList().getValue();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding      = FragmentGroupBinding.inflate(inflater, container, false);
        recyclerView = binding.chatsRecycleViewFragment;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(recyclerView);

                gm.getContactList().observe(getViewLifecycleOwner(), new
                Observer<List<IAccount>>() {
                    @Override
                    public void onChanged(List<IAccount> userList) {
                        // Update the RecyclerView adapter with the new
                        //list of users
                        contactAccounts = gm.getContactList().getValue();
                        setUpRecyclerView(recyclerView);
                    }
                });
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        contactAccounts = //gm.getContactList().getValue();
                messageHandler.retrieveAllChatAccountsByAccountID(
                        Objects
                                .requireNonNull(vm.getAccount().getValue())
                                .getAccountID());

        //        vm.setUsers(contactAccounts);
        UsersAdapter usersAdapter = new UsersAdapter(contactAccounts,this, vm, gm );
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onItemClicked(IAccount account) {
        int tutorID, studentID;
        assert(account != null);
        assert (account.getAccountID()>0);
        // Step 1: Get the Account ID

        if (Boolean.TRUE.equals(vm.getIsTutor().getValue())) {
            tutorID = vm.getTutor().getValue().getTutorID();
            AccessStudents accessStudents = new AccessStudents();
            IStudent student = accessStudents.getStudentByAccountID(account.getAccountID());
            studentID = student.getStudentID();
            mm.setOtherUser(account);
            assert (studentID>0);

            assert (tutorID>0);

        } else {
            studentID = vm.getStudent().getValue().getStudentID();
            AccessTutors accessTutors = new AccessTutors();
            ITutor tutor = accessTutors.getTutorByAccountID(account.getAccountID());
            tutorID = tutor.getTutorID();
            mm.setOtherUser(account);
            assert (studentID>0);

            assert (tutorID>0);
        }

        //account.getAccountID();


        // Step 2: Retrieve Group ID using the Account ID
        int groupID = messageHandler.searchGroupByIDs(studentID, tutorID);
        assert(groupID>0);
        // Step 3: Retrieve Message History using the Group ID
        List<IMessage> messageHistory = messageHandler.retrieveAllMessageByGroupID(groupID);
        //Map<Integer, Map<Timestamp, String>> messageHistoryV1 = messageHandler.chatHistoryOfGroupV1(groupID);
        mm.setGroupID(groupID);
        //mm.setMessageByID(messageHistoryV1);
        mm.setMessageList(messageHistory);

        // Step 4: Pass necessary data to Individual Chat Fragment
        //Bundle args = new Bundle();
        //args.putInt("groupID", groupID);
        //args.putSerializable("messages", (Serializable) messageHistory);
//        NavController navController = NavHostFragment.findNavController(binding.getRoot());
//        navController.navigate(R.id.actionToIndividualChatFragment);
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToIndividualChatFragment);
//        NavHostFragment
//                .findNavController(requireParentFragment())
//                .navigate(R.id.actionToIndividualChatFragment);
        //Navigation.findNavController(requireView()).navigate(R.id.actionToIndividualChatFragment);//,args);
    }
        // Navigate to the Individual Chat Fragment
//        FragmentManager fm = getChildFragmentManager();
//        NavHostFragment navHostFragment = (NavHostFragment) binding.;
//        NavController nc = navHostFragment.getNavController();
//        nc.navigate(R.id.actionToIndividualChatFragment);
//
//        NavHostFragment.findNavController(this).navigate(action);
        //Toast.makeText(getContext(), account.getAccountEmail(), Toast.LENGTH_SHORT).show();
//        Bundle args = new Bundle();
//        bundle.putSerializable("account", (Serializable) account);
//        NavHostFragment.findNavController(this)
//                .navigate(R.id.actionToIndividualChatFragment, bundle);
//        args.putInt("accountId", account.getAccountID());
//
//        // Navigate to the IndividualChatFragment
//        NavDirections action = GroupFragmentDirections
//                .actionGroupFragmentToIndividualChatFragment()
//                .setAccountId(account.getAccountID());
//
//        NavHostFragment.findNavController(this).navigate(action);


   // }

//    public void onAccountClick(IAccount account){
//        Bundle args = new Bundle();
////        bundle.putSerializable("account", (Serializable) account);
////        NavHostFragment.findNavController(this)
////                .navigate(R.id.actionToIndividualChatFragment, bundle);
//        args.putInt("accountId", account.getAccountID());
//
//        // Navigate to the IndividualChatFragment
//        NavDirections action = GroupFragmentDirections
//                .actionGroupFragmentToIndividualChatFragment()
//                .setAccountId(account.getAccountID());
//
//        NavHostFragment.findNavController(this).navigate(action);
//    }
}