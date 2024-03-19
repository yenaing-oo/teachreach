package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.TRViewModel;
import comp3350.teachreach.presentation.profile.TutorProfileViewFragment;

public class SearchFragment extends Fragment
{
    private ISearchSortHandler searchSortHandler;

    private TRViewModel vm;

    private SearchTutorRecyclerAdapter adapter;
    private FragmentSearchBinding      binding;

    private Parcelable recyclerViewState;

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

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable(
                    "recyclerViewState");
        }

        RecyclerView recyclerView = binding
                .getRoot()
                .findViewById(R.id.rvSearchResult);
        if (recyclerViewState != null) {
            Objects
                    .requireNonNull(recyclerView.getLayoutManager())
                    .onRestoreInstanceState(recyclerViewState);
        } else {
            setUpRecyclerView(recyclerView);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle state)
    {
        super.onSaveInstanceState(state);
        RecyclerView recyclerView = binding
                .getRoot()
                .findViewById(R.id.rvSearchResult);
        recyclerViewState = recyclerView
                .getLayoutManager()
                .onSaveInstanceState();
        state.putParcelable("recyclerViewState", recyclerViewState);
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        adapter = new SearchTutorRecyclerAdapter(vm.getTutors().getValue(),
                                                 this::openDetails);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    void openDetails(ITutor t)
    {
        vm.setTutorId(t.getTutorID());
        FragmentTransaction ft = getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.tutorProfileViewFragment,
                         new TutorProfileViewFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}