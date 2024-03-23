package comp3350.teachreach.presentation.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.DialogAddLocationBinding;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class DialogueAddLocation extends AppCompatDialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        FragmentActivity a = requireActivity();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(a);

        DialogAddLocationBinding binding
                = DialogAddLocationBinding.inflate(a.getLayoutInflater());

        TRViewModel vm = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);

        TutorProfileViewModel tvm
                = new ViewModelProvider(requireActivity()).get(
                TutorProfileViewModel.class);

        View root = binding.getRoot();

        TextInputLayout tilAddLocation = root.findViewById(R.id.tilAddLocation);
        EditText        etLocation     = tilAddLocation.getEditText();

        return builder
                .setTitle("Adding a New Location")
                .setPositiveButton("Add", (d, w) -> {
                    try {
                        ITutor tutor = vm.getTutor().getValue();
                        assert tutor != null;

                        assert etLocation != null;

                        String location = etLocation
                                .getText()
                                .toString()
                                .trim();

                        tvm.addLocation(tutor, location);
                    } catch (final AssertionError |
                                   InvalidInputException |
                                   DataAccessException e) {
                        Toast
                                .makeText(requireContext(),
                                          e.getMessage(),
                                          Toast.LENGTH_LONG)
                                .show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
    }
}
