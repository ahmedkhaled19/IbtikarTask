package khaled.ahmed.ibtikartask.Objects;

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
    private String Token;
    private String SToken;

    /**
     * this constructor for followers users
     */
    public Users(String id, String name, String handle, String imageURL, String backroundURl, String bio) {
        this.id = id;
        this.name = name;
        this.handle = handle;
        this.imageURL = imageURL;
        this.backroundURl = backroundURl;
        this.bio = bio;
    }

    /**
     * this constructor for app account
     */
    public Users(String id, String name, String imageURL, String token, String SToken) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        Token = token;
        this.SToken = SToken;
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

    public String getToken() {
        return Token;
    }

    public String getSToken() {
        return SToken;
    }

    public void setBackroundURl(String backroundURl) {
        this.backroundURl = backroundURl;
    }
}
