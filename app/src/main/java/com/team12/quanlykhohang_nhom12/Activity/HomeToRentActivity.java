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

import com.google.android.material.navigation.NavigationView;
import com.team12.quanlykhohang_nhom12.Fragment.CapPhatFragment;
import com.team12.quanlykhohang_nhom12.Fragment.HomeUserFragment;
import com.team12.quanlykhohang_nhom12.R;

public class HomeToRentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawertorent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_to_rent);

        Toolbar toolbar = findViewById(R.id.toolbartorent);
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

            case R.id.mn_login:
                Intent login = new Intent(this, LoginActivity.class);
                startActivity(login);
                drawertorent.closeDrawers();
                return true;

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