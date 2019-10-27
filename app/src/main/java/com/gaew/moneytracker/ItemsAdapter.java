package com.gaew.moneytracker;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private LinkedList<Item> mItemList = new LinkedList<Item>();
    private int colorId;
    private ItemsAdapterListener mListener;

    private SparseBooleanArray mSelectedItems = new SparseBooleanArray();

    public void clesrSelections() {
        mSelectedItems.clear();
        notifyDataSetChanged();

    }

    public void toggleItem(int position) {

        mSelectedItems.put(position, !mSelectedItems.get(position));
        notifyDataSetChanged();
    }

    public void clearItem(int position) {
        mSelectedItems.put(position, false);
        notifyDataSetChanged();

    }


    public void setmListener(ItemsAdapterListener mListener) {
        this.mListener = mListener;
    }

    public ItemsAdapter(int colorId) {
        this.colorId = colorId;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(parent.getContext(), R.layout.item_view, null);

        return new ItemViewHolder(itemView, colorId);

    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindItem(mItemList.get(position), mSelectedItems.get(position));
        holder.setListener(mListener, mItemList.get(position), position);
    }

    public void addItem(Item item) {


        mItemList.addFirst(item);

        notifyDataSetChanged();

    }

    public void clearItems() {
        mItemList.clear();
        notifyDataSetChanged();

    }

    public int getSelectedSize() {
        int res = 0;
        for (int i = 0; i < mItemList.size(); i++) {
            if (mSelectedItems.get(i)) {

                res++;
            }
        }
        return res;
    }

    public List<Integer> getSelectedItemIds() {

        List<Integer> result = new ArrayList<>();
        int i = 0;

        for (Item item : mItemList) {


            if (mSelectedItems.get(i)) {

                result.add(item.getId());
            } i++;
        }
        return result;
    }


    @Override
    public int getItemCount() {

        return mItemList.size();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameView;
        private TextView mPriceView;
        private View mItemView;


        public ItemViewHolder(@NonNull View itemView, final int colorId) {
            super(itemView);
            mItemView = itemView;
            mNameView = itemView.findViewById(R.id.name_view);
            mPriceView = itemView.findViewById(R.id.price_view);
            final Context context = mPriceView.getContext();
            mPriceView.setTextColor(ContextCompat.getColor(context, colorId));
        }

        public void bindItem(final Item item, final boolean isSelected) {
            mItemView.setSelected(isSelected);
            mNameView.setText(item.getName());
            mPriceView.setText(mPriceView.getContext().getResources().getString(R.string.price_this_currency,
                    String.valueOf(item.getPrice())));

        }

        public void setListener(final ItemsAdapterListener listener, final Item item, final int position) {
            mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item, position);
                }
            });

            mItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(item, position);
                    return false;
                }
            });


        }


    }
}


