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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.AdapterListarEjercicios;
import com.example.reachthegym.objetos.Ejercicio;
import com.example.reachthegym.objetos.EjercicioEmpleado;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarEjercicios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarEjercicios extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.buscadorListarEjercicios)
    EditText buscadorListarEjercicios;
    @BindView(R.id.recy_ejercicios)
    RecyclerView recyEjercicios;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private Unbinder unbinder;
    private ArrayList<Ejercicio> lista_ejercicios;
    private ArrayList<EjercicioEmpleado> lista_ejercicios_empleado;
    private DatabaseReference ref;
    private StorageReference sto;
    private AdapterListarEjercicios adapter;

    public ListarEjercicios() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarEjercicios.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarEjercicios newInstance(String param1, String param2) {
        ListarEjercicios fragment = new ListarEjercicios();
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
        View vista = inflater.inflate(R.layout.fragment_listar_ejercicios, container, false);
        ButterKnife.bind(this,vista);

        lista_ejercicios = new ArrayList<>();
        lista_ejercicios_empleado = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();


        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String tipo_usuario = prefs.getString("tipo_usuario","no hay nada");



        if (tipo_usuario.equalsIgnoreCase("empleado")){

            ref.child("centro")
                    .child("ejercicios_empleado")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            lista_ejercicios.clear();
                            lista_ejercicios_empleado.clear();

                            for(DataSnapshot hijo: dataSnapshot.getChildren()){
                                EjercicioEmpleado pojo_ejercicio = hijo.getValue(EjercicioEmpleado.class);
                                lista_ejercicios.add(convertirObjeto(pojo_ejercicio));
                                adapter.notifyDataSetChanged();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


        }else{

            ref.child("centro")
                    .child("ejercicios")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            lista_ejercicios.clear();

                            for (DataSnapshot hijo: dataSnapshot.getChildren()){
                                Ejercicio pojo_ejercicio = hijo.getValue(Ejercicio.class);
                                lista_ejercicios.add(pojo_ejercicio);
                                adapter.notifyDataSetChanged();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

        }

        adapter = new AdapterListarEjercicios(lista_ejercicios,getActivity());
        recyEjercicios.setLayoutManager(new GridLayoutManager(getContext(),1));
        recyEjercicios.setAdapter(adapter);

        buscadorListarEjercicios.addTextChangedListener(new TextWatcher() {
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

    public Ejercicio convertirObjeto (EjercicioEmpleado ejercicioEmpleado){
        String nombre,zona,objetivo,id;

        nombre = ejercicioEmpleado.getNombre();
        zona = ejercicioEmpleado.getZona();
        objetivo = ejercicioEmpleado.getObjetivo();
        id = ejercicioEmpleado.getId_ejercicio();

        return new Ejercicio(id,nombre,zona,objetivo,"");

    }
    public void filtrar(String texto){
        ArrayList<Ejercicio> filtrarLista = new ArrayList<>();

        for (Ejercicio ejercicio: lista_ejercicios){

            if (ejercicio.getNombre().toLowerCase().contains(texto.toLowerCase())){
                filtrarLista.add(ejercicio);
            }

            adapter.filtrar(filtrarLista);
        }

    }

}
