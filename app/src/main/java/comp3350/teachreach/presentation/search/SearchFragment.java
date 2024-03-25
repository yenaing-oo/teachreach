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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

import comp3350.teachreach.R;
import comp3350.teachreach.databinding.DialogFiltersBinding;
import comp3350.teachreach.databinding.FragmentSearchBinding;
import comp3350.teachreach.logic.TutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.utils.TRViewModel;

public class SearchFragment extends Fragment
{
    static ITutorFilter tutorFilter = TutorFilter.New();
    EditText maxPrice;
    EditText minPrice;
    CheckBox priceMaxSwitch;
    CheckBox priceMinSwitch;
    private double          prevMaxPrice   = -1.0;
    private double          prevMinPrice   = -1.0;
    private int             selectedReview = -1;
    private String          selectedCourse;
    private TRViewModel     vm;
    private List<ITutor>    tutorList;
    private List<String>    courseList;
    private SearchViewModel searchViewModel;

    public SearchFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        vm = new ViewModelProvider(requireActivity()).get(TRViewModel.class);

        searchViewModel = new ViewModelProvider(requireActivity()).get(
                SearchViewModel.class);

        tutorList  = searchViewModel.getTutors().getValue();
        courseList = searchViewModel.getCourses().getValue();
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
        TextInputLayout searchTextInput = view.findViewById(R.id.textField);
        EditText        searchEditText  = searchTextInput.getEditText();
        Button          filterButton    = view.findViewById(R.id.btnFilter);
        Button          searchButton    = view.findViewById(R.id.btnSearch);

