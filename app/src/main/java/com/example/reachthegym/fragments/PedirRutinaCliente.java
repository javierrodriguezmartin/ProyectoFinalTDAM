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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.PedirRutina;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PedirRutinaCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedirRutinaCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.textView16)
    TextView textView16;
    @BindView(R.id.descripcion_pedir_rutina)
    EditText descripcionPedirRutina;
    @BindView(R.id.bton_publica_pedir_rutina)
    Button btonPublicaPedirRutina;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;

    public PedirRutinaCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedirRutinaCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static PedirRutinaCliente newInstance(String param1, String param2) {
        PedirRutinaCliente fragment = new PedirRutinaCliente();
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
        View vista = inflater.inflate(R.layout.fragment_pedir_rutina_cliente, container, false);
        ButterKnife.bind(this, vista);
        ref = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","");


        if (isAdded()) {

            btonPublicaPedirRutina.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (descripcionPedirRutina.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Rellene la descripción", Toast.LENGTH_SHORT).show();

                    }else{
                        String clave = ref.child("centro").child("pedir_rutinas").push().getKey();

                        PedirRutina pojo_rutina = new PedirRutina(clave,id_usuario,descripcionPedirRutina.getText().toString());

                        ref.child("centro").child("pedir_rutinas").child(clave).setValue(pojo_rutina);
                        Toast.makeText(getContext(), "Anuncio publicado correctamente", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cerrarFragment();
                            }
                        },100);
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
    private void cerrarFragment(){
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }
}
