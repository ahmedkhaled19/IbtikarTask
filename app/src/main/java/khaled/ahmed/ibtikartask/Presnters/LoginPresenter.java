package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;

import khaled.ahmed.ibtikartask.Threads.GetTwitterTokenTask;
import khaled.ahmed.ibtikartask.UI.LoginActivity;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import khaled.ahmed.ibtikartask.Views.LoginView;
import twitter4j.User;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class LoginPresenter {

    private LoginView view;
    private Context context;

    public LoginPresenter(LoginView view, Context context) {
        this.view = view;
        this.context = context;
    }


    public void Login(LoginActivity loginActivity, LoginPresenter presenter) {
        new GetTwitterTokenTask(loginActivity, presenter).execute();
    }

    /**
     * receve back data from model and save it to shared then move to main activity
     */
    public void callBackDataFromAsyncTask(User user) {
        SharedData.getInstance().Edit(context).setID(String.valueOf(user.getId()));
        SharedData.getInstance().Edit(context).setNAME(user.getName());
        SharedData.getInstance().Edit(context).setPIMAGE(user.getOriginalProfileImageURL());
        SharedData.getInstance().Edit(context).setIsLogged(true);
        view.moveToMain();
    }

}
