package com.team12.quanlykhohang_nhom12.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
import com.team12.quanlykhohang_nhom12.Fragment.HomeAdminKhoFragment;
import com.team12.quanlykhohang_nhom12.Fragment.MessagerFragment;
import com.team12.quanlykhohang_nhom12.Fragment.PhongBanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.VaiTroFragment;
import com.team12.quanlykhohang_nhom12.Notifications.Token;
import com.team12.quanlykhohang_nhom12.R;

public class AdminKhoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
    String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_kho);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeAdminKhoFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectedListener);

        HomeAdminKhoFragment homeAdminKhoFragment = new HomeAdminKhoFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, homeAdminKhoFragment);
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
                            HomeAdminKhoFragment homeAdminKhoFragment = new HomeAdminKhoFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, homeAdminKhoFragment);
                            fragmentTransaction.commit();
                            checkUser();
                            return true;
                        case R.id.mn_messager:
                            MessagerFragment messagerFragment = new MessagerFragment();
                            FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction2.replace(R.id.fragment_container, messagerFragment, "");
                            fragmentTransaction2.commit();
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
            startActivity(new Intent(AdminKhoActivity.this, DangNhapActivity.class));
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
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }
    //Navigation code
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mn_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeAdminKhoFragment()).commit();
                break;
            case R.id.mn_department_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PhongBanFragment()).commit();
                break;
            case R.id.mn_staft_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaiKhoanFragment()).commit();
                break;

//            case R.id.mn_stationary_management:
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VanPhongPhamActivity()).commit();
//                break;

            case R.id.mn_role_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VaiTroFragment()).commit();
                break;

            case R.id.mn_allocation:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CapPhatFragment()).commit();
                break;


            case R.id.mn_infor:
                Intent info = new Intent(this, ThongTinAppActivity.class);
                startActivity(info);

                drawer.closeDrawers();
                return true;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adminkho, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seach:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


}