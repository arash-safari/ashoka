package com.game.senjad.sgame.results_view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.game.senjad.base.api.BasicApiInterface;
import com.game.senjad.sgame.GameActivity;
import com.game.senjad.sgame.R;
import com.game.senjad.sgame.api.RankingApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllResultFragment extends Fragment implements BasicApiInterface {
    RankingApi api;
    int page = 1;
    private AllResultAdapter adapter;
    private long gameId;
    private GameActivity gameActivity;

    public AllResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_result, container, false);
        view.setRotationY(180);
        api = new RankingApi(getContext());
        api.setApiInterface(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllResultAdapter(getContext());
        gameActivity= (GameActivity) getActivity();
        gameId = gameActivity.getGameId();
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==adapter.items.size()-3){

                    api.start(gameId,page);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        api.start(gameId,1);
        return view;
    }

    @Override
    public void onResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray scores = data.getJSONArray("scores");
            int myRank = data.getInt("my-rank");
            List<AllResultEntity> newItems = new ArrayList<>();
            for (int i = 0; i < scores.length(); i++) {
                JSONObject score = scores.getJSONObject(i);
                AllResultEntity newItem =
                        new AllResultEntity(
                                score.getString("user"),
                                score.getInt("index"),
                                score.getInt("score")
                                );
                newItems.add(newItem);
            }
            adapter.addItems(newItems);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onLogout() {
        gameActivity.exit();
    }
}
