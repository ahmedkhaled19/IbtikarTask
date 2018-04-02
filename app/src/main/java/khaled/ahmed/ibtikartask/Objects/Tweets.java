package khaled.ahmed.ibtikartask.Objects;

/**
 * Created by ah.khaled1994@gmail.com on 4/2/2018.
 */

public class Tweets {

    private String content;
    private String date;

    public Tweets(String content, String date) {
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}
