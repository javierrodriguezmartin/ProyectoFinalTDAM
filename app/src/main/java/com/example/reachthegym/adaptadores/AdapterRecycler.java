package com.example.reachthegym.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reachthegym.MenuPrincipal;
import com.example.reachthegym.R;
import com.example.reachthegym.fragments.VerUsuarioAdmin;
import com.example.reachthegym.objetos.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
//Listar Usuarios Admin
public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private ArrayList<Usuario> lista_usuario = new ArrayList<>();
    private Context mContext;
    private DatabaseReference ref;
    private StorageReference sto;


    public AdapterRecycler(ArrayList<Usuario> lista, Context mContext) {
        this.mContext = mContext;
        this.lista_usuario = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listar_usu_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        ButterKnife.bind(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ref = FirebaseDatabase.getInstance().getReference();
        sto = FirebaseStorage.getInstance().getReference();

        final Usuario pojo_usuario = lista_usuario.get(position);
        holder.nomUsuAdmin.setText(pojo_usuario.getNombre());
        holder.apellidosUsuAdmin.setText(pojo_usuario.getApellidos());
        holder.faltaUsuAdmin.setText(pojo_usuario.getFecha_alta());
        holder.tipoUsuarioAdmin.setText(pojo_usuario.getTipo());
        Glide.with(mContext).load(pojo_usuario.getImg_url()).into(holder.imgListarAdmin);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerUsuarioAdmin fragUsu = VerUsuarioAdmin.newInstance(pojo_usuario.getId(),"");
                AppCompatActivity activity = (AppCompatActivity)holder.itemView.getContext();

                activity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,fragUsu).addToBackStack(null).commit();

                /*
                MenuPrincipal menu = (MenuPrincipal)mContext;
                FragmentManager manager = ((AppCompatActivity)mContext).getSupportFragmentManager();
                menu.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.animator.fade_in,R.animator.fade_out).replace(R.id.frame_principal,fragUsu);
*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista_usuario.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        //Probando a usar ButterKnife
        @BindView(R.id.img_listar_admin)
         ImageView imgListarAdmin;
        @BindView(R.id.nom_usu_admin)
         TextView nomUsuAdmin;
        @BindView(R.id.tipo_usuario_admin)
         TextView tipoUsuarioAdmin;
        @BindView(R.id.apellidos_usu_admin)
         TextView apellidosUsuAdmin;
        @BindView(R.id.falta_usu_admin)
         TextView faltaUsuAdmin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

    }
    public void filtrar(ArrayList<Usuario> filtroUsuarios){
        this.lista_usuario = filtroUsuarios;
        notifyDataSetChanged();
    }
}
