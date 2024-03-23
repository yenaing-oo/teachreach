package comp3350.teachreach.presentation.booking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.threeten.bp.LocalDate;

public class BookingViewModel extends ViewModel
{
    MutableLiveData<LocalDate> bookingDate = new MutableLiveData<>();

    public LiveData<LocalDate> getBookingDate()
    {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate)
    {
        this.bookingDate.postValue(bookingDate);
    }
}
