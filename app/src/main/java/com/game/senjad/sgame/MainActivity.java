package com.game.senjad.sgame;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.base.api.BasicApiInterface;
import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.sgame.api.FireBaseTokenApi;
import com.game.senjad.sgame.api.GameListApi;
import com.game.senjad.sgame.api.GetMeApi;
import com.game.senjad.sgame.utils.ImageUtils;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import database.App;
import database.Game;
import database.Game_;
import database.User;
import database.User_;
import io.objectbox.Box;
import io.objectbox.BoxStore;

public class MainActivity extends AppBaseActivity
            implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private IranSansTextView name;
    User user;
    private Box<User> userBox;
    private Box<Game> gameBox;
    private NavigationView navigationView;
    private GamesAdapter gamesAdapter;
    private GameListApi gameListApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        name = navigationView.getHeaderView(0).findViewById(R.id.name_text);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userBox = (((App) getApplication()).getBoxStore()).boxFor(User.class);
        gameBox = (((App) getApplication()).getBoxStore()).boxFor(Game.class);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        gameListApi = new GameListApi(this);
        displayMessageDialog(getString(R.string.bargozari));
        gameListApi.setApiInterface(new BasicApiInterface() {
            @Override
            public void onResponse(String response) {
                dismissMessageDialog();
                Log.d(TAG, "onResponse: " + response);
                JSONObject jsonObject ;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray gameArray = jsonObject.getJSONArray("data");
                    SaveGames saveGames = new SaveGames();
                    saveGames.execute(gameArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onLogout() {
                exit();
            }
        });

        gameListApi.start();
        if (SharedPreferenceUtils.getInstance(this).getMyId() == 0) {
            GetMeApi getMeApi = new GetMeApi(this);
            getMeApi.setApiInterface(new BasicApiInterface() {
                @Override
                public void onResponse(String response) {
                    user = new User();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject userJson = jsonObject.getJSONObject("data").getJSONObject("user");
                        user.name = userJson.getString("naam");
                        user.family = userJson.getString("family");
                        if(name!=null)
                        name.setText(user.name + " " + user.family);
                        user.paye = userJson.getString("paye");
                        user.jensiat = userJson.getString("jensiat");
                        user.code_daneshamoozi = userJson.getString("code_daneshamoozi");
                        user.server_id = userJson.getLong("id");
                        SharedPreferenceUtils.getInstance(MainActivity.this).setMyId(user.server_id);
                        if (userBox == null)
                            Log.d(TAG, "onResponse: nulling userbox");
                        userBox.put(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String message) {

                }

                @Override
                public void onLogout() {
                    exit();
                }
            });
            getMeApi.start();
        } else {
            user = userBox.query().equal(User_.server_id, SharedPreferenceUtils.getInstance(this).getMyId()).build().findFirst();
            Log.d(TAG, "onCreate: " + user.name);
        }

        if (user != null)
            name.setText(user.name + " " + user.family);

        toggle.syncState();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        gamesAdapter = new GamesAdapter();
        gamesAdapter.setGames(gameBox.getAll());
        recyclerView.setAdapter(gamesAdapter);
        name = navigationView.findViewById(R.id.name_text);
        checkFireBaseToken();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void checkFireBaseToken() {
        boolean changeFirebaseToken = SharedPreferenceUtils.getInstance(getApplicationContext()).getMyFtokenChange();
        if(true) {
            FireBaseTokenApi fireBaseTokenApi = new FireBaseTokenApi(this);
            String fireBaseToken =SharedPreferenceUtils.getInstance(getApplicationContext()).getMyFtoken();
            fireBaseTokenApi.setApiInterface(new BasicApiInterface() {
                @Override
                public void onResponse(String response) {
                    JSONObject responseJson ;
                    try {
                        responseJson = new JSONObject(response);
                        Boolean validation = responseJson.getJSONArray("validations").getJSONObject(0).getString("type").equals("SUCCESS");
                        Log.d(TAG, "firebasetoken onResponse: " + validation);
                        if(validation){
                            SharedPreferenceUtils.getInstance(getApplicationContext()).setMyFtokenChange(false);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String message) {

                }

                @Override
                public void onLogout() {
                    exit();
                }
            });
            fireBaseTokenApi.start(fireBaseToken);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_refresh){
            gameListApi.start();
        }
        return super.onOptionsItemSelected(item);    }

    public SharedPreferenceUtils getSharePrefrence() {
        return SharedPreferenceUtils.getInstance(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Log.d(TAG, "onNavigationItemSelected: ");
        item.setChecked(true);
        int id = item.getItemId();
        if (id == R.id.nav_account) {
            // Handle the camera action
            startActivity(PersonalActivity.class);
        } else if (id == R.id.nav_about) {
            startActivity(AboutUsActivity.class);
        } else if (id == R.id.nav_call) {
            startActivity(CallActivity.class);
        }
//        else if (id == R.id.nav_buy) {
//
//        } else if (id == R.id.nav_message) {
//
//        }
        else if (id == R.id.nav_exit) {
            exit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    class GamesAdapter extends RecyclerView.Adapter {
        List<Game> games;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_game, parent, false);
            return new GameViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final Game game = games.get(position);
            final GameViewHolder gameViewHolder = (GameViewHolder) holder;
            RequestOptions options = new RequestOptions();
            DisplayMetrics displayMetrics = MainActivity.this.getResources().getDisplayMetrics();
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            int widthSize = (int) (dpWidth / 1);

            gameViewHolder.imageView.setMinimumWidth(widthSize);
            options.fitCenter();
            Glide.with(MainActivity.this)
                    .load(ImageUtils.getInstance(MainActivity.this).getImage(game.imageSrc))
                    .apply(options)
                    .into(gameViewHolder.imageView);

            gameViewHolder.name.setText(game.name);
            if (game.active) {
                gameViewHolder.lock.setVisibility(View.GONE);
            }
            gameViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (game.active) {
                        Bundle bundle = new Bundle();
                        bundle.putString("link", game.link);
                        bundle.putLong("game_id", game.server_id);
                        startActivity(GameActivity.class, bundle);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return games.size();
        }

        public void setGames(List<Game> games) {
            this.games = games;
        }

        private class GameViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView, lock;
            IranSansTextView name;

            public GameViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.imageView);
                name = view.findViewById(R.id.name);
                lock = view.findViewById(R.id.lock);
                lock.bringToFront();
            }
        }
    }

        class SaveGames extends AsyncTask<JSONArray, Void, Void> {

        protected Void doInBackground(JSONArray... gameArray) {
            try {
                for (int i = 0; i < gameArray[0].length(); i++) {
                    Log.d(TAG, "doInBackground: " + gameArray[0].length());
                    JSONObject gameJson = gameArray[0].getJSONObject(i);
                    Log.d(TAG, "gamename: " + gameJson.getString("name"));
                    if (gameBox.query().equal(Game_.name, gameJson.getString("name")).build().count() > 0) {
                        gameBox.remove(gameBox.query().equal(Game_.name, gameJson.getString("name")).build().findFirst());
                    }
                    Game game = new Game();
                    game.link = gameJson.getString("url");
                    game.server_id = gameJson.getLong("id");
                    game.name = gameJson.getString("name");
                    game.logoLink = gameJson.getString("logo_image");
                    game.active = !gameJson.getString("active").equals("0");
                    URL imageurl = new URL(game.logoLink);
                    game.imageSrc = game.name + "-" + game.logoLink.substring(game.logoLink.lastIndexOf("/") + 1);
                    Log.d(TAG, "game.logoLink: " + game.logoLink);
                    Bitmap bitmap;
                    try{
                        bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
                    }catch(Exception e){
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ashooka);
                    }

                    ImageUtils.getInstance(MainActivity.this).saveImage(bitmap, game.imageSrc);
                    MainActivity.this.gameBox.put(game);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: ");
            gamesAdapter.setGames(gameBox.getAll());
            gamesAdapter.notifyDataSetChanged();
        }
    }

}
