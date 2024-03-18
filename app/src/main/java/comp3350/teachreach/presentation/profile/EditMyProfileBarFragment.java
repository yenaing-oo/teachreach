package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;

import comp3350.teachreach.R;

public class EditMyProfileBarFragment extends Fragment
{
    private static final String ARG_ACCOUNT_ID = "ACCOUNT_ID";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;
    private static Fragment        previousFragment       = null;
    private        View            rootView;

    private int accountID = -1;

    public EditMyProfileBarFragment()
    {
    }

    public static EditMyProfileBarFragment newInstance(FragmentManager fragmentManager,
                                                       int accountID,
                                                       Fragment prevTopBar,
                                                       Fragment prevFragment)
    {
        EditMyProfileBarFragment fragment = new EditMyProfileBarFragment();
        Bundle                   args     = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        fragment.setArguments(args);
        previousTopBarFragment                   = prevTopBar;
        previousFragment                         = prevFragment;
        EditMyProfileBarFragment.fragmentManager = fragmentManager;
        return fragment;
    }

    private void setNavigationBarBack(View v)
    {
        MaterialToolbar mtTopBar = v.findViewById(R.id.topAppBar);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_edit_my_profile_bar,
                                    container,
                                    false);
        setNavigationBarBack(rootView);
        return rootView;
    }
}