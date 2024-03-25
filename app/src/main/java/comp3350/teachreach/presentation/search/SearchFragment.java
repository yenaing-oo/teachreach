package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

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
    static  ITutorFilter       tutorFilter = TutorFilter.New();
    private ISearchSortHandler searchSortHandler;
    private TRViewModel        vm;

    private List<ITutor> tutorList;

    private SearchViewModel searchViewModel;

    public SearchFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        searchSortHandler = new SearchSortHandler();

        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        searchViewModel
                = new ViewModelProvider(this).get(SearchViewModel.class);

        tutorList = searchViewModel.getTutors().getValue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        return FragmentSearchBinding
                .inflate(inflater, container, false)
                .getRoot();
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
                searchViewModel.postTutorsFiltered(tutorFilter
                                                           .filterFunc()
                                                           .apply(tutorList));
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
                    searchViewModel.postTutorsFiltered(tutorFilter
                                                               .filterFunc()
                                                               .apply(tutorList));
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show());
    }

    private void setUpRecyclerView(View view)
    {
        searchViewModel.setTutorsFiltered(tutorList);
        RecyclerView recyclerView = view.findViewById(R.id.rvSearchResult);
        SearchTutorRecyclerAdapter adapter = new SearchTutorRecyclerAdapter(
                tutorList,
                this::openDetails);
        searchViewModel
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