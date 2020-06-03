package com.example.reachthegym.fragments;

import android.content.Context;
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
import com.example.reachthegym.adaptadores.ListarClientesRutinaAdapter;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.material.textfield.TextInputEditText;
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
 * Use the {@link AnadirRutinaCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnadirRutinaCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.recy_listar_usu_rutinas)
    RecyclerView recyListarUsuRutinas;
    @BindView(R.id.buscador_cliente_rutinas)
    TextInputEditText buscadorClienteRutinas;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private ArrayList<Usuario> lista_usurios = new ArrayList<>();
    private DatabaseReference ref;
    private ListarClientesRutinaAdapter adapter;


    public AnadirRutinaCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnadirRutinaCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static AnadirRutinaCliente newInstance(String param1, String param2) {
        AnadirRutinaCliente fragment = new AnadirRutinaCliente();
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
        View vista = inflater.inflate(R.layout.fragment_anadir_rutina_cliente, container, false);
        ButterKnife.bind(this, vista);
        if (isAdded()) {


            ref = FirebaseDatabase.getInstance().getReference();

            ref.child("centro").child("usuarios").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.hasChildren()) {
                        lista_usurios.clear();

                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {

                            Usuario pojo_usuario = hijo.getValue(Usuario.class);

                            if (pojo_usuario.getTipo().toLowerCase().equalsIgnoreCase("cliente")) {

                                lista_usurios.add(pojo_usuario);
                                adapter.notifyDataSetChanged();
                                System.out.println(lista_usurios.size());

                            }

                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            adapter = new ListarClientesRutinaAdapter(getContext(), lista_usurios);
            recyListarUsuRutinas.setLayoutManager(new LinearLayoutManager(getContext()));
            recyListarUsuRutinas.setAdapter(adapter);

            buscadorClienteRutinas.addTextChangedListener(new TextWatcher() {
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

    public void filtrar(String texto) {
        ArrayList<Usuario> filtrarLista = new ArrayList<>();

        for (Usuario rutina : lista_usurios) {

            if (rutina.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(rutina);
            }

            adapter.filtrar(filtrarLista);
        }

    }


}
