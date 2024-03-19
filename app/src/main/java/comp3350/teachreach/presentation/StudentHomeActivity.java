package comp3350.teachreach.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.application.Server;

public class StudentHomeActivity extends AppCompatActivity
{
    private static final int  BACK_DELAY = 2000;
    private              long backPressedTime;

    private NavigationBarView navigationMenu;
    private NavController     navController;

    private TRViewModel vm;

    private FragmentManager fragmentManager;
    private Fragment        currentFragment = null;

    private OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_student);
        navigationMenu  = findViewById(R.id.navigation_menu);
        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment
                =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment_student);
        navController = navHostFragment.getNavController();
        vm            = new ViewModelProvider(this).get(TRViewModel.class);
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
        setUpNavigationMenu();
        setUpBackButtonHandler();
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
            } else if (itemId == R.id.NavBarSearch) {
                navController.navigate(R.id.searchFragment);
            } else if (itemId == R.id.NavBarProfile) {
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