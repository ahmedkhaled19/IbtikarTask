package khaled.ahmed.ibtikartask.Threads;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import khaled.ahmed.ibtikartask.Presnters.LoginPresenter;
import khaled.ahmed.ibtikartask.UI.LoginActivity;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class AccessTokenGetTask extends AsyncTask<String, String, User> {

    ProgressDialog progressBar;
    // Twitter variables
    private Twitter twitter;
    private RequestToken requestToken;
    private AccessToken accessToken;
    private LoginActivity activity;
    private String oauthURL, verifier;
    private LoginPresenter presenter;

    public AccessTokenGetTask(Twitter twitter, RequestToken requestToken, AccessToken accessToken, LoginActivity activity, String oauthURL, String verifier, LoginPresenter presenter) {
        this.twitter = twitter;
        this.requestToken = requestToken;
        this.accessToken = accessToken;
        this.activity = activity;
        this.oauthURL = oauthURL;
        this.verifier = verifier;
        this.presenter = presenter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = new ProgressDialog(activity);
        progressBar.setMessage("Fetching Data ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    @Override
    protected User doInBackground(String... args) {
        User user = null;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
            user = twitter.showUser(accessToken.getUserId());
            SharedData.getInstance().Edit(activity).setToken(accessToken.getToken());
            SharedData.getInstance().Edit(activity).setSecretToken(accessToken.getTokenSecret());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    protected void onPostExecute(User response) {
        if (response == null) {
            Log.e("AsyncTask", "null user");
        } else {
            //call back data to UI here
            presenter.callBackDataFromAsyncTask(response);
        }
        progressBar.dismiss();
    }
}

