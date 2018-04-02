package khaled.ahmed.ibtikartask.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.User;

/**
 * Created by ah.khaled1994@gmail.com on 4/2/2018.
 */

public class SharedData {

    private static final SharedData ourInstance = new SharedData();

    /*** shared pref tags*/
    public static String isLogged = "isLogged";
    public static String saved_user = "saved_user";
    public static String UID = "UID";
    public static String LANGUAGE = "LANGUAGE";

    /*** shared pref tools*/
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private SharedData() {
    }

    public static SharedData getInstance() {
        return ourInstance;
    }

    public SharedData Edit(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences("STORAGE", Context.MODE_PRIVATE);
        editor = preferences.edit();
        return ourInstance;
    }

    public void saveUser(ArrayList<User> user) {
        Gson gson = new Gson();
        String userStr = gson.toJson(user);
        editor.putString(saved_user, userStr);
        editor.apply();
    }

    public ArrayList<User> getUser() {
        Gson gson = new Gson();
        Type type = new TypeToken<User>() {
        }.getType();
        String json = preferences.getString(saved_user, "");
        return gson.fromJson(json, type);
    }

    public void setIsLogged(boolean state) {
        editor.putBoolean(isLogged, state);
        editor.apply();
    }

    public boolean IsLogged() {
        return preferences.getBoolean(isLogged, false);
    }

    public String getLang() {
        return preferences.getString(LANGUAGE, "en");
    }

    public void setLang(String lang) {
        editor.putString(LANGUAGE, lang);
        editor.apply();
    }

    public String getUID() {
        return preferences.getString(UID, "");
    }

    public void setUID(String uid) {
        editor.putString(UID, uid);
        editor.apply();
    }
}
