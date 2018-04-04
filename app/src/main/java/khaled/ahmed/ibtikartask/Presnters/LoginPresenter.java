package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;
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
     * receive back data from model and save it to shared
     * handle if this first account or new account or this account already logged in
     * then move to main activity
     */
    public void callBackDataFromAsyncTask(User user) {
        Users u = new Users(String.valueOf(user.getId()), user.getName(), user.getOriginalProfileImageURL(),
                SharedData.getInstance().Edit(context).getToken(),
                SharedData.getInstance().Edit(context).getSECRETToken());
        u.setBackroundURl(user.getProfileBannerURL());
        boolean flag = true;
        ArrayList<Users> users;
        if (SharedData.getInstance().getAccount() != null) {
            users = SharedData.getInstance().getAccount();
        } else {
            users = new ArrayList<>();
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == u.getId()) {
                flag = false;
                break;
            }
        }
        if (flag) {
            users.add(u);
        }
        SharedData.getInstance().Edit(context).setID(String.valueOf(user.getId()));
        SharedData.getInstance().Edit(context).setNAME(user.getName());
        SharedData.getInstance().Edit(context).setPIMAGE(user.getOriginalProfileImageURL());
        SharedData.getInstance().Edit(context).setBackgroundIMAGE(user.getProfileBannerURL());
        SharedData.getInstance().Edit(context).setIsLogged(true);
        SharedData.getInstance().Edit(context).saveAccount(users);
        view.moveToMain();
    }

}
