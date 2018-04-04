package khaled.ahmed.ibtikartask.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import khaled.ahmed.ibtikartask.Adapters.TweetsAdapter;
import khaled.ahmed.ibtikartask.Presnters.UserPresenter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Views.UserView;
import twitter4j.ResponseList;
import twitter4j.Status;

public class UserActivity extends AppCompatActivity implements UserView {

    private static boolean active = false;
    private CircleImageView back, profileImage;
    private ImageView bakground;
    private TextView name, handle, noData;
    private ProgressBar progressBar;
    private Intent intent;
    private RecyclerView recyclerView;
    private String Name, Handle, ImageProfile, ImageBackground;
    private TweetsAdapter adapter;
    private UserPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        back = findViewById(R.id.back);
        profileImage = findViewById(R.id.userimage);
        bakground = findViewById(R.id.profile_background);
        name = findViewById(R.id.profile_name);
        handle = findViewById(R.id.profile_handle);
        noData = findViewById(R.id.profile_text);
        progressBar = findViewById(R.id.profile_progress);
        intent = getIntent();
        if (intent != null) {
            Name = intent.getStringExtra("name");
            Handle = intent.getStringExtra("handle");
            ImageProfile = intent.getStringExtra("image");
            ImageBackground = intent.getStringExtra("back");
        }
        presenter = new UserPresenter(UserActivity.this, this);
        initviews();
    }

    /**
     * to initialization recycler
     * set data from intent
     * then check connection state and get data
     */
    private void initviews() {
        recyclerView = findViewById(R.id.tweets_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(UserActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //set data to view
        Picasso.with(UserActivity.this).load(ImageBackground).into((bakground), new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                bakground.setBackgroundResource(R.drawable.ic_background);
            }
        });
        Picasso.with(UserActivity.this).load(ImageProfile).into(profileImage, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                profileImage.setBackgroundResource(R.drawable.ic_person);
            }
        });

        name.setText(Name);
        handle.setText("@" + Handle);
        if (presenter.checkConnection()) {
            presenter.getData(intent.getStringExtra("id"));
        } else {
            progressBar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void setData(ResponseList<Status> tweets) {
        if (!tweets.isEmpty() && active) {
            progressBar.setVisibility(View.GONE);
            adapter = new TweetsAdapter(UserActivity.this, tweets, Name, Handle, ImageProfile);
            recyclerView.setAdapter(adapter);
        } else {
            if (active) {
                progressBar.setVisibility(View.GONE);
                noData.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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


