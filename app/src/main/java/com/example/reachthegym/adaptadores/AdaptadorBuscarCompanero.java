package com.example.reachthegym.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.ChatFragment;
import com.example.reachthegym.objetos.BuscarCompañero;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdaptadorBuscarCompanero extends RecyclerView.Adapter<AdaptadorBuscarCompanero.ViewHolder> {
    private ArrayList<BuscarCompañero> lista_compañeros = new ArrayList<>();
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;


    public AdaptadorBuscarCompanero(ArrayList<BuscarCompañero> lista , Context mContext){
        this.lista_compañeros = lista;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buscar_companero, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(this,v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBuscarCompanero.ViewHolder holder, int position) {

        SharedPreferences prefs = mContext.getSharedPreferences("datos_usuario",Context.MODE_PRIVATE);
        String id_usuario = prefs.getString("id_usuario","");
        String nom = prefs.getString("nombre_usuario","");

        sto = FirebaseStorage.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();

        final BuscarCompañero pojo_compañero = lista_compañeros.get(position);

        holder.horarioBuscaCompi.setText(pojo_compañero.getHorario());
        holder.descripBuscarCompi.setText(pojo_compañero.getDescripcion());
        sto.child("centro").child("imagenes").child(pojo_compañero.getId_creador()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri).into(holder.imgBuscarCompañero);
            }
        });

        holder.borrarBuscarCompi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pojo_compañero.getId_creador().equalsIgnoreCase(id_usuario)){

                    ref.child("centro").child("buscar_compañero").child(pojo_compañero.getId_anuncio()).removeValue();
                    Toast.makeText(mContext, "Publicación borrada correctamente", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(mContext, "Solo el creador puede borrarlo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.btonChatCompañero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChatFragment chatFragment = ChatFragment.newInstance(pojo_compañero.getId_anuncio(),"");

                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();

                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,chatFragment).addToBackStack(null).commit();

            }
        });



    }

    @Override
    public int getItemCount() {
        return lista_compañeros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_buscar_compañero)
        ImageView imgBuscarCompañero;
        @BindView(R.id.horario_busca_compi)
        TextView horarioBuscaCompi;
        @BindView(R.id.descrip_buscar_compi)
        TextView descripBuscarCompi;
        @BindView(R.id.borrar_buscar_compa)
        Button borrarBuscarCompi;
        @BindView(R.id.bton_chat_compañero)
        Button btonChatCompañero;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();

        }
    }
}