        searchEditText.setOnEditorActionListener((v, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                 keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                return onSearchClick(searchEditText);
            }
            return false;
        });

        searchButton.setOnClickListener(v -> onSearchClick(searchEditText));
        filterButton.setOnClickListener(v -> onFilterButtonClick(searchEditText));
    }

    private void onFilterButtonClick(EditText searchEditText)
    {
        View filterDialogView = DialogFiltersBinding
                .inflate(this.getLayoutInflater())
                .getRoot();

        setUpCourseCodeField(filterDialogView);

        setUpReviewFilterField(filterDialogView);

        setUpPriceFilterField(filterDialogView);

        setUpSortSwitches(filterDialogView);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Apply Filters")
                .setMessage("Long press on field to reset")
                .setView(filterDialogView)
                .setPositiveButton("Apply", (d, i) -> {
                    if (priceMinSwitch.isChecked()) {
                        String s = minPrice.getText().toString().trim();
                        if (!s.isEmpty()) {
                            prevMinPrice = Double.parseDouble(s);
                            tutorFilter.setMinimumHourlyRate(prevMinPrice);
                        }
                    } else {
                        prevMinPrice = -1.0;
                        tutorFilter.clearMinimumHourlyRate();
                    }
                    if (priceMaxSwitch.isChecked()) {
                        String s = maxPrice.getText().toString().trim();
                        if (!s.isEmpty()) {
                            prevMaxPrice = Double.parseDouble(s);
                            tutorFilter.setMaximumHourlyRate(prevMaxPrice);
                        }
                    } else {
                        prevMaxPrice = -1.0;
                        tutorFilter.clearMaximumHourlyRate();
                    }
                    onSearchClick(searchEditText);
                })
                .setNeutralButton("Reset", (d, i) -> {
                    prevMinPrice   = -1.0;
                    prevMaxPrice   = -1.0;
                    selectedReview = -1;
                    selectedCourse = null;
                    searchViewModel.postTutorsFiltered(tutorFilter
                                                               .Reset()
                                                               .filterFunc()
                                                               .apply(tutorList));
                })
                .setNegativeButton("Cancel", (d, i) -> d.dismiss())
                .create()
                .show();
    }

    private void setUpReviewFilterField(View filterDialogView)
    {
        TextInputLayout tilMinReviews
                = filterDialogView.findViewById(R.id.tilMinReviews);

        List<Integer> intList = List.of(1, 2, 3, 4);
        ArrayAdapter<Integer> arrayAdapter
                = new ArrayAdapter<>(requireActivity(),
                                     android.R.layout.simple_list_item_1,
                                     intList);
        AutoCompleteTextView reviewsField
                = filterDialogView.findViewById(R.id.starField);

        CheckBox reviewsSwitch
                = filterDialogView.findViewById(R.id.reviewsSwitch);

        reviewsField.setAdapter(arrayAdapter);
        reviewsField.setThreshold(1);
        reviewsField.setLongClickable(true);
        reviewsField.setOnLongClickListener(v -> {
            reviewsField.setText("");
            selectedReview = -1;
            tutorFilter.clearMinimumAvgRating();
            return true;
        });
        reviewsField.setOnItemClickListener((p, v, pos, id) -> {
            reviewsField.clearFocus();
            selectedReview = intList.get(pos);
            reviewsField.setText("⭐️".repeat(selectedReview));
            tutorFilter.setMinimumAvgRating(selectedReview);
        });

        reviewsSwitch.setOnCheckedChangeListener((b, checked) -> {
            tilMinReviews.setEnabled(checked);
            if (!checked) {
                selectedReview = -1;
                tutorFilter.clearMinimumAvgRating();
            }
        });

        if (selectedReview > 0) {
            reviewsField.setText("⭐️".repeat(selectedReview));
        }
        reviewsSwitch.setChecked(tutorFilter.getMinimumAvgRatingState());
        tilMinReviews.setEnabled(tutorFilter.getMinimumAvgRatingState());
    }

    private void setUpSortSwitches(View filterDialogView)
    {
        MaterialSwitch sortByPriceSwitch
                = filterDialogView.findViewById(R.id.sortByPriceSwitch);
        MaterialSwitch sortByRatingsSwitch
                = filterDialogView.findViewById(R.id.sortByReviewsSwitch);

        sortByPriceSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) {
                searchViewModel.postTutorsFiltered(tutorFilter
                                                           .setSortByPrice()
                                                           .filterFunc()
                                                           .apply(tutorList));
            } else {
                searchViewModel.postTutorsFiltered(tutorFilter
                                                           .clearSortByPrice()
                                                           .filterFunc()
                                                           .apply(tutorList));
            }
        });

        sortByRatingsSwitch.setOnCheckedChangeListener((c, checked) -> {
            if (checked) {
                searchViewModel.postTutorsFiltered(tutorFilter
                                                           .setSortByReviews()
                                                           .filterFunc()
                                                           .apply(tutorList));
            } else {
                searchViewModel.postTutorsFiltered(tutorFilter
                                                           .clearSortByReviews()
                                                           .filterFunc()
                                                           .apply(tutorList));
            }
        });

        sortByPriceSwitch.setChecked(tutorFilter.getSortByPriceState());
        sortByRatingsSwitch.setChecked(tutorFilter.getSortByReviewsState());
    }

    private void setUpPriceFilterField(View filterDialogView)
    {
        TextInputLayout tilMinPrice
                = filterDialogView.findViewById(R.id.tilMinPrice);

        TextInputLayout tilMaxPrice
                = filterDialogView.findViewById(R.id.tilMaxPrice);

        minPrice = tilMinPrice.getEditText();
        maxPrice = tilMaxPrice.getEditText();

        priceMinSwitch = filterDialogView.findViewById(R.id.priceMinSwitch);

        priceMaxSwitch = filterDialogView.findViewById(R.id.priceMaxSwitch);

        priceMinSwitch.setOnCheckedChangeListener((b, checked) -> {
            tilMinPrice.setEnabled(checked);
        });
        priceMaxSwitch.setOnCheckedChangeListener((b, checked) -> {
            tilMaxPrice.setEnabled(checked);
        });

        priceMinSwitch.setChecked(tutorFilter.getMinimumHourlyRateState());
        priceMaxSwitch.setChecked(tutorFilter.getMaximumHourlyRateState());

        if (prevMinPrice != -1.0) {
            minPrice.setText(String.format(Locale.getDefault(),
                                           "%.2f",
                                           prevMinPrice));
        }

        if (prevMaxPrice != -1.0) {
            maxPrice.setText(String.format(Locale.getDefault(),
                                           "%.2f",
                                           prevMaxPrice));
        }

        tilMinPrice.setEnabled(tutorFilter.getMinimumHourlyRateState());
        tilMaxPrice.setEnabled(tutorFilter.getMaximumHourlyRateState());
    }

    private void setUpCourseCodeField(View filterDialogView)
    {
        TextInputLayout tilCourseCodes
                = filterDialogView.findViewById(R.id.tilCourseCode);

        ArrayAdapter<String> coursesArrayAdapter = new ArrayAdapter<>(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                courseList);
        AutoCompleteTextView courseCodes
                = filterDialogView.findViewById(R.id.courseCodeField);

        CheckBox courseSwitch
                = filterDialogView.findViewById(R.id.courseSwitch);

        courseCodes.setAdapter(coursesArrayAdapter);
        courseCodes.setThreshold(1);
        courseCodes.setLongClickable(true);
        courseCodes.setOnLongClickListener(v -> {
            courseCodes.setText("");
            selectedCourse = null;
            tutorFilter.clearCourseCode();
            return true;
        });
        courseCodes.setOnItemClickListener((p, v, pos, id) -> {
            courseCodes.clearFocus();
            selectedCourse = courseList.get(pos);
            tutorFilter.setCourseCode(selectedCourse);
        });

        if (selectedCourse != null) {
            courseCodes.setText(selectedCourse);
        }

        courseSwitch.setOnCheckedChangeListener((b, checked) -> {
            tilCourseCodes.setEnabled(checked);
            if (!checked) {
                tutorFilter.clearCourseCode();
                selectedCourse = null;
            }
        });
        courseSwitch.setChecked(tutorFilter.getCourseCodeState());
        tilCourseCodes.setEnabled(tutorFilter.getCourseCodeState());
    }

    private boolean onSearchClick(EditText searchEditText)
    {
        String searchString = searchEditText.getText().toString().trim();
        tutorFilter = searchString.isEmpty() ?
                      tutorFilter.resetSearchString() :
                      tutorFilter.setSearchFilter(searchString);
        searchViewModel.postTutorsFiltered(tutorFilter
                                                   .filterFunc()
                                                   .apply(tutorList));
        return true;
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