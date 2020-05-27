package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reachthegym.R;
import com.example.reachthegym.objetos.Rutina;
import com.example.reachthegym.objetos.Usuario;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RutinasAdapter extends RecyclerView.Adapter<RutinasAdapter.ViewHolder> {



    private Context mContext;
    private ArrayList<Rutina> lista_rutinas = new ArrayList<>();
    private DatabaseReference ref;
    private String id_cliente;

    public RutinasAdapter(Context mContext, ArrayList<Rutina> lista_rutinas, String id_cliente) {
        this.mContext = mContext;
        this.lista_rutinas = lista_rutinas;
        this.id_cliente = id_cliente;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutina, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);

        ButterKnife.bind(vista);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ref = FirebaseDatabase.getInstance().getReference();

        final Rutina pojo_rutina = lista_rutinas.get(position);

        holder.nomItemItem.setText(pojo_rutina.getNombre());

        ref.child("centro").child("usuarios").child(pojo_rutina.getCreador()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {

                    Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                    holder.nomCreadorRut.setText(pojo_usuario.getNombre());

                } else {
                    Toast.makeText(mContext, "No se encuentra al usuario que creo la rutina", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                holder.nomCreadorRut.setText("Desconocido");

            }
        });

        holder.imgAnadirItemRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("centro").child("usuarios").child(id_cliente).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()){

                            Usuario pojo_usuario = dataSnapshot.getValue(Usuario.class);
                            ArrayList<Rutina> lista_rutinas = new ArrayList<>();
                            lista_rutinas.add(pojo_rutina);
                            pojo_usuario.setLista_rutinas(lista_rutinas);
                            ref.child("centro").child("usuarios").child(id_cliente).setValue(pojo_usuario);
                            Toast.makeText(mContext, "Rutina añadida al cliente "+pojo_usuario.getNombre(), Toast.LENGTH_SHORT).show();



                        }else{
                            Toast.makeText(mContext, "No existe el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_rutinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_item_rutina)
        ImageView imgItemRutina;
        @BindView(R.id.nombre_item_rut)
        MaterialTextView nombreItemRut;
        @BindView(R.id.nombre_creador_rut)
        MaterialTextView nombreCreadorRut;
        @BindView(R.id.nom_item_item)
        MaterialTextView nomItemItem;
        @BindView(R.id.nom_creador_rut)
        MaterialTextView nomCreadorRut;
        @BindView(R.id.img_anadir_item_rutina)
        ImageView imgAnadirItemRutina;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

    }

    public void filtrar(ArrayList<Rutina> filtroRutinas) {
        this.lista_rutinas = filtroRutinas;
        notifyDataSetChanged();
    }
}
