package comp3350.teachreach.presentation;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.search.SearchFragment;

public class StudentHomeActivity extends AppCompatActivity
{
    private static final int                   BACK_DELAY      = 2000;
    private              NavigationBarView     navigationMenu;
    private              OnBackPressedCallback onBackPressedCallback;
    private              FragmentManager       fragmentManager;
    private              int                   accountID;
    private              long                  backPressedTime;
    private              Fragment              currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        navigationMenu  = findViewById(R.id.navigation_menu);
        accountID       = getIntent().getIntExtra("ACCOUNT_ID", -1);
        fragmentManager = getSupportFragmentManager();

        setUpNavigationMenu();
        setUpBackButtonHandler();
        setUpDefaultFragment(R.id.NavBarSearch);
    }

    private void setUpDefaultFragment(int id)
    {
        currentFragment = SearchFragment.newInstance(accountID);
        navigationMenu.setSelectedItemId(id);
        setNewFragment();
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
            } else if (itemId == R.id.NavBarSearch) {
                currentFragment = SearchFragment.newInstance(accountID);
            } else if (itemId == R.id.NavBarProfile) {
            } else if (itemId == R.id.NavBarChats) {
            }
            setNewFragment();
            return true;
        });
    }

    private void setNewFragment()
    {
        if (currentFragment != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.navFrameLayout, currentFragment)
                    .commit();
        }
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