package khaled.ahmed.ibtikartask.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import khaled.ahmed.ibtikartask.Presnters.LoginPresenter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Views.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private Button login;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this, LoginActivity.this);
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.Login(LoginActivity.this, presenter);
            }
        });
    }

    @Override
    public void moveToMain() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishAffinity();
        System.exit(0);
        super.onBackPressed();

    }
}
