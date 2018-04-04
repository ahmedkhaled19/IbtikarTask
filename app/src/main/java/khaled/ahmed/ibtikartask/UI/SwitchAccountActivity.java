package khaled.ahmed.ibtikartask.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import khaled.ahmed.ibtikartask.Adapters.AccountAdapter;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.Utils.SharedData;

public class SwitchAccountActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button add;
    private AccountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.switch_account_activity);
        Toolbar toolbar = findViewById(R.id.accounts_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.accounts_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new AccountAdapter(SwitchAccountActivity.this, SharedData.getInstance().Edit(this).getAccount());
        recyclerView.setAdapter(adapter);
        add = findViewById(R.id.add_account);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SwitchAccountActivity.this, LoginActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return (true);
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
