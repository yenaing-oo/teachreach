package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import comp3350.teachreach.data.IAccountPersistence;
import comp3350.teachreach.data.ITutorPersistence;
import comp3350.teachreach.objects.IAccount;
import comp3350.teachreach.objects.ITutor;

public class TutorStub implements ITutorPersistence {

    IAccountPersistence accountsDataAccess;
    ArrayList<ITutor> tutors;

    public TutorStub(IAccountPersistence accounts) {
        accountsDataAccess = accounts;
        tutors = new ArrayList<>();
    }

    @Override
    public ITutor storeTutor(ITutor newTutor) throws RuntimeException {
        if (accountsDataAccess.getAccountByEmail(
                newTutor.getOwner().getEmail()).isPresent()) {
            tutors.add(newTutor);
            return newTutor;
        } else {
            throw new RuntimeException(
                    "Failed to store new Tutor profile:-" +
                            "(Associated account not found)");
        }
    }

    @Override
    public ITutor updateTutor(ITutor newTutor) {
        Optional<ITutor> maybeTutor =
                getTutorByEmail(newTutor.getOwner().getEmail());

        maybeTutor.ifPresent(tutor -> {
            tutor
                    .setHourlyRate(newTutor.getHourlyRate())
                    .renewAvailability(newTutor.getAvailability())
                    .renewPreferredAvailability(newTutor.getPreferredAvailability())
                    .setReviewCount(newTutor.getReviewCount())
                    .setReviewTotal(newTutor.getReviewTotalSum());

            tutor.setName(newTutor.getName());
            tutor.setMajor(newTutor.getMajor());
            tutor.setPronouns(newTutor.getPronouns());
        });

        return maybeTutor.orElseThrow(() -> new RuntimeException(
                "Failed to update Tutor profile:-" +
                        "(Tutor not found)"));
    }

    @Override
    public Optional<ITutor> getTutorByEmail(String email) throws NullPointerException {
        return accountsDataAccess
                .getAccountByEmail(email)
                .flatMap(IAccount::getTutorProfile);
    }

    @Override
    public ArrayList<ITutor> getTutors() {
        return this.tutors;
    }

    @Override
    public ArrayList<ITutor> getTutorsByName(String name) {
        return tutors
                .stream()
                .filter(tutor -> tutor.getName().contains(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
