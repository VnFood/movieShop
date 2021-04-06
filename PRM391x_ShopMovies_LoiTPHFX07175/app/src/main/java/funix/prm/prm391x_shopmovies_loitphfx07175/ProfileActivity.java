package funix.prm.prm391x_shopmovies_loitphfx07175;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends Activity {
    // variables
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_main);

        // init event
        sharedPreferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        ImageView userPic = findViewById(R.id.userPic);
        TextView userName = findViewById(R.id.userName);
        TextView userMail = findViewById(R.id.userMail);
        Button btnLogOut = findViewById(R.id.btnLogOut);

        // init data
        Picasso.with(ProfileActivity.this)
            .load(sharedPreferences.getString("USER_PIC", null))
            .placeholder(R.drawable.ic_launcher_background)
            .into(userPic);
        userName.setText(sharedPreferences.getString("USER_NAME", ""));
        userMail.setText(sharedPreferences.getString("USER_MAIL", ""));

        // set up button logout
        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Call Movie activity
     * @param view view
     */
    public void openMovieActivity(View view) {
        Intent intent = new Intent(ProfileActivity.this, MovieActivity.class);
        startActivity(intent);
    }

    /**
     * Call Profile activity
     * @param view view
     */
    public void openProfileActivity(View view) {
        Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
        startActivity(intent);
    }
}
