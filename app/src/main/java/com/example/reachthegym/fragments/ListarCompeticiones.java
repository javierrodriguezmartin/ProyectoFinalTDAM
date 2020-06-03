package com.example.reachthegym.fragments;

import android.content.Context;
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
import com.example.reachthegym.adaptadores.AdapterCompeticiones;
import com.example.reachthegym.objetos.Competicion;
import com.google.android.gms.tasks.OnSuccessListener;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListarCompeticiones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListarCompeticiones extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recy_listar_competiciones)
    RecyclerView recyListarCompeticiones;
    @BindView(R.id.no_hay_competis)
    TextView noHayCompetis;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private DatabaseReference ref;
    private StorageReference sto;
    private AdapterCompeticiones adapter;
    private ArrayList<Competicion> lista_competiciones;


    public ListarCompeticiones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListarCompeticiones.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarCompeticiones newInstance(String param1, String param2) {
        ListarCompeticiones fragment = new ListarCompeticiones();
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
        View vista = inflater.inflate(R.layout.fragment_listar_competiciones, container, false);
        ButterKnife.bind(this,vista);
        if (isAdded()) {

            ref = FirebaseDatabase.getInstance().getReference();
            sto = FirebaseStorage.getInstance().getReference();
            lista_competiciones = new ArrayList<>();

            ref.child("centro").child("competiciones").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        lista_competiciones.clear();

                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            Competicion pojo_competicion = hijo.getValue(Competicion.class);

                            lista_competiciones.add(pojo_competicion);
                            adapter.notifyDataSetChanged();


                        }
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            Competicion pojo_competicion = hijo.getValue(Competicion.class);
                            sto.child("centro").child("imagenes").child("imagenes_competiciones").child(pojo_competicion.getId_competicion()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pojo_competicion.setImg_url(uri);
                                }
                            });
                        }
                    } else {

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        if (lista_competiciones.size() == 0) {

        } else {
            noHayCompetis.setVisibility(View.GONE);
            adapter = new AdapterCompeticiones(getContext(), lista_competiciones);
            recyListarCompeticiones.setLayoutManager(new LinearLayoutManager(getContext()));
            recyListarCompeticiones.setAdapter(adapter);
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
