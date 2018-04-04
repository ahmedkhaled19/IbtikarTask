package khaled.ahmed.ibtikartask.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Utils.AppController;
import khaled.ahmed.ibtikartask.Utils.SharedData;

public class LanguageActivity extends AppCompatActivity {

    private ImageView save;
    private TextView Textlang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Toolbar toolbar = findViewById(R.id.lang_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save = toolbar.findViewById(R.id.savelang);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeData();
            }
        });
        Textlang = findViewById(R.id.langText);
        Locale currentLocale = getResources().getConfiguration().locale;
        Textlang.setText(currentLocale.getDisplayName(currentLocale));
        Textlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppController.getInstance().StartLangDialog(LanguageActivity.this);
                Locale currentLocale = getResources().getConfiguration().locale;
                Textlang.setText(currentLocale.getDisplayName(currentLocale));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }


    private void ChangeData() {
        Locale currentLocale = getResources().getConfiguration().locale;
        final String lang = currentLocale.getDisplayLanguage();
        if (lang.equals("English")) {
            AppController.getInstance().SetLang(Locale.US, LanguageActivity.this);
            SharedData.getInstance().Edit(LanguageActivity.this).setLang("en");
        } else {
            Locale arabic = new Locale("ar");
            AppController.getInstance().SetLang(arabic, LanguageActivity.this);
            SharedData.getInstance().Edit(LanguageActivity.this).setLang("ar");
        }
        startActivity(new Intent(LanguageActivity.this, HomeActivity.class));
    }

    /**
     * here we have 2 condition
     * first one that app working in device local which mean that no data saved on shared
     * second one that we save last language on shared so we can get it
     * we see this condition and handle language to make sure that not change
     */
    @Override
    public void onBackPressed() {
        String lang = AppController.getInstance().GetLang();
        if (lang == null) {
            Locale currentLocale = getResources().getConfiguration().locale;
            lang = currentLocale.getDisplayLanguage();
            if (lang.equals("English")) {
                Locale arabic = new Locale("ar");
                AppController.getInstance().SetLang(arabic, LanguageActivity.this);
                SharedData.getInstance().Edit(LanguageActivity.this).setLang("ar");
            } else {
                AppController.getInstance().SetLang(Locale.US, LanguageActivity.this);
                SharedData.getInstance().Edit(LanguageActivity.this).setLang("en");
            }
        } else {
            if (SharedData.getInstance().Edit(LanguageActivity.this).getLang().equals("ar")) {
                Locale arabic = new Locale("ar");
                AppController.getInstance().SetLang(arabic, LanguageActivity.this);
                SharedData.getInstance().Edit(LanguageActivity.this).setLang("ar");
            } else {
                AppController.getInstance().SetLang(Locale.US, LanguageActivity.this);
                SharedData.getInstance().Edit(LanguageActivity.this).setLang("en");
            }
        }
        startActivity(new Intent(LanguageActivity.this, HomeActivity.class));
    }
}
