package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.SeleccionarRutinaCliente;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ListarClientesRutinaAdapter extends RecyclerView.Adapter<ListarClientesRutinaAdapter.ViewHolder> {


    private ArrayList<Usuario> lista_usuario = new ArrayList<>();
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;

    public ListarClientesRutinaAdapter(Context context, ArrayList<Usuario> lista) {
        this.mContext = context;
        this.lista_usuario = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_usuario_rutina, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        final Usuario pojo_usuario = lista_usuario.get(position);
        holder.nombreUsuRutina.setText(pojo_usuario.getNombre());
        holder.apellidosUsuRutina.setText(pojo_usuario.getApellidos());

        sto.child("centro").child("imagenes").child(pojo_usuario.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(mContext).load(uri).into(holder.imgListarRutina);

            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeleccionarRutinaCliente seleccionarRutinaCliente = SeleccionarRutinaCliente.newInstance(pojo_usuario.getId(),"");
                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,seleccionarRutinaCliente).addToBackStack(null).commit();


            }
        });


    }

    @Override
    public int getItemCount() {
        return lista_usuario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_listar_rutina)
        CircleImageView imgListarRutina;
        @BindView(R.id.nombre_usu_rut)
        MaterialTextView nombreUsuRut;
        @BindView(R.id.apellidos_usu_rut)
        MaterialTextView apellidosUsuRut;
        @BindView(R.id.nombre_usu_rutina)
        MaterialTextView nombreUsuRutina;
        @BindView(R.id.apellidos_usu_rutina)
        MaterialTextView apellidosUsuRutina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

    }

    public void filtrar(ArrayList<Usuario> filtroUsuarios) {
        this.lista_usuario = filtroUsuarios;
        notifyDataSetChanged();
    }
}
