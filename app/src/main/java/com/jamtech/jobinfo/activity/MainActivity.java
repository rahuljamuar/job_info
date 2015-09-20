package com.jamtech.jobinfo.activity;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jamtech.jobinfo.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,View.OnClickListener {

    private Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    boolean doubleBackToExitPressedOnce = false;
    int testValue;
    SessionManager session;
    private static final String TAG_ONE="firstbutton";
    private static final String TAG_TWO="secondbutton";
    private static final String TAG_THREE="thirdbutton";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        displayView(0);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        ImageView iconAcionButton=new ImageView(this);
        iconAcionButton.setImageResource(R.drawable.ic_action_new);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(iconAcionButton)
                .setBackgroundDrawable(R.drawable.selector_button_red)
                .build();

        ImageView first=new ImageView(this);
        first.setImageResource(R.drawable.ic_action_alphabets);
        ImageView second=new ImageView(this);
        second.setImageResource(R.drawable.ic_action_calendar);
        ImageView third=new ImageView(this);
        third.setImageResource(R.drawable.ic_action_important);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_sub_button_gray));

        //Implement onclicklistener to make sub buttons work


        SubActionButton one = itemBuilder.setContentView(first).build();
        SubActionButton two = itemBuilder.setContentView(second).build();
        SubActionButton three = itemBuilder.setContentView(third).build();

        one.setTag(TAG_ONE);
        two.setTag(TAG_TWO);
        three.setTag(TAG_THREE);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(one)
                .addSubActionView(two)
                .addSubActionView(three)
                .attachTo(actionButton)
                .build();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);


        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                testValue=1;
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friends);
                testValue=2;
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                testValue=3;
                break;
            case 3:
                openDialog();
                break;
            default:

                break;
        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    public void openDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout? ");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                session.logoutUser();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }


        if(testValue==2 || testValue==3) {
            Fragment fragment = null;


            String title = null;
            fragment = new HomeFragment();
            title = getString(R.string.title_home);
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();

                // set the toolbar title
                getSupportActionBar().setTitle(title);
            }
            testValue=1;
        }
        else {

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getTag().equals(TAG_ONE)){
            Toast.makeText(this,"First sub button pressed",Toast.LENGTH_SHORT).show();
        }
        else if(v.getTag().equals(TAG_TWO)){
            Toast.makeText(this,"Second sub button pressed",Toast.LENGTH_SHORT).show();
        }
        else if (v.getTag().equals(TAG_THREE)){
            Toast.makeText(this,"Three sub button pressed",Toast.LENGTH_SHORT).show();
        }

    }
}

