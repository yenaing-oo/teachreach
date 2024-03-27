package comp3350.teachreach.presentation.search;

import android.os.Bundle;
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

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.DialogFiltersBinding;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.profile.tutor.StringRecyclerAdapter;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class SearchFragment extends Fragment {
    EditText maxPrice;
    EditText minPrice;
    CheckBox priceMaxSwitch;
    CheckBox priceMinSwitch;
    SlidingPaneLayout slidingPaneLayout;
    private FragmentSearchBinding binding;
    private double prevMaxPrice = -1.0;
    private double prevMinPrice = -1.0;
    private int selectedReview = -1;
    private String selectedCourse;
    private TRViewModel viewModel;
    private List<ITutor> tutorList;
    private List<String> courseList;
    private SearchViewModel searchViewModel;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TRViewModel.class);
        searchViewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        tutorList = searchViewModel.getTutors();
        courseList = searchViewModel.getCourses();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slidingPaneLayout = binding.searchFragment;
        setUpRecyclerView();
        setUpSearchBar();
    }

    private void setUpSearchBar() {
        TextInputLayout searchTextInput = binding.textField;
        EditText searchEditText = searchTextInput.getEditText();
        Button filterButton = binding.btnFilter;
        Button searchButton = binding.btnSearch;

        searchEditText.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                            keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                return onSearchClick(searchEditText);
            }
            return false;
        });
        filterButton.setOnClickListener(v -> onFilterButtonClick(searchEditText));
        searchButton.setOnClickListener(v -> onSearchClick(searchEditText));
    }

    private void onFilterButtonClick(EditText searchEditText) {
        DialogFiltersBinding filtersBinding = DialogFiltersBinding.inflate(
                this.getLayoutInflater());
        View filterDialogView = filtersBinding.getRoot();

        setUpCourseCodeField(filtersBinding);
        setUpReviewFilterField(filtersBinding);
        setUpPriceFilterField(filtersBinding);
        setUpSortSwitches(filtersBinding);

        new MaterialAlertDialogBuilder(requireContext()).setTitle("Apply Filters")
                                                        .setMessage("Long press on field to reset")
                                                        .setView(filterDialogView)
                                                        .setPositiveButton("Apply", (d, i) -> {
                                                            onSearchClick(searchEditText);
                                                        })
                                                        .setNeutralButton("Reset", (d, i) -> {
                                                            prevMinPrice = -1.0;
                                                            prevMaxPrice = -1.0;
                                                            selectedReview = -1;
                                                            selectedCourse = null;
                                                            searchViewModel.resetFilter();
                                                        })
                                                        .setNegativeButton("Cancel",
                                                                           (d, i) -> d.dismiss())
                                                        .create()
                                                        .show();
    }

    private void setUpReviewFilterField(DialogFiltersBinding b) {
        TextInputLayout tilMinReviews = b.tilMinReviews;
        List<Integer> intList = List.of(1, 2, 3, 4);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(requireActivity(),
                                                                android.R.layout.simple_list_item_1,
                                                                intList);
        AutoCompleteTextView reviewsField = b.starField;
        CheckBox reviewsSwitch = b.reviewsSwitch;
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
            if (!checked) selectedReview = -1;
        });
        if (selectedReview > 0) reviewsField.setText("⭐️".repeat(selectedReview));
        searchViewModel.getFilteringByRatings().observe(getViewLifecycleOwner(), checked -> {
            reviewsSwitch.setChecked(checked);
            tilMinReviews.setEnabled(checked);
        });
    }

    private void setUpSortSwitches(DialogFiltersBinding b) {
        MaterialSwitch sortByPriceSwitch = b.sortByPriceSwitch;
        MaterialSwitch sortByRatingsSwitch = b.sortByReviewsSwitch;
        sortByPriceSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) searchViewModel.setSortByPrice();
            else searchViewModel.resetSortByPrice();
        });
        sortByRatingsSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) searchViewModel.setSortByReviews();
            else searchViewModel.resetSortByReviews();
        });
        searchViewModel.getSortingByPrice()
                       .observe(getViewLifecycleOwner(), sortByPriceSwitch::setChecked);
        searchViewModel.getSortingByReviews()
                       .observe(getViewLifecycleOwner(), sortByRatingsSwitch::setChecked);
    }

    private void setUpPriceFilterField(DialogFiltersBinding b) {
        TextInputLayout tilMinPrice = b.tilMinPrice;
        TextInputLayout tilMaxPrice = b.tilMaxPrice;
        minPrice = tilMinPrice.getEditText();
        maxPrice = tilMaxPrice.getEditText();
        priceMinSwitch = b.priceMinSwitch;
        priceMaxSwitch = b.priceMaxSwitch;
        searchViewModel.getFilteringByMaxPrice().observe(getViewLifecycleOwner(), enabled -> {
            tilMaxPrice.setEnabled(enabled);
            priceMaxSwitch.setChecked(enabled);
        });
        searchViewModel.getFilteringByMinPrice().observe(getViewLifecycleOwner(), enabled -> {
            tilMinPrice.setEnabled(enabled);
            priceMinSwitch.setChecked(enabled);
        });
        priceMinSwitch.setOnCheckedChangeListener((d, checked) -> tilMinPrice.setEnabled(checked));
        priceMaxSwitch.setOnCheckedChangeListener((d, checked) -> tilMaxPrice.setEnabled(checked));
        if (prevMinPrice != -1.0)
            minPrice.setText(String.format(Locale.getDefault(), "%.2f", prevMinPrice));

        if (prevMaxPrice != -1.0)
            maxPrice.setText(String.format(Locale.getDefault(), "%.2f", prevMaxPrice));
    }

    private void setUpCourseCodeField(DialogFiltersBinding b) {
        TextInputLayout tilCourseCodes = b.tilCourseCode;
        ArrayAdapter<String> coursesArrayAdapter = new ArrayAdapter<>(requireActivity(),
                                                                      android.R.layout.simple_list_item_1,
                                                                      courseList);
        AutoCompleteTextView courseCodes = b.courseCodeField;
        CheckBox courseSwitch = b.courseSwitch;
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
        if (selectedCourse != null) courseCodes.setText(selectedCourse);
        courseSwitch.setOnCheckedChangeListener((d, checked) -> {
            tilCourseCodes.setEnabled(checked);
            if (!checked) selectedCourse = null;
        });
        searchViewModel.getFilteringByCourse().observe(getViewLifecycleOwner(), checked -> {
            tilCourseCodes.setEnabled(checked);
            courseSwitch.setChecked(checked);
        });
    }

    private boolean onSearchClick(EditText searchEditText) {
        String searchString = searchEditText.getText().toString().trim();
        if (priceMinSwitch != null && priceMinSwitch.isChecked()) {
            String s = minPrice.getText().toString().trim();
            if (!s.isEmpty()) {
                prevMinPrice = Double.parseDouble(s);
                searchViewModel.setMinimumHourlyRate(prevMinPrice);
            }
        } else {
            prevMinPrice = -1.0;
            searchViewModel.clearMinimumHourlyRate();
        }
        if (priceMaxSwitch != null && priceMaxSwitch.isChecked()) {
            String s = maxPrice.getText().toString().trim();
            if (!s.isEmpty()) {
                prevMaxPrice = Double.parseDouble(s);
                searchViewModel.setMaximumHourlyRate(prevMaxPrice);
            }
        } else {
            prevMaxPrice = -1.0;
            searchViewModel.clearMaximumHourlyRate();
        }
        if (selectedReview == -1) searchViewModel.clearMinimumAvgRating();
        else searchViewModel.setMinimumAvgRating(selectedReview);

        if (selectedCourse == null) searchViewModel.clearCourseCode();
        else searchViewModel.setCourseCode(selectedCourse);

        if (searchString.isEmpty()) searchViewModel.resetSearchString();
        else searchViewModel.setSearchFilter(searchString);
        return true;
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerView = binding.rvSearchResult;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new StringRecyclerAdapter(new ArrayList<>()));
        SearchTutorRecyclerAdapter adapter = new SearchTutorRecyclerAdapter(
                searchViewModel.getUserFetcher(), searchViewModel.getTutorFetcher(), tutorList,
                this::openDetails);
        searchViewModel.getTutorsFiltered().observe(getViewLifecycleOwner(), adapter::updateData);
        recyclerView.setAdapter(adapter);
    }

    private void openDetails(ITutor t) {
        viewModel.setTutor(t);
        FragmentManager fm = getChildFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fm.findFragmentById(R.id.rightSide);
        assert navHostFragment != null;
        slidingPaneLayout.open();
        NavController nc = navHostFragment.getNavController();
        nc.navigate(R.id.actionToTutorProfileViewFragment);
    }
}