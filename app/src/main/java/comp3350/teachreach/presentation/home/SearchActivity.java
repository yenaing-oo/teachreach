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
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.presentation.profile.TutorProfileActivity;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {

    private SearchSortHandler handler;
    private ArrayList<Tutor> tutorList;
    private ArrayList<Course> courseList;
    private ArrayList<String> courseCodeList;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchResultRecyclerView);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        handler = new SearchSortHandler();
        courseCodeList = new ArrayList<>();

        setUpCourseList();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseCodeList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(2);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                autoCompleteTextView.clearFocus();
                Course selectedCourse = courseList.get(position);

                updateTutorModelList(selectedCourse);
            }
        });


        setUpTutorModels();

        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, tutorList, this);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpCourseList() {
        courseList = handler.getListOfCourses();
        for (int i = 0; i < courseList.size(); i++) {
            courseCodeList.add(courseList.get(i).getCourseCode());
        }

    }

    private void setUpTutorModels() {
        tutorList = handler.getListOfTutors();
    }

    private void updateTutorModelList(Course selectedCourse) {
        tutorList.clear();
        tutorList = handler.searchTutorByCourse(selectedCourse);
        // needs to use DIffUtil to improve efficiency
        searchRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTutorItemClick(int position) {
        Intent intent = new Intent(this, TutorProfileActivity.class);
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
}