package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDashboard extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_dash_usuario)
    CircleImageView imgDashUsuario;
    @BindView(R.id.listar_usuarios_dashboard)
    CardView listarUsuariosDashboard;
    @BindView(R.id.anadir_rutina_dashboard)
    CardView anadirRutinaDashboard;
    @BindView(R.id.anadir_ejercicio_dashboard)
    CardView anadirEjercicioDashboard;
    @BindView(R.id.anadir_competi_dashboard)
    CardView anadirCompetiDashboard;
    @BindView(R.id.anadir_clase_dashboard)
    CardView anadirClaseDashboard;
    @BindView(R.id.crear_rutina_dashboard)
    CardView crearRutinaDashboard;
    @BindView(R.id.listar_clases_dashboard)
    MaterialCardView listarClasesDashboard;
    @BindView(R.id.listar_anuncios_dashboard)
    MaterialCardView listarAnunciosDashboard;

    private StorageReference sto;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;

    public FragmentDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDashboard newInstance(String param1, String param2) {
        FragmentDashboard fragment = new FragmentDashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, vista);

        sto = FirebaseStorage.getInstance().getReference();
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);


        sto.child("centro").child("imagenes").child(prefs.getString("id_usuario", "")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(imgDashUsuario);
            }
        });


        listarUsuariosDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarUsuariosAdmin listarUsuariosAdmin = ListarUsuariosAdmin.newInstance("", "");
                loadFragment(listarUsuariosAdmin).commit();

            }
        });

        anadirRutinaDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnadirRutinaCliente anadirRutinaCliente = AnadirRutinaCliente.newInstance("", "");
                loadFragment(anadirRutinaCliente).commit();


            }
        });

        anadirEjercicioDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnadirEjercicioAdmin anadirEjercicioAdmin = AnadirEjercicioAdmin.newInstance("", "");
                loadFragment(anadirEjercicioAdmin).commit();
            }
        });

        anadirCompetiDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        anadirClaseDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AnadirClase anadirClase = AnadirClase.newInstance("", "");
                loadFragment(anadirClase).commit();

            }
        });

        crearRutinaDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CrearRutina crearRutina = CrearRutina.newInstance("", "");
                loadFragment(crearRutina).commit();

            }
        });

        listarClasesDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarClases listarClases = ListarClases.newInstance("", "");
                loadFragment(listarClases).commit();
            }
        });

        listarAnunciosDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("", "");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionList) {
            mListener = (OnFragmentInteractionList) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public FragmentTransaction loadFragment(Fragment loadFrag) {
        getActivity().getSupportFragmentManager().popBackStack();
        return getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                .addToBackStack(null)
                .replace(R.id.frame_principal, loadFrag);
    }
}
