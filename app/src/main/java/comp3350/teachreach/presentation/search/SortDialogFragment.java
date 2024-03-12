package comp3350.teachreach.presentation.search;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import comp3350.teachreach.R;
import comp3350.teachreach.presentation.enums.SortCriteria;

public
class SortDialogFragment extends AppCompatDialogFragment
{
    private SortDialogListener listener;

    @NonNull
    @Override
    public
    Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder
                = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View           view     = inflater.inflate(R.layout.sort_dialog, null);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);

        builder
                .setView(view)
                .setNegativeButton("Cancel",
                                   new DialogInterface.OnClickListener()
                                   {
                                       @Override
                                       public
                                       void onClick(DialogInterface dialog,
                                                    int which)
                                       {
                                       }
                                   })
                .setPositiveButton("Apply",
                                   new DialogInterface.OnClickListener()
                                   {
                                       @Override
                                       public
                                       void onClick(DialogInterface dialog,
                                                    int which)
                                       {
                                           RadioButton selectedRadioButton
                                                   = view.findViewById(
                                                   radioGroup.getCheckedRadioButtonId());
                                           SortCriteria sortCriteria
                                                   = (SortCriteria) selectedRadioButton.getTag();
                                           listener.applySort(sortCriteria);
                                       }
                                   });

        return builder.create();
    }

    @Override
    public
    void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try {
            listener = (SortDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context + "must implement dialog listener.");
        }
    }

    public
    interface SortDialogListener
    {
        void applySort(SortCriteria sortCriteria);
    }
}
