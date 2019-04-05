package com.game.senjad.sgame.results_view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.sgame.R;

import java.util.ArrayList;
import java.util.List;

public class AllResultAdapter extends RecyclerView.Adapter {
    private final Context context;
    private static final String TAG = "AllResultAdapter";
    List<AllResultEntity> items = new ArrayList<>();

    public AllResultAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_ranking_all, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder holder = (ItemViewHolder) viewHolder;
        AllResultEntity item = items.get(i);
        if (item.getRank() <= 3) {
            holder.rank_text.setVisibility(View.GONE);
            holder.rank_icon.setVisibility(View.VISIBLE);
            switch (item.getRank()) {
                case 1:
                    holder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_gold_medal));
                    break;
                case 2:
                    holder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_silver_medal));
                    break;
                default:
                    holder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bronze_medal));
                    break;
            }
        }else {
            holder.rank_text.setVisibility(View.VISIBLE);
            holder.rank_icon.setVisibility(View.GONE);
            holder.rank_text.setText(item.getRank()+"");
        }
        Log.d(TAG, "onBindViewHolder:i " + i );
        Log.d(TAG, "onBindViewHolder:rank " + item.getRank());
        Log.d(TAG, "onBindViewHolder:getName " + item.getName());
        Log.d(TAG, "onBindViewHolder:getScore " + item.getScore());
        holder.score_text.setText(item.getScore()+"");
        holder.name_text.setText(item.getName()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        IranSansTextView rank_text, name_text, score_text;
        ImageView rank_icon;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rank_text = itemView.findViewById(R.id.rank_text);
            rank_icon = itemView.findViewById(R.id.rank_icon);
            name_text = itemView.findViewById(R.id.name_text);
            score_text = itemView.findViewById(R.id.score_text);
        }
    }

    public void addItems(List<AllResultEntity> newItems) {
        boolean subtitude = false;
        if(newItems.size()>0 && newItems.get(0).getRank()<items.size()){
            for (int i = 0; i < newItems.size(); i++) {
                items.remove(i+newItems.get(0).getRank());
                items.add(i+newItems.get(0).getRank(),newItems.get(i));
            }
        }else if(newItems.size()>0){
            items.addAll(newItems);
        }
        notifyDataSetChanged();
    }

}
