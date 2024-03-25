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
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.profile.TutorProfileViewModel;

public class TutorHomeActivity extends AppCompatActivity
{
    private static final int                   BACK_DELAY = 2000;
    private              TRViewModel           vm;
    private              long                  backPressedTime;
    private              NavigationBarView     navigationMenu;
    private              NavController         navController;
    private              OnBackPressedCallback onBackPressedCallback;

    private IAccount account;

    private ITutor tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(this).get(TRViewModel.class);

        setContentView(R.layout.activity_navigation_tutor);
        navigationMenu = findViewById(R.id.navigation_menu);
        NavHostFragment navHostFragment
                =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment_tutor);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        int accountID = getIntent().getIntExtra("ACCOUNT_ID", -1);
        int tutorID   = getIntent().getIntExtra("TUTOR_ID", -1);
        account = Server.getAccountDataAccess().getAccounts().get(accountID);
        tutor   = Server.getTutorDataAccess().getTutors().get(tutorID);

        vm.setAccount(account);
        vm.setTutor(tutor);
        vm.setIsTutor();
        TutorProfileViewModel profileViewModel
                = new ViewModelProvider(this).get(TutorProfileViewModel.class);
        profileViewModel.setTutor(tutor);
        profileViewModel.setTutorAccount(account);
        setUpNavigationMenu();
        setUpBackButtonHandler();
    }

    private void setUpNavigationMenu()
    {

        navigationMenu.setSelectedItemId(R.id.NavBarProfile);
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
            } else if (itemId == R.id.NavBarRequests) {
            } else if (itemId == R.id.NavBarProfile) {
                navController.navigate(R.id.tutorProfileSelfViewFragment);
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