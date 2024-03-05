package comp3350.teachreach.data.interfaces;

import java.util.List;

import comp3350.teachreach.objects.TimeSlice;
import comp3350.teachreach.objects.interfaces.IAccount;

public interface IAccountPersistence {

    IAccount storeAccount(IAccount newAccount);

    boolean updateAccount(IAccount existingAccount);

    List<IAccount> getAccounts();

    List<String> getTutorLocations(int tutorID);

    List<TimeSlice> getTutorAvailability(int tutorID);
}
