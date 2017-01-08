package com.thirdmono.reddit.presentation.list.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thirdmono.reddit.R;
import com.thirdmono.reddit.data.entity.SubReddit;
import com.thirdmono.reddit.data.entity.Thing;
import com.thirdmono.reddit.domain.utils.CircleTransformation;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public void setItems(List<Thing> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_subreddit, parent, false));
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
                    .into(viewHolderBody.itemIcon);
        }

        viewHolderBody.itemTitle.setText(data.getUrl()+" : "+data.getDisplayName());
        viewHolderBody.itemDescription.setText(data.getPublicDescription());
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

    public interface OnItemClickListener {
        void onClick(View view, Thing thing);
    }

    static class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView itemIcon;
        @BindView(R.id.item_name)
        TextView itemTitle;
        @BindView(R.id.item_description)
        TextView itemDescription;

        ViewHolderItem(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
