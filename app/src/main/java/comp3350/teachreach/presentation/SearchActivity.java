package comp3350.teachreach.presentation;

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
import comp3350.teachreach.R;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.presentation.models.TutorModel;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {

  private SearchSortHandler handler;
  private ArrayList<TutorModel> tutorModelList;
  private ArrayList<String> courseList;
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
    tutorModelList = new ArrayList<>();
    courseList = new ArrayList<>();

    setUpCourseList();
    arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
    autoCompleteTextView.setAdapter(arrayAdapter);
    autoCompleteTextView.setThreshold(2);

    autoCompleteTextView.setOnItemClickListener(
        new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            autoCompleteTextView.clearFocus();
            String selectedCourse = (String) parent.getItemAtPosition(position);

            updateTutorModelList(selectedCourse);
          }
        });

    setUpTutorModels();

    searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this, tutorModelList, this);
    recyclerView.setAdapter(searchRecyclerViewAdapter);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
  }

  private void setUpCourseList() {
    ArrayList<Course> courses = handler.getListOfCourses();
    for (int i = 0; i < courses.size(); i++) {
      courseList.add(courses.get(i).getCourseCode());
    }
  }

  private void setUpTutorModels() {
    ArrayList<Tutor> tutors = handler.getListOfTutors();

    for (int i = 0; i < tutors.size(); i++) {
      tutorModelList.add(new TutorModel(tutors.get(i)));
    }
  }

  private void updateTutorModelList(String selectedCourse) {
    tutorModelList.clear();
    ArrayList<Tutor> newTutorList = handler.searchTutorByCourse(selectedCourse);
    for (int i = 0; i < newTutorList.size(); i++) {
      tutorModelList.add(new TutorModel(newTutorList.get(i)));
    }
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
          InputMethodManager imm =
              (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
      }
    }
    return super.dispatchTouchEvent(event);
  }
}
