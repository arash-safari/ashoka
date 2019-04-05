package com.game.senjad.sgame.results_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.base.utils.LanguageUtils;
import com.game.senjad.sgame.R;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import java.util.List;

public class DefultِRankingAdapter extends RecyclerView.Adapter {
    private final Context context;

    public DefultِRankingAdapter(Context context) {
        this.context = context;
    }

    public static class DefultRank {
        long id;
        int rank;
        String name;
        String secoundParam;
        int score;
    }

    private List<DefultRank> rankList;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_defult_ranking, parent, false);
        return new MyViewHolder(view);
    }

    public void setRankList(List<DefultRank> rankList) {
        this.rankList = rankList;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        DefultRank defultRank = rankList.get(position);
        switch (defultRank.score) {
            case 1:
                myViewHolder.rank_icon.setVisibility(View.VISIBLE);
                myViewHolder.rank_text.setVisibility(View.GONE);
                myViewHolder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_gold_medal));
                break;
            case 2:
                myViewHolder.rank_icon.setVisibility(View.VISIBLE);
                myViewHolder.rank_text.setVisibility(View.GONE);
                myViewHolder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_silver_medal));
                break;
            case 3:
                myViewHolder.rank_icon.setVisibility(View.VISIBLE);
                myViewHolder.rank_text.setVisibility(View.GONE);
                myViewHolder.rank_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_bronze_medal));
                break;
            default:
                myViewHolder.rank_icon.setVisibility(View.GONE);
                myViewHolder.rank_text.setVisibility(View.VISIBLE);
                myViewHolder.rank_text.setText(LanguageUtils.getPersianNumbers(defultRank.score+""));
                break;
        }
        myViewHolder.name_text.setText(defultRank.name);
        myViewHolder.second_param_text.setText(defultRank.secoundParam);
        myViewHolder.score_text.setText(LanguageUtils.getPersianNumbers(defultRank.score+""));
        if(SharedPreferenceUtils.getInstance(context).getMyId()==defultRank.id){
            myViewHolder.name_text.setTextColor(R.color.white);
            myViewHolder.second_param_text.setTextColor(R.color.white);
            myViewHolder.score_text.setTextColor(R.color.white);
            myViewHolder.rank_text.setTextColor(R.color.white);
            myViewHolder.defultRankingView.setBackgroundColor(R.color.blue_40);
        }
    }

    @Override
    public int getItemCount() {
        return rankList.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        IranSansTextView rank_text, name_text, second_param_text, score_text;
        ImageView rank_icon;
        RelativeLayout defultRankingView;
        public MyViewHolder(View view) {
            super(view);
            rank_text = view.findViewById(R.id.rank_text);
            name_text = view.findViewById(R.id.name_text);
            second_param_text = view.findViewById(R.id.second_param_text);
            score_text = view.findViewById(R.id.score_text);
            rank_icon = view.findViewById(R.id.rank_icon);
            defultRankingView = view.findViewById(R.id.defultRankingView);
        }
    }
}
