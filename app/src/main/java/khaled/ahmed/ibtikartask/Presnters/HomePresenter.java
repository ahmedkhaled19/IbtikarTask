package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Model.HomModel;
import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import khaled.ahmed.ibtikartask.Views.HomeView;
import twitter4j.PagableResponseList;
import twitter4j.User;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class HomePresenter {

    public static long cursor;
    private HomeView view;
    private HomModel model;
    private Context context;
    private ArrayList<Users> datalist;

    public HomePresenter(HomeView view, Context context) {
        this.view = view;
        this.context = context;
        model = new HomModel();
        cursor = -1;
        datalist = new ArrayList<>();
    }

    public void getData() {
        if (checkConnection()) {
            getServerData();
        } else {
            getLocalData();
        }
    }

    /**
     * this method call model to get my followers from api server
     */
    public void getServerData() {
        model.GetFollowers(context, this);
    }

    /**
     * this method call model to get my followers from local in case of no internet connection
     */
    public void getLocalData() {
        datalist = SharedData.getInstance().getUsers();
        view.SetDataLocal(datalist);
    }

    /**
     * this method get back data from api and working on it
     * creating our object the put it in array list
     * check cursor and change it to next cursor
     */
    public void HandleData(PagableResponseList<User> users) {
        datalist.clear();
        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                Users user = new Users(String.valueOf(users.get(i).getId()),
                        users.get(i).getName(),
                        users.get(i).getScreenName(),
                        users.get(i).getOriginalProfileImageURL(),
                        users.get(i).getProfileBannerURL(),
                        users.get(i).getDescription());
                datalist.add(user);
            }
            saveLocal();
            if (cursor == -1) {
                cursor = users.getNextCursor();
                view.SetDataFirstTime(datalist);
            } else {
                cursor = users.getNextCursor();
                view.SetDataReload(datalist);
            }
        } else {
            view.SetDataFirstTime(datalist);
        }
    }

    /**
     * to save array list of followers to shared preferences
     */
    private void saveLocal() {
        ArrayList<Users> backUp;
        if (SharedData.getInstance().getUsers() != null) {
            backUp = SharedData.getInstance().getUsers();
        } else {
            backUp = new ArrayList<>();
        }
        if (cursor == -1) {
            backUp.clear();
        }
        backUp.addAll(datalist);
        SharedData.getInstance().Edit(context).saveUsers(backUp);
    }

    /**
     * this method for check connection if connected then call api
     * else will call data that cashed in shared
     */
    public boolean checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * this method handle log out for multi account
     */
    public void Logout() {
        ArrayList<Users> users;
        if (SharedData.getInstance().getAccount() != null &&
                !SharedData.getInstance().getAccount().isEmpty() && SharedData.getInstance().getAccount().size() != 1) {
            users = SharedData.getInstance().getAccount();
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(SharedData.getInstance().getID())) {
                    users.remove(i);
                    break;
                }
            }
            SharedData.getInstance().Edit(context).setID(users.get(0).getId());
            SharedData.getInstance().Edit(context).setNAME(users.get(0).getName());
            SharedData.getInstance().Edit(context).setPIMAGE(users.get(0).getImageURL());
            SharedData.getInstance().Edit(context).setToken(users.get(0).getToken());
            SharedData.getInstance().Edit(context).setSecretToken(users.get(0).getSToken());
            SharedData.getInstance().Edit(context).setBackgroundIMAGE(users.get(0).getBackroundURl());
            SharedData.getInstance().Edit(context).saveAccount(users);
            view.ChangeAccount();
        } else {
            users = new ArrayList<>();
            SharedData.getInstance().Edit(context).setID("");
            SharedData.getInstance().Edit(context).setNAME("");
            SharedData.getInstance().Edit(context).setPIMAGE("");
            SharedData.getInstance().Edit(context).setBackgroundIMAGE("");
            SharedData.getInstance().Edit(context).setToken("");
            SharedData.getInstance().Edit(context).setSecretToken("");
            SharedData.getInstance().Edit(context).saveAccount(users);
            SharedData.getInstance().Edit(context).setIsLogged(false);
            view.Logout();
        }
    }
}
