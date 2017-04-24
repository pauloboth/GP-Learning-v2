package com.gplearning.gplearning.Controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gplearning.gplearning.Enums.Fragments;
import com.gplearning.gplearning.Models.ComentarioDao;
import com.gplearning.gplearning.Models.Quote;
import com.gplearning.gplearning.R;
import com.gplearning.gplearning.Utils.MetodosPublicos;


import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (MetodosPublicos.ExisteSessao(this)) {
            Log.i("Event", "string null, vai para login");
            Intent intentL = new Intent(this, LoginActivity.class);
            startActivity(intentL);
        } else {
            SharedPreferences shModo = getSharedPreferences("modoAcesso", MODE_PRIVATE);
            String modoAcesso = shModo.getString("modoAcesso", null);
            if (modoAcesso == null) {
                changefragment(Fragments.nivelAcesso.toString());
            } else {
                Intent intent = getIntent();
                if (intent != null) {
                    Log.i("Event", "Tem Intent");
                    Bundle bundle = intent.getExtras();
                    if (bundle != null) {
                        Log.i("Event", "Tem bundle");
                        if (bundle.containsKey("PAGE")) {
                            String page = bundle.getString("PAGE"); //intent.getLongExtra("ID", 0);
                            Log.i("Event", "PAGE " + page);
                            changefragment(page);
                        }
                    } else {
                        Log.i("Event", "CArrega projetos");
                        changefragment(Fragments.projetos.toString());
                    }
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // changefragment("");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
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

            SharedPreferences pref;
            pref = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("user", null);
            editor.commit();

            new getAssync().execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//http://stackoverflow.com/questions/24777985/how-to-implement-onfragmentinteractionlistener


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        if (item.getItemId() == R.id.nav_project) {
            // Handle the camera action
            changefragment(Fragments.projetos.toString());
        } else if (item.getItemId() == R.id.nav_comments) {
            changefragment(Fragments.comentarios.toString());
        } else if (item.getItemId() == R.id.nav_area) {
            changefragment(Fragments.nivelAcesso.toString());
        }

        return true;
    }

    public void changefragment(String fragments) {
        Fragment fragment = null;
        Bundle args = new Bundle();

        if (fragments.equals(Fragments.projetos.toString())) {
            // Handle the camera action
            fragment = new ProjetoFragment();
        } else if (fragments == Fragments.comentarios.toString()) {
            // Handle the camera action
            //  fragment = new ProjetoFragment();
        } else if (fragments == Fragments.nivelAcesso.toString()) {
            Intent intent = new Intent(this, NivelAcessoActivity.class);
            startActivity(intent);
        }
//        else { //if (fragments == Fragments.nivelAcesso) {
//            Intent intent = new Intent(this, NivelAcessoActivity.class);
//            startActivity(intent);
//        }

        if (fragment != null) {
            fragment.setArguments(args);
//            FragmentTransaction transaction = getSupportFragmentManager(); //getFragmentManager().beginTransaction();
//            transaction.add(R.id.content_frame, fragment);
//           // transaction.addToBackStack(null);
//            // Commit the transaction
//            transaction.commit();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    public class getAssync extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
                Log.i("WB", quote.toString());

            } catch (Exception e) {
            }
            ;


            return null;
        }

    }

    public void MostraEtapa(View view) {
        Intent intent = new Intent(this, EtapaActivity.class);
        startActivity(intent);
    }

    /**
     * Check whether the device is connected, and if so, whether the connection
     * is wifi or mobile (it could be something else).
     */
//    private void checkNetworkConnection() {
//        // BEGIN_INCLUDE(connect)
//        ConnectivityManager connMgr =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
//        if (activeInfo != null && activeInfo.isConnected()) {
//            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
//            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
//            if(wifiConnected) {
//                Log.i(TAG, getString(R.string.wifi_connection));
//            } else if (mobileConnected){
//                Log.i(TAG, getString(R.string.mobile_connection));
//            }
//        } else {
//            Log.i(TAG, getString(R.string.no_wifi_or_mobile));
//        }
//        // END_INCLUDE(connect)
//    }


}
