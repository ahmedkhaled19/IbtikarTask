package khaled.ahmed.ibtikartask.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;

/**
 * Created by ah.khaled1994@gmail.com on 4/2/2018.
 */

public class SharedData {

    private static final SharedData ourInstance = new SharedData();

    /*** shared pref tags*/
    private String isLogged = "isLogged";
    private String saved_user = "saved_user";
    private String TOKEN = "TOKEN";
    private String LANGUAGE = "LANGUAGE";
    private String UID = "UID";
    private String NAME = "NAME";
    private String PIMAGE = "PIMAGE";
    private String BACKIMAGE = "BACKIMAGE";
    private String SECRET = "SECRET";
    private String FIRST_TIME = "FT";
    private String ACCOUNTS = "ACCOUNT";

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

    public void saveUsers(ArrayList<Users> users) {
        Gson gson = new Gson();
        String userStr = gson.toJson(users);
        editor.putString(saved_user, userStr);
        editor.apply();
    }

    public ArrayList<Users> getUsers() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Users>>() {
        }.getType();
        String json = preferences.getString(saved_user, "");
        return gson.fromJson(json, type);
    }

    public void saveAccount(ArrayList<Users> users) {
        Gson gson = new Gson();
        String userStr = gson.toJson(users);
        editor.putString(ACCOUNTS, userStr);
        editor.apply();
    }

    public ArrayList<Users> getAccount() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Users>>() {
        }.getType();
        String json = preferences.getString(ACCOUNTS, "");
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

    public String getToken() {
        return preferences.getString(TOKEN, "");
    }

    public void setToken(String uid) {
        editor.putString(TOKEN, uid);
        editor.apply();
    }

    public String getID() {
        return preferences.getString(UID, "");
    }

    public void setID(String uid) {
        editor.putString(UID, uid);
        editor.apply();
    }

    public String getNAME() {
        return preferences.getString(NAME, "");
    }

    public void setNAME(String name) {
        editor.putString(NAME, name);
        editor.apply();
    }

    public String getProfile() {
        return preferences.getString(PIMAGE, "");
    }

    public void setPIMAGE(String url) {
        editor.putString(PIMAGE, url);
        editor.apply();
    }

    public String getBackgroundProfile() {
        return preferences.getString(BACKIMAGE, "");
    }

    public void setBackgroundIMAGE(String url) {
        editor.putString(BACKIMAGE, url);
        editor.apply();
    }

    public String getSECRETToken() {
        return preferences.getString(SECRET, "");
    }

    public void setSecretToken(String secretToken) {
        editor.putString(SECRET, secretToken);
        editor.apply();
    }

    public Boolean isFirstTime() {
        return preferences.getBoolean(FIRST_TIME, true);
    }

    public void setFirstTime(Boolean flag) {
        editor.putBoolean(FIRST_TIME, flag);
        editor.apply();
    }

}
