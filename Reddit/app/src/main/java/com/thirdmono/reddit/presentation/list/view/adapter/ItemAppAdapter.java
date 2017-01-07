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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link android.support.v7.widget.RecyclerView.Adapter} for the Apps list.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class ItemAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Thing> items;
    private Context context;
    private OnItemClickListener clickListener;

    public ItemAppAdapter(List<Thing> list, OnItemClickListener listener, Context context) {
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
        return new ViewHolderItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_simple_reddit, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolderItem viewHolderBody = (ViewHolderItem) holder;
        final Thing thing = items.get(position);
        final SubReddit data = thing.getData();

        final String image = thing.getData().getHeaderImg();
        Picasso.with(context)
                .load(image)
                .into(viewHolderBody.itemIcon);

        viewHolderBody.itemTitle.setText(data.getName());
        viewHolderBody.itemArtist.setText(data.getSubmitText());
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

    public interface OnItemClickListener {
        void onClick(View view, Thing thing);
    }

    static class ViewHolderItem extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon)
        ImageView itemIcon;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.item_artist)
        TextView itemArtist;

        ViewHolderItem(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
