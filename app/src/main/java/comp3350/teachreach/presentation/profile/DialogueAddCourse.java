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
import comp3350.teachreach.databinding.DialogAddCourseBinding;
import comp3350.teachreach.logic.exceptions.DataAccessException;
import comp3350.teachreach.logic.exceptions.input.InvalidInputException;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class DialogueAddCourse extends AppCompatDialogFragment
{

    public DialogueAddCourse()
    {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        FragmentActivity a = requireActivity();

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(a);

        DialogAddCourseBinding binding
                = DialogAddCourseBinding.inflate(a.getLayoutInflater());

        TRViewModel vm = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);

        TutorProfileViewModel tvm
                = new ViewModelProvider(requireActivity()).get(
                TutorProfileViewModel.class);

        View root = binding.getRoot();

        TextInputLayout tilCourseCode = root.findViewById(R.id.tilCourseCode);
        EditText        etCourseCode  = tilCourseCode.getEditText();

        TextInputLayout tilCourseName = root.findViewById(R.id.tilCourseName);
        EditText        etCourseName  = tilCourseName.getEditText();

        return builder
                .setTitle("Adding a New Course")
                .setPositiveButton("Add", (d, w) -> {
                    try {
                        tilCourseCode.setError(null);
                        ITutor tutor = vm.getTutor().getValue();
                        assert tutor != null;

                        assert etCourseCode != null;
                        assert etCourseName != null;

                        String courseCode = etCourseCode
                                .getText()
                                .toString()
                                .trim();
                        String courseName = etCourseName
                                .getText()
                                .toString()
                                .trim();

                        tvm.addCourse(tutor, courseCode, courseName);
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
                .setView(root)
                .create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);
    }
}
