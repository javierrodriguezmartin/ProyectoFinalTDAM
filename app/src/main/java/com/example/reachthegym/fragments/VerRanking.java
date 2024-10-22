package com.example.reachthegym.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.RankingAdapter;
import com.example.reachthegym.objetos.AuxiliarRanking;
import com.example.reachthegym.objetos.Ranking;
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
 * Use the {@link VerRanking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerRanking extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_ver_ranking)
    ImageView imgVerRanking;
    @BindView(R.id.nombre_competicion_ver_ranking)
    TextView nombreCompeticionVerRanking;
    @BindView(R.id.recy_ranking)
    RecyclerView recyRanking;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private RankingAdapter adapter;
    private DatabaseReference ref;
    private ArrayList<AuxiliarRanking> lista_aux = new ArrayList<>();

    public VerRanking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerRanking.
     */
    // TODO: Rename and change types and number of parameters
    public static VerRanking newInstance(String param1, String param2) {
        VerRanking fragment = new VerRanking();
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
        View vista = inflater.inflate(R.layout.fragment_ver_ranking, container, false);
        ButterKnife.bind(this,vista);
        ref = FirebaseDatabase.getInstance().getReference();
        final Ranking pojo_ranking;

        if (isAdded()) {


            if (mParam1 != null && mParam2 != null) {

                nombreCompeticionVerRanking.setText("Ranking de: "+mParam2);

                ref.child("centro").child("ranking").child(mParam1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){

                            Ranking pojo_rank = dataSnapshot.getValue(Ranking.class);

                            ArrayList<AuxiliarRanking> lista2 = new ArrayList<>();
                             lista2 = pojo_rank.getLista_usuarios();

                             for (AuxiliarRanking item : lista2){
                                 lista_aux.add(item);
                                 adapter.notifyDataSetChanged();
                             }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setReverseLayout(false);
            adapter = new RankingAdapter(lista_aux,getContext());
            recyRanking.setLayoutManager(linearLayoutManager);
            recyRanking.setAdapter(adapter);
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
