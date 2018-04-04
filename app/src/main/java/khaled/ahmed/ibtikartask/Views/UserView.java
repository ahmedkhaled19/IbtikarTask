package khaled.ahmed.ibtikartask.Views;

import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public interface UserView {
    void setData(ResponseList<Status> tweets);
}
