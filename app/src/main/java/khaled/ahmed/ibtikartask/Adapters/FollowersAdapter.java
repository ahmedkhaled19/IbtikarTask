package khaled.ahmed.ibtikartask.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.R;
import khaled.ahmed.ibtikartask.UI.UserActivity;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class FollowersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //for load more data
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private ArrayList<Users> usersData;
    private ProgressBar progressBar;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;

    public FollowersAdapter(Context context, ArrayList<Users> users, RecyclerView recyclerView, ProgressBar progressBar) {
        this.context = context;
        this.usersData = users;
        this.progressBar = progressBar;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }

    public int getItemViewType(int position) {
        return usersData.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public boolean IsNullLoadMore(){
        return this.onLoadMoreListener == null ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.followers_item_view, parent, false));
        } else if (viewType == VIEW_TYPE_LOADING) {
            return new LoadingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final Users user = usersData.get(position);
            Picasso.with(context).load(user.getImageURL()).into(((ViewHolder) holder).profile_picture, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    ((ViewHolder) holder).profile_picture.setBackgroundResource(R.drawable.ic_person);
                }
            });
            ((ViewHolder) holder).name.setText(user.getName());
            ((ViewHolder) holder).handle.setText("@" + user.getHandle());
            ((ViewHolder) holder).bio.setText(user.getBio());
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, UserActivity.class)
                            .putExtra("id", user.getId())
                            .putExtra("name", user.getName())
                            .putExtra("handle", user.getHandle())
                            .putExtra("image", user.getImageURL())
                            .putExtra("back", user.getBackroundURl()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return usersData.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    /**
     * this method take data from home to set it in adapter
     * in case of first time data we send boolean with false
     * in case of reload we send boolean true to add list to our old list
     * and notify adapter with new data in position that inserted in
     */
    public void setItems(ArrayList<Users> users, boolean IsAddToList) {
        if (!IsAddToList) {
            this.usersData = users;
            this.notifyDataSetChanged();
        } else {
            this.usersData.addAll(users);
            this.notifyItemRangeInserted(usersData.size(), users.size());
        }
    }

    public void cleardata() {
        usersData.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile_picture;
        TextView name, handle, bio;

        public ViewHolder(View itemView) {
            super(itemView);
            profile_picture = itemView.findViewById(R.id.profile_picture);
            name = itemView.findViewById(R.id.username);
            handle = itemView.findViewById(R.id.userhandle);
            bio = itemView.findViewById(R.id.userbio);

        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }

    }
}
