package com.example.reachthegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.reachthegym.fragments.AnadirEjercicioAdmin;
import com.example.reachthegym.fragments.ListarUsuariosAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity implements OnFragmentInteractionList{

    private BottomNavigationView menuBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);


        menuBottom = (BottomNavigationView)findViewById(R.id.menuBottom);

        menuBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:

                        ListarUsuariosAdmin listarUsuariosAdmin = ListarUsuariosAdmin.newInstance("","");
                        loadFragment(listarUsuariosAdmin).commit();
                        break;

                    case R.id.action_camera:

                        AnadirEjercicioAdmin anadirEjercicioAdmin = AnadirEjercicioAdmin.newInstance("","");
                        loadFragment(anadirEjercicioAdmin).commit();
                        break;

                    case R.id.action_search:

                            //FragmentMapa frag_mapa = FragmentMapa.newInstance(tipo, tipo);
                            //loadFragment(frag_mapa).commit();
                        break;


                    case R.id.action_settings:
                        //AnadirReserva frag_ana_res = AnadirReserva.newInstance(tipo,tipo);
                        //loadFragment(frag_ana_res).commit();
                        break;

                    case R.id.action_navi:


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
