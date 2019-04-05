package com.game.senjad.sgame;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.game.senjad.base.activity.BaseActivity;
import com.game.senjad.base.components.IranSansTextView;
import com.game.senjad.sgame.utils.ImageUtils;
import com.game.senjad.sgame.utils.SharedPreferenceUtils;

import database.App;
import database.User;
import database.User_;
import io.objectbox.Box;

public class PersonalActivity extends AppBaseActivity {
    IranSansTextView nameText,payeText;
    ImageView backGroundImage,personalImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        setTitle("اطلاعات شخصی");
        setBackToolbarEnable();
        nameText = findViewById(R.id.name_text);
        payeText = findViewById(R.id.paye_text);
        backGroundImage = findViewById(R.id.back_ground_image);
        personalImage = findViewById(R.id.personal_image);
        setBackToolbarEnable();
        RequestOptions options = new RequestOptions();
        Glide.with(this)
                .load(R.drawable.do_not_crash)
                .apply(options.fitCenter())
                .into(backGroundImage);
        Glide.with(this).load(R.drawable.ic_man).apply(options.fitCenter()).into(personalImage);
        Box<User> userBox = (((App) getApplication()).getBoxStore()).boxFor(User.class);
        User user = userBox.query().equal(User_.server_id, SharedPreferenceUtils.getInstance(this).getMyId()).build().findFirst();
        if (user != null) {
            nameText.setText(user.name + " " + user.family);
            payeText.setText(user.paye);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }
}
