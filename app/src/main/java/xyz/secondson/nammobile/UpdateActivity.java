package xyz.secondson.nammobile;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener{

    // Untuk Spinner
    Spinner spinner;

    // Inisialisasi Untuk Drawer Navigation dan Toolbar
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    // Inisialisasi Button dan TextView Untuk fungsi Scan
    private Button mbuttonScan;
    private TextView mTextKoil, mTextSuratJalan;
    //qr code scanner object
    private IntentIntegrator qrScan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


//================ Mulai Untuk Toolbar dan Draw Navigation =========================================//

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
                        Intent home = new Intent(UpdateActivity.this, HomeActivity.class);
                        startActivity(home);
                        finish();
                        return true;
                    case R.id.profile:
                        Intent profile = new Intent(UpdateActivity.this, ProfileActivity.class);
                        startActivity(profile);
                        finish();
                        return true;
                    case R.id.updateData:
                        Intent update = new Intent(UpdateActivity.this, UpdateActivity.class);
                        startActivity(update);
                        finish();
                        return true;
                    case R.id.inquiryData:
                        Intent inquiry = new Intent(UpdateActivity.this, InquiryActivity.class);
                        startActivity(inquiry);
                        finish();
                        return true;
                    case R.id.keluar:
                        keluarAplikasi();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(),"Kesalahan Terjadi ",Toast.LENGTH_SHORT).show();
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

//================================ Akhir Dari Toolbar dan Drawer Navigator ==================================//


        spinner = findViewById(R.id.pilihDealer);
        spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        //================ Ini Untuk Scan ================================//
        mbuttonScan = findViewById(R.id.btnScan);
        mTextSuratJalan =  findViewById(R.id.totSuratJalan);
        mTextKoil =  findViewById(R.id.totKoil);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        //attaching onclick listener
        mbuttonScan.setOnClickListener(this);
        //================ Akhir Untuk Scan ============================= //

    } //ini tutup untuk method OnCreate




//==================== Di bawah ini method untuk fungsi Spinner yang di panggil di atas CustomOnItemSelectedListener ==================
    public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
        String firstItem = String.valueOf(spinner.getSelectedItem());

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
            if (firstItem.equals(String.valueOf(spinner.getSelectedItem()))) {
            } else {
                TextView dealer = findViewById(R.id.dealer);
                dealer.setText(parent.getItemAtPosition(pos).toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg) {

        }
    }

//=========================== Akhir dari Method CustomOnItemSelectedListener untuk Spinner ==============================



//=========================== Mulai Method onActivityResult untuk Scan QR Barcode ==============================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    mTextSuratJalan.setText(obj.getString("totalSuratJalan"));
                    mTextKoil.setText(obj.getString("totalKoil"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

//=========================== Akhir Dari Method onActivityResult untuk Scan QR Barcode ==============================

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
                        UpdateActivity.this.finish();
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
