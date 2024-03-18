package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.objects.interfaces.IAccount;

public class StudentProfileSelfViewFragment extends Fragment
{
    private static final String ARG_ACCOUNT_ID = "ACCOUNT_ID";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;
    private static Fragment        previousFragment       = null;

    private View     rootView;
    private TextView tvName, tvMajor, tvPronouns;
    private Button btnEditProfile;

    private int            accountID = -1;
    private IAccount       account   = null;
    private AccessAccounts accessAccounts;

    public StudentProfileSelfViewFragment()
    {
    }

    public static StudentProfileSelfViewFragment newInstance(FragmentManager fragmentManager,
                                                             Fragment topBarFragment,
                                                             int accountID)
    {
        StudentProfileSelfViewFragment fragment
                = new StudentProfileSelfViewFragment();
        StudentProfileSelfViewFragment.fragmentManager = fragmentManager;
        Bundle args = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        fragment.setArguments(args);
        StudentProfileSelfViewFragment.previousTopBarFragment = topBarFragment;
        StudentProfileSelfViewFragment.previousFragment       = fragment;
        return fragment;
    }

    private void fillUpProfileDetails(View v)
    {
        tvName     = v.findViewById(R.id.tvNameField);
        tvPronouns = v.findViewById(R.id.tvPronounsField);
        tvMajor    = v.findViewById(R.id.tvMajorField);
        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
    }

    private void setUpEditProfileButton(View v)
    {
        btnEditProfile = v.findViewById(R.id.fabEditProfile);
        btnEditProfile.setOnClickListener(view -> startEditProfileActivity());
    }

    private void startEditProfileActivity()
    {
        fragmentManager
                .beginTransaction()
                .replace(R.id.abTop,
                         TopBarFragment.newInstance(fragmentManager,
                                                    previousTopBarFragment,
                                                    this,
                                                    "Edit Profile",
                                                    accountID))
                .replace(R.id.navFrameLayout,
                         EditStudentProfileFragment.newInstance(fragmentManager,
                                                                previousTopBarFragment,
                                                                accountID))
                .commit();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessAccounts = new AccessAccounts();
        if (getArguments() != null) {
            accountID = getArguments().getInt(ARG_ACCOUNT_ID);
        }
        if (account == null) {
            account = accessAccounts.getAccounts().get(accountID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_student_profile_self_view,
                                    container,
                                    false);
        fillUpProfileDetails(rootView);
        setUpEditProfileButton(rootView);
        return rootView;
    }
}