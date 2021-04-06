package funix.prm.prm391x_shopmovies_loitphfx07175;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    // variables
    public SharedPreferences pref;
    public String methodLogin;
    CallbackManager callbackManager;
    GoogleSignInClient mGoogleSignInClient;

    @SuppressLint({"WorldReadableFiles", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init event login
        pref = getSharedPreferences("LoginData", 0);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        // set auto logout if login already
        if (pref.getString("USER_MAIL", null) != null) {
            LoginManager.getInstance().logOut();
        }
        // Login with Facebook
        LoginButton mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this::loginWithFacebook);
        // Sign-in with Google
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this::loginWithGoogle);
    }

    /**
     * Store data when method login active
     * @param userPic user picture
     * @param userName user name
     * @param userMail user mail
     */
    @SuppressLint("CommitPrefEdits")
    public void storeMethodLogin(String userPic, String userName, String userMail) {
        pref.edit().putString("USER_PIC", userPic).commit();
        pref.edit().putString("USER_NAME", userName).commit();
        pref.edit().putString("USER_MAIL", userMail).commit();
        // call Movie activity after store success
        startMovieActivity();
    }

    /**
     * Login with Google acount
     * @param view view
     */
    public void loginWithGoogle(View view) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // log out all account was logged in
        if (pref.getString("USER_MAIL", null) != null) {
            mGoogleSignInClient.signOut();
        }
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1997);
    }

    /**
     * Handle activity result
     * @param requestCode request code
     * @param resultCode result code
     * @param data input data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1997) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Handle sign-in result data
     * @param completedTask google sign-in already data
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            storeMethodLogin(
                    String.valueOf(account.getPhotoUrl()),
                    account.getDisplayName(),
                    account.getEmail()
            );
        } catch (ApiException e) {
            Log.w("Log: ", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    /**
     * Handle login with Facebook account
     * @param view view
     */
    public void loginWithFacebook(View view) {
        // init event
        methodLogin = "Facebook";
        // logout all account logged in
        if (pref.getString("USER_MAIL", null) != null) {
            LoginManager.getInstance().logOut();
        }
        // add permissions
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            // login active success
            @Override
            public void onSuccess(LoginResult loginResult) {
                @SuppressLint("SetTextI18n") GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    (object, response) -> {
                        // Application code
                        try {
                            String personId = "/" + object.getString("id") + "/";
                            Bundle parameters = new Bundle();
                            parameters.putString(
                                "fields",
                                "id,name,email,picture.type(large)"
                            );
                            /* make the API call */
                            new GraphRequest(
                                loginResult.getAccessToken(),
                                personId,
                                parameters,
                                HttpMethod.GET,
                                res -> {
                                    /* handle the result */
                                    try {
                                        JSONObject data = res.getJSONObject();
                                        if (data.has("picture")) {
                                            String profilePicUrl = data
                                                .getJSONObject("picture")
                                                .getJSONObject("data")
                                                .getString("url");

                                            // handle store data from account log-in success
                                            storeMethodLogin(
                                                    profilePicUrl,
                                                    data.getString("name"),
                                                    data.getString("email")
                                            );
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            ).executeAsync();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                request.executeAsync();
            }

            // login active cancel
            @Override
            public void onCancel() {
                Log.d("Main", "Cancel", null);
            }

            // login active error
            @Override
            public void onError(FacebookException error) {
                Log.d("Main", "Error", null);
            }
        });
    }

    /**
     * Start Movie activity
     */
    public void startMovieActivity() {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        startActivity(intent);
    }
}