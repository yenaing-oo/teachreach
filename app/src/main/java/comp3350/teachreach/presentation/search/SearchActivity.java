package comp3350.teachreach.presentation.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessTutors;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.logic.interfaces.ISearchSortHandler;
import comp3350.teachreach.objects.interfaces.ICourse;
import comp3350.teachreach.objects.interfaces.ITutor;
import comp3350.teachreach.presentation.communication.Groups.GroupsActivity;
import comp3350.teachreach.presentation.enums.SortCriteria;
import comp3350.teachreach.presentation.profile.StudentProfileActivity;
import comp3350.teachreach.presentation.profile.TutorProfileViewFragment;

public class SearchActivity extends AppCompatActivity
        implements ITutorRecyclerView, SortDialogFragment.SortDialogListener
{
    private NavigationBarView         navigationMenu;
    private ISearchSortHandler        searchSortHandler;
    private List<ITutor>              tutors;
    private List<ICourse>             courses;
    private List<String>              courseStringList;
    private SearchRecyclerViewAdapter searchRecyclerViewAdapter;
    private AutoCompleteTextView      autoCompleteTextView;
    private long                      backPressedTime;
    private int                       accountID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        accountID = getIntent().getIntExtra("ACCOUNT_ID", -1);
        RecyclerView recyclerView = findViewById(R.id.searchResultRecyclerView);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Button sortButton = findViewById(R.id.sortButton);
        AccessTutors accessTutors = new AccessTutors();

        searchSortHandler = new SearchSortHandler();
        tutors            = new ArrayList<>(accessTutors.getTutors().values());
        courseStringList  = new ArrayList<>();

        setUpNavigationMenu();
        setUpCourseList();
        setUpAutoCompleteTextView();
        sortButton.setOnClickListener(v -> openDialog());
        populateTutors();
        setUpRecyclerView(recyclerView);
        setUpBackButtonHandler();

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(
                true)
        {
            @Override
            public void handleOnBackPressed()
            {
                backIsPressed();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void setUpCourseList()
    {
        courses = searchSortHandler.getCourses();
        for (int i = 0; i < courses.size(); i++) {
            courseStringList.add(courses.get(i).getCourseCode());
        }
    }

    private void setUpNavigationMenu()
    {
        navigationMenu.setSelectedItemId(R.id.NavBarSearch);
        navigationMenu.setOnItemSelectedListener(i -> {
            int itemId = i.getItemId();
            if (itemId == R.id.NavBarSessions) {
                return true;
            } else if (itemId == R.id.NavBarSearch) {
                return true;
            } else if (itemId == R.id.NavBarProfile) {
                Intent intent = new Intent(this, StudentProfileActivity.class);
                intent.putExtra("ACCOUNT_ID", accountID);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.NavBarChats) {
                Intent intent = new Intent(this, GroupsActivity.class);
                intent.putExtra("ACCOUNT_ID", accountID);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    private void setUpAutoCompleteTextView()
    {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                                                               android.R.layout.simple_list_item_1,
                                                               courseStringList);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setOnItemClickListener((parent, view, position,
                                                     id) -> {
            autoCompleteTextView.clearFocus();
            ICourse selectedCourse = courses.get(position);
            updateTutorList(selectedCourse);
        });
    }

    private void populateTutors()
    {
        tutors = searchSortHandler.getTutors();
    }

    private void updateTutorList(ICourse selectedCourse)
    {
        tutors = searchSortHandler.getTutorsByCourse(selectedCourse);
        // needs to use DIffUtil to improve efficiency
        searchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void setUpRecyclerView(RecyclerView recyclerView)
    {
        searchRecyclerViewAdapter = new SearchRecyclerViewAdapter(this,
                                                                  tutors,
                                                                  this);
        recyclerView.setAdapter(searchRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpBackButtonHandler()
    {
        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(
                true /* enabled by default */)
        {
            @Override
            public void handleOnBackPressed()
            {
                backIsPressed();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);
    }

    private void sortTutors(SortCriteria sortCriteria)
    {

        switch (sortCriteria) {
            case HIGHEST_RATING:
                tutors = searchSortHandler.getTutorsByRating();
                break;
            case HOURLY_RATE_ASCENDING:
                tutors = searchSortHandler.getTutorsByHourlyRateAsc();
                break;
            case HOURLY_RATE_DESCENDING:
                tutors = searchSortHandler.getTutorsByHourlyRateDesc();
                break;
            default:
                break;
        }

        // needs to use DIffUtil to improve efficiency
        searchRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTutorItemClick(int position)
    {
        Intent intent = new Intent(this, TutorProfileViewFragment.class);
        intent.putExtra("TUTOR_ID", tutors.get(position).getTutorID());
        startActivity(intent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(),
                                      (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm
                            =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void openDialog()
    {
        SortDialogFragment sortDialogFragment = new SortDialogFragment();
        sortDialogFragment.show(getSupportFragmentManager(), "Sort Dialog");
    }

    @Override
    public void applySort(SortCriteria sortCriteria)
    {
        sortTutors(sortCriteria);
    }

    private void backIsPressed()
    {
        // Check if back button was pressed within a certain interval (e.g.,
        // 2 seconds)
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            // If back button was pressed within the interval, exit the app
            finishAffinity();
        } else {
            // Otherwise, display a toast message indicating to press back
            // again to exit
            Toast
                    .makeText(this,
                              "Press back again to exit",
                              Toast.LENGTH_SHORT)
                    .show();
        }
        // Update the back press time
        backPressedTime = System.currentTimeMillis();
    }
}