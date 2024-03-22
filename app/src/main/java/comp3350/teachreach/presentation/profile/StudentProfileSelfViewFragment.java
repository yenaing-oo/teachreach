package comp3350.teachreach.presentation.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.appbar.MaterialToolbar;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentStudentProfileSelfViewBinding;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.TRViewModel;

public class StudentProfileSelfViewFragment extends Fragment
{
    private FragmentStudentProfileSelfViewBinding binding;

    private TRViewModel vm;

    private TextView tvName, tvMajor, tvPronouns;
    private Button btnEditProfile;

    private IAccount account;

    public StudentProfileSelfViewFragment()
    {
    }

    private void fillUpProfileDetails()
    {
        View v = binding.getRoot();
        tvName     = v.findViewById(R.id.tvNameField);
        tvPronouns = v.findViewById(R.id.tvPronounsField);
        tvMajor    = v.findViewById(R.id.tvMajorField);

        tvName.setText(account.getUserName());
        tvPronouns.setText(account.getUserPronouns());
        tvMajor.setText(account.getUserMajor());
    }

    private void setUpEditProfileButton()
    {
        btnEditProfile = binding.getRoot().findViewById(R.id.fabEditProfile);
        btnEditProfile.setOnClickListener(view -> NavHostFragment
                .findNavController(this)
                .navigate(R.id.actionToEditStudentProfileFragment));
    }

    private void setUpTopBarMenu()
    {
        MaterialToolbar mtTopBar = binding
                .getRoot()
                .findViewById(R.id.topAppBar);
        mtTopBar.setOnMenuItemClickListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.tbAccountSettings) {
                NavHostFragment
                        .findNavController(this)
                        .navigate(R.id.actionToAccountSettingsFragment);
            }
            return true;
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        account = vm.getAccount().getValue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentStudentProfileSelfViewBinding.inflate(inflater,
                                                                container,
                                                                false);
        fillUpProfileDetails();
        setUpEditProfileButton();
        setUpTopBarMenu();
        return binding.getRoot();
    }
}