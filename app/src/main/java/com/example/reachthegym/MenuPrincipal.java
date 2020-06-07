package com.example.reachthegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.reachthegym.fragments.AnadirEjercicioAdmin;
import com.example.reachthegym.fragments.FragmentDashboard;
import com.example.reachthegym.fragments.FragmentDashboardCliente;
import com.example.reachthegym.fragments.ListarCompeticiones;
import com.example.reachthegym.fragments.ListarEjercicios;
import com.example.reachthegym.fragments.ListarUsuariosAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity implements OnFragmentInteractionList{

    private BottomNavigationView menuBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        menuBottom = (BottomNavigationView)findViewById(R.id.menuBottom);
        SharedPreferences prefs = getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        String tipo_usuario = prefs.getString("tipo_usuario",null);

        if (tipo_usuario.equalsIgnoreCase("empleado")){

            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
            loadFragment(fragmentDashboard).commit();

        }else if(tipo_usuario.equalsIgnoreCase("admin")){
            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
            loadFragment(fragmentDashboard).commit();
        }else{
            FragmentDashboardCliente fragmentDashboardCliente = FragmentDashboardCliente.newInstance("","");
            loadFragment(fragmentDashboardCliente).commit();
        }







        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.action_camera:

                        ListarCompeticiones listarCompeticiones = ListarCompeticiones.newInstance("","");
                        loadFragment(listarCompeticiones).commit();
                        break;

                    case R.id.action_navi:
                        if (tipo_usuario.equalsIgnoreCase("empleado")){

                            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
                            loadFragment(fragmentDashboard).commit();

                        }else if(tipo_usuario.equalsIgnoreCase("admin")){
                            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
                            loadFragment(fragmentDashboard).commit();
                        }else{
                            FragmentDashboardCliente fragmentDashboardCliente = FragmentDashboardCliente.newInstance("","");
                            loadFragment(fragmentDashboardCliente).commit();
                        }
                        break;
                }

                return true;
            }
        });

    }

    public FragmentTransaction loadFragment(Fragment loadFrag){
        getSupportFragmentManager().popBackStack();
        return getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .addToBackStack(null)
                .replace(R.id.frame_principal, loadFrag);
    }

    @Override
    public void onFragmentMessage(String data1, String data) {

    }
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
