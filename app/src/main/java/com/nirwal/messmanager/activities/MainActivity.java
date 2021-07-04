package com.nirwal.messmanager.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nirwal.messmanager.MyApp;
import com.nirwal.messmanager.R;
import com.nirwal.messmanager.databinding.ActivityMainBinding;
import com.nirwal.messmanager.fragments.AdminFragment;
import com.nirwal.messmanager.fragments.HomeFragment;
import com.nirwal.messmanager.activities.Authorization.LoginFragment;
import com.nirwal.messmanager.fragments.ManageUserFragment;
import com.nirwal.messmanager.fragments.MealOrderFragment;
import com.nirwal.messmanager.fragments.NotificationCreatorFragment;
import com.nirwal.messmanager.fragments.OrderDetailFragment;
import com.nirwal.messmanager.services.ImageLoadTask;

import java.lang.ref.WeakReference;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity {

    public WeakReference<MyApp> _app;
    public Fragment _homeFragment, _adminFragment,_loginFragment, _manageUserFragment,
          _mealOrderFragment, _orderDetailFragment, _notificationCreatorFragment;


    private ActivityMainBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        _app = new WeakReference<>((MyApp) getApplication());
        initToolbar();
        updateUserDetailOnToolbar();
        fragmentInit();

        // set default start fragment i.e: HomeFragment()
        setFragment(_homeFragment, null,true);

    }

    // toolbar initialization
    private void initToolbar(){
        _binding.mainactivityToolbar.inflateMenu(R.menu.main_menu);
        _binding.mainactivityToolbar.setOnMenuItemClickListener(item -> {
            if(item.getTitle()==null) return false;
            String title = item.getTitle().toString();

            switch (title){
                case "Sign out":{
                    _app.get().signOutFromFirebase();
                    break;
                }

                case "Sign in":{
                    startActivity(new Intent(this,AuthorizationActivity.class));
                    break;
                }

                case "About":{
                    startActivity(new Intent(this,AboutAndLicenceActivity.class));
                    break;
                }

            }
            Log.v("Auth","id :"+item.getItemId());
            if(item.getItemId()==R.id.main_menu_username){
                startActivity(new Intent(this,AuthorizationActivity.class));
            }
            return true;
        });

    }

    private void fragmentInit(){
        _homeFragment = new HomeFragment();
        _adminFragment = new AdminFragment();
        _loginFragment = new LoginFragment();
        _manageUserFragment = new ManageUserFragment();
        _mealOrderFragment = new MealOrderFragment();
        _orderDetailFragment = new OrderDetailFragment();
        _notificationCreatorFragment = new NotificationCreatorFragment();


        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
//                Toast.makeText(_app.get(), Objects.requireNonNull(getSupportFragmentManager().getPrimaryNavigationFragment()).toString(),Toast.LENGTH_LONG).show();

                if(_homeFragment.isVisible()){
                  //  Objects.requireNonNull(getSupportActionBar()).setTitle("");
                }else {

                }
            }
        });

    }

    public void setFragment(Fragment fragment, @Nullable String tag, boolean expandToolbar){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.framelayout_container_1,fragment)
                .addToBackStack(tag)
                .commit();

        if(tag!=null){
            _binding.mainactivityToolbar.setTitle(tag);
        }



        if(expandToolbar){
            _binding.mainactivityAppbarLayout.setExpanded(true,true);
        }else{
            _binding.mainactivityAppbarLayout.setExpanded(false,true);
        }
    }

    private void updateUserDetailOnToolbar(){
        updateUserUi(_app.get().get_firebaseAuth().getCurrentUser());

        _app.get().get_firebaseAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()==null){
                    Snackbar.make(_binding.mainactivityAppbarLayout.getRootView(),"Sign out", BaseTransientBottomBar.LENGTH_LONG)
                            .setBackgroundTint(Color.RED)
                            .setDuration(2000)
                            .show();
                }
                else {
                    Snackbar.make(_binding.mainactivityAppbarLayout.getRootView(),"Sign in success", BaseTransientBottomBar.LENGTH_LONG)
                            .setBackgroundTint(Color.GREEN)
                            .setDuration(2000)
                            .show();
                }
                updateUserUi(firebaseAuth.getCurrentUser());
            }
        });
    }

    private void updateUserUi(FirebaseUser user){
        MenuItem menuItem = _binding.mainactivityToolbar.getMenu().getItem(0);

        if(user!=null){
            menuItem.setTitle(user.getDisplayName());
            String name;
            name = user.getDisplayName()=="" ? user.getPhoneNumber():user.getDisplayName();
            name = user.getPhoneNumber()=="" ? user.getEmail(): name;

            menuItem.getSubMenu().getItem(0).setTitle(name);
            menuItem.getSubMenu().getItem(1).setTitle("Sign out");
            Drawable drawable;
            if(user.getPhotoUrl()!=null){
                new ImageLoadTask(user.getPhotoUrl().toString(),menuItem).execute();
            }

        }else{
            menuItem.setTitle("");
            menuItem.setIcon(R.drawable.ic_account);
            menuItem.getSubMenu().getItem(0).setTitle("Guest User");
            menuItem.getSubMenu().getItem(1).setTitle("Sign in");
        }

    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _binding = null;
    }
}
