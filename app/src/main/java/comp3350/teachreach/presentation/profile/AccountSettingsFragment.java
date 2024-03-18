package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import comp3350.teachreach.R;

public class AccountSettingsFragment extends Fragment
{
    public AccountSettingsFragment()
    {
    }

    public static AccountSettingsFragment newInstance()
    {
        AccountSettingsFragment fragment = new AccountSettingsFragment();
        Bundle                  args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_account_settings,
                                container,
                                false);
    }
}