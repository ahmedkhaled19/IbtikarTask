package khaled.ahmed.ibtikartask.Model;

import android.content.Context;
import android.os.AsyncTask;

import khaled.ahmed.ibtikartask.Presnters.UserPresenter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public class UserModel {

    private Twitter twitter;
    private UserPresenter presenter;


    public void getTweets(Context context, UserPresenter presenter, Long id) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(context.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY))
                .setOAuthConsumerSecret(context.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET))
                .setOAuthAccessToken(SharedData.getInstance().getToken())
                .setOAuthAccessTokenSecret(SharedData.getInstance().getSECRETToken());
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        this.presenter = presenter;
        new getTweetsThread().execute(id);
    }

    private class getTweetsThread extends AsyncTask<Long, Void, ResponseList<Status>> {

        @Override
        protected ResponseList<twitter4j.Status> doInBackground(Long... cursor) {
            ResponseList<twitter4j.Status> tweets = null;
            try {
                tweets = twitter.getUserTimeline(cursor[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return tweets;
        }

        @Override
        protected void onPostExecute(ResponseList<twitter4j.Status> tweets) {
            presenter.sendData(tweets);
        }
    }

}
