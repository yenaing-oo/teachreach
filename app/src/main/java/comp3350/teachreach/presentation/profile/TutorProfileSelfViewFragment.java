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
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;

public class TutorProfileSelfViewFragment extends Fragment
{
    private static final String ARG_ACCOUNT_ID = "ACCOUNT_ID";
    private static final String ARG_TUTOR_ID   = "TUTOR_ID";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;
    private static Fragment        previousFragment       = null;

    private View     rootView;
    private TextView tvName, tvMajor, tvPronouns;
    private Button btnEditProfile;

    private int accountID = -1;
    private int tutorID   = -1;

    private IAccount account = null;
    private ITutor   tutor   = null;

    private AccessAccounts accessAccounts;
    private AccessTutors   accessTutors;

    public TutorProfileSelfViewFragment()
    {
    }

    public static TutorProfileSelfViewFragment newInstance(FragmentManager fragmentManager,
                                                           Fragment topBarFragment,
                                                           int accountID,
                                                           int tutorID)
    {
        TutorProfileSelfViewFragment fragment
                = new TutorProfileSelfViewFragment();
        TutorProfileSelfViewFragment.fragmentManager = fragmentManager;
        Bundle args = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        args.putInt(ARG_TUTOR_ID, tutorID);
        fragment.setArguments(args);
        TutorProfileSelfViewFragment.previousTopBarFragment = topBarFragment;
        TutorProfileSelfViewFragment.previousFragment       = fragment;
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
                         EditMyProfileBarFragment.newInstance(fragmentManager,
                                                              previousTopBarFragment,
                                                              previousFragment,
                                                              accountID))
                .replace(R.id.navFrameLayout,
                         EditStudentProfileFragment.newInstance(fragmentManager,
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
        rootView = inflater.inflate(R.layout.fragment_tutor_profile_self_view,
                                    container,
                                    false);
        fillUpProfileDetails(rootView);
        setUpEditProfileButton(rootView);
        return rootView;
    }
}