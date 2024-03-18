package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import comp3350.teachreach.R;

public class MyProfileBarFragment extends Fragment
{
    private static String                    ARG_ACCOUNT_ID = "ACCOUNT_ID";
    private        int                       accountID      = -1;
    public MyProfileBarFragment()
    {
    }

    public static MyProfileBarFragment newInstance(int accountID)
    {
        MyProfileBarFragment fragment = new MyProfileBarFragment();
        Bundle               args     = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_my_profile_bar,
                                container,
                                false);
    }
}