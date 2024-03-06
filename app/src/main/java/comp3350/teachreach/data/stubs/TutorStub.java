package comp3350.teachreach.data.stubs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import comp3350.teachreach.data.interfaces.IAccountPersistence;
import comp3350.teachreach.data.interfaces.ITutorPersistence;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.objects.interfaces.ITutor;

public
class TutorStub implements ITutorPersistence
{

    AccessAccounts accessAccounts;
    List<ITutor>   tutors;

    public
    TutorStub(IAccountPersistence accountPersistence)
    {
        accessAccounts = new AccessAccounts(accountPersistence);
        tutors         = new ArrayList<>();
    }

    @Override
    public
    ITutor storeTutor(ITutor newTutor) throws RuntimeException
    {
        if (accessAccounts.getAccountByEmail(newTutor.getEmail()) != null) {
            tutors.add(newTutor);
            return newTutor;
        } else {
            throw new RuntimeException("Failed to store new Tutor profile:-" +
                                       "(Associated account not found)");
        }
    }

    @Override
    public
    ITutor updateTutor(ITutor newTutor)
    {
        Optional<ITutor> maybeTutor = getTutorByEmail(newTutor.getEmail());

        maybeTutor.ifPresent(tutor -> {
            tutor
                    .setHourlyRate(newTutor.getHourlyRate())
                    .setPreferredAvailability(newTutor.getPreferredAvailability())
                    .setReviewCount(newTutor.getReviewCount())
                    .setReviewTotal(newTutor.getReviewTotalSum());

            tutor.setName(newTutor.getUserName());
            tutor.setMajor(newTutor.getUserMajor());
            tutor.setPronouns(newTutor.getUserPronouns());
        });

        return maybeTutor.orElseThrow(() -> new RuntimeException(
                "Failed to update Tutor profile:-" + "(Tutor not found)"));
    }

    @Override
    public
    Optional<ITutor> getTutorByEmail(String email) throws NullPointerException
    {
        return tutors
                .stream()
                .filter(t -> t.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public
    List<ITutor> getTutors()
    {
        return this.tutors;
    }

    @Override
    public
    List<ITutor> getTutorsByName(String name)
    {
        return tutors
                .stream()
                .filter(tutor -> tutor.getUserName().contains(name))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
