package com.example.reachthegym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuPrincipal extends AppCompatActivity {

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

                           // FragmentVerTienda frag_ver_tienda = FragmentVerTienda.newInstance(tipo, tipo);
                            //loadFragment(frag_ver_tienda).commit();


                        break;
                    case R.id.action_camera:

                            //FragmentProducto frag_prod = FragmentProducto.newInstance(tipo, tipo);
                            //loadFragment(frag_prod).commit();

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
}
