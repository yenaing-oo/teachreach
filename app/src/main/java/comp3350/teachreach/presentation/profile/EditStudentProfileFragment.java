package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputLayout;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.account.AccountManager;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.AccountManagerException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.objects.interfaces.IAccount;

public class EditStudentProfileFragment extends Fragment
{
    private static final String ARG_ACCOUNT_ID = "ACCOUNT_ID";

    private static FragmentManager fragmentManager;
    private static Fragment        previousTopBarFragment = null;

    private int             accountID = -1;
    private IAccount        account   = null;
    private AccessAccounts  accessAccounts;
    private IAccountManager accountManager;

    private View            rootView;
    private TextInputLayout tilName, tilMajor, tilPronouns;
    private EditText etName, etMajor, etPronouns;
    private Button btnApply;

    public EditStudentProfileFragment()
    {
    }

    public static EditStudentProfileFragment newInstance(FragmentManager fragmentManager,
                                                         Fragment prevTopBar,
                                                         int accountID)
    {
        EditStudentProfileFragment fragment = new EditStudentProfileFragment();
        Bundle                     args     = new Bundle();
        previousTopBarFragment = prevTopBar;
        args.putInt(ARG_ACCOUNT_ID, accountID);
        fragment.setArguments(args);
        EditStudentProfileFragment.fragmentManager = fragmentManager;
        return fragment;
    }

    private void getAccount()
    {
        account = accessAccounts.getAccounts().get(accountID);
    }

    private void setUpInputBoxes(View v)
    {
        tilName     = v.findViewById(R.id.tilEditName);
        tilMajor    = v.findViewById(R.id.tilEditMajor);
        tilPronouns = v.findViewById(R.id.tilEditPronouns);
        etName      = tilName.getEditText();
        etPronouns  = tilPronouns.getEditText();
        etMajor     = tilMajor.getEditText();
        etName.setText(account.getUserName());
        String currentPronouns = account.getUserPronouns();
        if (currentPronouns != null && !currentPronouns.isEmpty()) {
            etPronouns.setText(currentPronouns);
        }
        String currentMajor = account.getUserMajor();
        if (currentMajor != null && !currentMajor.isEmpty()) {
            etMajor.setText(currentMajor);
        }
    }

    private void setUpApplyButton(View v)
    {
        btnApply = v.findViewById(R.id.fabApply);
        btnApply.setOnClickListener(view -> {
            if (applyChanges()) {
                goBack();
            }
        });
    }

    private void goBack()
    {
        fragmentManager
                .beginTransaction()
                .replace(R.id.abTop, previousTopBarFragment)
                .replace(R.id.navFrameLayout,
                         StudentProfileSelfViewFragment.newInstance(
                                 fragmentManager,
                                 previousTopBarFragment,
                                 accountID))
                .commit();
    }

    private boolean applyChanges()
    {
        String newName     = etName.getText().toString().trim();
        String newMajor    = etMajor.getText().toString().trim();
        String newPronouns = etPronouns.getText().toString().trim();
        try {
            tilName.setError(null);
            InputValidator.validateName(newName);
            accountManager
                    .updateAccountUsername(newName)
                    .updateAccountUserMajor(newMajor)
                    .updateAccountUserPronouns(newPronouns);
            return true;
        } catch (AccountManagerException e) {
            Toast
                    .makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        } catch (InvalidNameException e) {
            tilName.setError(e.getMessage());
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessAccounts = new AccessAccounts();
        if (getArguments() != null) {
            accountID = getArguments().getInt(ARG_ACCOUNT_ID);
            getAccount();
        }
        accountManager = new AccountManager(account);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (account == null) {
            getAccount();
        }
        rootView = inflater.inflate(R.layout.fragment_edit_student_profile,
                                    container,
                                    false);
        setUpInputBoxes(rootView);
        setUpApplyButton(rootView);
        return rootView;
    }
}