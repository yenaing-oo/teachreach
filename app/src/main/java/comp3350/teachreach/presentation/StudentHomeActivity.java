package comp3350.teachreach.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.logic.communication.MessageHandler;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public class StudentHomeActivity extends AppCompatActivity
{
    private static final int  BACK_DELAY = 2000;
    private              long backPressedTime;

    private NavigationBarView navigationMenu;
    private NavController     navController;

    private TRViewModel vm;

    private OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_student);
        navigationMenu = findViewById(R.id.navigation_menu);
        NavHostFragment navHostFragment
                =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment_student);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        vm = new ViewModelProvider(this).get(TRViewModel.class);
        int accountId = getIntent().getIntExtra("ACCOUNT_ID", -1);
        int studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        vm.setAccount(Server
                              .getAccountDataAccess()
                              .getAccounts()
                              .get(accountId));
        vm.setStudent(Server
                              .getStudentDataAccess()
                              .getStudents()
                              .get(studentId));
        vm.setAccounts(new ArrayList<>(Server
                                               .getAccountDataAccess()
                                               .getAccounts()
                                               .values()));
        vm.setCourses(new ArrayList<>(Server
                                              .getCourseDataAccess()
                                              .getCourses()
                                              .values()));
        vm.setTutors(new ArrayList<>(Server
                                             .getTutorDataAccess()
                                             .getTutors()
                                             .values()));
        IMessageHandler messageHandler = new MessageHandler();
        //AtomicInteger accountID = new AtomicInteger();
        //vm.getAccount().observe(this, account->{ accountID.set(account.getAccountID());});
        List<IAccount> users = messageHandler.retrieveAllChatAccountsByAccountID(accountId);
        vm.setUsers(users);
        setUpNavigationMenu();
        setUpBackButtonHandler();
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setSelectedItemId(R.id.NavBarSearch);
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
            } else if (itemId == R.id.NavBarSearch) {
                navController.navigate(R.id.searchFragment);
            } else if (itemId == R.id.NavBarProfile) {
                navController.navigate(R.id.actionToStudentProfileSelfViewFragment);
            } else if (itemId == R.id.NavBarChats) {
                navController.navigate(R.id.actionToGroupFragment);// i need it work
            }
            return true;
        });
    }

    private void setUpBackButtonHandler()
    {
        onBackPressedCallback = new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                backIsPressed();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void backIsPressed()
    {
        if (backPressedTime + BACK_DELAY > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast
                    .makeText(this,
                              "Press back again to exit",
                              Toast.LENGTH_SHORT)
                    .show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}