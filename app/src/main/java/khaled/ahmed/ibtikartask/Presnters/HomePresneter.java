package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;

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

public class HomePresneter {

    public static long cursor;
    private HomeView view;
    private HomModel model;
    private Context context;
    private ArrayList<Users> datalist;

    public HomePresneter(HomeView view, Context context) {
        this.view = view;
        this.context = context;
        model = new HomModel();
        cursor = -1;
        datalist = new ArrayList<>();
    }

    /**
     * this method call model to get my followers from api server for first time
     */
    public void getData(HomePresneter presneter) {
        model.GetFollowers(context, presneter);
    }

    /**
     * this method call model to get my followers from api server by paging
     */
    public void getDataReload(HomePresneter presneter) {
        model.GetFollowers(context, presneter);
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
        for (int i = 0; i < users.size(); i++) {
            Users user = new Users(String.valueOf(users.get(i).getId()),
                    users.get(i).getName(),
                    users.get(i).getScreenName(),
                    users.get(i).getBiggerProfileImageURL(),
                    users.get(i).getProfileBackgroundImageURL(),
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

}
