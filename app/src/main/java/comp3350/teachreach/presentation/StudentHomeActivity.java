package comp3350.teachreach.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationBarView;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;
import comp3350.teachreach.logic.DAOs.AccessStudents;

public class StudentHomeActivity extends AppCompatActivity
{
    private static final int               BACK_DELAY = 2000;
    private              long              backPressedTime;
    private              NavigationBarView navigationMenu;
    private              NavController     navController;
    private              TRViewModel       vm;

    private OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(TRViewModel.class);
        setContentView(R.layout.activity_navigation_student);
        navigationMenu = findViewById(R.id.navigation_menu);
        NavHostFragment navHostFragment
                =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment_student);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        int accountId = getIntent().getIntExtra("ACCOUNT_ID", -1);
        int studentId = getIntent().getIntExtra("STUDENT_ID", -1);
        vm.setAccount(Server
                              .getAccountDataAccess()
                              .getAccounts()
                              .get(accountId));
        AccessStudents accessStudents = new AccessStudents();
        vm.setStudent(accessStudents.getStudentByAccountID(accountId));
        assert vm.getStudent() != null;
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