package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;

import comp3350.teachreach.R;

public class TopBarFragment extends Fragment
{
    private static final String ARG_ACCOUNT_ID = "ACCOUNT_ID";
    private static final String ARG_BAR_TITLE  = "BAR_TITLE";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;
    private static Fragment        previousFragment       = null;
    private        View            rootView;

    private int    accountID = -1;
    private String barTitle;

    public TopBarFragment()
    {
    }

    public static TopBarFragment newInstance(FragmentManager fragmentManager,
                                             Fragment prevTopBar,
                                             Fragment prevFragment,
                                             String title,
                                             int accountID)
    {
        TopBarFragment fragment = new TopBarFragment();
        Bundle         args     = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        args.putString(ARG_BAR_TITLE, title);
        fragment.setArguments(args);
        previousTopBarFragment         = prevTopBar;
        previousFragment               = prevFragment;
        TopBarFragment.fragmentManager = fragmentManager;
        return fragment;
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
        rootView = inflater.inflate(R.layout.fragment_top_bar_default,
                                    container,
                                    false);
        setUpTopBar(rootView);
        return rootView;
    }
}