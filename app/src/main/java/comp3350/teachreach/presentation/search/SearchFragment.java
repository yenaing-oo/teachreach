package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.sidesheet.SideSheetBehavior;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.function.Function;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.TutorFilter;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.profile.TutorProfileViewFragment;

public class SearchFragment extends Fragment
{
    Function<List<ITutor>, List<ITutor>> tutorFilter;
    private ISearchSortHandler    searchSortHandler;
    private TRViewModel           vm;
    private FragmentSearchBinding binding;
    private Fragment              tutorProfileView;

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

        tutorProfileView
                = getChildFragmentManager().findFragmentById(R.id.rightSide);

        assert tutorProfileView != null;
        hideDetails();
        setUpFilterSheet();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpFilterSheet()
    {
        FrameLayout filterSheet = binding
                .getRoot()
                .findViewById(R.id.filterSheet);
        SideSheetBehavior<FrameLayout> behavior = SideSheetBehavior.from(
                filterSheet);
        behavior.setState(SideSheetBehavior.STATE_HIDDEN);
    }

    private void setUpSearchBar()
    {
        TextInputLayout til = binding.getRoot().findViewById(R.id.searchField);
        EditText        et  = til.getEditText();
        tutorFilter = TutorFilter.New().filterFunc();
    }

    private void setUpRecyclerView()
    {
        RecyclerView recyclerView = binding
                .getRoot()
                .findViewById(R.id.rvSearchResult);
        SearchTutorRecyclerAdapter adapter = new SearchTutorRecyclerAdapter(vm
                                                                                    .getTutors()
                                                                                    .getValue(),
                                                                            this::openDetails);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void openDetails(ITutor t)
    {
        vm.setTutor(t);
        tutorProfileView = new TutorProfileViewFragment(v -> hideDetails());
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.rightSide, tutorProfileView)
                .show(tutorProfileView)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    void hideDetails()
    {
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction()
                .hide(tutorProfileView)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }
}