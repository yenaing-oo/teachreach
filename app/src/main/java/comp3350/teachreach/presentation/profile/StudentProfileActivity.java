package comp3350.teachreach.presentation.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.NoSuchElementException;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.DAOs.AccessAccounts;
import comp3350.teachreach.logic.interfaces.IUserProfileHandler;
import comp3350.teachreach.objects.interfaces.IAccount;
import comp3350.teachreach.presentation.search.SearchActivity;

public class StudentProfileActivity extends AppCompatActivity
{
    private static final int      EDIT_PROFILE_REQUEST = 1;
    private              TextView tvName, tvPronoun, tvMajor;
    private NavigationBarView   navigationMenu;
    private Button              btnEditProfile;
    private AccessAccounts      accessAccounts;
    private IUserProfileHandler userProfile;
    private int                 accountID;

    private void initializeViews()
    {
        tvName         = findViewById(R.id.tvName);
        tvPronoun      = findViewById(R.id.tvPronouns);
        tvMajor        = findViewById(R.id.tvMajor);
        btnEditProfile = findViewById(R.id.fabEditProfile);
    }

    private void extractDataFromIntent()
    {
        Intent intent = getIntent();
        if (intent == null) {
            throw new NullPointerException();
        }

        accountID = intent.getIntExtra("ACCOUNT_ID", -1);
        IAccount account = accessAccounts.getAccounts().get(accountID);

        if (account == null) {
            throw new NoSuchElementException();
        }

        updateProfileViews(account.getUserName(),
                           account.getUserPronouns(),
                           account.getUserMajor());
    }

    private void updateProfileViews(String name, String pronoun, String major)
    {
        tvName.setText(name != null ? name : "Name not provided");
        tvPronoun.setText(pronoun != null ? pronoun : "Pronoun not provided");
        tvMajor.setText(major != null ? major : "Major not provided");
    }

    private void navigateToSearch()
    {
        Intent searchIntent = new Intent(StudentProfileActivity.this,
                                         SearchActivity.class);
        startActivity(searchIntent);
    }

    private void setupEditProfileButton()
    {
        btnEditProfile.setOnClickListener(v -> startEditProfileActivity());
    }

    private void startEditProfileActivity()
    {
        Intent editIntent = new Intent(this, EditUserProfileActivity.class);
        editIntent.putExtra("ACCOUNT_ID", accountID);
        startActivityForResult(editIntent, EDIT_PROFILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST && resultCode == RESULT_OK &&
            data != null) {
            String updatedName    = data.getStringExtra("UPDATED_NAME");
            String updatedPronoun = data.getStringExtra("UPDATED_PRONOUN");
            String updatedMajor   = data.getStringExtra("UPDATED_MAJOR");
            updateProfileViews(updatedName, updatedPronoun, updatedMajor);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        accessAccounts = new AccessAccounts();

        initializeViews();
        extractDataFromIntent();
        setupEditProfileButton();
        BottomNavigationView bottomNavigationView
                = findViewById(R.id.navigation_menu);
        ExtendedFloatingActionButton extendedFab
                = findViewById(R.id.fabEditProfile);

        final int bottomNavHeight = bottomNavigationView.getHeight();
        final ViewGroup.MarginLayoutParams layoutParams
                = (ViewGroup.MarginLayoutParams) extendedFab.getLayoutParams();
        layoutParams.setMargins(layoutParams.leftMargin,
                                layoutParams.topMargin,
                                layoutParams.rightMargin,
                                bottomNavHeight +
                                getResources().getDimensionPixelSize(R.dimen.fab_margin));
        extendedFab.setLayoutParams(layoutParams);
    }
}
