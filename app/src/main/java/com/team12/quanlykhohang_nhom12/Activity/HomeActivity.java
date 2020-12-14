package com.team12.quanlykhohang_nhom12.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.team12.quanlykhohang_nhom12.Fragment.CapPhatFragment;
import com.team12.quanlykhohang_nhom12.Fragment.HomeFragment;
import com.team12.quanlykhohang_nhom12.Fragment.PhongBanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.VaiTroFragment;
import com.team12.quanlykhohang_nhom12.Fragment.TaiKhoanFragment;
import com.team12.quanlykhohang_nhom12.Fragment.VanPhongPhamActivity;
import com.team12.quanlykhohang_nhom12.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.mn_home);
        }

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.mn_department_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PhongBanFragment()).commit();
                break;
            case R.id.mn_staft_manager:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TaiKhoanFragment()).commit();
                break;

            case R.id.mn_stationary_management:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VanPhongPhamActivity()).commit();
                break;

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
        getMenuInflater().inflate(R.menu.menu_allocation, menu);

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