package comp3350.teachreach.presentation.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.objects.interfaces.ITutor;

public class SearchViewModel extends ViewModel
{
    private final MutableLiveData<List<ITutor>> tutors = new MutableLiveData<>(
            new ArrayList<>(Server.getTutorDataAccess().getTutors().values()));

    private final MutableLiveData<List<ITutor>> tutorsFiltered
            = new MutableLiveData<>();

    public LiveData<List<ITutor>> getTutorsFiltered()
    {
        return tutorsFiltered;
    }

    public void setTutorsFiltered(List<ITutor> tutors)
    {
        tutorsFiltered.setValue(tutors);
    }

    public void postTutorsFiltered(List<ITutor> tutors)
    {
        tutorsFiltered.postValue(tutors);
    }

    public LiveData<List<ITutor>> getTutors()
    {
        return tutors;
    }

    public void postTutors(List<ITutor> l)
    {
        this.tutors.postValue(l);
    }
}
