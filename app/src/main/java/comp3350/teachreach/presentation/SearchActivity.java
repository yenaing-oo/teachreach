package comp3350.teachreach.presentation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.BookingHandler;
import comp3350.teachreach.objects.Tutor;
import comp3350.teachreach.presentation.models.TutorModel;

public class SearchActivity extends AppCompatActivity {

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

        SearchRecyclerViewAdapter adapter = new SearchRecyclerViewAdapter(this, tutorModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpTutorModels() {
//        String[] tutorNames = getResources().getStringArray(R.array.user_names);
//        String[] ratings = getResources().getStringArray(R.array.user_ratings);
//        String[] hourlyRates = getResources().getStringArray(R.array.user_hourly_rates);

        ArrayList<Tutor> tutors = handler.getListofTutor();

        for (int i = 0; i < tutors.size(); i++) {
            tutorModelList.add(new TutorModel(tutors.get(i)));
        }

    }
}