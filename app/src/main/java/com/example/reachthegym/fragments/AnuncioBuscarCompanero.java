package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.BuscarCompañero;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnuncioBuscarCompanero#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnuncioBuscarCompanero extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.texto_cabecera)
    TextView textoCabecera;
    @BindView(R.id.horario_anuncio)
    TextInputEditText horarioAnuncio;
    @BindView(R.id.desc_anuncio)
    TextInputEditText descAnuncio;
    @BindView(R.id.publicar_buscar_compi)
    Button publicarBuscarCompi;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;


    public AnuncioBuscarCompanero() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnuncioBuscarCompanero.
     */
    // TODO: Rename and change types and number of parameters
    public static AnuncioBuscarCompanero newInstance(String param1, String param2) {
        AnuncioBuscarCompanero fragment = new AnuncioBuscarCompanero();
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
        View vista = inflater.inflate(R.layout.fragment_anuncio_buscar_companero, container, false);
        ButterKnife.bind(this, vista);
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuari = prefs.getString("id_usuario","");
        ref = FirebaseDatabase.getInstance().getReference();

        if (isAdded()) {

            publicarBuscarCompi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!horarioAnuncio.getText().toString().isEmpty() && !descAnuncio.getText().toString().isEmpty() ){

                        String clave = ref.child("centro").child("buscar_compañero").push().getKey();
                        BuscarCompañero pojo_com = new BuscarCompañero(clave,id_usuari,descAnuncio.getText().toString(),horarioAnuncio.getText().toString());
                        ref.child("centro").child("buscar_compañero").child(clave).setValue(pojo_com);
                        Toast.makeText(getContext(), "Anuncio añadido correctamente", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                FragmentDashboardCliente fragmentDashboardCliente = FragmentDashboardCliente.newInstance("","");
                                getActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,fragmentDashboardCliente).addToBackStack(null).commit();

                            }
                        },100);

                    }else{
                        Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

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

}
