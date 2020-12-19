package com.team12.quanlykhohang_nhom12.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.team12.quanlykhohang_nhom12.Fragment.CapPhatFragment;
import com.team12.quanlykhohang_nhom12.Fragment.HomeUserFragment;
import com.team12.quanlykhohang_nhom12.Fragment.MessagerFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanKhoFragment;
import com.team12.quanlykhohang_nhom12.Notifications.Token;
import com.team12.quanlykhohang_nhom12.R;

public class HomeToRentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawertorent;
    private FirebaseAuth firebaseAuth;
    String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_to_rent);

        Toolbar toolbar = findViewById(R.id.toolbartorent);
        firebaseAuth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);

        drawertorent = findViewById(R.id.drawer_layout_hometorent);
        NavigationView navigationView = findViewById(R.id.nav_view_torent);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawertorent, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawertorent.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_torentcontainer, new HomeUserFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        HomeUserFragment homeUserFragment = new HomeUserFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_torentcontainer, homeUserFragment);
        fragmentTransaction.commit();

        checkUser();
        //
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.mn_home:
                            HomeUserFragment homeUserFragment = new HomeUserFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_torentcontainer, homeUserFragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.mn_messager:
                            MessagerFragment messagerFragment = new MessagerFragment();
                            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction2.replace(R.id.fragment_torentcontainer, messagerFragment, "");
                            fragmentTransaction2.commit();
                            return true;
                        case R.id.mn_profile:
                            TaiKhoanKhoFragment taiKhoanKhoFragment = new TaiKhoanKhoFragment();
                            FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction3.replace(R.id.fragment_torentcontainer, taiKhoanKhoFragment, "");
                            fragmentTransaction3.commit();
                            return true;
                    }
                    return false;
                }
            };
    @Override
    protected void onResume() {
        checkUser();
        super.onResume();
    }

    // Kiểm tra tài khoản đã tồn tại hay chưa?
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(HomeToRentActivity.this, DangNhapActivity.class));
            finish();

        }
        else
        {

            mUID = user.getUid();

            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Current_USERID", mUID);
            editor.apply();
        }
    }
    public void updateToken(String token){
        DatabaseReference ref  = FirebaseDatabase.getInstance().getReference("Tokens");
        Token mToken = new Token(token);
        ref.child(mUID).setValue(mToken);
    }
    @Override
    public void onBackPressed() {
        if (drawertorent.isDrawerOpen(GravityCompat.START))
        {
            drawertorent.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    //Navigation code
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mn_allocation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_torentcontainer, new CapPhatFragment()).commit();
                break;

                case R.id.mn_statistic:
                Intent admin = new Intent(this, HomeActivity.class);
                startActivity(admin);
                drawertorent.closeDrawers();
                return true;

            case R.id.mncontract:
                Intent dieukhoan = new Intent(this, DieuKhoanActivity.class);
                startActivity(dieukhoan);
                drawertorent.closeDrawers();
                return true;

            case R.id.mn_infor:
                Intent info = new Intent(this, ThongTinAppActivity.class);
                startActivity(info);

                drawertorent.closeDrawers();
                return true;
        }
        drawertorent.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_allocation, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //nut tiem kiem
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seach:
                startActivity(new Intent(this, TimKiemActivity.class));
                //Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}