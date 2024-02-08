package comp3350.teachreach.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.SearchSortHandler;
import comp3350.teachreach.objects.Course;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.presentation.models.TutorModel;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {

    SearchSortHandler handler;
    private ArrayList<TutorModel> tutorModelList;
    private ArrayList<Course> courseList;
    private RecyclerView recyclerView;
    private SearchRecyclerViewAdapter adapter;
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayAdapter<Course> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.searchResultRecyclerView);
        autoCompleteTextView.findViewById(R.id.autoCompleteTextView);

//        arrayAdapter = new ArrayAdapter<>(this, );

        tutorModelList = new ArrayList<>();
        handler = new SearchSortHandler();
        setUpTutorModels();

        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(this, tutorModelList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpTutorModels() {
        ArrayList<Tutor> tutors = handler.getListOfTutors();

        for (int i = 0; i < tutors.size(); i++) {
            tutorModelList.add(new TutorModel(tutors.get(i)));
        }

    }

    @Override
    public void onTutorItemClick(int position) {
        Intent intent = new Intent(this, TutorProfileActivity.class);
        startActivity(intent);
    }

}