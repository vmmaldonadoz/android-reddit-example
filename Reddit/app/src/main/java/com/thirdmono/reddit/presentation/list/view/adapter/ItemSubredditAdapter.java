package com.thirdmono.reddit.presentation.list.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.thirdmono.reddit.R;
import com.thirdmono.reddit.data.entity.SubReddit;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.databinding.ViewSubredditBinding;
import com.thirdmono.reddit.domain.utils.CircleTransformation;

import java.util.List;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for the subreddit list.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class ItemSubredditAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Thing> items;
    private Context context;
    private OnItemClickListener clickListener;

    public ItemSubredditAdapter(List<Thing> list, OnItemClickListener listener, Context context) {
        this.items = list;
        this.clickListener = listener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewSubredditBinding binding = ViewSubredditBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolderItem(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolderItem viewHolderBody = (ViewHolderItem) holder;
        final Thing thing = items.get(position);
        final SubReddit data = thing.getData();

        final String image = thing.getData().getIconImg();
        if (image != null && !image.trim().isEmpty()) {
            Picasso.with(context)
                    .load(image)
                    .transform(new CircleTransformation())
                    .into(viewHolderBody.binding.itemIcon);
        } else {
            Picasso.with(context)
                    .load(R.mipmap.ic_launcher_round)
                    .into(viewHolderBody.binding.itemIcon);
        }

        viewHolderBody.binding.itemName.setText(data.getUrl() + " : " + data.getDisplayName());
        viewHolderBody.binding.itemDescription.setText(data.getPublicDescription());
        viewHolderBody.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onClick(view, thing);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Thing> list) {
        items.addAll(list);
        notifyDataSetChanged();
    }

    public List<Thing> getItems() {
        return items;
    }

    public void setItems(List<Thing> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClick(View view, Thing thing);
    }

    static class ViewHolderItem extends RecyclerView.ViewHolder {
        ViewSubredditBinding binding;

        ViewHolderItem(@NonNull ViewSubredditBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
