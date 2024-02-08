package comp3350.teachreach.presentation;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.BookingHandler;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.presentation.models.TutorModel;

public class SearchActivity extends AppCompatActivity implements RecyclerViewInterface {

    ArrayList<TutorModel> tutorModelList;
    BookingHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView recyclerView = findViewById(R.id.searchResultRecyclerView);

        tutorModelList = new ArrayList<>();
        handler = new BookingHandler();
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