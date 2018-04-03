package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Model.HomModel;
import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.Views.HomeView;
import twitter4j.PagableResponseList;
import twitter4j.User;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class HomePresneter {

    public static long cursor = -1;
    private HomeView view;
    private HomModel model;
    private Context context;
    private ArrayList<Users> datalist;

    public HomePresneter(HomeView view, Context context) {
        this.view = view;
        this.context = context;
        model = new HomModel();
    }

    /**
     * this method call model to get my followers from api server
     */
    public void getData(HomePresneter presneter) {
        model.GetFollowers(context, presneter);
    }

    /**
     * this method call model to get my followers from local in case of no internet connection
     */
    public void getLocalData() {

    }

    /**
     * this method get back data from api and working on it
     */
    public void HandleData(PagableResponseList<User> users) {
        for (int i = 0; i < users.size(); i++) {

        }
    }

}
