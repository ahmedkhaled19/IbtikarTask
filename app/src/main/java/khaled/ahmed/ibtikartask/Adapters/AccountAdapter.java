package khaled.ahmed.ibtikartask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.UI.HomeActivity;
import khaled.ahmed.ibtikartask.Utils.SharedData;

/**
 * Created by ah.khaled1994@gmail.com on 4/4/2018.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Users> users;

    public AccountAdapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.account_item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Users u = users.get(position);
        Picasso.with(context).load(u.getImageURL()).into(holder.picture, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                holder.picture.setBackgroundResource(R.drawable.ic_person);
            }
        });

        holder.name.setText(u.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedData.getInstance().Edit(context).setID(u.getId());
                SharedData.getInstance().Edit(context).setNAME(u.getName());
                SharedData.getInstance().Edit(context).setPIMAGE(u.getImageURL());
                SharedData.getInstance().Edit(context).setToken(u.getToken());
                SharedData.getInstance().Edit(context).setSecretToken(u.getSToken());
                SharedData.getInstance().Edit(context).setBackgroundIMAGE(u.getBackroundURl());
                context.startActivity(new Intent(context, HomeActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.account_image);
            name = itemView.findViewById(R.id.account_name);
        }

    }
}
