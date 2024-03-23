package comp3350.teachreach.presentation.communication.Groups;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.ActivityGroupsBinding;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.profile.StudentProfileActivity;
import comp3350.teachreach.presentation.profile.TutorProfileViewFragment;
import comp3350.teachreach.presentation.search.SearchRecyclerViewAdapter;

public class GroupsActivity extends AppCompatActivity {

    private NavigationBarView navigationMenu;
    private IMessageHandler messageHandler;
    private List<IAccount> users;

    private ActivityGroupsBinding binding;
    private UsersAdapter usersAdapter;

    private int                       accountID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        accountID = getIntent().getIntExtra("ACCOUNT_ID", -1);
        setUpNavigationMenu();
        messageHandler = new MessageHandler();
        setUpGroups();
        RecyclerView recyclerView = binding.chatsRecycleView;
        setUpRecyclerView(recyclerView);

        //sersAdapter - new UsersAdapter();

    }

//    private void loadUserDetails(){
//        binding.
//    }
    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        usersAdapter = new UsersAdapter(
                users
                );
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setSelectedItemId(R.id.NavBarSearch);
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
                return true;
            } else if (itemId == R.id.NavBarSearch) {
                return true;
            } else if (itemId == R.id.NavBarProfile) {
                Intent intent = new Intent(this, BlankFragment.class);
                intent.putExtra("ACCOUNT_ID", accountID);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    public void setUpGroups(){
        users = messageHandler.retrieveAllChatAccountsByAccountID(accountID);
    }

//    @Override
//    public void onTutorItemClick(int position)
//    {
//        Intent intent = new Intent(this, TutorProfileViewFragment.class);
//        intent.putExtra("TUTOR_ID", tutors.get(position).getTutorID());
//        startActivity(intent);
//    }




}