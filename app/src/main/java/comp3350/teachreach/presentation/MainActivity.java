package comp3350.teachreach.presentation;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import comp3350.teachreach.R;
import comp3350.teachreach.application.TRData;
import comp3350.teachreach.presentation.login.StudentLoginActivity;
import comp3350.teachreach.presentation.login.TutorLoginActivity;

public
class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyDatabaseToDevice();
        TRData.loadEnums(getApplicationContext());
        AndroidThreeTen.init(this);

        Button btnStudent = findViewById(R.id.btnStudentLogin);
        Button btnTutor   = findViewById(R.id.btnTutorLogin);

        CharSequence charStudent = "Student";
        CharSequence charTutor   = "Tutor";
        btnStudent.setText(charStudent);
        btnTutor.setText(charTutor);

        btnStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);

            startActivity(intent);
        });

        btnTutor.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TutorLoginActivity.class);
            startActivity(intent);
        });
    }

    private void copyDatabaseToDevice() {
        final String DB_PATH = "db";

        String[]     assetNames;
        Context      context       = getApplicationContext();
        File         dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager  = getAssets();

        try {
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < Objects.requireNonNull(assetNames).length; i++) {
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }

            copyAssetsToDirectory(assetNames, dataDirectory);

            TRData.setDBPathName(dataDirectory.toString() + "/" + TRData.getDBPathName());
        } catch (final IOException ioe) {
            Toast.makeText(this, "Unable to access application data: " + ioe.getMessage(),
                           Toast.LENGTH_SHORT).show();
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
        AssetManager assetManager = getAssets();

        for (String asset : assets) {
            String[] components = asset.split("/");
            String   copyPath   = directory.toString() + "/" + components[components.length - 1];

            char[] buffer = new char[1024];
            int    count;

            File outFile = new File(copyPath);

            if (!outFile.exists()) {
                InputStreamReader in  = new InputStreamReader(assetManager.open(asset));
                FileWriter        out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1) {
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }
}