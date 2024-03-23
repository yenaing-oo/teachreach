package comp3350.teachreach.presentation.communication.Groups;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import comp3350.teachreach.R;
import comp3350.teachreach.logic.interfaces.IMessageHandler;
import comp3350.teachreach.objects.interfaces.IAccount;

public class GroupsActivity extends AppCompatActivity {

    //private NavigationBarView navigationMenu;
    private IMessageHandler messageHandler;
    private List<IAccount> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
    }
}