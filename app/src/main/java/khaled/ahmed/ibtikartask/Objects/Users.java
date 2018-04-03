package khaled.ahmed.ibtikartask.Objects;

import java.util.ArrayList;

/**
 * Created by ah.khaled1994@gmail.com on 4/2/2018.
 */

public class Users {
    private String id;
    private String name;
    private String handle;
    private String imageURL;
    private String backroundURl;
    private String bio;


    public Users(String id, String name, String handle, String imageURL, String backroundURl, String bio) {
        this.id = id;
        this.name = name;
        this.handle = handle;
        this.imageURL = imageURL;
        this.backroundURl = backroundURl;
        this.bio = bio;
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

    public String getHandle() {
        return handle;
    }
}
