package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.RutinasAdapter;
import com.example.reachthegym.objetos.Rutina;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeleccionarRutinaCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarRutinaCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.buscador_rutina)
    TextInputEditText buscadorRutina;
    @BindView(R.id.texo_buscar_rutina)
    MaterialTextView texoBuscarRutina;
    @BindView(R.id.recycler_rutinas)
    RecyclerView recyclerRutinas;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private RutinasAdapter adapter;
    private DatabaseReference ref;
    private ArrayList<Rutina> lista_rutina = new ArrayList<>();;

    public SeleccionarRutinaCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarRutinaCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionarRutinaCliente newInstance(String param1, String param2) {
        SeleccionarRutinaCliente fragment = new SeleccionarRutinaCliente();
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
        View vista = inflater.inflate(R.layout.fragment_seleccionar_rutina_cliente, container, false);
        ButterKnife.bind(this,vista);
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario",null);


        ref = FirebaseDatabase.getInstance().getReference();

        ref.child("centro")
                .child("rutinas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                lista_rutina.clear();
                for (DataSnapshot hijo : dataSnapshot.getChildren()){
                    Rutina pojo_rutina = hijo.getValue(Rutina.class);
                    lista_rutina.add(pojo_rutina);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter = new RutinasAdapter(getContext(),lista_rutina,mParam1);
        recyclerRutinas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRutinas.setAdapter(adapter);


        buscadorRutina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filtrar(s.toString());

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


    public void filtrar(String texto){
        ArrayList<Rutina> filtrarLista = new ArrayList<>();

        for (Rutina rutina: lista_rutina){

            if (rutina.getNombre().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(rutina);
            }

            adapter.filtrar(filtrarLista);
        }

    }

}
