package com.project.digimagz.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.R;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.view.fragment.EmagzFragment;
import com.project.digimagz.view.fragment.ProfileFragment;
import com.project.digimagz.view.fragment.SearchFragment;
import com.project.digimagz.view.fragment.HomeFragment;
import com.project.digimagz.view.fragment.VideoFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

//    private MaterialToolbar materialToolbar;

    private InitRetrofit initRetrofit;

    private List<AuthUI.IdpConfig> idpConfigList;
    private AuthMethodPickerLayout authMethodPickerLayout;

    private FirebaseUser firebaseUser;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    private Menu menu;
    private MenuItem menuItem;

    private boolean doubleBackToExit, checkFragment;
    private Fragment fragmentActive, fragmentHome, fragmentVideo, fragmentSearch, fragmentEmagz, fragmentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        materialToolbar = findViewById(R.id.materialToolbar);
//        setSupportActionBar(materialToolbar);

        initRetrofit = new InitRetrofit();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fragmentManager = getSupportFragmentManager();
        fragmentHome = new HomeFragment();
        fragmentVideo = new VideoFragment();
        fragmentSearch = new SearchFragment();
        fragmentEmagz = new EmagzFragment();
        fragmentProfile = new ProfileFragment();
        fragmentActive = fragmentHome;

        setIdpConfigList();
        authMethodPickerLayout = new AuthMethodPickerLayout
                .Builder(R.layout.activity_login)
                .setGoogleButtonId(R.id.materialButtonGoogle)
                .setFacebookButtonId(R.id.materialButtonFacebook)
                .setEmailButtonId(R.id.materialButtonEmail)
                .build();

        doubleBackToExit = true;
        checkFragment = false;

        menu = bottomNavigationView.getMenu();
        menuItem = menu.findItem(R.id.login_profile_menu);
        if (firebaseUser != null) {
            menuItem.setTitle("Profile");
        } else {
            menuItem.setCheckable(false);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.frameLayoutForFragment, fragmentProfile, "Profile").hide(fragmentProfile).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayoutForFragment, fragmentEmagz, "Emagz").hide(fragmentEmagz).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayoutForFragment, fragmentSearch, "Search").hide(fragmentSearch).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayoutForFragment, fragmentVideo, "Video").hide(fragmentVideo).commit();
        fragmentManager.beginTransaction().add(R.id.frameLayoutForFragment, fragmentHome, "Home").commit();

    }

    public void setIdpConfigList() {
        idpConfigList = Arrays.asList(
                new AuthUI.IdpConfig.FacebookBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build()
        );
    }

    public void showSignInOption() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(idpConfigList)
                        .setIsSmartLockEnabled(false)
                        .setTheme(R.style.AppTheme)
                        .setAuthMethodPickerLayout(authMethodPickerLayout)
                        .build(),
                RC_SIGN_IN);
    }

    public void toMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home_menu:
                    //((VideoFragment) fragmentVideo).pauseVideo();
                    fragmentManager.beginTransaction().hide(fragmentActive).show(fragmentHome).commit();
                    if (fragmentActive == fragmentHome) {
                        ((HomeFragment) fragmentHome).scrollUp();
//                        ((VideoFragment) fragmentVideo).resumeVideo();
                    }
                    fragmentActive = fragmentHome;
                    doubleBackToExit = true;
                    return true;
                case R.id.video_menu:
                    //((VideoFragment) fragmentVideo).pauseVideo();
                    fragmentManager.beginTransaction().hide(fragmentActive).show(fragmentVideo).commit();
                    if (fragmentActive == fragmentVideo) {
                        ((VideoFragment) fragmentVideo).scrollUp();
                    }
                    fragmentActive = fragmentVideo;
                    doubleBackToExit = false;
                    return true;
                case R.id.explore_menu:
                    //((VideoFragment) fragmentVideo).pauseVideo();
                    fragmentManager.beginTransaction().hide(fragmentActive).show(fragmentSearch).commit();
                    if (fragmentActive == fragmentSearch) {
                        ((SearchFragment) fragmentSearch).scrollUp();
                    }
                    fragmentActive = fragmentSearch;
                    doubleBackToExit = false;
                    return true;
                case R.id.emagz_menu:
                    //((VideoFragment) fragmentVideo).pauseVideo();
                    fragmentManager.beginTransaction().hide(fragmentActive).show(fragmentEmagz).commit();
                    fragmentActive = fragmentEmagz;
                    doubleBackToExit = false;
                    return true;
                case R.id.login_profile_menu:
                    if (firebaseUser != null) {
                        fragmentManager.beginTransaction().hide(fragmentActive).show(fragmentProfile).commit();
                        fragmentActive = fragmentProfile;
                        doubleBackToExit = false;
                    } else {
                        showSignInOption();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("Email", String.valueOf(firebaseUser.getEmail()));
                Log.d("Name", String.valueOf(firebaseUser.getDisplayName()));
                Log.d("Phone", String.valueOf(firebaseUser.getPhoneNumber()));
                Log.d("Photo", String.valueOf(firebaseUser.getPhotoUrl()));
                Log.d("Privider Id", String.valueOf(firebaseUser.getProviderId()));
                Log.d("U Id", String.valueOf(firebaseUser.getUid()));

                initRetrofit.postUserToApi(String.valueOf(firebaseUser.getEmail()), String.valueOf(firebaseUser.getDisplayName()), String.valueOf(firebaseUser.getPhotoUrl()));

                toMain();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExit = true;
        fragmentManager.beginTransaction().hide(fragmentActive).show(new HomeFragment()).commit();
        fragmentActive = fragmentHome;
        bottomNavigationView.setSelectedItemId(R.id.home_menu);
    }
}
