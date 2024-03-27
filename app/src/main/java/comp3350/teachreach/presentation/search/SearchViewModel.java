package comp3350.teachreach.presentation.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import comp3350.teachreach.application.Server;
import comp3350.teachreach.logic.TutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorFilter;
import comp3350.teachreach.logic.interfaces.ITutorProfileHandler;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.logic.profile.TutorProfileHandler;
import comp3350.teachreach.logic.profile.UserProfileFetcher;
import comp3350.teachreach.objects.interfaces.ITutor;

public class SearchViewModel extends ViewModel {
    private final IUserProfileHandler<ITutor> userFetcher = new UserProfileFetcher<>();
    private final ITutorProfileHandler tutorFetcher = new TutorProfileHandler();
    private final ITutorFilter tutorFilter = new TutorFilter(userFetcher, tutorFetcher);

    private final MutableLiveData<Boolean> sortingByPrice = new MutableLiveData<>(
            tutorFilter.getSortByPriceState());
    private final MutableLiveData<Boolean> sortingByReviews = new MutableLiveData<>(
            tutorFilter.getSortByReviewsState());
    private final MutableLiveData<Boolean> filteringByMinPrice = new MutableLiveData<>(
            tutorFilter.getMinimumHourlyRateState());
    private final MutableLiveData<Boolean> filteringByMaxPrice = new MutableLiveData<>(
            tutorFilter.getMaximumHourlyRateState());
    private final MutableLiveData<Boolean> filteringByRatings = new MutableLiveData<>(
            tutorFilter.getMinimumAvgRatingState());
    private final MutableLiveData<Boolean> filteringByCourse = new MutableLiveData<>(
            tutorFilter.getCourseCodeState());
    private final List<ITutor> tutors = new CopyOnWriteArrayList<>(
            Server.getTutorDataAccess().getTutors().values());
    private final List<String> courses = new CopyOnWriteArrayList<>(
            Server.getCourseDataAccess().getCourses().keySet());
    private final MutableLiveData<List<ITutor>> tutorsFiltered = new MutableLiveData<>();

    public LiveData<Boolean> getSortingByPrice() {
        return sortingByPrice;
    }

    public LiveData<Boolean> getSortingByReviews() {
        return sortingByReviews;
    }

    public LiveData<Boolean> getFilteringByCourse() {
        return filteringByCourse;
    }

    public LiveData<Boolean> getFilteringByMaxPrice() {
        return filteringByMaxPrice;
    }

    public LiveData<Boolean> getFilteringByMinPrice() {
        return filteringByMinPrice;
    }

    public LiveData<Boolean> getFilteringByRatings() {
        return filteringByRatings;
    }

    public LiveData<List<ITutor>> getTutorsFiltered() {
        return tutorsFiltered;
    }

    public void resetFilter() {
        tutorsFiltered.postValue(tutorFilter.Reset().filterFunc().apply(tutors));
        sortingByReviews.postValue(tutorFilter.getSortByReviewsState());
        sortingByPrice.postValue(tutorFilter.getSortByPriceState());
        filteringByMaxPrice.postValue(tutorFilter.getMaximumHourlyRateState());
        filteringByMinPrice.postValue(tutorFilter.getMinimumHourlyRateState());
        filteringByRatings.postValue(tutorFilter.getMinimumAvgRatingState());
        filteringByCourse.postValue(tutorFilter.getCourseCodeState());
    }

    public void setSortByReviews() {
        tutorsFiltered.postValue(tutorFilter.setSortByReviews().filterFunc().apply(tutors));
        sortingByReviews.postValue(tutorFilter.getSortByReviewsState());
    }

    public void resetSortByReviews() {
        tutorsFiltered.postValue(tutorFilter.clearSortByReviews().filterFunc().apply(tutors));
        sortingByReviews.postValue(tutorFilter.getSortByReviewsState());
    }

    public void setSortByPrice() {
        tutorsFiltered.postValue(tutorFilter.setSortByPrice().filterFunc().apply(tutors));
        sortingByPrice.postValue(tutorFilter.getSortByPriceState());
    }

    public void resetSortByPrice() {
        tutorsFiltered.postValue(tutorFilter.clearSortByPrice().filterFunc().apply(tutors));
        sortingByPrice.postValue(tutorFilter.getSortByPriceState());
    }

    public void setMinimumAvgRating(double minimumAvgRating) {
        tutorFilter.setMinimumAvgRating(minimumAvgRating);
        filteringByRatings.postValue(tutorFilter.getMinimumAvgRatingState());
    }

    public void setMinimumHourlyRate(double desiredHourlyRate) {
        tutorFilter.setMinimumHourlyRate(desiredHourlyRate);
        filteringByMinPrice.postValue(tutorFilter.getMinimumHourlyRateState());
    }

    public void setMaximumHourlyRate(double desiredHourlyRate) {
        tutorFilter.setMaximumHourlyRate(desiredHourlyRate);
        filteringByMaxPrice.postValue(tutorFilter.getMaximumHourlyRateState());
    }

    public void setCourseCode(String courseCode) {
        tutorFilter.setCourseCode(courseCode);
        filteringByCourse.postValue(tutorFilter.getCourseCodeState());
    }

    public void setSearchFilter(String searchString) {
        tutorsFiltered.postValue(
                tutorFilter.setSearchFilter(searchString).filterFunc().apply(tutors));
    }

    public void resetSearchString() {
        tutorsFiltered.postValue(tutorFilter.resetSearchString().filterFunc().apply(tutors));
    }

    public void clearMinimumAvgRating() {
        tutorFilter.clearMinimumAvgRating();
        filteringByRatings.postValue(tutorFilter.getMinimumAvgRatingState());
    }

    public void clearMinimumHourlyRate() {
        tutorFilter.clearMinimumHourlyRate();
        filteringByMinPrice.postValue(tutorFilter.getMinimumHourlyRateState());
    }

    public void clearMaximumHourlyRate() {
        tutorFilter.clearMaximumHourlyRate();
        filteringByMaxPrice.postValue(tutorFilter.getMaximumHourlyRateState());
    }

    public void clearCourseCode() {
        tutorFilter.clearCourseCode();
        filteringByCourse.postValue(tutorFilter.getCourseCodeState());
    }

    public List<String> getCourses() {
        return courses;
    }

    public List<ITutor> getTutors() {
        return tutors;
    }

    public IUserProfileHandler<ITutor> getUserFetcher() {
        return userFetcher;
    }

    public ITutorProfileHandler getTutorFetcher() {
        return tutorFetcher;
    }
}
