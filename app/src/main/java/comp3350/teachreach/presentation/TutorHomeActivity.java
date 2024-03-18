package comp3350.teachreach.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationBarView;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.profile.MyProfileBarFragment;
import comp3350.teachreach.presentation.profile.TutorProfileSelfViewFragment;

public class TutorHomeActivity extends AppCompatActivity
{
    private static final int  BACK_DELAY = 2000;
    private              long backPressedTime;

    private NavigationBarView navigationMenu;

    private FragmentManager fragmentManager;
    private Fragment        topBarFragment  = null;
    private Fragment        currentFragment = null;

    private OnBackPressedCallback onBackPressedCallback;

    private int accountID, tutorID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_tutor);
        navigationMenu  = findViewById(R.id.navigation_menu);
        accountID       = getIntent().getIntExtra("ACCOUNT_ID", -1);
        tutorID         = getIntent().getIntExtra("TUTOR_ID", -1);
        fragmentManager = getSupportFragmentManager();

        setUpNavigationMenu();
        setUpBackButtonHandler();
        setUpDefaultFragment(R.id.NavBarSearch);
    }

    private void setUpDefaultFragment(int resourceID)
    {
        navigationMenu.setSelectedItemId(resourceID);
        setNewFragment();
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
            } else if (itemId == R.id.NavBarRequests) {
            } else if (itemId == R.id.NavBarProfile) {
                topBarFragment  = MyProfileBarFragment.newInstance(accountID);
                currentFragment = TutorProfileSelfViewFragment.newInstance(
                        fragmentManager,
                        topBarFragment,
                        accountID,
                        tutorID);
            } else if (itemId == R.id.NavBarChats) {
            }
            setNewFragment();
            return true;
        });
    }

    private void setNewFragment()
    {
        FragmentTransaction newTransaction = fragmentManager.beginTransaction();
        if (topBarFragment != null) {
            newTransaction.replace(R.id.abTop, topBarFragment);
        }
        if (currentFragment != null) {
            newTransaction.replace(R.id.navFrameLayout, currentFragment);
        }
        newTransaction.commit();
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