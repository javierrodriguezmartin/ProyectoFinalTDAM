package com.example.reachthegym.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.reachthegym.OnFragmentInteractionList;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarPerfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_mod_perfil)
    CircleImageView imgModPerfil;
    @BindView(R.id.nombre_mod_perfil)
    EditText nombreModPerfil;
    @BindView(R.id.apellidos_mod_perfil)
    EditText apellidosModPerfil;
    @BindView(R.id.email_mod_perfil)
    EditText emailModPerfil;
    @BindView(R.id.contrasena_mod_perfil)
    EditText contrasenaModPerfil;
    @BindView(R.id.editText5)
    EditText direccion;
    @BindView(R.id.telefono_mod_perfil)
    EditText telefonoModPerfil;
    @BindView(R.id.modificar_perfil)
    Button modificarPerfil;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionList mListener;
    private Uri img_url =  null;
    private final static int seleccionar_img = 1;
    private DatabaseReference ref;
    private StorageReference sto;
    private Usuario usuario_completo = new Usuario();

    public ModificarPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarPerfil newInstance(String param1, String param2) {
        ModificarPerfil fragment = new ModificarPerfil();
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
        View vista = inflater.inflate(R.layout.fragment_modificar_perfil, container, false);
        ButterKnife.bind(this, vista);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        SharedPreferences prefs = getActivity().getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String tipo_usuario =  prefs.getString("tipo_usuario","");


        if (isAdded()) {


            if (mParam1 != null) {

                ref.child("centro").child("usuarios").child(mParam1).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){

                            usuario_completo = dataSnapshot.getValue(Usuario.class);



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                nombreModPerfil.setText(usuario_completo.getNombre());
                apellidosModPerfil.setText(usuario_completo.getApellidos());
                telefonoModPerfil.setText(usuario_completo.getTelefono());
                direccion.setText(usuario_completo.getDireccion());
                emailModPerfil.setText(usuario_completo.getDireccion());
                contrasenaModPerfil.setText(usuario_completo.getContrasena());


                modificarPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!nombreModPerfil.getText().toString().isEmpty() && !apellidosModPerfil.getText().toString().isEmpty() && !telefonoModPerfil.getText().toString().isEmpty() && !contrasenaModPerfil.getText().toString().isEmpty()
                        && !emailModPerfil.getText().toString().isEmpty() && !direccion.getText().toString().isEmpty() ){

                            if (validarEmail(emailModPerfil.getText().toString())){
                                Usuario pojo_usuario = usuario_completo;
                                pojo_usuario.setNombre(nombreModPerfil.getText().toString());
                                pojo_usuario.setApellidos(apellidosModPerfil.getText().toString());
                                pojo_usuario.setContrasena(contrasenaModPerfil.getText().toString());
                                pojo_usuario.setTelefono(telefonoModPerfil.getText().toString());
                                pojo_usuario.setDireccion(direccion.getText().toString());
                                pojo_usuario.setEmail(emailModPerfil.getText().toString());

                                ref.child("centro").child("usuarios").child(pojo_usuario.getId()).setValue(pojo_usuario);

                                if (img_url!=null){
                                    sto.child("centro").child("imagenes").child(pojo_usuario.getId()).putFile(img_url);
                                }
                                Toast.makeText(getContext(), "Modificacion correctamente terminada", Toast.LENGTH_SHORT).show();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (tipo_usuario.equalsIgnoreCase("empleado")){

                                            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .setCustomAnimations(R.animator.fade_in,R.animator.fade_out)
                                                    .replace(R.id.frame_principal,fragmentDashboard)
                                                    .addToBackStack(null)
                                                    .commit();

                                        }else if(tipo_usuario.equalsIgnoreCase("admin")){
                                            FragmentDashboard fragmentDashboard = FragmentDashboard.newInstance("","");
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .setCustomAnimations(R.animator.fade_in,R.animator.fade_out)
                                                    .replace(R.id.frame_principal,fragmentDashboard)
                                                    .addToBackStack(null)
                                                    .commit();
                                        }else{
                                            FragmentDashboardCliente fragmentDashboardcliente = FragmentDashboardCliente.newInstance("","");
                                            getActivity().getSupportFragmentManager().beginTransaction()
                                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                                    .setCustomAnimations(R.animator.fade_in,R.animator.fade_out)
                                                    .replace(R.id.frame_principal,fragmentDashboardcliente)
                                                    .addToBackStack(null)
                                                    .commit();
                                        }


                                    }
                                },100);


                            }



                        }else{
                            Toast.makeText(getContext(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                        }

                    }
                });





                imgModPerfil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,seleccionar_img);
                    }
                });









            }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == seleccionar_img && resultCode == RESULT_OK) {

            img_url = data.getData();
            imgModPerfil.setImageURI(img_url);
            Toast.makeText(getContext(), "Imagen seleccionada", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
