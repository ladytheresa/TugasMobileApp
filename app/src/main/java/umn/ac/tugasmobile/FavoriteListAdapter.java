package umn.ac.tugasmobile;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class FavoriteListAdapter extends
        RecyclerView.Adapter<FavoriteListAdapter.FavoriteListViewHolder> {

    private List<FavoriteList> daftarFavorite;
    private Context context;

    public FavoriteListAdapter(Context context, List<FavoriteList> daftarFavorite) {
        this.daftarFavorite= daftarFavorite;
        this.context=context;
    }

    @NonNull
    @Override
    public FavoriteListViewHolder onCreateViewHolder(@NonNull
                                                          ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.fav_item_layout,parent,false);
        return new FavoriteListAdapter.FavoriteListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteListViewHolder holder,
                                 int position){

        if(daftarFavorite != null){
            FavoriteList fav = daftarFavorite.get(position);
            holder.favnama.setText(fav.getNama_lengkap());
            holder.favhp.setText(fav.getNomor_hp());
        } else {
            holder.favnama.setText("Belum ada favorite");
        }
    }


    @Override
    public int getItemCount() {
        if(daftarFavorite!=null){
            return daftarFavorite.size();
        } else {
            return 0;
        }
    }
    public FavoriteList getFavoriteAtPosition(int posisi){
        return daftarFavorite.get(posisi);
    }

    void setDaftarFavorite(List<FavoriteList> daftarFavorite){
        this.daftarFavorite = daftarFavorite;
        notifyDataSetChanged();
    }

    public class FavoriteListViewHolder extends RecyclerView.ViewHolder{
        TextView favnama;
        TextView favhp;

        public FavoriteListViewHolder(@NonNull View itemView){
            super(itemView);
            favnama = itemView.findViewById(R.id.favnama);
            favhp = itemView.findViewById(R.id.favhp);
        }
    }

}
