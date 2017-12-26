package studio.kucuela.rootcheck;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.jrummyapps.android.animations.Technique;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;




public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    /*@Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            // Handle the camera action

            new MaterialStyledDialog.Builder(this)
                    .setTitle("About")
                    .setDescription("A simple app made to help you determine whether your device is rooted or not")
                    .setHeaderDrawable(R.drawable.root).withDialogAnimation(true)

                    .setPositiveText("OK").onPositive(new MaterialDialog.SingleButtonCallback() {

                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    dialog.dismiss();
                }
            })




                    .show();

        } else if (id == R.id.nav_donate) {

            String url = "https://paypal.me/svetaz";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.nav_feedback) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("mailto:" + "rollbarbullbar@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            startActivity(Intent.createChooser(intent, "Send email"));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void check(View view) {

        ImageView neutral = (ImageView)findViewById(R.id.neutral);
        ImageView not = (ImageView)findViewById(R.id.not);
        ImageView yes = (ImageView)findViewById(R.id.root);
        TextView text = (TextView)findViewById(R.id.text1);

        if (findBinary("su")==false){

            neutral.setVisibility(View.INVISIBLE);
            not.setVisibility(View.VISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(not);
            Technique.SWING.getComposer().duration(1000).delay(0).playOn(text);
            text.setText("Your device is not rooted!\n(click on the image to start again)");


        }

        if (findBinary("su")==true){

            neutral.setVisibility(View.INVISIBLE);
            yes.setVisibility(View.VISIBLE);
            Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(yes);
            Technique.SWING.getComposer().duration(1000).delay(0).playOn(text);
            text.setText("Your device is rooted\n(click on the image to start again)");


        }



    }

    public void not(View view) {

        ImageView neutral = (ImageView)findViewById(R.id.neutral);
        ImageView not = (ImageView)findViewById(R.id.not);
        TextView text = (TextView)findViewById(R.id.text1);
        neutral.setVisibility(View.VISIBLE);
        not.setVisibility(View.INVISIBLE);
        Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(neutral);
        Technique.SWING.getComposer().duration(1000).delay(0).playOn(text);
        text.setText("Click on the image to check for root");

    }

    public void root(View view) {

        ImageView neutral = (ImageView)findViewById(R.id.neutral);
        ImageView not = (ImageView)findViewById(R.id.not);
        ImageView yes = (ImageView)findViewById(R.id.root);

        TextView text = (TextView)findViewById(R.id.text1);
        neutral.setVisibility(View.VISIBLE);
        yes.setVisibility(View.INVISIBLE);
        Technique.ROTATE.getComposer().duration(1000).delay(0).playOn(neutral);
        Technique.SWING.getComposer().duration(1000).delay(0).playOn(text);
        text.setText("Click on the image to check for root");

    }



    /** @author Kevin Kowalewski *//*
    public static class RootUtil {
        public static boolean isDeviceRooted() {
            return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
        }

        private static boolean checkRootMethod1() {
            String buildTags = android.os.Build.TAGS;
            return buildTags != null && buildTags.contains("test-keys");
        }

        private static boolean checkRootMethod2() {
            String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                    "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
            for (String path : paths) {
                if (new File(path).exists()) return true;
            }
            return false;
        }

        public static boolean checkRootMethod3() {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
                if (in.readLine() != null) return true;
                return false;
            } catch (Throwable t) {
                return false;
            } finally {
                if (process != null) process.destroy();
            }
        }
    }*/

    private static boolean isRooted() {
        return findBinary("su");
    }



    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                    "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
            for (String where : places) {
                if ( new File( where + binaryName ).exists() ) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }


}
