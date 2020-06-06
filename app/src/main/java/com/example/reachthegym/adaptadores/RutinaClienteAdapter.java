package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.VerRutina;
import com.example.reachthegym.objetos.Rutina;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RutinaClienteAdapter extends RecyclerView.Adapter<RutinaClienteAdapter.ViewHolder> {



    private ArrayList<Rutina> lista_rutinas = new ArrayList<>();
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;

    public RutinaClienteAdapter(ArrayList<Rutina> lista_rutinas, Context mContext) {
        this.mContext = mContext;
        this.lista_rutinas = lista_rutinas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rutina_cliente, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        final Rutina pojo_rutina = lista_rutinas.get(position);

        if (pojo_rutina.isHecha()==true){
            Glide.with(mContext).load(R.drawable.correct).into(holder.imgHecha);
        }

        holder.nombreRutina.setText(pojo_rutina.getNombre());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerRutina verRutina = VerRutina.newInstance(pojo_rutina.getId_rutina(),"");

                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();
                activity.getSupportFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .setCustomAnimations(R.animator.fade_in,R.animator.fade_out)
                        .replace(R.id.frame_principal,verRutina)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_rutinas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_item_rutina_cliente)
        ImageView imgItemRutinaCliente;
        @BindView(R.id.nom)
        TextView nom;
        @BindView(R.id.nombre_rutina)
        TextView nombreRutina;
        @BindView(R.id.img_hecha)
        ImageView imgHecha;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }
    }
}
