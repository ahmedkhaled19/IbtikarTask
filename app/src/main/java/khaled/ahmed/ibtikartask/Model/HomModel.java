package khaled.ahmed.ibtikartask.Model;

import android.content.Context;
import android.os.AsyncTask;

import khaled.ahmed.ibtikartask.Presnters.HomePresneter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class HomModel {

    private Twitter twitter;
    private HomePresneter presneter;

    public void GetFollowers(Context context, HomePresneter presneter) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(context.getString(R.string.com_twitter_sdk_android_CONSUMER_KEY))
                .setOAuthConsumerSecret(context.getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET))
                .setOAuthAccessToken(SharedData.getInstance().getToken())
                .setOAuthAccessTokenSecret(SharedData.getInstance().getSECRETToken());
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        this.presneter = presneter;
        new getFollowers().execute(presneter.cursor);
    }

    private class getFollowers extends AsyncTask<Long, Void, PagableResponseList<User>> {

        @Override
        protected PagableResponseList<User> doInBackground(Long... cursor) {
            PagableResponseList<User> users = null;
            try {
                users = twitter.getFollowersList(Long.valueOf(SharedData.getInstance().getID()), cursor[0]);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void onPostExecute(PagableResponseList<User> users) {
            presneter.HandleData(users);
        }
    }


}
