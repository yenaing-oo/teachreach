package comp3350.teachreach.presentation.communication.Groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import comp3350.teachreach.databinding.FragmentGroupBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class GroupFragment extends Fragment
{

    LiveData<IAccount> accountLiveData;
    List<IAccount>     contactAccounts;
    private IMessageHandler      messageHandler;
    private TRViewModel          vm;
    private FragmentGroupBinding binding;
    private List<IAccount>       users;
    private Fragment             chatGroupView;

    private int          accountID;
    private RecyclerView recyclerView;

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
        contactAccounts = messageHandler.retrieveAllChatAccountsByAccountID(
                Objects
                        .requireNonNull(vm.getAccount().getValue())
                        .getAccountID());
        //        LiveData<IAccount> accountLiveData = vm.getAccount();
        //        accountLiveData.observe(this, account -> {
        //            // Extract the int value from the IAccount object
        //            accountID = account.getAccountID();});
        //        users = messageHandler.retrieveAllChatAccountsByAccountID
        //        (accountID);
        //users = vm.getUsers();

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

        //        vm.getUsers().observe(getViewLifecycleOwner(), new
        //        Observer<List<IAccount>>() {
        //            @Override
        //            public void onChanged(List<IAccount> userList) {
        //                // Update the RecyclerView adapter with the new
        //                list of users
        //                UsersAdapter usersAdapter = new UsersAdapter
        //                (userList);
        //                recyclerView.setAdapter(usersAdapter);
        //            }
        //        });
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        contactAccounts = messageHandler.retrieveAllChatAccountsByAccountID(
                accountID);
        //        vm.setUsers(contactAccounts);
        UsersAdapter usersAdapter = new UsersAdapter(contactAccounts);
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}