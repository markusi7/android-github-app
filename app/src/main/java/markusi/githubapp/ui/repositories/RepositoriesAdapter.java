package markusi.githubapp.ui.repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import markusi.githubapp.R;
import markusi.githubapp.data.models.user.User;
import markusi.githubapp.utils.ImageUtils;

class RepositoriesAdapter extends RecyclerView.Adapter<RepositoriesAdapter.ViewHolder> {

    private OnRepositoryClickListener onRepositoryClickListener;

    private List<RepositoryListItem> items;

    void setOnRepositoryClickListener(OnRepositoryClickListener onRepositoryClickListener) {
        this.onRepositoryClickListener = onRepositoryClickListener;
    }

    void setItems(List<RepositoryListItem> items) {
        clearItems();
        this.items = items;
        notifyItemRangeInserted(0, items.size());
    }

    void appendItems(List<RepositoryListItem> items) {
        int currentSize = this.items.size();
        this.items.addAll(items);
        notifyItemRangeInserted(currentSize, items.size());
    }

    void clearItems() {
        int count = items != null ? items.size() : 0;
        this.items = null;
        if (count > 0) {
            notifyItemRangeRemoved(0, count);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new ViewHolder(view, onRepositoryClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    interface OnRepositoryClickListener {

        void onRepositoryClicked(View view, RepositoryListItem item);

        void onUserAvatarClicked(View view, User user);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final OnRepositoryClickListener onRepositoryClickListener;

        @BindView(R.id.userAvatar)
        ImageView avatar;

        @BindView(R.id.repositoryName)
        TextView repositoryName;

        @BindView(R.id.userLogin)
        TextView userLogin;

        @BindView(R.id.watchersCount)
        TextView watchersCount;

        @BindView(R.id.forksCount)
        TextView forksCount;

        @BindView(R.id.issuesCount)
        TextView issuesCount;

        ViewHolder(View itemView, OnRepositoryClickListener onRepositoryClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onRepositoryClickListener = onRepositoryClickListener;
        }

        void bindData(RepositoryListItem item) {
            ImageUtils.displayCircleCroppedImage(avatar, item.user().avatarUrl(), R.drawable.ic_avatar_placeholder);
            repositoryName.setText(item.name());
            userLogin.setText(item.user().login());
            watchersCount.setText(String.valueOf(item.watchersCount()));
            forksCount.setText(String.valueOf(item.forksCount()));
            issuesCount.setText(String.valueOf(item.issuesCount()));

            avatar.setOnClickListener(v -> {
                if (onRepositoryClickListener != null) {
                    onRepositoryClickListener.onUserAvatarClicked(itemView, item.user());
                }
            });

            itemView.setOnClickListener(v -> {
                if (onRepositoryClickListener != null) {
                    onRepositoryClickListener.onRepositoryClicked(itemView, item);
                }
            });
        }
    }
}
