package comp3350.teachreach.presentation.communication.IndividualChat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentIndividualChatBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.utils.TRViewModel;
//import comp3350.teachreach.presentation.TRViewModel;

public class IndividualChatFragment extends Fragment
{

    LiveData<IAccount> accountLiveData;
    private IMessageHandler messageHandler;
    private TRViewModel     vm;

    private MessageModel                  mm;
    private FragmentIndividualChatBinding binding;
    private List<IAccount> users;
    private Fragment       chatGroupView;
    private int          accountID;
    private RecyclerView recyclerView;
    private FullMessageAdaptor fullMessageAdaptor;
    //private Map<Integer, Map<Timestamp, String>> messages;

    public IndividualChatFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        messageHandler = new MessageHandler();
        vm             = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
        mm             = new ViewModelProvider(requireActivity()).get(
                MessageModel.class);
    }
        //        LiveData<IAccount> accountLiveData = vm.getAccount();
        //        accountLiveData.observe(this, account -> {
        //            // Extract the int value from the IAccount object
        //            accountID = account.getAccountID();});
        //        users = messageHandler.retrieveAllChatAccountsByAccountID
        //        (accountID);
        //users = vm.getUsers();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding      = FragmentIndividualChatBinding.inflate(inflater,
                                                             container,
                                                             false);
        recyclerView = binding.IndividualChatsRecycleViewFragment;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(recyclerView);
        setUpTopBar();
        //assert getArguments() != null;
        //IAccount account = (IAccount) getArguments().getSerializable("account",this);
        //vm.getMessages().observe(getViewLifecycleOwner(), new
        // Observer<List<IMessage>>() {

        LiveData<Map<Integer, Map<Timestamp, String>>> messagesLiveData
                = mm.getMessagesByID();
        messagesLiveData.observe(getViewLifecycleOwner(),
                                 new Observer<Map<Integer, Map<Timestamp,
                                         String>>>()
                                 {
                                     @Override
                                     public void onChanged(Map<Integer,
                                             Map<Timestamp, String>> messages)
                                     {
                                         // Update the RecyclerView adapter
                                         // with the new messages
                                         fullMessageAdaptor.setMessages(messages);
                                     }
                                 });
    }


        private void setUpTopBar()
        {
            MaterialToolbar materialToolbar = binding.topAppBar;
            materialToolbar.setTitle(mm.getOtherUser().getValue().getUserName());

            materialToolbar.setNavigationOnClickListener(view -> {
                NavController navController
                        = NavHostFragment.findNavController(this);
                navController.navigate(R.id.actionToGroupFragment);
            });

        }

    private void setUpRecyclerView(RecyclerView recyclerView) {

        //recyclerView = binding.IndividualChatsRecycleViewFragment;
        fullMessageAdaptor = new FullMessageAdaptor(getContext(), mm.getMessagesByID().getValue(), vm);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(fullMessageAdaptor);
    }
        //get IDs first
        //retrieve original message
        //split it into two (sender/receiver)
        //set two recycleView
        //        int accounID = vm.getAccount().getValue().getAccountID();
        //        int studentID,tutorID;
        //        if(Boolean.TRUE.equals(vm.getIsTutor().getValue())){
        //
        //        }
        //        SentMessageAdaptor messageAdaptor = new SentMessageAdaptor(
        //                vm.getMessages().getValue()
        //        );
        //        recyclerView.setAdapter(messageAdaptor);
        //        recyclerView.setLayoutManager(new LinearLayoutManager
        //        (getContext()));


}