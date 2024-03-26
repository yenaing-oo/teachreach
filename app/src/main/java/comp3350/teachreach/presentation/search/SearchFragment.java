package comp3350.teachreach.presentation.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.DialogFiltersBinding;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.logic.TutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.logic.profile.UserProfileFetcher;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.profile.tutor.StringRecyclerAdapter;
import comp3350.teachreach.presentation.utils.TRViewModel;

public
class SearchFragment extends Fragment
{
    static final   Object                      lock                = new Object();
    static         ITutorFilter                tutorFilter         = TutorFilter.New();
    private static IUserProfileHandler<ITutor> profileHandler      = new UserProfileFetcher<>();
    private static ITutorProfileHandler        tutorProfileHandler = new TutorProfileHandler();
    EditText          maxPrice;
    EditText          minPrice;
    CheckBox          priceMaxSwitch;
    CheckBox          priceMinSwitch;
    SlidingPaneLayout slidingPaneLayout;
    private FragmentSearchBinding binding;
    private double                prevMaxPrice   = -1.0;
    private double                prevMinPrice   = -1.0;
    private int                   selectedReview = -1;
    private String                selectedCourse;
    private TRViewModel           vm;
    private List<ITutor>          tutorList;
    private List<String>          courseList;
    private SearchViewModel       searchViewModel;

    public
    SearchFragment()
    {
    }

