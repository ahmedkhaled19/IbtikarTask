package khaled.ahmed.ibtikartask.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import khaled.ahmed.ibtikartask.R;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    private Context context;
    private ResponseList<Status> tweets;
    private String name, handle, image;

    public TweetsAdapter(Context context, ResponseList<Status> tweets, String name, String handle, String image) {
        this.context = context;
        this.tweets = tweets;
        this.name = name;
        this.handle = handle;
        this.image = image;
    }

    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.tweet_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final TweetsAdapter.ViewHolder holder, int position) {
        Status tweet = tweets.get(position);
        Picasso.with(context).load(image).into(holder.picture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                holder.picture.setBackgroundResource(R.drawable.ic_person);
            }
        });
        holder.name.setText(name);
        holder.handle.setText("@" + handle);
        holder.date.setText("." + getDateFormat(tweet.getCreatedAt()));
        holder.tweet.setText(tweet.getText());
    }

    private String getDateFormat(Date createdAt) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("dd MMM");
        String displayValue = timeFormatter.format(createdAt);
        return displayValue;

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name, handle, date, tweet;


        public ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.tweet_profile_picture);
            name = itemView.findViewById(R.id.tweet_user_name);
            handle = itemView.findViewById(R.id.tweet_user_handle);
            date = itemView.findViewById(R.id.tweet_date);
            tweet = itemView.findViewById(R.id.tweet_text);
        }

    }
}
