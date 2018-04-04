package khaled.ahmed.ibtikartask.Utils;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;

import com.ahmedjazzar.rosetta.LanguageSwitcher;

import java.util.HashSet;
import java.util.Locale;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public class AppController extends Application {

    private static AppController appInstance;
    private LanguageSwitcher languageSwitcher;

    public static AppController getInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        /**
         * set data of languages
         */
        SetupLanguage();
    }

    private void SetupLanguage() {
        Locale arabic = new Locale("ar");
        HashSet<Locale> lang = new HashSet<>();
        lang.add(Locale.US);
        lang.add(arabic);
        languageSwitcher = new LanguageSwitcher(getApplicationContext());
        languageSwitcher.setSupportedLocales(lang);
    }

    public void SetLang(Locale locale, Activity activity) {
        languageSwitcher.setLocale(locale, activity);
    }

    public String GetLang() {
        return languageSwitcher.getCurrentLocale().getLanguage();
    }

    public void StartLangDialog(FragmentActivity activity) {
        languageSwitcher.showChangeLanguageDialog(activity);
    }

}
