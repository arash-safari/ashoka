package com.game.senjad.sgame;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.sgame.results_view.ResultsFragment;

public class GameActivity extends AppBaseActivity {
    private String link;
    private long gameId;
    private FragmentManager fragmentManager;
    private GameFragment gameFragment;
    private ProfileFragment profileFragment;
    private ResultsFragment resultsFragment;
    private BottomNavigationView bottomNavigationView;
    public interface OnRefresh {
        void onRefresh();
    }

    private OnRefresh onRefreshInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_game);
        setTitle(getClass().getName());

        setBackToolbarEnable();
        link = getIntent().getStringExtra("link");
        gameId = getIntent().getLongExtra("game_id",1);
        bottomNavigationView = findViewById(R.id.navigation);
        fragmentManager = getSupportFragmentManager();
        startGamesFragment();
        final MenuItem face = bottomNavigationView.getMenu().getItem(0);
        final MenuItem game = bottomNavigationView.getMenu().getItem(1);
        game.setChecked(true);
        final MenuItem flag = bottomNavigationView.getMenu().getItem(2);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.games:
                        face.setIcon(getResources().getDrawable
                                        (R.drawable.ic_empty_face_black_24px));
                        flag.setIcon(getResources().getDrawable
                                        (R.drawable.ic_empty_flag_black_24px));
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fill_games_black_24dp));
                        startGamesFragment();
                        return true;
                    case R.id.profile:
                        flag.setIcon(getResources().getDrawable
                                        (R.drawable.ic_empty_flag_black_24px));
                        game.setIcon(getResources().getDrawable
                                        (R.drawable.ic_empty_games_black_24px));
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fill_face_black_24dp));
                        startProfileFragment();
                        return true;
                    case R.id.results:
                        face.setIcon(getResources().getDrawable
                                (R.drawable.ic_empty_face_black_24px));
                        game.setIcon(getResources().getDrawable
                                        (R.drawable.ic_empty_games_black_24px));
                        item.setIcon(getResources().getDrawable(R.drawable.ic_fill_flag_black_24dp));
                        startResultsFragment();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    public void setOnRefreshInterface(OnRefresh onRefreshInterface) {
        this.onRefreshInterface = onRefreshInterface;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    private void startResultsFragment() {
        if(resultsFragment==null)
            resultsFragment = new ResultsFragment();
        startFragment(resultsFragment);
    }

    private void startProfileFragment() {
        if(profileFragment==null)
            profileFragment = new ProfileFragment();
        startFragment(profileFragment);
    }

    private void startGamesFragment() {
        if(gameFragment ==null)
            gameFragment = new GameFragment();
        startFragment(gameFragment);
    }

    private Fragment getFragment(Fragment fragment) {
        if(fragment!=null)
            return fragment;
        else {
            if(fragment.getClass()==GameFragment.class)
             return new GameFragment();
            else if(fragment.getClass()==ProfileFragment.class)
                return new ProfileFragment();
            else
                return new ResultsFragment();
        }
    }

    private void startFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame,fragment).commit();
    }

    public String getLink() {
        return link;
    }
    public long getGameId() {
        return gameId;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){
            onRefreshInterface.onRefresh();
        }
        return super.onOptionsItemSelected(item);
    }
}
