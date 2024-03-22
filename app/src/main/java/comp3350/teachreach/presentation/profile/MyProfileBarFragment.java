package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;

import comp3350.teachreach.R;

public class MyProfileBarFragment extends Fragment
{
    private static final String ARG_BAR_TITLE  = "BAR_TITLE";
    private static       String ARG_ACCOUNT_ID = "ACCOUNT_ID";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;
    private static Fragment        previousFragment       = null;

    private View fragView;

    private int    accountID = -1;
    private String barTitle;

    public MyProfileBarFragment()
    {
    }

    public static MyProfileBarFragment newInstance(FragmentManager fragmentManager,
                                                   Fragment prevTopBar,
                                                   Fragment prevFragment,
                                                   String title,
                                                   int accountID)
    {
        MyProfileBarFragment fragment = new MyProfileBarFragment();
        Bundle               args     = new Bundle();
        previousTopBarFragment               = prevTopBar;
        previousFragment                     = prevFragment;
        MyProfileBarFragment.fragmentManager = fragmentManager;
        args.putInt(ARG_ACCOUNT_ID, accountID);
        args.putString(ARG_BAR_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountID = getArguments().getInt(ARG_ACCOUNT_ID);
            barTitle  = getArguments().getString(ARG_BAR_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        fragView = inflater.inflate(R.layout.fragment_my_profile_bar,
                                    container,
                                    false);
        setUpTopBar(fragView);
        setUpTopBarMenu();
        return fragView;
    }

    protected void setUpTopBar(View v)
    {
        MaterialToolbar mtTopBar = v.findViewById(R.id.topAppBar);
        mtTopBar.setTitle(barTitle);
        mtTopBar.setNavigationOnClickListener(view -> {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.abTop, previousTopBarFragment)
                    .replace(R.id.navFrameLayout, previousFragment)
                    .commit();
        });
    }

    private void setUpTopBarMenu()
    {
        MaterialToolbar mtTopBar = fragView.findViewById(R.id.topAppBar);
        mtTopBar.setOnMenuItemClickListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.tbAccountSettings) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.abTop,
                                 TopBarFragment.newInstance(fragmentManager,
                                                            previousTopBarFragment,
                                                            previousFragment,
                                                            "Account Settings",
                                                            accountID))
                        .replace(R.id.navFrameLayout,
                                 new AccountSettingsFragment())
                        .commit();
            }
            return true;
        });
    }
}