    @Override
    public
    void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        vm              = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        tutorList       = searchViewModel.getTutors().getValue();
        courseList      = searchViewModel.getCourses().getValue();
    }

    @Override
    public
    View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public
    void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        slidingPaneLayout = binding.searchFragment;
        setUpRecyclerView();
        setUpSearchBar();
    }

    private
    void setUpSearchBar()
    {
        TextInputLayout searchTextInput = binding.textField;
        EditText        searchEditText  = searchTextInput.getEditText();
        Button          filterButton    = binding.btnFilter;
        Button          searchButton    = binding.btnSearch;

        searchEditText.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                return onSearchClick(searchEditText);
            }
            return false;
        });
        filterButton.setOnClickListener(v -> onFilterButtonClick(searchEditText));
        searchButton.setOnClickListener(v -> onSearchClick(searchEditText));
    }

    private
    void onFilterButtonClick(EditText searchEditText)
    {
        DialogFiltersBinding filtersBinding   = DialogFiltersBinding.inflate(this.getLayoutInflater());
        View                 filterDialogView = filtersBinding.getRoot();

        setUpCourseCodeField(filtersBinding);
        setUpReviewFilterField(filtersBinding);
        setUpPriceFilterField(filtersBinding);
        setUpSortSwitches(filtersBinding);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Apply Filters")
                .setMessage("Long press on field to reset")
                .setView(filterDialogView)
                .setPositiveButton("Apply", (d, i) -> {
                    onSearchClick(searchEditText);
                })
                .setNeutralButton("Reset", (d, i) -> {
                    prevMinPrice   = -1.0;
                    prevMaxPrice   = -1.0;
                    selectedReview = -1;
                    selectedCourse = null;
                    searchViewModel.postTutorsFiltered(tutorFilter.Reset().filterFunc().apply(tutorList));
                })
                .setNegativeButton("Cancel", (d, i) -> d.dismiss())
                .create()
                .show();
    }

    private
    void setUpReviewFilterField(DialogFiltersBinding b)
    {
        TextInputLayout tilMinReviews = b.tilMinReviews;
        List<Integer>   intList       = List.of(1, 2, 3, 4);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(requireActivity(),
                                                                android.R.layout.simple_list_item_1,
                                                                intList);
        AutoCompleteTextView reviewsField  = b.starField;
        CheckBox             reviewsSwitch = b.reviewsSwitch;
        reviewsField.setAdapter(arrayAdapter);
        reviewsField.setThreshold(1);
        reviewsField.setLongClickable(true);
        reviewsField.setOnLongClickListener(v -> {
            reviewsField.setText("");
            selectedReview = -1;
            return true;
        });
        reviewsField.setOnItemClickListener((p, v, pos, id) -> {
            reviewsField.clearFocus();
            selectedReview = intList.get(pos);
            reviewsField.setText("⭐️".repeat(selectedReview));
        });
        reviewsSwitch.setOnCheckedChangeListener((d, checked) -> {
            tilMinReviews.setEnabled(checked);
            if (!checked) {
                selectedReview = -1;
            }
        });
        if (selectedReview > 0) {
            reviewsField.setText("⭐️".repeat(selectedReview));
        }
        reviewsSwitch.setChecked(tutorFilter.getMinimumAvgRatingState());
        tilMinReviews.setEnabled(tutorFilter.getMinimumAvgRatingState());
    }

    private
    void setUpSortSwitches(DialogFiltersBinding b)
    {
        MaterialSwitch sortByPriceSwitch   = b.sortByPriceSwitch;
        MaterialSwitch sortByRatingsSwitch = b.sortByReviewsSwitch;
        sortByPriceSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) {
                searchViewModel.postTutorsFiltered(tutorFilter.setSortByPrice().filterFunc().apply(tutorList));
            } else {
                searchViewModel.postTutorsFiltered(tutorFilter.clearSortByPrice().filterFunc().apply(tutorList));
            }
        });
        sortByRatingsSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) {
                searchViewModel.postTutorsFiltered(tutorFilter.setSortByReviews().filterFunc().apply(tutorList));
            } else {
                searchViewModel.postTutorsFiltered(tutorFilter.clearSortByReviews().filterFunc().apply(tutorList));
            }
        });
        sortByPriceSwitch.setChecked(tutorFilter.getSortByPriceState());
        sortByRatingsSwitch.setChecked(tutorFilter.getSortByReviewsState());
    }

    private
    void setUpPriceFilterField(DialogFiltersBinding b)
    {
        TextInputLayout tilMinPrice = b.tilMinPrice;
        TextInputLayout tilMaxPrice = b.tilMaxPrice;
        minPrice       = tilMinPrice.getEditText();
        maxPrice       = tilMaxPrice.getEditText();
        priceMinSwitch = b.priceMinSwitch;
        priceMaxSwitch = b.priceMaxSwitch;
        tilMinPrice.setEnabled(tutorFilter.getMinimumHourlyRateState());
        tilMaxPrice.setEnabled(tutorFilter.getMaximumHourlyRateState());
        priceMinSwitch.setOnCheckedChangeListener((d, checked) -> tilMinPrice.setEnabled(checked));
        priceMaxSwitch.setOnCheckedChangeListener((d, checked) -> tilMaxPrice.setEnabled(checked));
        priceMinSwitch.setChecked(tutorFilter.getMinimumHourlyRateState());
        priceMaxSwitch.setChecked(tutorFilter.getMaximumHourlyRateState());
        if (prevMinPrice != -1.0) {
            minPrice.setText(String.format(Locale.getDefault(), "%.2f", prevMinPrice));
        }
        if (prevMaxPrice != -1.0) {
            maxPrice.setText(String.format(Locale.getDefault(), "%.2f", prevMaxPrice));
        }

    }

    private
    void setUpCourseCodeField(DialogFiltersBinding b)
    {
        TextInputLayout tilCourseCodes = b.tilCourseCode;
        ArrayAdapter<String> coursesArrayAdapter = new ArrayAdapter<>(requireActivity(),
                                                                      android.R.layout.simple_list_item_1,
                                                                      courseList);
        AutoCompleteTextView courseCodes  = b.courseCodeField;
        CheckBox             courseSwitch = b.courseSwitch;
        courseCodes.setAdapter(coursesArrayAdapter);
        courseCodes.setThreshold(1);
        courseCodes.setLongClickable(true);
        courseCodes.setOnLongClickListener(v -> {
            courseCodes.setText("");
            selectedCourse = null;
            return true;
        });
        courseCodes.setOnItemClickListener((p, v, pos, id) -> {
            courseCodes.clearFocus();
            selectedCourse = courseList.get(pos);
        });
        if (selectedCourse != null) {
            courseCodes.setText(selectedCourse);
        }
        courseSwitch.setOnCheckedChangeListener((d, checked) -> {
            tilCourseCodes.setEnabled(checked);
            if (!checked) {
                selectedCourse = null;
            }
        });
        courseSwitch.setChecked(tutorFilter.getCourseCodeState());
        tilCourseCodes.setEnabled(tutorFilter.getCourseCodeState());
    }

    private
    boolean onSearchClick(EditText searchEditText)
    {
        String searchString = searchEditText.getText().toString().trim();
        Executors.newSingleThreadExecutor().execute(() -> {
            if (priceMinSwitch != null && priceMinSwitch.isChecked()) {
                String s = minPrice.getText().toString().trim();
                if (!s.isEmpty()) {
                    prevMinPrice = Double.parseDouble(s);
                    tutorFilter.setMinimumHourlyRate(prevMinPrice);
                }
            } else {
                prevMinPrice = -1.0;
                tutorFilter.clearMinimumHourlyRate();
            }
            if (priceMaxSwitch != null && priceMaxSwitch.isChecked()) {
                String s = maxPrice.getText().toString().trim();
                if (!s.isEmpty()) {
                    prevMaxPrice = Double.parseDouble(s);
                    tutorFilter.setMaximumHourlyRate(prevMaxPrice);
                }
            } else {
                prevMaxPrice = -1.0;
                tutorFilter.clearMaximumHourlyRate();
            }
            tutorFilter = selectedReview == -1 ?
                          tutorFilter.clearMinimumAvgRating() :
                          tutorFilter.setMinimumAvgRating(selectedReview);
            tutorFilter = selectedCourse == null ?
                          tutorFilter.clearCourseCode() :
                          tutorFilter.setCourseCode(selectedCourse);
            tutorFilter = searchString.isEmpty() ?
                          tutorFilter.resetSearchString() :
                          tutorFilter.setSearchFilter(searchString);
            synchronized (lock) {
                List<ITutor> result = tutorFilter.filterFunc().apply(tutorList);
                new Handler(Looper.getMainLooper()).post(() -> searchViewModel.postTutorsFiltered(result));
            }
        });
        return true;
    }

    private
    void setUpRecyclerView()
    {
        RecyclerView recyclerView = binding.rvSearchResult;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchViewModel.setTutorsFiltered(tutorList);
        recyclerView.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        Executors.newSingleThreadExecutor().execute(() -> {
            synchronized (lock) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    SearchTutorRecyclerAdapter adapter = new SearchTutorRecyclerAdapter(profileHandler,
                                                                                        tutorProfileHandler,
                                                                                        tutorList,
                                                                                        this::openDetails);
                    searchViewModel.getTutorsFiltered().observe(getViewLifecycleOwner(), adapter::updateData);
                    recyclerView.setAdapter(adapter);
                });
            }
        });
    }

    private
    void openDetails(ITutor t)
    {
        vm.setTutor(t);
        FragmentManager fm              = getChildFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.rightSide);
        assert navHostFragment != null;
        slidingPaneLayout.open();
        NavController nc = navHostFragment.getNavController();
        nc.navigate(R.id.actionToTutorProfileViewFragment);
    }
}