package khaled.ahmed.ibtikartask.Presnters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import khaled.ahmed.ibtikartask.Model.UserModel;
import khaled.ahmed.ibtikartask.Views.UserView;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public class UserPresenter {
    private Context context;
    private UserView view;
    private UserModel model;

    public UserPresenter(Context context, UserView view) {
        this.context = context;
        this.view = view;
        model = new UserModel();
    }

    public void getData(String id) {
        model.getTweets(context, this, Long.valueOf(id));
    }

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
     * get data from model to send it to view
     */
    public void sendData(ResponseList<Status> tweets) {
        view.setData(tweets);
    }
}
