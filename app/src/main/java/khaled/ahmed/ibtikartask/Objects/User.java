package khaled.ahmed.ibtikartask.Objects;

import java.util.ArrayList;

/**
 * Created by ah.khaled1994@gmail.com on 4/2/2018.
 */

public class User {
    private String id;
    private String name;
    private String imageURL;
    private String backroundURl;
    private String bio;
    private ArrayList<Tweets> tweets;

    public User(String id, String name, String imageURL, String backroundURl, String bio, ArrayList<Tweets> tweets) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.backroundURl = backroundURl;
        this.bio = bio;
        this.tweets = tweets;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBackroundURl() {
        return backroundURl;
    }

    public String getBio() {
        return bio;
    }

    public ArrayList<Tweets> getTweets() {
        return tweets;
    }
}
