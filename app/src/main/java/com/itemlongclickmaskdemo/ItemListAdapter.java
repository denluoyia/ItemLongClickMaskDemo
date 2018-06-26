package com.itemlongclickmaskdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/26
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{

    private List<String> mDataList = new ArrayList<>();
    private OnItemClickCallback mItemClickCallback;

    public void setData(List<String> dataList){
        if (dataList == null || dataList.size() == 0) return;
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setOnItemClickCallback(OnItemClickCallback clickCallback){
        this.mItemClickCallback = clickCallback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        String item = mDataList.get(position);
        if (item == null) return;
        holder.tvTitle.setText(item);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mItemClickCallback != null){
                    mItemClickCallback.itemLongClick(holder.itemView, position);
                }
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickCallback != null){
                    mItemClickCallback.itemClick(holder.itemView, position);
                }
            }
        });
    }

    public String getItem(int position){
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_title);
        }
    }

    interface OnItemClickCallback {
        void itemLongClick(View view, int position);
        void itemClick(View view, int position);
    }

}
