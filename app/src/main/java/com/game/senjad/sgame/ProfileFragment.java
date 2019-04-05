package com.game.senjad.sgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.base.api.BasicApiInterface;
import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.base.fragment.BaseFragment;
import com.game.senjad.base.utils.LanguageUtils;
import com.game.senjad.sgame.api.MyScoreRankApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends BaseFragment implements BasicApiInterface {
    private GameActivity gameActivity;
    MyScoreRankApi api;
    List<MyScoreRankEntity> myScoreRankEntities = new ArrayList<>();
    private Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameActivity = (GameActivity) getActivity();
        gameActivity.setTitle("پروفایل");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        api = new MyScoreRankApi(getContext());
        api.setApiInterface(this);
        gameActivity.displayMessageDialog(getString(R.string.bargozari));
        api.start();
        return view;
    }


    @Override
    public void onResponse(String response) {
           gameActivity.dismissMessageDialog();
        JSONObject jsonObject ;
        try {
            jsonObject = new JSONObject(response);
            JSONArray data = jsonObject.getJSONArray("data");
            if(data.length()>0){
                myScoreRankEntities = new ArrayList<>();
            }
            for (int i = 0; i < data.length(); i++) {
                JSONObject gameRank = data.getJSONObject(i);
                MyScoreRankEntity myScoreRankEntity = new MyScoreRankEntity(gameRank.getString("game-name"),
                        gameRank.getInt("my-rank"),
                        gameRank.getInt("my-score"),
                        gameRank.getInt("best-score"));
                myScoreRankEntities.add(myScoreRankEntity);
            }
            adapter.setListScoreRank(myScoreRankEntities);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {
        gameActivity.dismissMessageDialog();
    }

    @Override
    public void onLogout() {
        gameActivity.exit();
    }

    private class Adapter extends RecyclerView.Adapter{
        List<MyScoreRankEntity> listScoreRank;

        public void setListScoreRank(List<MyScoreRankEntity> listScoreRank) {
            this.listScoreRank = listScoreRank;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_profile,viewGroup,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            holder.gameName.setText(listScoreRank.get(i).getGameName());
            holder.bestScore.setText(LanguageUtils.getPersianNumbers(listScoreRank.get(i).getBestScore()+""));
            holder.myScore.setText(LanguageUtils.getPersianNumbers(listScoreRank.get(i).getMyScore()+""));
            holder.myRank.setText(LanguageUtils.getPersianNumbers(listScoreRank.get(i).getMyRank()+""));
        }

        @Override
        public int getItemCount() {
            return listScoreRank.size();
        }
        private class MyViewHolder extends RecyclerView.ViewHolder{
            IranSansTextView gameName,myRank,myScore,bestScore;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                gameName = itemView.findViewById(R.id.game_name);
                myRank = itemView.findViewById(R.id.my_rank);
                myScore = itemView.findViewById(R.id.my_score);
                bestScore = itemView.findViewById(R.id.best_score);
            }
        }
    }

}
