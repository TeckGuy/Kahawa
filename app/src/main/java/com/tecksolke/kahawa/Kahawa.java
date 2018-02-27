package com.tecksolke.kahawa;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

public class Kahawa extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static TextView userlogged, officername, officerid, officerregion, coffeecollection, Eserved, COserved,regionLogged;
    NiftyDialogBuilder niftyDialogBuilder;
    //calling mySQLite class
    TemporaryDB temporaryDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kahawa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //import other libraries
        temporaryDB = new TemporaryDB(this);
        niftyDialogBuilder = NiftyDialogBuilder.getInstance(this);

        //drawer activation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //import components
        View headerView = navigationView.getHeaderView(0);
        userlogged = headerView.findViewById(R.id.loggedIn);
        regionLogged = headerView.findViewById(R.id.textRegion);
        officername = findViewById(R.id.officerName);
        officerid = findViewById(R.id.officerID);
        officerregion = findViewById(R.id.officerRegion);
        coffeecollection = findViewById(R.id.coffeeCollection);
        Eserved = findViewById(R.id.estateServed);
        COserved = findViewById(R.id.coopServed);

        //retrieving  data from mySQLite
        dataFromMysqLite();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kahawa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout_settings) {
            deleteData();
            startActivity(new Intent(this, Login.class));
            finish();
        } else if (id == R.id.changePasswords) {
            //start activity to change password
            startActivity(new Intent(Kahawa.this, ChangePassword.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.coop_capture_data) {
            startActivity(new Intent(this, CoopSector.class));
        } else if (id == R.id.coop_export_data) {
            //
        } else if (id == R.id.estate_capture_data) {
            startActivity(new Intent(this, EstateSector.class));
        } else if (id == R.id.estate_export_data) {
            //do something
        } else if (id == R.id.logout) {
            deleteData();
            startActivity(new Intent(this, Login.class));
            finish();
        } else if (id == R.id.passwordChange) {
            //do something
            startActivity(new Intent(Kahawa.this, ChangePassword.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //retrieve data from MYSQLITE
    public void dataFromMysqLite() {
        Cursor res = temporaryDB.getAllData();
        if (res != null && res.getCount() > 0) {
            String name = null;
            String userID = null;
            String region = null;
            String collection = null;
            String estateserved = null;
            String coopserved = null;
            while (res.moveToNext()) {
                name = String.valueOf((res.getString(1)));
                userID = String.valueOf((res.getString(2)));
                region = String.valueOf((res.getString(3)));
                collection = String.valueOf((res.getString(4)));
                estateserved = String.valueOf((res.getString(5)));
                coopserved = String.valueOf((res.getString(6)));
            }
            double col = Double.parseDouble(collection);
            double servedE = Double.parseDouble(estateserved);
            double servedC = Double.parseDouble(coopserved);
            officername.setText(name);
            officerid.setText(userID);
            officerregion.setText(region);
            coffeecollection.setText(String.format("%,.2f",col));
            Eserved.setText(String.format("%,.0f",servedE));
            COserved.setText(String.format("%,.0f",servedC));
            userlogged.setText(name);
            regionLogged.setText(region);
        }
    }

    //delete data from SQlite
    public void deleteData(){
        Cursor res = temporaryDB.getAllData();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                temporaryDB.deleteData(res.getString(0));
            }
        }
    }
}
