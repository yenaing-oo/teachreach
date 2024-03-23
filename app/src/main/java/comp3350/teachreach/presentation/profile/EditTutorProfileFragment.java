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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentEditTutorProfileBinding;
import comp3350.teachreach.logic.account.AccountManager;
import comp3350.teachreach.logic.account.InputValidator;
import comp3350.teachreach.logic.exceptions.AccountManagerException;
import comp3350.teachreach.logic.exceptions.InvalidNameException;
import comp3350.teachreach.logic.interfaces.IAccountManager;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class EditTutorProfileFragment extends Fragment
{
    private FragmentEditTutorProfileBinding binding;
    private TRViewModel                     vm;

    private TextInputLayout tilName, tilMajor, tilPronouns, tilPrice;
    private EditText etName, etMajor, etPronouns, etPrice;
    private Button btnApply;

    private IAccount             account;
    private ITutor               tutor;
    private IAccountManager      accountManager;
    private ITutorProfileHandler tph;

    public EditTutorProfileFragment()
    {
    }

    private void setUpInputBoxes()
    {
        View v = binding.getRoot();
        tilName     = v.findViewById(R.id.tilEditName);
        tilMajor    = v.findViewById(R.id.tilEditMajor);
        tilPronouns = v.findViewById(R.id.tilEditPronouns);
        tilPrice    = v.findViewById(R.id.tilEditHourlyRate);

        etName     = tilName.getEditText();
        etPronouns = tilPronouns.getEditText();
        etMajor    = tilMajor.getEditText();
        etPrice    = tilPrice.getEditText();
        etName.setText(account.getUserName());

        etPrice.setText(String.format(Locale.US,
                                      "%.2f",
                                      tutor.getHourlyRate()));
        String currentPronouns = account.getUserPronouns();
        if (currentPronouns != null && !currentPronouns.isEmpty()) {
            etPronouns.setText(currentPronouns);
        }
        String currentMajor = account.getUserMajor();
        if (currentMajor != null && !currentMajor.isEmpty()) {
            etMajor.setText(currentMajor);
        }
    }

    protected void setUpTopBar()
    {
        MaterialToolbar mtTopBar = binding
                .getRoot()
                .findViewById(R.id.topAppBar);
        mtTopBar.setNavigationOnClickListener(view -> {
            NavController navController
                    = NavHostFragment.findNavController(this);
            navController.navigate(R.id.actionToTutorProfileSelfViewFragment);
        });
    }

    private void setUpApplyButton()
    {
        btnApply = binding.getRoot().findViewById(R.id.fabApply);
        btnApply.setOnClickListener(view -> {
            if (applyChanges()) {
                goBack();
            }
        });
    }

    private void goBack()
    {
        NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToTutorProfileSelfViewFragment);
    }

    private boolean applyChanges()
    {
        String newName     = etName.getText().toString().trim();
        String newMajor    = etMajor.getText().toString().trim();
        String newPronouns = etPronouns.getText().toString().trim();
        String newPriceStr = etPrice.getText().toString().trim();
        double newPrice    = Double.parseDouble(newPriceStr);
        try {
            tilName.setError(null);
            InputValidator.validateName(newName);
            accountManager
                    .updateAccountUsername(newName)
                    .updateAccountUserMajor(newMajor)
                    .updateAccountUserPronouns(newPronouns);
            tph.setHourlyRate(newPrice).updateTutorProfile();
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
        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        account = vm.getAccount().getValue();
        tutor   = vm.getTutor().getValue();

        accountManager = new AccountManager(account);
        tph            = new TutorProfileHandler(tutor);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentEditTutorProfileBinding.inflate(inflater,
                                                          container,
                                                          false);
        setUpInputBoxes();
        setUpApplyButton();
        setUpTopBar();
        return binding.getRoot();
    }
}