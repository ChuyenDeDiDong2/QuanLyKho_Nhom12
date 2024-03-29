package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.team12.quanlykhohang_nhom12.Fragment.CapPhatFragment;
import com.team12.quanlykhohang_nhom12.Fragment.DanhSachHangKhoFragment;
import com.team12.quanlykhohang_nhom12.Fragment.DanhSachTaiKhoanBlockFragment;
import com.team12.quanlykhohang_nhom12.Fragment.DanhSachUserFragment;
import com.team12.quanlykhohang_nhom12.Fragment.HomeAdminAppFragment;
import com.team12.quanlykhohang_nhom12.Fragment.HomeAdminKhoFragment;
import com.team12.quanlykhohang_nhom12.Fragment.PhongBanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.SliderFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanKhoFragment;
import com.team12.quanlykhohang_nhom12.Notifications.Token;
import com.team12.quanlykhohang_nhom12.R;

public class AdminAppActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
    String mUID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);
        //Thanh menu:
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new HomeAdminAppFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
        }

        checkUser();
        //
        updateToken(FirebaseInstanceId.getInstance().getToken());
    }
    @Override
    protected void onResume() {
        checkUser();
        super.onResume();
    }

    // Kiểm tra tài khoản đã tồn tại hay chưa?
    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(AdminAppActivity.this, DangNhapActivity.class));
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
    //Update token lên firebase:
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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new HomeAdminAppFragment()).commit();
                break;
            case R.id.mn_danhsachhangkho:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new DanhSachHangKhoFragment()).commit();
                break;
            case R.id.mn_danhsachnguoidung:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new DanhSachUserFragment()).commit();
                break;
            case R.id.mn_danhsachtaikhoanbikhoa:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new DanhSachTaiKhoanBlockFragment()).commit();
                break;
            case R.id.mn_slider:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new SliderFragment()).commit();
                break;
            //case R.id.mn_guithongbao:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_adminapp_container, new ()).commit();
                //break;
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