package comp3350.teachreach.presentation.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessCourses;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.profile.TutorProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment
{
    private static String                    ARG_ACCOUNT_ID = "ACCOUNT_ID";
    private        AccessTutors              accessTutors;
    private        AccessCourses             accessCourses;
    private        View                      rootView;
    private        int                       accountID      = -1;
    private        ISearchSortHandler        searchSortHandler;
    private        SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private        List<ITutor>              tutors;
    private        List<ICourse>             courses;
    private        List<String>              courseStringList;

    public SearchFragment()
    {
    }

    public static SearchFragment newInstance(int accountID)
    {
        SearchFragment fragment = new SearchFragment();
        Bundle         args     = new Bundle();
        args.putInt(ARG_ACCOUNT_ID, accountID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            accountID = getArguments().getInt(ARG_ACCOUNT_ID);
        }
        accessTutors      = new AccessTutors();
        accessCourses     = new AccessCourses();
        searchSortHandler = new SearchSortHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        RecyclerView recyclerView
                = rootView.findViewById(R.id.searchResultRecyclerView);
        retrieveData();
        setUpRecyclerView(recyclerView);
        return rootView;
    }

    private void retrieveData()
    {
        tutors           = new ArrayList<>(accessTutors.getTutors().values());
        courses          = new ArrayList<>(accessCourses.getCourses().values());
        courseStringList = courses
                .stream()
                .map(ICourse::getCourseCode)
                .collect(Collectors.toList());
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        searchRecyclerViewAdapter
                = new SearchRecyclerViewAdapter(recyclerView.getContext(),
                                                tutors,
                                                this::onTutorItemClick);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onTutorItemClick(int position)
    {
        Intent intent = new Intent(getContext(), TutorProfileActivity.class);
        intent.putExtra("TUTOR_ID", tutors.get(position).getTutorID());
        startActivity(intent);
    }
}