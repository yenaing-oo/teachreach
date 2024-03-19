package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.profile.TutorProfileViewFragment;

public class SearchFragment extends Fragment
{
    private AccessTutors       accessTutors;
    private AccessCourses      accessCourses;
    private ISearchSortHandler searchSortHandler;

    private TRViewModel vm;

    private View                       rootView;
    private SearchTutorRecyclerAdapter adapter;

    private Parcelable recyclerViewState;

    public SearchFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        accessTutors      = new AccessTutors();
        accessCourses     = new AccessCourses();
        searchSortHandler = new SearchSortHandler();
        vm                = new ViewModelProvider(requireActivity()).get(
                TRViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(
                    "recyclerViewState");
        }

        RecyclerView recyclerView = rootView.findViewById(R.id.rvSearchResult);
        if (recyclerViewState != null) {
            recyclerView
                    .getLayoutManager()
                    .onRestoreInstanceState(recyclerViewState);
        } else {
            setUpRecyclerView(recyclerView);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state)
    {
        super.onSaveInstanceState(state);
        RecyclerView recyclerView = rootView.findViewById(R.id.rvSearchResult);
        recyclerViewState = recyclerView
                .getLayoutManager()
                .onSaveInstanceState();
        state.putParcelable("recyclerViewState", recyclerViewState);
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        adapter = new SearchTutorRecyclerAdapter(vm.getTutors().getValue());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onTutorItemClick(int position)
    {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.tutorProfileViewFragment,
                         new TutorProfileViewFragment())
                .commit();
    }
}