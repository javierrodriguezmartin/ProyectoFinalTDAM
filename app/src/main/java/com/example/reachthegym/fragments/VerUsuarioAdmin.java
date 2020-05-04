package com.example.reachthegym.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Usuario;
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
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerUsuarioAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerUsuarioAdmin extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_usu_ver)
    ImageView imgUsuVer;
    @BindView(R.id.nombre_usu_ver)
    TextView nombreUsuVer;
    @BindView(R.id.apellidos_usu_ver)
    TextView apellidosUsuVer;
    @BindView(R.id.telefono_usu_ver)
    TextView telefonoUsuVer;
    @BindView(R.id.email_usu_ver)
    TextView emailUsuVer;
    @BindView(R.id.direccion_usu_ver)
    TextView direccionUsuVer;
    @BindView(R.id.fechaalta_usu_ver)
    TextView fechaaltaUsuVer;
    @BindView(R.id.cliente_usu_ver)
    TextView clienteUsuVer;
    @BindView(R.id.tipo_usu_ver)
    Spinner tipoUsuVer;
    @BindView(R.id.mod_usu_ver)
    Button modUsuVer;
    @BindView(R.id.borrar_usu_ver)
    Button borrarUsuVer;
    Unbinder unbinder;
    private Usuario pojo_usu;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference ref;
    private StorageReference sto;
    private static ArrayList<String> tipos = new ArrayList<>();

    public VerUsuarioAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerUsuarioAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static VerUsuarioAdmin newInstance(String param1, String param2) {
        VerUsuarioAdmin fragment = new VerUsuarioAdmin();
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
        View v = inflater.inflate(R.layout.fragment_ver_usuario_admin, container, false);
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();


        if(mParam1.toString().trim().isEmpty()){
            Toast.makeText(getContext(), "La info no llega correctamente", Toast.LENGTH_SHORT).show();
        }else{
            ref.child("centro").
                    child("usuarios").
                    child(mParam1)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.hasChildren()){
                                Toast.makeText(getContext(), "Data snatchot no tiene children bro ", Toast.LENGTH_SHORT).show();
                            }else{
                                final Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                                pojo_usu = pojo_usuario;

                                ref.child("centro").child("tipos").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        tipos.clear();
                                        int i=0;
                                        for (DataSnapshot hijo: dataSnapshot.getChildren()){
                                            final String tipo_usuario = hijo.getValue(String.class);
                                            tipos.add(i,tipo_usuario);
                                            i++;
                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,tipos);
                                        tipoUsuVer.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        Toast.makeText(getContext(), "Ha habido problemas con la consulta", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                nombreUsuVer.setText(pojo_usu.getNombre());
                                apellidosUsuVer.setText(pojo_usu.getApellidos());
                                telefonoUsuVer.setText(pojo_usu.getTelefono());
                                emailUsuVer.setText(pojo_usu.getEmail());
                                direccionUsuVer.setText(pojo_usu.getDireccion());
                                fechaaltaUsuVer.setText(pojo_usu.getFecha_alta());
                                sto.child("centro").child("imagenes").child(pojo_usu.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Glide.with(getContext()).load(uri).into(imgUsuVer);
                                    }
                                });


                                borrarUsuVer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(pojo_usuario.getTipo().toLowerCase().equals("empleado")){
                                            Toast.makeText(getContext(), "El usuario no puede borrarse, puede que tenga clientes a su cargo", Toast.LENGTH_SHORT).show();
                                        }else{
                                            ref.child("centro").child("usuarios").child(pojo_usuario.getId()).removeValue();
                                            Toast.makeText(getContext(), "Usuario borrado correctamente", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Algo falla en la consulta", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        unbinder = ButterKnife.bind(this,v);
        return v;
    }
}
