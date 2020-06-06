package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDashboardCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDashboardCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView((R.id.img_dash_usuario_cliente))
    ImageView imgDashUsuarioCliente;
    @BindView((R.id.mis_rutinas_cliente))
    MaterialCardView misRutinasCLiente;
    @BindView((R.id.mi_perfil))
    MaterialCardView miPerfil;
    @BindView((R.id.buscar_compañero_cliente))
    MaterialCardView buscarCompañeroCliente;
    @BindView((R.id.buscar_compañero))
    MaterialCardView buscarCompañero;
    @BindView((R.id.pedir_asesoramiento))
    MaterialCardView pedirAsesoramiento;
    @BindView((R.id.listar_clases_cliente))
    MaterialCardView listarClaseCliente;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private StorageReference sto;

    public FragmentDashboardCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDashboardCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDashboardCliente newInstance(String param1, String param2) {
        FragmentDashboardCliente fragment = new FragmentDashboardCliente();
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
        View vista = inflater.inflate(R.layout.fragment_dashboard_cliente, container, false);
        ButterKnife.bind(this,vista);
        sto = FirebaseStorage.getInstance().getReference();
        SharedPreferences pref = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id = pref.getString("id_usuario","");

        sto.child("centro").child("imagenes").child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(getContext()).load(uri).into(imgDashUsuarioCliente);

            }
        });


        miPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerPerfil verPerfil = VerPerfil.newInstance(id,"");
                loadFragment(verPerfil).commit();

            }
        });

        misRutinasCLiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buscarCompañero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        buscarCompañeroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pedirAsesoramiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listarClaseCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListarClases listarClases = ListarClases.newInstance("","");
                loadFragment(listarClases).commit();

            }
        });


        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMessage("","");
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
