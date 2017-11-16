package xyz.secondson.nammobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    // Inisialisasi Untuk Drawer Navigation dan Toolbar
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    TextView txtDate, txtDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy ");
        String strDate = mdformat.format(calendar.getTime());
        Integer hari = calendar.get(Calendar.DAY_OF_WEEK);
        txtDay = findViewById(R.id.hari);
        txtDate = findViewById(R.id.tanggal);
        String namaHari = null;
        if (hari == 1) {
            namaHari = "Minggu";
        } else if (hari== 2) {
            namaHari = "Senin";
        } else if (hari == 3) {
            namaHari = "Selasa";
        } else if (hari == 4) {
            namaHari = "Rabu";
        } else if (hari == 5) {
            namaHari = "Kamis";
        } else if (hari  == 6) {
            namaHari = "Jumat";
        }else if (hari == 7) {
            namaHari = "Sabtu";}

        txtDay.setText(namaHari+"/");
        txtDate.setText(strDate);

        // Inisialisasi yang digunakan untuk Drawer (toolbar dan navigation_view)
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Memeriksa apakah item tersebut dalam keadaan dicek  atau tidak,
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Menutup  drawer item klik
                drawerLayout.closeDrawers();
                //Memeriksa untuk melihat item yang akan dilklik dan melalukan aksi
                switch (menuItem.getItemId()){
                    case R.id.home:
                        Intent home = new Intent(HomeActivity.this, HomeActivity.class);
                        startActivity(home);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
                        startActivity(profile);
                        finish();
                        return true;
                    case R.id.updateData:
                        Intent update = new Intent(HomeActivity.this, UpdateActivity.class);
                        startActivity(update);
                        finish();
                        return true;
                    case R.id.inquiryData:
                        Intent inquiry = new Intent(HomeActivity.this, InquiryActivity.class);
                        startActivity(inquiry);
                        finish();
                        return true;
                    case R.id.keluar:
                        keluarAplikasi();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Telah Terjadi Error",Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Menginisasi Drawer Layout dan ActionBarToggle
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Kode di bawah untuk merespon ketika Drawer menutup
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                //  Kode di bawah untuk merespon ketika Drawer terbuka
                super.onDrawerOpened(drawerView);
            }
        };
        //Mensetting actionbarToggle untuk drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //memanggil synstate
        actionBarDrawerToggle.syncState();



    } //ini tutup untuk method OnCreate


    public void toUpdate(View v){
        Intent update = new Intent(HomeActivity.this, UpdateActivity.class);
        startActivity(update);
        finish();
    }

    @Override
    public void onBackPressed(){
        keluarAplikasi();
    }

    // Method Close/Exit Application
    public void keluarAplikasi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


} //ini tutup untuk Class HomeActivity
