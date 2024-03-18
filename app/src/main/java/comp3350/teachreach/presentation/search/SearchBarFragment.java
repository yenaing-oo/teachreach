package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.search.SearchView;

import comp3350.teachreach.R;

public class SearchBarFragment extends Fragment
{
    private View rootView;

    public SearchBarFragment()
    {
    }

    public static SearchBarFragment newInstance()
    {
        SearchBarFragment fragment = new SearchBarFragment();
        Bundle            args     = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_search_bar,
                                    container,
                                    false);

        SearchView searchView = rootView.findViewById(R.id.svSearchTutor);

        return rootView;
    }
}