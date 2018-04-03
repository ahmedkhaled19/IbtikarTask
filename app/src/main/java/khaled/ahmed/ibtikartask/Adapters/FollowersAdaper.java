package khaled.ahmed.ibtikartask.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import khaled.ahmed.ibtikartask.Objects.Users;
import khaled.ahmed.ibtikartask.R;

/**
 * Created by ah.khaled1994@gmail.com on 4/3/2018.
 */

public class FollowersAdaper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //for load more data
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private Context context;
    private ArrayList<Users> users;
    private ProgressBar progressBar;
    private boolean isLoading;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public FollowersAdaper(Context context, ArrayList<Users> users, RecyclerView recyclerView, ProgressBar progressBar) {
        this.context = context;
        this.users = users;
        this.progressBar = progressBar;
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItem + visibleThreshold >= 20) {
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            }
        });
    }

    public int getItemViewType(int position) {
        return users.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Users user = users.get(position);
            Picasso.with(context).load(user.getImageURL()).into(((ViewHolder) holder).profile_picture);
            ((ViewHolder) holder).name.setText(user.getName());
            ((ViewHolder) holder).handle.setText("@" + user.getHandle());
            ((ViewHolder) holder).bio.setText(user.getBio());
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setItems(ArrayList<Users> users, boolean IsAddToList) {
        if (!IsAddToList) {
            this.users = users;
            this.notifyDataSetChanged();
        } else {
            this.users.addAll(users);
            this.notifyItemRangeInserted(users.size(), users.size());
        }
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
