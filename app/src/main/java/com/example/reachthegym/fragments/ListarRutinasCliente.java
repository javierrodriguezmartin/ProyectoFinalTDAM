package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.RutinaClienteAdapter;
import com.example.reachthegym.objetos.Rutina;
import com.example.reachthegym.objetos.Usuario;
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
 * Use the {@link ListarRutinasCliente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarRutinasCliente extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.mis_rutinas)
    TextView misRutinas;
    @BindView(R.id.recy_rutinas_cliente)
    RecyclerView recyRutinasCliente;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private RutinaClienteAdapter adapter;
    private ArrayList<Rutina> lista_rutinas = new ArrayList<>();

    public ListarRutinasCliente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarRutinasCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarRutinasCliente newInstance(String param1, String param2) {
        ListarRutinasCliente fragment = new ListarRutinasCliente();
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
        View vista = inflater.inflate(R.layout.fragment_listar_rutinas_cliente, container, false);
        ButterKnife.bind(this,vista);
        ref = FirebaseDatabase.getInstance().getReference();
        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","");
        if (isAdded()){

            ref.child("centro").child("usuarios").child(id_usuario).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista_rutinas.clear();

                    if (dataSnapshot.hasChildren()){

                        Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                        ArrayList<Rutina> list = pojo_usuario.getLista_rutinas();

                        for (Rutina item : list){
                            lista_rutinas.add(item);
                            adapter.notifyDataSetChanged();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            adapter = new RutinaClienteAdapter(lista_rutinas,getContext());
            recyRutinasCliente.setLayoutManager(new LinearLayoutManager(getContext()));
            recyRutinasCliente.setAdapter(adapter);
            adapter.notifyDataSetChanged();


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
