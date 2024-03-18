package comp3350.teachreach.application;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Map;

public
class TRData {
    private static String dbName = "TR";
    private static Map<String, Integer> SessionStatus;

    public static String getDBPathName() {
        return dbName;
    }

    public static void setDBPathName(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    public static void loadEnums(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("enums/enums_config.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();

            Gson gson = new Gson();
            Type mapType = new TypeToken<Map<String, Map<String, Integer>>>() {
            }.getType();
            Map<String, Map<String, Integer>> enumMap = gson.fromJson(stringBuilder.toString(), mapType);
            SessionStatus = enumMap.get("statusEnums");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getSessionStatusEnumValue(String key) {
        if (SessionStatus == null) {
            throw new IllegalStateException("Enums have not been loaded yet");
        }
        Integer value = SessionStatus.get(key);
        if (value == null) {
            throw new IllegalArgumentException("No value found for key: " + key);
        }
        return value;
    }

}
