package comp3350.teachreach.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.ITutor;
import comp3350.teachreach.presentation.enums.SortCriteria;
import comp3350.teachreach.presentation.profile.TutorProfileActivity;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface, SortDialogFragment.SortDialogListener {

    private SearchSortHandler handler;
    private ArrayList<ITutor> tutorList;
    private ArrayList<String> courseStringList;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.searchResultRecyclerView);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Button sortButton = findViewById(R.id.sortButton);

        handler = new SearchSortHandler();
        tutorList = new ArrayList<>();
        courseStringList = new ArrayList<>();

        setUpCourseList();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseStringList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(2);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.clearFocus();
                String selectedCourse = (String) parent.getItemAtPosition(position);

                updateTutorList(selectedCourse);
            }
        });

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        populateTutors();

        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, tutorList, this);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCourseList() {
        ArrayList<Course> courses = handler.getListOfCourses();
        for (int i = 0; i < courses.size(); i++) {
            courseStringList.add(courses.get(i).getCourseCode());
        }

    }

    private void populateTutors() {
        tutorList = handler.getListOfTutors();
    }

    private void updateTutorList(String selectedCourse) {
        tutorList = handler.searchTutorByCourse(selectedCourse);
        // needs to use DIffUtil to improve efficiency
        searchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void sortTutors(SortCriteria sortCriteria) throws Exception {

        switch (sortCriteria) {
            case HIGHEST_RATING:
//                tutorList = handler.getTutorsByHighestRating();
                break;
            case HOURLY_RATE_ASCENDING:
//                tutorList = handler.getTutorsByHourlyRateAsc();
                break;
            case HOURLY_RATE_DESCENDING:
//                tutorList = handler.getTutorsByHourlyRateDesc();
                break;
            default:
                throw new Exception("Unable to handle sort critera: " + sortCriteria);
        }

        // needs to use DIffUtil to improve efficiency
        searchRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onTutorItemClick(int position) {
        Intent intent = new Intent(this, TutorProfileActivity.class);
        intent.putExtra("TUTOR_EMAIL_KEY", tutorList.get(position).getOwner().getEmail());
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void openDialog() {
        SortDialogFragment sortDialogFragment = new SortDialogFragment();
        sortDialogFragment.show(getSupportFragmentManager(), "Sort Dialog");
    }

    @Override
    public void applySort(SortCriteria sortCriteria) {

    }
}