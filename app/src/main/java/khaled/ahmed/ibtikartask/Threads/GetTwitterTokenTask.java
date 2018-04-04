package khaled.ahmed.ibtikartask.Threads;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import khaled.ahmed.ibtikartask.Presnters.LoginPresenter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.UI.LoginActivity;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class GetTwitterTokenTask extends AsyncTask<String, Void, String> {

    // Twitter variables
    private static Twitter twitter;
    private static RequestToken requestToken;
    private static AccessToken accessToken;
    ProgressDialog progressBar;
    private LoginActivity activity;
    private String oauthURL, verifier;
    private Dialog dialog;
    private WebView webView;
    private LoginPresenter presenter;

    public GetTwitterTokenTask(LoginActivity activity, LoginPresenter presenter) {
        this.activity = activity;
        this.presenter = presenter;
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(activity.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), activity.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //showing a progress dialog
        progressBar = new ProgressDialog(activity);
        progressBar.setMessage("Connecting...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.show();
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            requestToken = twitter.getOAuthRequestToken("oAuth/twitter");
            oauthURL = requestToken.getAuthenticationURL();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return oauthURL;
    }

    @Override
    protected void onPostExecute(String oauthUrl) {
        if (oauthUrl != null) {
            dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.webviewlayout);
            webView = dialog.findViewById(R.id.webView);
            webView.loadUrl(oauthUrl);

            webView.setWebViewClient(new WebViewClient() {
                boolean authComplete = false;

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.contains("oauth_verifier") && authComplete == false) {
                        authComplete = true;
                        Log.e("AsyncTask", url);
                        Uri uri = Uri.parse(url);
                        verifier = uri.getQueryParameter("oauth_verifier");
                        dialog.dismiss();
                        //revoke access token asynctask
                        new AccessTokenGetTask(twitter, requestToken, accessToken, activity, oauthURL, verifier, presenter).execute();
                    } else if (url.contains("denied")) {
                        dialog.dismiss();
                        Toast.makeText(activity, "Permission is Denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
            dialog.setCancelable(true);
            progressBar.dismiss(); //dismiss progress dialog when task finished.
        } else {
            progressBar.dismiss();
            Toast.makeText(activity, "Network Error!", Toast.LENGTH_SHORT).show();
        }
    }
}
