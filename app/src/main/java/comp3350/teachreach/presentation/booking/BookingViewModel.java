package comp3350.teachreach.presentation.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.threeten.bp.LocalDate;

import java.util.List;

import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.objects.interfaces.ITimeSlice;
import comp3350.teachreach.objects.interfaces.ITutor;

public class BookingViewModel extends ViewModel
{
    MutableLiveData<IAccount> tutorAccount = new MutableLiveData<>();
    MutableLiveData<ITutor>   tutor        = new MutableLiveData<>();

    MutableLiveData<LocalDate> bookingDate = new MutableLiveData<>();

    MutableLiveData<List<ITimeSlice>> timeSlots = new MutableLiveData<>();

    MutableLiveData<ITimeSlice> sessionTime = new MutableLiveData<>();

    public LiveData<LocalDate> getBookingDate()
    {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate)
    {
        this.bookingDate.postValue(bookingDate);
    }

    public LiveData<ITimeSlice> getSessionTime()
    {
        return sessionTime;
    }

    public void setSessionTime(ITimeSlice sessionTime)
    {
        this.sessionTime.postValue(sessionTime);
    }

    public LiveData<List<ITimeSlice>> getTimeSlots()
    {
        return timeSlots;
    }

    public void setTimeSlots(List<ITimeSlice> timeSlots)
    {
        this.timeSlots.postValue(timeSlots);
    }

    public LiveData<IAccount> getTutorAccount()
    {
        return tutorAccount;
    }

    public void setTutorAccount(IAccount tutorAccount)
    {
        this.tutorAccount.postValue(tutorAccount);
    }

    public LiveData<ITutor> getTutor()
    {
        return tutor;
    }

    public void setTutor(ITutor tutor)
    {
        this.tutor.postValue(tutor);
    }
}
