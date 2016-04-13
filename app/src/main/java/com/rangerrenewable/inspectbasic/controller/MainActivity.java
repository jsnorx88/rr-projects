package com.rangerrenewable.inspectbasic.controller;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rangerrenewable.inspectbasic.R;
import com.rangerrenewable.inspectbasic.model.Inspection;
import com.rangerrenewable.inspectbasic.model.WTGSystem;
import com.rangerrenewable.inspectbasic.view.ImportChecklistFragment;
import com.rangerrenewable.inspectbasic.view.InspectionFragment;
import com.rangerrenewable.inspectbasic.view.InspectionListFragment;
import com.rangerrenewable.inspectbasic.view.SystemListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SystemListFragment.OnSystemListFragmentListener,
        InspectionListFragment.OnInspectionListFragmentListener, InspectionFragment.OnInspectionFragmentListener,
        ImportChecklistFragment.OnChecklistImportedListener {

    private NavigationView navigationView;

    private WTGSystem selectedSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        this.navigationView.setNavigationItemSelectedListener(this);

        // open app by opening the import checklist page
        this.navigationView.getMenu().getItem(0).setChecked(true);
        this.pushImportChecklistScreen();
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_import) {
            // do the import
            this.setTitle("Gamesa Android App");

            // clear the back stack if this is selected
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            this.pushImportChecklistScreen();

            // in menu highlight item 1 was selected
            MenuItem menuItem = navigationView.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setChecked(true);
            }
        } else if (id == R.id.nav_select_system) {
            this.setTitle("Gamesa Android App");

            // clear the back stack if this is selected
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            this.pushSystemListScreen();

            // in menu highlight item 2 was selected
            MenuItem menuItem = navigationView.getMenu().getItem(1);
            if (menuItem != null) {
                menuItem.setChecked(true);
            }
        } else if (id == R.id.nav_export) {
            // show export screen
            final ProgressDialog progress = ProgressDialog.show(this, "Export File",
                    "Exporting Inspections...", true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // do the thing that takes a long time
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                            Toast.makeText(MainActivity.this, "File has been exported!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).start();

//            // in menu highlight item 3 was selected
//            MenuItem menuItem = navigationView.getMenu().getItem(2);
//            if (menuItem != null) {
//                menuItem.setChecked(true);
//            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // push the import checklist fragment
    private void pushImportChecklistScreen() {
        // show import checklist
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .replace(R.id.container, ImportChecklistFragment.newInstance());
        fragmentTransaction.commit();
    }

    // push the system list fragment
    private void pushSystemListScreen() {
        // show system list for currently loaded checklist
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .replace(R.id.container, SystemListFragment.newInstance());
        fragmentTransaction.commit();
    }

    // System List Fragment Callback

    @Override
    public void onSystemSelected(WTGSystem system) {
        this.setTitle(system.getName() + " Inspections");

        this.selectedSystem = system;

        // show inspection list for currently loaded system
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .replace(R.id.container, InspectionListFragment.newInstance(system));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Inspection List Fragment Callback

    @Override
    public void onInspectionTapped(Inspection inspection) {
        // show inspection details
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction
                .replace(R.id.container, InspectionFragment.newInstance(inspection));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Inspection Details Fragment Callback

    @Override
    public void onNextTapped(Inspection currentInspection) {
        // get a handle on the next inspection
        int nextInspectionIndex = this.selectedSystem.getIndexForInspection(currentInspection);
        if (nextInspectionIndex == -1) {
            // finished.. show the list.. and toast you finished the inspection
            this.onSystemSelected(this.selectedSystem);
            Toast.makeText(this, "Finished Inspection List. Use menu to export.", Toast.LENGTH_LONG).show();
            return;
        }

        Inspection nextInspection = this.selectedSystem.getInspection(++nextInspectionIndex);
        // pop the last inspection
        getSupportFragmentManager().popBackStack();
        // push the next inspection
        onInspectionTapped(nextInspection);
    }

    // Import Checlist Fragment Callback

    @Override
    public void onChecklistImported(WTGSystem system) {
        // call through to above callback
        this.onSystemSelected(system);
    }
}
