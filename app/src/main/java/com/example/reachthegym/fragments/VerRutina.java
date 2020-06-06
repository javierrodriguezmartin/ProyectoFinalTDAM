package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.ListEjerciciosAdapter;
import com.example.reachthegym.objetos.EjercicioEmpleado;
import com.example.reachthegym.objetos.Rutina;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerRutina#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerRutina extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.nom_rut_ver)
    TextView nomRutVer;
    @BindView(R.id.rut)
    TextView rut;
    @BindView(R.id.list_ejer_rut)
    ListView listEjerRut;
    @BindView(R.id.terminar_rutina)
    Button terminarRutina;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private ArrayList<EjercicioEmpleado> lista_ejercicios;
    private ListEjerciciosAdapter adapter;

    public VerRutina() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerRutina.
     */
    // TODO: Rename and change types and number of parameters
    public static VerRutina newInstance(String param1, String param2) {
        VerRutina fragment = new VerRutina();
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
        View vista = inflater.inflate(R.layout.fragment_ver_rutina, container, false);
        ButterKnife.bind(this,vista);

        ref = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","nada");


        if (isAdded()){


            ref.child("centro").child("usuarios").child(id_usuario).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()){
                        lista_ejercicios.clear();

                        Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                        ArrayList<Rutina> lista = pojo_usuario.getLista_rutinas();

                        for (Rutina item : lista){

                            if (item.getId_rutina().equalsIgnoreCase(mParam1)){
                                ArrayList<EjercicioEmpleado> lista_ejer = item.getLista_ejercicios();

                                for (EjercicioEmpleado item2:lista_ejer){
                                    lista_ejercicios.add(item2);
                                    adapter.notifyDataSetChanged();
                                }

                            }

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            adapter = new ListEjerciciosAdapter(getContext(),lista_ejercicios);
            listEjerRut.setAdapter(adapter);
            listEjerRut.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    VerEjercicio verEjercicio = VerEjercicio.newInstance("","");

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
