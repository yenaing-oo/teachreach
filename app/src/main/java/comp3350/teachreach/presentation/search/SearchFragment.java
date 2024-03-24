package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.DialogAddLocationBinding;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.TutorFilter;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;

public class SearchFragment extends Fragment
{
    static ITutorFilter tutorFilter = TutorFilter.New();
    SideSheetBehavior<FrameLayout> filterSheetBehaviour;
    FrameLayout                    filterSheet;
    private ISearchSortHandler    searchSortHandler;
    private TRViewModel           vm;
    private FragmentSearchBinding binding;

    public SearchFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        searchSortHandler = new SearchSortHandler();

        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        setUpRecyclerView(view);
        setUpSearchBar(view);
    }

    private void setUpSearchBar(View view)
    {
        TextInputLayout til = view.findViewById(R.id.textField);
        EditText        et  = til.getEditText();
        Button          btn = view.findViewById(R.id.btnFilter);

        et.setOnEditorActionListener((v, id, e) -> {
            if (id == EditorInfo.IME_ACTION_DONE ||
                (e.getAction() == KeyEvent.ACTION_DOWN &&
                 e.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String searchString = et.getText().toString().trim();
                tutorFilter.Reset();
                if (!searchString.isEmpty()) {
                    tutorFilter.setSearchFilter(searchString);
                }
                vm.postTutorsFiltered(tutorFilter
                                              .filterFunc()
                                              .apply(vm
                                                             .getTutors()
                                                             .getValue()));
                return true;
            }
            return false;
        });

        btn.setOnClickListener(v -> new MaterialAlertDialogBuilder(
                requireContext())
                .setTitle("Apply Filters")
                .setView(DialogAddLocationBinding
                                 .inflate(this.getLayoutInflater())
                                 .getRoot())
                .setPositiveButton("Apply", (d, i) -> {
                    String searchString = et.getText().toString().trim();
                    tutorFilter.Reset();
                    if (!searchString.isEmpty()) {
                        tutorFilter.setSearchFilter(searchString);
                    }
                    vm.postTutorsFiltered(tutorFilter
                                                  .filterFunc()
                                                  .apply(vm
                                                                 .getTutors()
                                                                 .getValue()));
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show());
    }

    private void setUpRecyclerView(View view)
    {
        List<ITutor> tutorList = vm.getTutors().getValue();
        vm.setTutorsFiltered(tutorList);
        RecyclerView recyclerView = view.findViewById(R.id.rvSearchResult);
        SearchTutorRecyclerAdapter adapter = new SearchTutorRecyclerAdapter(
                tutorList,
                this::openDetails);
        vm
                .getTutorsFiltered()
                .observe(getViewLifecycleOwner(), adapter::updateData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void openDetails(ITutor t)
    {
        vm.setTutor(t);
        FragmentManager fm = getChildFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(
                R.id.rightSide);
        NavController nc = navHostFragment.getNavController();
        nc.navigate(R.id.actionToTutorProfileViewFragment);
    }
}