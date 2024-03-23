package comp3350.teachreach.presentation.communication.IndividualChat;

import android.os.Bundle;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentIndividualChatBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.IMessage;
import comp3350.teachreach.presentation.TRViewModel;


public class IndividualChatFragment extends Fragment {


    public IndividualChatFragment() {
    }

    private IMessageHandler messageHandler;
    private TRViewModel vm;
    private FragmentIndividualChatBinding binding;

    LiveData<IAccount> accountLiveData;

    private List<IAccount> users;
    private Fragment              chatGroupView;

    private int accountID;
    private RecyclerView recyclerView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageHandler = new MessageHandler();
        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
//        LiveData<IAccount> accountLiveData = vm.getAccount();
//        accountLiveData.observe(this, account -> {
//            // Extract the int value from the IAccount object
//            accountID = account.getAccountID();});
//        users = messageHandler.retrieveAllChatAccountsByAccountID(accountID);
        //users = vm.getUsers();

    }

        @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentIndividualChatBinding.inflate(inflater, container, false);
        recyclerView = binding.IndividualChatsRecycleViewFragment;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView(recyclerView);

        vm.getMessages().observe(getViewLifecycleOwner(), new Observer<List<IMessage>>() {
            @Override
            public void onChanged(List<IMessage> messagesList) {
                // Update the RecyclerView adapter with the new list of users
                SendMessageAdaptor messageAdapter = new SendMessageAdaptor(messagesList);
                recyclerView.setAdapter(messageAdapter);
            }
        });
    }


    private void setUpTopBar(View v)
    {
        MaterialToolbar materialToolbar = v.findViewById(R.id.topAppBar);
        materialToolbar.setTitle(tutorAccount.getUserName());
        materialToolbar.setNavigationOnClickListener(listener);
        NavController navController
                = NavHostFragment.findNavController(this);
        navController.navigate(R.id.actionToStudentProfileSelfViewFragment);

    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {

        SendMessageAdaptor messageAdaptor = new SendMessageAdaptor(
                vm.getMessages().getValue()
        );
        recyclerView.setAdapter(messageAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}