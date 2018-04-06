package khaled.ahmed.ibtikartask.Views;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public interface HomeView {

    void SetDataLocal(ArrayList<Users> list);

    void SetDataFirstTime(ArrayList<Users> list);

    void SetDataReload(ArrayList<Users> list);

    void serverError();

    void ChangeAccount();

    void Logout();

}
