package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.adaptadores.ChatAdapter;
import com.example.reachthegym.objetos.Mensaje;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recy_chat)
    RecyclerView recyChat;
    @BindView(R.id.chat_edit)
    EditText chatEdit;
    @BindView(R.id.bton_enviar_chat)
    ImageButton btonEnviarChat;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private ArrayList<Mensaje> lista_mensajes;
    private DatabaseReference ref;
    private ChatAdapter adapter;


    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
        View vista = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this,vista);
        ref = FirebaseDatabase.getInstance().getReference();
        lista_mensajes = new ArrayList<>();

        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","");
        String nom = prefs.getString("nombre_usuario","");


            ref.child("centro").child("chat").child(mParam1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lista_mensajes.clear();
                    if (dataSnapshot.hasChildren()){

                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {

                            final Mensaje pojo_mensaje = hijo.getValue(Mensaje.class);
                            lista_mensajes.add(pojo_mensaje);
                            adapter.notifyDataSetChanged();
                            recyChat.smoothScrollToPosition(adapter.getItemCount());
                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            adapter = new ChatAdapter(lista_mensajes,getContext(),id_usuario);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
            linearLayoutManager.setStackFromEnd(true);
            recyChat.setLayoutManager(linearLayoutManager);
            recyChat.setHasFixedSize(true);
            recyChat.setAdapter(adapter);






            btonEnviarChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (chatEdit.getText().toString().isEmpty()){
                        Toast.makeText(getContext(), "Escribe algo!", Toast.LENGTH_SHORT).show();
                    }else{

                        String contenido = chatEdit.getText().toString();
                        String clave = ref.child("centro").child("chat").child(mParam1).push().getKey();

                        Mensaje pojo_mensaje = new Mensaje(id_usuario,contenido,nom,clave);

                        ref.child("centro").child("chat").child(mParam1).child(clave).setValue(pojo_mensaje);
                        chatEdit.setText("");


                    }

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

}
