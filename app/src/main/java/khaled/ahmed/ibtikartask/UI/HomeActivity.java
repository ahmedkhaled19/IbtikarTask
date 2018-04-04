package khaled.ahmed.ibtikartask.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import khaled.ahmed.ibtikartask.Adapters.FollowersAdapter;
import khaled.ahmed.ibtikartask.Adapters.OnLoadMoreListener;
import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.Presnters.HomePresenter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Utils.SharedData;
import khaled.ahmed.ibtikartask.Views.HomeView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeView {

    private static boolean active = false;
    private CircleImageView profileImage;
    private TextView name, noData;
    private HomePresenter presenter;
    private RecyclerView recyclerView;
    private FollowersAdapter adapter;
    private ProgressBar progressBar;
    private ArrayList<Users> usersList;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.home);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        profileImage = hView.findViewById(R.id.imageView);
        name = hView.findViewById(R.id.username);
        Picasso.with(getApplicationContext())
                .load(SharedData.getInstance().getProfile()).into(profileImage);
        name.setText(SharedData.getInstance().getNAME());
        presenter = new HomePresenter(this, HomeActivity.this);
        initviews();
    }

    /**
     * to initialization text , progress , recycler , adapter
     * then check connection state
     */
    private void initviews() {
        mSwipeRefreshLayout = findViewById(R.id.home_swip);
        progressBar = findViewById(R.id.home_progress);
        noData = findViewById(R.id.home_text);
        recyclerView = findViewById(R.id.home_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
      /*  recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);*/

        usersList = new ArrayList<>();
        adapter = new FollowersAdapter(HomeActivity.this, usersList, recyclerView, progressBar);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (presenter.cursor != -1 && presenter.checkConnection()) {
                    usersList.add(null);
                    adapter.notifyItemInserted(usersList.size() - 1);
                    presenter.getServerData();
                } else {
                    adapter.setLoaded();
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noData.setVisibility(View.INVISIBLE);
                if (presenter.checkConnection()) {
                    presenter.cursor = -1;
                    presenter.getServerData();
                } else {
                    showSnickBar();
                }
            }
        });
        presenter.getData();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
            System.exit(0);
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lang) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * set data from local in case of no internet connection
     */
    @Override
    public void SetDataLocal(ArrayList<Users> list) {
        if (list.isEmpty() && active) {
            progressBar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
            adapter.setItems(list, false);
        }
    }

    /**
     * presenter call this method in case of this is first time we get data from server
     */
    @Override
    public void SetDataFirstTime(ArrayList<Users> list) {
        usersList.clear();
        if (list.isEmpty() && active) {
            progressBar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            usersList.addAll(list);
            adapter.setItems(usersList, false);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    /**
     * presenter call this method in case of this is reload data from server by paging
     */
    @Override
    public void SetDataReload(ArrayList<Users> list) {
        if (list.size() == 0) {
            if (usersList.size() != 0) {
                usersList.remove(usersList.size() - 1);
                adapter.notifyItemRemoved(usersList.size());
            }
            adapter.setOnLoadMoreListener(null);
            return;
        }
        usersList.remove(usersList.size() - 1);
        adapter.notifyItemRemoved(usersList.size());
        adapter.setItems(list, true);
        adapter.setLoaded();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showSnickBar() {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), getString(R.string.nointernet), Snackbar.LENGTH_LONG);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }


}
